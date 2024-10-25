package com.ruoyi.web.controller.exam;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.exam.domain.dto.QuRepoBatchReqDTO;
import com.ruoyi.exam.domain.dto.RepoDTO;
import com.ruoyi.exam.domain.entity.Repo;
import com.ruoyi.exam.domain.vo.RepoVO;
import com.ruoyi.exam.service.IQuRepoService;
import com.ruoyi.exam.service.IRepoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 题库控制器
 *
 * @author yd
 */
@Api(tags = {"题库"})
@RestController
@RequestMapping("/exam/api/repo")
public class RepoController extends BaseController {

    @Autowired
    private IRepoService repoService;

    @Autowired
    private IQuRepoService quRepoService;

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/page")
    public TableDataInfo page(@RequestBody RepoDTO reqDTO) {
        List<RepoVO> list = repoService.selectRepoList(reqDTO);
        return getDataTable(list);
    }

    @ApiOperation(value = "添加或修改")
    @PostMapping(value = "/save")
    public AjaxResult save(@RequestBody RepoDTO reqDTO) {
        repoService.save(reqDTO);
        return super.success();
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "/delete")
    public AjaxResult edit(@RequestBody List<String> ids) {
        //根据ID删除
        repoService.removeByIds(ids);
        return super.success();
    }

    @ApiOperation(value = "查找详情")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult find(@PathVariable("id") Long id) {
        Repo entity = repoService.getById(id);
        RepoDTO dto = new RepoDTO();
        BeanUtils.copyProperties(entity, dto);
        return super.success(dto);
    }

    @ApiOperation(value = "批量操作", notes = "批量加入或从题库移除")
    @PostMapping(value = "/batch-action")
    public AjaxResult batchAction(@RequestBody QuRepoBatchReqDTO reqDTO) {

        //分页查询并转换
        quRepoService.batchAction(reqDTO);
        return super.success();
    }
}
