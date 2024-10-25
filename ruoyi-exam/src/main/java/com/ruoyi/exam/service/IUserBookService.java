package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.UserBookDTO;
import com.ruoyi.exam.domain.entity.UserBook;

import java.util.List;

/**
* 错题本业务类
*
* @author yd
*/
public interface IUserBookService extends IService<UserBook> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    List<UserBookDTO> paging(UserBookDTO reqDTO);

    /**
     * 加入错题本
     * @param quId
     * @param examId
     */
    void addBook(String examId, String quId);

    /**
     * 查找第一个错题
     * @param quId
     * @param examId
     * @return
     */
    String findNext(String examId, String quId);
}
