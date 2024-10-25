package com.ruoyi.web.controller.exam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.exam.domain.dto.*;
import com.ruoyi.exam.domain.entity.Exam;
import com.ruoyi.exam.service.IExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考试控制器
 *
 * @author yd
 */
@Api(tags = {"考试"})
@RestController
@RequestMapping("/exam/api/exam/exam")
public class ExamController extends BaseController {

    @Autowired
    private IExamService examService;

    /**
     * 分页查找
     */
    @ApiOperation(value = "分页查找")
    @PostMapping(value = "/page")
    public TableDataInfo page(ExamDTO reqDTO) {
        startPage();
        //分页查询并转换
        List<ExamDTO> list = examService.page(reqDTO);

        return getDataTable(list);
    }

    /**
     * 添加或修改
     */
    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public AjaxResult save(@RequestBody ExamSaveReqDTO reqDTO) {
        //复制参数
        examService.save(reqDTO);
        return super.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public AjaxResult edit(@RequestBody List<String> ids) {
        //根据ID删除
        examService.removeByIds(ids);
        return super.success();
    }

    /**
     * 查找详情
     */
    @ApiOperation(value = "查找详情")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult find(@PathVariable("id") String id) {
        ExamSaveReqDTO dto = examService.findDetail(id);
        return super.success(dto);
    }

    /**
     * 查找详情
     */
    @ApiOperation(value = "查找详情")
    @RequestMapping(value = "/state", method = {RequestMethod.POST})
    public AjaxResult state(@RequestBody BaseStateReqDTO reqDTO) {

        QueryWrapper<Exam> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(Exam::getId, reqDTO.getIds());
        Exam exam = new Exam();
        exam.setState(reqDTO.getState());
        exam.setUpdateTime(LocalDateTime.now());

        examService.update(exam, wrapper);
        return super.success();
    }


    /**
     * 分页查找
     */
    @ApiOperation(value = "考试视角")
    @PostMapping(value = "/online-paging")
    public TableDataInfo myPaging(@RequestBody ExamDTO reqDTO) {
        startPage();
        //分页查询并转换
        List<ExamOnlineRespDTO> list = examService.onlinePaging(reqDTO);
        return getDataTable(list);
    }

    /**
     * 分页查找
     */
    @ApiOperation(value = "待阅试卷")
    @PostMapping(value = "/review-paging")
    public TableDataInfo reviewPaging(@RequestBody ExamDTO reqDTO) {
        startPage();
        //分页查询并转换
        List<ExamReviewRespDTO> list = examService.reviewPaging(reqDTO);

        return getDataTable(list);
    }


}
