package com.jjh.business.common.gen.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 生成指定路径下的代码 表单
 *
 * @author jjh
 * @date 2019/11/20
 **/
@ApiModel("生成指定路径代码 表单")
@Data
public class GenTargetPathForm {

    /**
     * 指定的目录（包含到model包名）
     */
    @NotBlank(message = "目录不能为空")
    @ApiModelProperty("指定的目录（包含到model包名）")
    private String packagePath;

    /** 作者（比如：jjh）*/
    @NotBlank(message = "作者不能为空")
    @ApiModelProperty("作者")
    private String author;

    /** 排除项*/
    @ApiModelProperty(value = "排除项", example = "UserInfo,RoInfo")
    private String excludeEntity;

}
