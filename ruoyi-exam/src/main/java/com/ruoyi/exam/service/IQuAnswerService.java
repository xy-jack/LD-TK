package com.ruoyi.exam.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.QuAnswerDTO;
import com.ruoyi.exam.domain.entity.QuAnswer;

import java.util.List;

/**
* 候选答案业务类
*
* @author yd
*/
public interface IQuAnswerService extends IService<QuAnswer> {

    /**
    * 根据条件查询
    */
    List<QuAnswerDTO> selectByCondition(QuAnswerDTO reqDTO);

    /**
     * 根据题目ID查询答案并随机
     */
    List<QuAnswer> listAnswerByRandom(String quId);

    /**
     * 根据问题查找答案
     */
    List<QuAnswerDTO> listByQu(String quId);

    /**
     * 保存试题
     */
    void saveAll(String quId, List<QuAnswerDTO> list);
}
