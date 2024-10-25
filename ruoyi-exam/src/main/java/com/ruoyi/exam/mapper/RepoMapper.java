package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.exam.domain.dto.RepoDTO;
import com.ruoyi.exam.domain.entity.Repo;
import com.ruoyi.exam.domain.vo.RepoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题库Mapper
 *
 * @author yd
 */
@DataSource(value = DataSourceType.SLAVE)
public interface RepoMapper extends BaseMapper<Repo> {

    /**
     * 查询题库列表
     */
    List<RepoVO> list(@Param("query") RepoDTO query);

}
