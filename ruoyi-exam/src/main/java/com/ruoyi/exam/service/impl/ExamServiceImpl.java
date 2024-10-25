package com.ruoyi.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.OpenType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.exam.domain.dto.*;
import com.ruoyi.exam.domain.entity.Exam;
import com.ruoyi.exam.mapper.ExamMapper;
import com.ruoyi.exam.service.IExamDepartService;
import com.ruoyi.exam.service.IExamRepoService;
import com.ruoyi.exam.service.IExamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 考试业务实现类
 *
 * @author yd
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {


    @Autowired
    private IExamRepoService examRepoService;

    @Autowired
    private IExamDepartService examDepartService;

    @Override
    public void save(ExamSaveReqDTO reqDTO) {

        // ID
        String id = reqDTO.getId();

        if (StringUtils.isBlank(id)) {
            id = IdWorker.getIdStr();
        }

        //复制参数
        Exam entity = new Exam();

        // 计算分值
        this.calcScore(reqDTO);


        // 复制基本数据
        BeanUtils.copyProperties(reqDTO, entity);
        entity.setId(id);

        // 修复状态
        if (reqDTO.getTimeLimit() != null
                && !reqDTO.getTimeLimit()
                && reqDTO.getState() != null
                && reqDTO.getState() == 2) {
            entity.setState(0);
        } else {
            entity.setState(reqDTO.getState());
        }

        // 题库组卷
        try {
            examRepoService.saveAll(id, reqDTO.getRepoList());
        } catch (DuplicateKeyException e) {
            throw new ServiceException("不能选择重复的题库！");
        }


        // 开放的部门
        if (OpenType.DEPT_OPEN.equals(reqDTO.getOpenType())) {
            examDepartService.saveAll(id, reqDTO.getDepartIds());
        }

        this.saveOrUpdate(entity);

    }

    @Override
    public ExamSaveReqDTO findDetail(String id) {
        ExamSaveReqDTO respDTO = new ExamSaveReqDTO();
        Exam exam = this.getById(id);
        BeanUtils.copyProperties(exam, respDTO);

        // 考试部门
        List<String> departIds = examDepartService.listByExam(id);
        respDTO.setDepartIds(departIds);

        // 题库
        List<ExamRepoExtDTO> repos = examRepoService.listByExam(id);
        respDTO.setRepoList(repos);

        return respDTO;
    }

    @Override
    public ExamDTO findById(String id) {
        ExamDTO respDTO = new ExamDTO();
        Exam exam = this.getById(id);
        BeanUtils.copyProperties(exam, respDTO);
        return respDTO;
    }

    @Override
    public List<ExamDTO> page(ExamDTO reqDTO) {
        return baseMapper.list(reqDTO);
    }

    @Override
    public List<ExamOnlineRespDTO> onlinePaging(ExamDTO reqDTO) {
        return baseMapper.online(reqDTO);
    }

    @Override
    public List<ExamReviewRespDTO> reviewPaging(ExamDTO reqDTO) {
        return baseMapper.reviewPaging(reqDTO);
    }


    /**
     * 计算分值
     *
     * @param reqDTO
     */
    private void calcScore(ExamSaveReqDTO reqDTO) {

        // 主观题分数
        int objScore = 0;

        // 题库组卷
        List<ExamRepoExtDTO> repoList = reqDTO.getRepoList();

        for (ExamRepoDTO item : repoList) {
            if (item.getRadioCount() != null
                    && item.getRadioCount() > 0
                    && item.getRadioScore() != null
                    && item.getRadioScore() > 0) {
                objScore += item.getRadioCount() * item.getRadioScore();
            }
            if (item.getMultiCount() != null
                    && item.getMultiCount() > 0
                    && item.getMultiScore() != null
                    && item.getMultiScore() > 0) {
                objScore += item.getMultiCount() * item.getMultiScore();
            }
            if (item.getJudgeCount() != null
                    && item.getJudgeCount() > 0
                    && item.getJudgeScore() != null
                    && item.getJudgeScore() > 0) {
                objScore += item.getJudgeCount() * item.getJudgeScore();
            }
        }


        reqDTO.setTotalScore(objScore);
    }

}
