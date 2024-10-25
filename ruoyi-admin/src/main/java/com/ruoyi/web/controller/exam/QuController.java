package com.ruoyi.web.controller.exam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.exam.domain.dto.QuDTO;
import com.ruoyi.exam.domain.dto.QuDetailDTO;
import com.ruoyi.exam.domain.dto.QuQueryReqDTO;
import com.ruoyi.exam.domain.dto.excel.QuExportDTO;
import com.ruoyi.exam.domain.entity.Qu;
import com.ruoyi.exam.service.IQuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 问题题目控制器
 *
 * @author yd
 */
@Api(tags = {"问题题目"})
@RestController
@RequestMapping("/exam/api/qu")
public class QuController extends BaseController {

    @Autowired
    private IQuService quService;

    /**
     * 分页查找
     */
    @ApiOperation(value = "分页查找")
    @PostMapping(value = "/page")
    public TableDataInfo page(@RequestBody QuQueryReqDTO reqDTO) {
        //分页查询并转换
        List<QuDTO> list = quService.list(reqDTO);
        return getDataTable(list);
    }

    /**
     * 添加或修改
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "添加或修改")
    @PostMapping(value = "/save")
    public AjaxResult save(@RequestBody QuDetailDTO reqDTO) {
        quService.save(reqDTO);
        return super.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "/delete")
    public AjaxResult edit(@RequestBody List<String> ids) {
        //根据ID删除
        quService.delete(ids);
        return super.success();
    }

    /**
     * 查找详情
     */
    @ApiOperation(value = "查找详情")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult detail(@PathVariable("id") String id) {
        QuDetailDTO dto = quService.detail(id);
        return super.success(dto);
    }

    /**
     * 查找列表，每次最多返回200条数据
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "查找列表")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public TableDataInfo list(@RequestBody QuDTO reqDTO) {

        //分页查询并转换
        QueryWrapper<Qu> wrapper = new QueryWrapper<>();

        //转换并返回
        List<Qu> list = quService.list(wrapper);

        //转换数据
        //List<QuDTO> dtoList = BeanMapper.mapList(list, QuDTO.class);

        return getDataTable(list);
    }


    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequestMapping(value = "/export")
    public AjaxResult exportFile(HttpServletResponse response, @RequestBody QuQueryReqDTO reqDTO) {


        // 导出文件名
        String fileName = "导出的试题-" + System.currentTimeMillis() + ".xlsx";

        try {

            int no = 0;
            String quId = "";
            List<QuExportDTO> list = quService.listForExport(reqDTO);
            for (QuExportDTO item : list) {
                if (!quId.equals(item.getQId())) {
                    quId = item.getQId();
                    no += 1;
                } else {
                    item.setQuType("0");
                    item.setQContent("");
                    item.setQAnalysis("");
                    item.setRepoList(null);
                    item.setQImage("");
                    item.setQVideo("");
                }
                item.setNo(String.valueOf(no));
            }
            //new ExportExcel("试题", QuExportDTO.class).setDataList(list).write(response, fileName).dispose();
            return super.success();
        } catch (Exception e) {
            //return failure(e.getMessage());
            return null;
        }
    }

    /**
     * 导入Excel
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "import")
    public AjaxResult importFile(@RequestParam("file") MultipartFile file) {
        /*try {

            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<QuExportDTO> list = ei.getDataList(QuExportDTO.class);

            // 校验数据
            this.checkExcel(list);

            // 导入数据条数
            quService.importExcel(list);

            // 导入成功
            return super.success();

        } catch (IOException e) {

        } catch (InvalidFormatException e) {

        } catch (IllegalAccessException e) {

        } catch (InstantiationException e) {

        }

        return super.failure();*/

