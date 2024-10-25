package com.ruoyi.web.controller.exam;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.exam.domain.dto.*;
import com.ruoyi.exam.domain.entity.Paper;
import com.ruoyi.exam.service.IPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 试卷控制器
 *
 * @author yd
 */
@Api(tags = {"试卷"})
@RestController
@RequestMapping("/exam/api/paper/paper")
public class PaperController extends BaseController {

    @Autowired
    private IPaperService baseService;

    /**
     * 添加或修改
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public AjaxResult save(@RequestBody PaperDTO reqDTO) {
        //复制参数
        Paper entity = new Paper();
        BeanUtils.copyProperties(reqDTO, entity);
        baseService.saveOrUpdate(entity);
        return super.success(entity.getId());
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/delete")
    public AjaxResult edit(@RequestBody List<String> ids) {
        //根据ID删除
        baseService.removeByIds(ids);
        return super.success();
    }

    /**
     * 查找详情
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "查找详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST})
    public AjaxResult find(@RequestBody BaseIdReqDTO reqDTO) {
        Paper entity = baseService.getById(reqDTO.getId());
        PaperDTO dto = new PaperDTO();
        BeanUtils.copyProperties(entity, dto);
        return super.success(dto);
    }


    /**
     * 分页查找
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "分页查找")
    @PostMapping(value = "/paging")
    public TableDataInfo paging(@RequestBody PaperListReqDTO reqDTO) {

        //分页查询并转换
        List<PaperListRespDTO> list = baseService.paging(reqDTO);

        return getDataTable(list);
    }


    /**
     * 创建试卷
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "创建试卷")
    @PostMapping("/create-paper")
    public AjaxResult save(@RequestBody PaperCreateReqDTO reqDTO) {
        //复制参数
        String paperId = baseService.createPaper(SecurityUtils.getLoginUser().getUserId(), reqDTO.getExamId());
        return super.success(paperId);
    }

    /**
     * 批量删除
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "试卷详情")
    @PostMapping("/paper-detail")
    public AjaxResult paperDetail(@RequestBody BaseIdReqDTO reqDTO) {
        //根据ID删除
        ExamDetailRespDTO respDTO = baseService.paperDetail(reqDTO.getId());
        return super.success(respDTO);
    }

    /**
     * 批量删除
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "试题详情")
    @PostMapping("/qu-detail")
    public AjaxResult quDetail(@RequestBody PaperQuQueryDTO reqDTO) {
        //根据ID删除
        PaperQuDetailDTO respDTO = baseService.findQuDetail(reqDTO.getPaperId(), reqDTO.getQuId());
        return super.success(respDTO);
    }

    /**
     * 填充答案
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "填充答案")
    @PostMapping("/fill-answer")
    public AjaxResult fillAnswer(@RequestBody PaperAnswerDTO reqDTO) {
        //根据ID删除
        baseService.fillAnswer(reqDTO);
        return super.success();
    }


    /**
     * 交卷操作
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "交卷操作")
    @PostMapping("/hand-exam")
    public AjaxResult handleExam(@RequestBody BaseIdReqDTO reqDTO) {
        //根据ID删除
        baseService.handExam(reqDTO.getId());
        return super.success();
    }


    /**
     * 批量删除
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "试卷详情")
    @PostMapping("/paper-result")
    public AjaxResult paperResult(@RequestBody BaseIdReqDTO reqDTO) {
        //根据ID删除
        ExamResultRespDTO respDTO = baseService.paperResult(reqDTO.getId());
        return super.success(respDTO);
    }


    /**
     * 检测用户有没有中断的考试
     *
     * @return
     */
    @ApiOperation(value = "检测进行中的考试")
    @PostMapping("/check-process")
    public AjaxResult checkProcess() {
        //复制参数
        PaperDTO dto = baseService.checkProcess(SecurityUtils.getLoginUser().getUserId());
        return super.success(dto);
    }
}
