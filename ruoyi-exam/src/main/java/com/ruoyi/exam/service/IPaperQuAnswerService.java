package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.PaperQuAnswerDTO;
import com.ruoyi.exam.domain.dto.PaperQuAnswerExtDTO;
import com.ruoyi.exam.domain.entity.PaperQuAnswer;

import java.util.List;

/**
* 试卷考题备选答案业务类
*
* @author yd
*/
public interface IPaperQuAnswerService extends IService<PaperQuAnswer> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    List<PaperQuAnswerDTO> paging(PaperQuAnswerDTO reqDTO);

    /**
     * 查找试卷试题答案列表
     * @param paperId
     * @param quId
     * @return
     */
    List<PaperQuAnswerExtDTO> listForExam(String paperId, String quId);

    /**
     * 查找答案列表，用来填充
     * @param paperId
     * @param quId
     * @return
     */
    List<PaperQuAnswer> listForFill(String paperId, String quId);
}
