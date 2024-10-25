package com.ruoyi.exam.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 题库请求类
 *
 * @author yd
 */
@Data
public class RepoDTO {

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
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
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

    /**
     * 排除题库ID
     */
    private List<String> excludes;
}
