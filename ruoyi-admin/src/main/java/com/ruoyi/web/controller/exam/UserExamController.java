package com.ruoyi.web.controller.exam;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.exam.domain.dto.UserExamReqDTO;
import com.ruoyi.exam.domain.dto.UserExamRespDTO;
import com.ruoyi.exam.service.IUserExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 考试记录控制器
*/
@Api(tags={"考试记录"})
@RestController
@RequestMapping("/exam/api/user/exam")
public class UserExamController extends BaseController {

    @Autowired
    private IUserExamService baseService;


    /**
     * 分页查找
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/paging", method = { RequestMethod.POST})
    public TableDataInfo paging(@RequestBody UserExamReqDTO reqDTO) {

        //分页查询并转换
        List<UserExamRespDTO> list = baseService.paging(reqDTO);

        return getDataTable(list);
    }


    /**
    * 分页查找
    * @param reqDTO
    * @return
    */
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/my-paging", method = { RequestMethod.POST})
    public TableDataInfo myPaging(@RequestBody UserExamReqDTO reqDTO) {

        //分页查询并转换
        List<UserExamRespDTO> list = baseService.myPaging(reqDTO);

        return getDataTable(list);
    }
}
