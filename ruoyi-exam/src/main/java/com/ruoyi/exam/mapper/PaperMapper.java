package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.exam.domain.dto.PaperDTO;
import com.ruoyi.exam.domain.dto.PaperListReqDTO;
import com.ruoyi.exam.domain.dto.PaperListRespDTO;
import com.ruoyi.exam.domain.entity.Paper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 试卷Mapper
*
* @author yd
*/
public interface PaperMapper extends BaseMapper<Paper> {

    /**
     * 查找试卷分页
     * @param page
     * @param query
     * @return
     */
    List<PaperListRespDTO> paging(@Param("query") PaperListReqDTO query);


    /**
     * 试卷列表响应类
     * @param query
     * @return
     */
    List<PaperListRespDTO> list(@Param("query") PaperDTO query);
}
