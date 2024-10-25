package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.exam.domain.dto.QuDTO;
import com.ruoyi.exam.domain.dto.QuQueryReqDTO;
import com.ruoyi.exam.domain.dto.excel.QuExportDTO;
import com.ruoyi.exam.domain.entity.Qu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 问题题目Mapper
*
* @author yd
*/
public interface QuMapper extends BaseMapper<Qu> {



    /**
     * 随机抽取题库的数据
     *
     * @param excludes 要排除的ID列表
     */
    List<Qu> listByRandom(@Param("repoId") String repoId,
                          @Param("quType") Integer quType,
                          @Param("excludes") List<String> excludes,
                          @Param("size") Integer size);

    /**
     * 查找导出列表
     */
    List<QuExportDTO> listForExport(@Param("query") QuQueryReqDTO query);

    /**
     * 分页查找
     */
    List<QuDTO> selectQuList(@Param("query") QuQueryReqDTO query);


}