        return null;
    }

    /**
     * 校验Excel
     *
     * @param list
     * @throws Exception
     */
    private void checkExcel(List<QuExportDTO> list) throws ServiceException {

        // 约定第三行开始导入
        int line = 3;
        StringBuffer sb = new StringBuffer();

        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("您导入的数据似乎是一个空表格！");
        }

        Integer quNo = null;
        for (QuExportDTO item : list) {

            System.out.println(item.getNo());
            if (StringUtils.isBlank(item.getNo())) {
                line++;
                continue;
            }

            System.out.println(item.getQContent());
            Integer no;

            try {
                no = Integer.parseInt(item.getNo());
            } catch (Exception e) {
                line++;
                continue;
            }

            if (no == null) {
                sb.append("第" + line + "行，题目序号不能为空！<br>");
            }

            if (quNo == null || !quNo.equals(no)) {

                if (item.getQuType() == null) {
                    sb.append("第" + line + "行，题目类型不能为空<br>");
                }

                if (StringUtils.isBlank(item.getQContent())) {
                    sb.append("第" + line + "行，题目内容不能为空<br>");
                }

                if (CollectionUtils.isEmpty(item.getRepoList())) {
                    sb.append("第" + line + "行，题目必须包含一个题库<br>");
                }
            }

            if (StringUtils.isBlank(item.getAIsRight())) {
                sb.append("第" + line + "行，选项是否正确不能为空<br>");
            }

            if (StringUtils.isBlank(item.getAContent()) && StringUtils.isBlank(item.getAImage())) {
                sb.append("第" + line + "行，选项内容和选项图片必须有一个不为空<br>");
            }

            quNo = no;
            line++;
        }

        // 存在错误
        if (!"".equals(sb.toString())) {
            throw new ServiceException(sb.toString());
        }
    }

    /**
     * 下载导入试题数据模板
     */
    @ResponseBody
    @RequestMapping(value = "import/template")
    public AjaxResult importFileTemplate(HttpServletResponse response) {
        try {
            String fileName = "试题导入模板.xlsx";
            List<QuExportDTO> list = Lists.newArrayList();

            QuExportDTO l1 = new QuExportDTO();
            l1.setNo("正式导入，请删除此说明行：数字，相同的数字表示同一题的序列");
            l1.setQContent("问题内容");
            l1.setQAnalysis("整个问题的解析");
            l1.setQuType("只能填写1、2、3、4；1表示单选题，2表示多选题，3表示判断题，4表示主观题");
            l1.setQImage("题目图片，完整URL，多个用逗号隔开，限制10个");
            l1.setQVideo("题目视频，完整URL，只限一个");
            l1.setAImage("答案图片，完整URL，只限一个");
            l1.setRepoList(Arrays.asList(new String[]{"已存在题库的ID，多个用逗号隔开，题库ID错误无法导入"}));
            l1.setAContent("候选答案1");
            l1.setAIsRight("只能填写0或1，0表示否，1表示是");
            l1.setAAnalysis("这个项是正确的");


            QuExportDTO l2 = new QuExportDTO();
            l2.setQContent("找出以下可以被2整除的数（多选）");
            l2.setQAnalysis("最基本的数学题，不做过多解析");
            l2.setQuType("2");
            l2.setNo("1");
            l2.setAIsRight("1");
            l2.setAContent("数字：2");
            l2.setAAnalysis("2除以2=1，对的");

            QuExportDTO l3 = new QuExportDTO();
            l3.setNo("1");
            l3.setAIsRight("0");
            l3.setAContent("数字：3");
            l3.setAAnalysis("3除以2=1.5，不能被整除");

            QuExportDTO l4 = new QuExportDTO();
            l4.setNo("1");
            l4.setAIsRight("1");
            l4.setAContent("数字：6");
            l4.setAAnalysis("6除以2=3，对的");


            list.add(l1);
            list.add(l2);
            list.add(l3);
            list.add(l4);

            //new ExportExcel("试题数据", QuExportDTO.class, 1).setDataList(list).write(response, fileName).dispose();
            return super.success();
        } catch (Exception e) {
            //return super.failure("导入模板下载失败！失败信息：" + e.getMessage());
            return null;
        }
    }
}
