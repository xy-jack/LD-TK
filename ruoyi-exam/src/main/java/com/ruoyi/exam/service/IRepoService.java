package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.RepoDTO;
import com.ruoyi.exam.domain.entity.Repo;
import com.ruoyi.exam.domain.vo.RepoVO;

import java.util.List;

/**
 * 题库业务类
 *
 * @author yd
 */
public interface IRepoService extends IService<Repo> {

    /**
     * 分页查询数据
     */
    List<RepoVO> selectRepoList(RepoDTO reqDTO);


    /**
     * 保存
     */
    void save(RepoDTO reqDTO);
}
