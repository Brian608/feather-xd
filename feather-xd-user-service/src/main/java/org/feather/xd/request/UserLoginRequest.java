package org.feather.xd.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: UserLoginRequest
 * @author: feather
 * @description:
 * @since: 2024-08-12 9:52
 * @version: 1.0
 */
@Data
@ApiModel(value = "登录对象",description = "用户登录请求对象")
public class UserLoginRequest {

    @ApiModelProperty(value = "邮箱", example = "826813443@qq.com",required = true)
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",message = "邮箱格式错误")
    @NotEmpty(message = "邮箱不能为空")
    private String mail;

    @ApiModelProperty(value = "密码", example = "RTEwQURDMzk0OUJBNTlBQkJFNTZFMDU3RjIwRjg4M0U=",required = true)
    @NotEmpty(message = "密码不能为空")
    private String pwd;
}
