package com.ruoyi.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.JobGroup;
import com.ruoyi.common.constant.JobPrefix;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.ApiError;
import com.ruoyi.common.enums.ExamState;
import com.ruoyi.common.enums.PaperState;
import com.ruoyi.common.enums.QuType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.BeanMapper;
import com.ruoyi.common.utils.CronUtils;
import com.ruoyi.exam.domain.dto.*;
import com.ruoyi.exam.domain.entity.*;
import com.ruoyi.exam.job.BreakExamJob;
import com.ruoyi.exam.mapper.PaperMapper;
import com.ruoyi.exam.service.*;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * 语言设置 服务实现类
 *
 * @author yd
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {


    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IExamService examService;

    @Autowired
    private IQuService quService;

    @Autowired
    private IQuAnswerService quAnswerService;

    @Autowired
    private IPaperService paperService;

    @Autowired
    private IPaperQuService paperQuService;

    @Autowired
    private IPaperQuAnswerService paperQuAnswerService;

    @Autowired
    private IUserBookService userBookService;

    @Autowired
    private IExamRepoService examRepoService;

    @Autowired
    private IUserExamService userExamService;

    @Autowired
    private ISysJobService jobService;

    /**
     * 展示的选项，ABC这样
     */
    private static List<String> ABC = Arrays.asList(new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"
            , "Y", "Z"
    });


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createPaper(Long userId, String examId) {

        // 校验是否有正在考试的试卷
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Paper::getUserId, userId)
                .eq(Paper::getState, PaperState.ING);

        long exists = this.count(wrapper);
        if (exists > 0) {
            throw new ServiceException(ApiError.ERROR_20010002.msg);
        }

        // 查找考试
        ExamDTO exam = examService.findById(examId);

        if (exam == null) {
            throw new ServiceException("考试不存在！");
        }

        if (!ExamState.ENABLE.equals(exam.getState())) {
            throw new ServiceException("考试状态不正确！");
        }

        // 考试题目列表
        List<PaperQu> quList = this.generateByRepo(examId);

        if (CollectionUtils.isEmpty(quList)) {
            throw new ServiceException("规则不正确，无对应的考题！");
        }

        //保存试卷内容
        Paper paper = this.savePaper(userId, exam, quList);

        // 强制交卷任务
        String jobName = JobPrefix.BREAK_EXAM + paper.getId();
        jobService.addCronJob(BreakExamJob.class, jobName, CronUtils.dateToCron(paper.getLimitTime()), paper.getId());

        return paper.getId();
    }

    @Override
    public ExamDetailRespDTO paperDetail(String paperId) {


        ExamDetailRespDTO respDTO = new ExamDetailRespDTO();

        // 试题基本信息
        Paper paper = paperService.getById(paperId);
        BeanUtils.copyProperties(paper, respDTO);

        // 查找题目列表
        List<PaperQuDTO> list = paperQuService.listByPaper(paperId);

        List<PaperQuDTO> radioList = new ArrayList<>();
        List<PaperQuDTO> multiList = new ArrayList<>();
        List<PaperQuDTO> judgeList = new ArrayList<>();
        for (PaperQuDTO item : list) {
            if (QuType.RADIO.equals(item.getQuType())) {
                radioList.add(item);
            }
            if (QuType.MULTI.equals(item.getQuType())) {
                multiList.add(item);
            }
            if (QuType.JUDGE.equals(item.getQuType())) {
                judgeList.add(item);
            }
        }

        respDTO.setRadioList(radioList);
        respDTO.setMultiList(multiList);
        respDTO.setJudgeList(judgeList);
        return respDTO;
    }

    @Override
    public ExamResultRespDTO paperResult(String paperId) {

        ExamResultRespDTO respDTO = new ExamResultRespDTO();

        // 试题基本信息
        Paper paper = paperService.getById(paperId);
        BeanUtils.copyProperties(paper, respDTO);

        List<PaperQuDetailDTO> quList = paperQuService.listForPaperResult(paperId);
        respDTO.setQuList(quList);

        return respDTO;
    }

    @Override
    public PaperQuDetailDTO findQuDetail(String paperId, String quId) {

        PaperQuDetailDTO respDTO = new PaperQuDetailDTO();
        // 问题
        Qu qu = quService.getById(quId);

        // 基本信息
        PaperQu paperQu = paperQuService.findByKey(paperId, quId);
        BeanUtils.copyProperties(paperQu, respDTO);
        respDTO.setContent(qu.getContent());
        respDTO.setImage(qu.getImage());

        // 答案列表
        List<PaperQuAnswerExtDTO> list = paperQuAnswerService.listForExam(paperId, quId);
        respDTO.setAnswerList(list);

        return respDTO;
    }


    /**
     * 题库组题方式产生题目列表
     *
     * @param examId
     * @return
     */
    private List<PaperQu> generateByRepo(String examId) {

        // 查找规则指定的题库
        List<ExamRepoExtDTO> list = examRepoService.listByExam(examId);

        //最终的题目列表
        List<PaperQu> quList = new ArrayList<>();

        //排除ID，避免题目重复
        List<String> excludes = new ArrayList<>();
        excludes.add("none");

        if (!CollectionUtils.isEmpty(list)) {
            for (ExamRepoExtDTO item : list) {

                // 单选题
                if (item.getRadioCount() > 0) {
                    List<Qu> radioList = quService.listByRandom(item.getRepoId(), QuType.RADIO, excludes, item.getRadioCount());
                    for (Qu qu : radioList) {
                        PaperQu paperQu = this.processPaperQu(item, qu);
                        quList.add(paperQu);
                        excludes.add(qu.getId());
                    }
                }

                //多选题
                if (item.getMultiCount() > 0) {
                    List<Qu> multiList = quService.listByRandom(item.getRepoId(), QuType.MULTI, excludes,
                            item.getMultiCount());
                    for (Qu qu : multiList) {
                        PaperQu paperQu = this.processPaperQu(item, qu);
                        quList.add(paperQu);
                        excludes.add(qu.getId());
                    }
                }

                // 判断题
                if (item.getJudgeCount() > 0) {
                    List<Qu> judgeList = quService.listByRandom(item.getRepoId(), QuType.JUDGE, excludes,
                            item.getJudgeCount());
                    for (Qu qu : judgeList) {
                        PaperQu paperQu = this.processPaperQu(item, qu);
                        quList.add(paperQu);
                        excludes.add(qu.getId());
                    }
                }
            }
        }
        return quList;
    }


    /**
     * 填充试题题目信息
     *
     * @param repo
     * @param qu
     * @return
     */
    private PaperQu processPaperQu(ExamRepoDTO repo, Qu qu) {

        //保存试题信息
        PaperQu paperQu = new PaperQu();
        paperQu.setQuId(qu.getId());
        paperQu.setAnswered(false);
        paperQu.setIsRight(false);
        paperQu.setQuType(qu.getQuType());

        if (QuType.RADIO.equals(qu.getQuType())) {
            paperQu.setScore(repo.getRadioScore());
            paperQu.setActualScore(repo.getRadioScore());
        }

        if (QuType.MULTI.equals(qu.getQuType())) {
            paperQu.setScore(repo.getMultiScore());
            paperQu.setActualScore(repo.getMultiScore());
        }

        if (QuType.JUDGE.equals(qu.getQuType())) {
            paperQu.setScore(repo.getJudgeScore());
            paperQu.setActualScore(repo.getJudgeScore());
        }

        return paperQu;
    }


    /**
     * 保存试卷
     *
     * @param userId
     * @param exam
     * @param quList
     * @return
     */
    private Paper savePaper(Long userId, ExamDTO exam, List<PaperQu> quList) {
        // 查找用户
        SysUser user = sysUserService.selectUserById(userId);

        //保存试卷基本信息
        Paper paper = new Paper();
        paper.setDepartId(String.valueOf(user.getDeptId()));
        paper.setExamId(exam.getId());
        paper.setTitle(exam.getTitle());
        paper.setTotalScore(exam.getTotalScore());
        paper.setTotalTime(exam.getTotalTime());
        paper.setUserScore(0);
        paper.setUserId(userId);
        paper.setCreateTime(LocalDateTime.now());
        paper.setUpdateTime(LocalDateTime.now());
        paper.setQualifyScore(exam.getQualifyScore());
        paper.setState(PaperState.ING);
        paper.setHasSaq(false);

        // 截止时间
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());
        cl.add(Calendar.MINUTE, exam.getTotalTime());
        paper.setLimitTime(cl.getTime());

        paperService.save(paper);

        if (!CollectionUtils.isEmpty(quList)) {
            this.savePaperQu(paper.getId(), quList);
        }

        return paper;
    }


    /**
     * 保存试卷试题列表
     *
     * @param paperId
     * @param quList
     */
    private void savePaperQu(String paperId, List<PaperQu> quList) {

        List<PaperQu> batchQuList = new ArrayList<>();
        List<PaperQuAnswer> batchAnswerList = new ArrayList<>();

        int sort = 0;
        for (PaperQu item : quList) {

            item.setPaperId(paperId);
            item.setSort(sort);
            item.setId(IdWorker.getIdStr());

            //回答列表
            List<QuAnswer> answerList = quAnswerService.listAnswerByRandom(item.getQuId());

            if (!CollectionUtils.isEmpty(answerList)) {

                int ii = 0;
                for (QuAnswer answer : answerList) {
                    PaperQuAnswer paperQuAnswer = new PaperQuAnswer();
                    paperQuAnswer.setId(UUID.randomUUID().toString());
                    paperQuAnswer.setPaperId(paperId);
                    paperQuAnswer.setQuId(answer.getQuId());
                    paperQuAnswer.setAnswerId(answer.getId());
                    paperQuAnswer.setChecked(false);
                    paperQuAnswer.setSort(ii);
                    paperQuAnswer.setAbc(ABC.get(ii));
                    paperQuAnswer.setIsRight(answer.getIsRight());
                    ii++;
                    batchAnswerList.add(paperQuAnswer);
                }
            }

            batchQuList.add(item);
            sort++;
        }

        //添加问题
        paperQuService.saveBatch(batchQuList);

        //批量添加问题答案
        paperQuAnswerService.saveBatch(batchAnswerList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void fillAnswer(PaperAnswerDTO reqDTO) {


        // 未作答
        if (CollectionUtils.isEmpty(reqDTO.getAnswers())
                && StringUtils.isBlank(reqDTO.getAnswer())) {
            return;
        }

        //查找答案列表
        List<PaperQuAnswer> list = paperQuAnswerService.listForFill(reqDTO.getPaperId(), reqDTO.getQuId());

        //是否正确
        boolean right = true;

        //更新正确答案
        for (PaperQuAnswer item : list) {

            if (reqDTO.getAnswers().contains(item.getId())) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }

            //有一个对不上就是错的
            if (item.getIsRight() != null && !item.getIsRight().equals(item.getChecked())) {
                right = false;
            }
            paperQuAnswerService.updateById(item);
        }

        //修改为已回答
        PaperQu qu = new PaperQu();
        qu.setQuId(reqDTO.getQuId());
        qu.setPaperId(reqDTO.getPaperId());
        qu.setIsRight(right);
        qu.setAnswer(reqDTO.getAnswer());
        qu.setAnswered(true);

        paperQuService.updateByKey(qu);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handExam(String paperId) {

        //获取试卷信息
        Paper paper = paperService.getById(paperId);

        //如果不是正常的，抛出异常
        if (!PaperState.ING.equals(paper.getState())) {
            throw new ServiceException("试卷状态不正确！");
        }

        // 客观分
        int objScore = paperQuService.sumObjective(paperId);
        paper.setObjScore(objScore);
        paper.setUserScore(objScore);

        // 主观分，因为要阅卷，所以给0
        paper.setSubjScore(0);

        // 待阅卷
        if (paper.getHasSaq()) {
            paper.setState(PaperState.WAIT_OPT);
        } else {

            // 同步保存考试成绩
            userExamService.joinResult(paper.getUserId(), paper.getExamId(), objScore, objScore >= paper.getQualifyScore());

            paper.setState(PaperState.FINISHED);
        }
        paper.setUpdateTime(LocalDateTime.now());

        //计算考试时长
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());

        long milliSecond = paper.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        int userTime = (int) ((System.currentTimeMillis() - milliSecond) / 1000 / 60);
        if (userTime == 0) {
            userTime = 1;
        }
        paper.setUserTime(userTime);

        //更新试卷
        paperService.updateById(paper);


        // 终止定时任务
        String name = JobPrefix.BREAK_EXAM + paperId;
        jobService.deleteJobByName(name, JobGroup.SYSTEM);

        //把打错的问题加入错题本
        List<PaperQuDTO> list = paperQuService.listByPaper(paperId);
        for (PaperQuDTO qu : list) {
            // 主观题和对的都不加入错题库
            if (qu.getIsRight()) {
                continue;
            }
            //加入错题本
            new Thread(() -> userBookService.addBook(paper.getExamId(), qu.getQuId())).run();
        }
    }

    @Override
    public List<PaperListRespDTO> paging(PaperListReqDTO reqDTO) {
        return baseMapper.paging(reqDTO);
    }

    @Override
    public PaperDTO checkProcess(Long userId) {

        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Paper::getUserId, userId)
                .eq(Paper::getState, PaperState.ING);

        Paper paper = this.getOne(wrapper, false);
        if (paper != null) {
            PaperDTO dto = new PaperDTO();
            BeanUtils.copyProperties(paper, dto);

            return dto;
        }
        return null;
    }
}
