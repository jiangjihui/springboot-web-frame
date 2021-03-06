package com.jjh.business.system.user.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 *
 * @author jjh
 * @date 2019/9/20
 **/
@ApiModel("登录表单")
@Data
public class LoginForm {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

}
