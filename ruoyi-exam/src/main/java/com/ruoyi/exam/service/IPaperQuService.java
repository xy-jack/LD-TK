package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.PaperQuDTO;
import com.ruoyi.exam.domain.dto.PaperQuDetailDTO;
import com.ruoyi.exam.domain.entity.PaperQu;

import java.util.List;

/**
 * 试卷考题业务类
 *
 * @author yd
 */
public interface IPaperQuService extends IService<PaperQu> {

    /**
     * 分页查询数据
     *
     * @param reqDTO
     * @return
     */
    List<PaperQuDTO> paging(PaperQuDTO reqDTO);

    /**
     * 根据试卷找出题目列表
     *
     * @param paperId
     * @return
     */
    List<PaperQuDTO> listByPaper(String paperId);

    /**
     * 查找详情
     *
     * @param paperId
     * @param quId
     * @return
     */
    PaperQu findByKey(String paperId, String quId);

    /**
     * 根据组合索引更新
     *
     * @param qu
     */
    void updateByKey(PaperQu qu);

    /**
     * 统计客观分
     *
     * @param paperId
     * @return
     */
    int sumObjective(String paperId);

    /**
     * 统计主观分
     *
     * @param paperId
     * @return
     */
    int sumSubjective(String paperId);

    /**
     * 找出全部试题列表
     *
     * @param paperId
     * @return
     */
    List<PaperQuDetailDTO> listForPaperResult(String paperId);
}
