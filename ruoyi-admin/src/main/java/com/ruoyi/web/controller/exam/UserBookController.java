package com.ruoyi.web.controller.exam;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.exam.domain.dto.UserBookDTO;
import com.ruoyi.exam.service.IUserBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 错题本控制器
 *
 * @author yd
 */
@Api(tags = {"错题本"})
@RestController
@RequestMapping("/exam/api/user/wrong-book")
public class UserBookController extends BaseController {

    @Autowired
    private IUserBookService baseService;


    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody List<String> ids) {
        //根据ID删除
        baseService.removeByIds(ids);
        return super.success();
    }

    /**
     * 分页查找
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/paging", method = {RequestMethod.POST})
    public AjaxResult paging(@RequestBody UserBookDTO reqDTO) {

        //分页查询并转换
        List<UserBookDTO> page = baseService.paging(reqDTO);

        return super.success(page);
    }

    /**
     * 查找列表，每次最多返回200条数据
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "查找列表")
    @RequestMapping(value = "/next", method = {RequestMethod.POST})
    public AjaxResult nextQu(@RequestBody UserBookDTO reqDTO) {
        //转换并返回
        String quId = baseService.findNext(reqDTO.getExamId(), reqDTO.getQuId());
        return super.success(quId);
    }
}
