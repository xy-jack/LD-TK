package com.ruoyi.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.BeanMapper;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.exam.domain.dto.UserBookDTO;
import com.ruoyi.exam.domain.entity.Qu;
import com.ruoyi.exam.domain.entity.UserBook;
import com.ruoyi.exam.mapper.UserBookMapper;
import com.ruoyi.exam.service.IQuService;
import com.ruoyi.exam.service.IUserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 语言设置 服务实现类
 *
 * @author yd
 */
@Service
public class UserBookServiceImpl extends ServiceImpl<UserBookMapper, UserBook> implements IUserBookService {

    @Autowired
    private IQuService quService;

    @Override
    public List<UserBookDTO> paging(UserBookDTO reqDTO) {
        //查询条件
        QueryWrapper<UserBook> wrapper = new QueryWrapper<>();
        // 查找用户的错题
        wrapper.lambda().eq(UserBook::getUserId, SecurityUtils.getLoginUser().getUserId());

        if (!StringUtils.isEmpty(reqDTO.getTitle())) {
            wrapper.lambda().like(UserBook::getTitle, reqDTO.getTitle());
        }

        if (!StringUtils.isEmpty(reqDTO.getExamId())) {
            wrapper.lambda().eq(UserBook::getExamId, reqDTO.getExamId());
        }

        //开始分页
        PageUtils.startPage();
        //获得数据
        List<UserBook> list = this.list(wrapper);
        //转换结果
        return BeanMapper.mapList(list, UserBookDTO.class);
    }

    @Override
    public void addBook(String examId, String quId) {

        QueryWrapper<UserBook> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(UserBook::getUserId, SecurityUtils.getLoginUser().getUserId())
                .eq(UserBook::getExamId, examId)
                .eq(UserBook::getQuId, quId);

        //查找已有的错题信息
        UserBook book = this.getOne(wrapper, false);


        // 问题
        Qu qu = quService.getById(quId);

        if (book == null) {
            book = new UserBook();
            book.setExamId(examId);
            book.setUserId(SecurityUtils.getLoginUser().getUserId());
            book.setTitle(qu.getContent());
            book.setQuId(quId);
            book.setWrongCount(1);
            Integer maxSort = this.findMaxSort(examId, SecurityUtils.getLoginUser().getUserId());
            book.setSort(maxSort + 1);

            this.save(book);
        } else {
            book.setWrongCount(book.getWrongCount() + 1);
            this.updateById(book);
        }
    }

    @Override
    public String findNext(String examId, String quId) {
        Integer sort = 999999;

        if (!StringUtils.isEmpty(quId)) {
            QueryWrapper<UserBook> wrapper = new QueryWrapper<>();
            wrapper.lambda()
                    .eq(UserBook::getUserId, SecurityUtils.getLoginUser().getUserId())
                    .eq(UserBook::getExamId, examId)
                    .eq(UserBook::getQuId, quId);
            wrapper.last(" ORDER BY `sort` DESC");

            UserBook last = this.getOne(wrapper, false);
            if (last != null) {
                sort = last.getSort();
            }
        }

        QueryWrapper<UserBook> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(UserBook::getUserId, SecurityUtils.getLoginUser().getUserId())
                .eq(UserBook::getExamId, examId)
                .lt(UserBook::getSort, sort);
        wrapper.last(" ORDER BY `sort` DESC");

        UserBook next = this.getOne(wrapper, false);
        if (next != null) {
            return next.getQuId();
        }

        return null;
    }

    /**
     * 查找最大的排序
     *
     * @param userId
     * @return
     */
    private Integer findMaxSort(String examId, Long userId) {

        QueryWrapper<UserBook> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(UserBook::getExamId, examId)
                .eq(UserBook::getUserId, userId);
        wrapper.last(" ORDER BY `sort` DESC");

        UserBook book = this.getOne(wrapper, false);
        if (book == null) {
            return 0;
        }
        return book.getSort();
    }


}
