package com.jjh.common.web.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 简单分页查询类
 */
@Data
@ApiModel("分页请求查询表单")
public class PageRequestForm<T> {

    /** 页码（从0开始） */
    @ApiModelProperty("页码")
    private Integer pageNum;

    /** 每页大小 */
    @ApiModelProperty("每页大小")
    private Integer pageSize;

    /** 总数 */
//    @ApiModelProperty("总数")
    @JsonIgnore
    private Long total;

    /** 查询实体类*/
    private T filter;

}
