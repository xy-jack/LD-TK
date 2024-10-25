package com.ruoyi.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.exam.domain.dto.RepoDTO;
import com.ruoyi.exam.domain.entity.Repo;
import com.ruoyi.exam.domain.vo.RepoVO;
import com.ruoyi.exam.mapper.RepoMapper;
import com.ruoyi.exam.service.IRepoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 语言设置 服务实现类
 *
 * @author ud
 */
@DataSource(value = DataSourceType.SLAVE)
@Service
public class RepoServiceImpl extends ServiceImpl<RepoMapper, Repo> implements IRepoService {

    @Override
    public List<RepoVO> selectRepoList(RepoDTO reqDTO) {
        PageUtils.startPage();
        return baseMapper.list(reqDTO);
    }

    @Override
    public void save(RepoDTO reqDTO) {
        //复制参数
        Repo entity = new Repo();
        BeanUtils.copyProperties(reqDTO, entity);
        this.saveOrUpdate(entity);
    }
}
