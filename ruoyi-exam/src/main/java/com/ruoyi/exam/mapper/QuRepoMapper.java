package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.exam.domain.entity.QuRepo;

/**
 * 试题题库Mapper
 *
 * @author yd
 */
@DataSource(value = DataSourceType.SLAVE)
public interface QuRepoMapper extends BaseMapper<QuRepo> {

}
