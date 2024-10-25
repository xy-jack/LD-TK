package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.QuRepoBatchReqDTO;
import com.ruoyi.exam.domain.dto.QuRepoDTO;
import com.ruoyi.exam.domain.entity.QuRepo;


import java.util.List;

/**
* 试题题库业务类
*
* @author yd
*/
public interface IQuRepoService extends IService<QuRepo> {

    /**
    * 分页查询数据
    */
    List<QuRepoDTO> list(QuRepoDTO reqDTO);

    /**
     * 保存全部列表
     */
    void saveAll(String quId, Integer quType, List<String> ids);

    /**
     * 根据问题查找题库
     */
    List<String> listByQu(String quId);

    /**
     * 根据题库查找题目ID列表
     */
    List<String> listByRepo(String repoId, Integer quType, boolean rand);

    /**
     * 批量操作
     */
    void batchAction(QuRepoBatchReqDTO reqDTO);

}
