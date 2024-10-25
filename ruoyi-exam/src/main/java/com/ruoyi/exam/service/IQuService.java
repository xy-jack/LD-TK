package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.QuDTO;
import com.ruoyi.exam.domain.dto.QuDetailDTO;
import com.ruoyi.exam.domain.dto.QuQueryReqDTO;
import com.ruoyi.exam.domain.dto.excel.QuExportDTO;
import com.ruoyi.exam.domain.entity.Qu;

import java.util.List;

/**
* 问题题目业务类
*
* @author yd
*/
public interface IQuService extends IService<Qu> {

    /**
     * 分页查询数据
     */
    List<QuDTO> list(QuQueryReqDTO reqDTO);

    /**
     * 删除试题
     */
    void delete(List<String> ids);

    /**
     * 随机抽取题库的数据
     *
     * @param excludes 要排除的ID列表
     */
    List<Qu> listByRandom(String repoId,
                          Integer quType,
                          List<String> excludes,
                          Integer size);

    /**
     * 问题详情
     */
    QuDetailDTO detail(String id);

    /**
     * 保存试题
     */
    void save(QuDetailDTO reqDTO);

    /**
     * 查找导出列表
     */
    List<QuExportDTO> listForExport(QuQueryReqDTO query);

    /**
     * 导入Excel
     */
    int importExcel(List<QuExportDTO> dtoList);
}
