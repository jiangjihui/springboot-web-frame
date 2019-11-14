package com.jjh.common.web.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 简单分页查询类
 */
@Data
@ApiModel("分页请求查询表单")
public class PageRequestForm {

    /** 页码（从0开始） */
    @ApiModelProperty("页码")
    private int pageNum;

    /** 每页大小 */
    @ApiModelProperty("每页大小")
    private int pageSize;

}
