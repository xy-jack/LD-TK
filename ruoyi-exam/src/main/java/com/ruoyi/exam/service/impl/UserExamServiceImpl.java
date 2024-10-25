package com.ruoyi.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.exam.domain.dto.UserExamReqDTO;
import com.ruoyi.exam.domain.dto.UserExamRespDTO;
import com.ruoyi.exam.domain.entity.UserExam;
import com.ruoyi.exam.mapper.UserExamMapper;
import com.ruoyi.exam.service.IUserExamService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
* 考试记录业务实现类
*
* @author 聪明笨狗
* @since 2020-09-21 15:13
*/
@Service
public class UserExamServiceImpl extends ServiceImpl<UserExamMapper, UserExam> implements IUserExamService {

    @Override
    public List<UserExamRespDTO> paging(UserExamReqDTO reqDTO) {
        //转换结果
        List<UserExamRespDTO> pageData = baseMapper.paging(reqDTO);
        return pageData;
     }

    @Override
    public List<UserExamRespDTO> myPaging(UserExamReqDTO reqDTO) {
        reqDTO.setUserId(SecurityUtils.getLoginUser().getUserId());
        return baseMapper.paging(reqDTO);
    }

    @Override
    public void joinResult(Long userId, String examId, Integer score, boolean passed) {

        //查询条件
        QueryWrapper<UserExam> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserExam::getUserId, userId)
                .eq(UserExam::getExamId, examId);

        UserExam record = this.getOne(wrapper, false);
        if(record == null){
            record = new UserExam();
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
            record.setUserId(userId);
            record.setExamId(examId);
            record.setMaxScore(score);
            record.setPassed(passed);
            this.save(record);
            return;
        }

        // 修复低分数不加入统计问题
        record.setTryCount(record.getTryCount()+1);
        record.setUpdateTime(LocalDateTime.now());

        if(record.getMaxScore() < score){
            record.setMaxScore(score);
            record.setPassed(passed);
        }

        this.updateById(record);


    }
}
