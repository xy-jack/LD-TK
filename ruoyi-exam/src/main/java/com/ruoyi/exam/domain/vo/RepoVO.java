package com.ruoyi.exam.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 题库查询返回参数
 *
 * @author yd
 */
@Data
public class RepoVO {

    /**
     * 题库ID
     */
    private String id;

    /**
     * 题库编号
     */
    private String code;

    /**
     * 题库名称
     */
    private String title;

    /**
     * 题库备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 多选题数量
     */
    private Integer multiCount;

    /**
     * 单选题数量
     */
    private Integer radioCount;

    /**
     * 判断题数量
     */
    private Integer judgeCount;

}
