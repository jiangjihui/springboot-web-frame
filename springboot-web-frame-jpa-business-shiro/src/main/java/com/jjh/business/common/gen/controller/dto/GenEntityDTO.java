package com.jjh.business.common.gen.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * GenEntityDTO
 *
 * @author jjh
 * @date 2019/11/13
 **/
@Data
public class GenEntityDTO {

    /** 所属分类*/
    @ApiModelProperty("所属分类")
    private String moduleName;

    /** 实体类名*/
    @ApiModelProperty("实体类名")
    private String className;

    /** 实体类描述*/
    @ApiModelProperty("实体类描述")
    private String comment;

    /** 包名（比如：com.business.demo）*/
    @ApiModelProperty("包名")
    private String packageName;

    /** 作者（比如：jjh）*/
    @ApiModelProperty("作者")
    private String author;

    /** 目的文件路径*/
    @ApiModelProperty("目的文件路径")
    private String targetPath;

}
