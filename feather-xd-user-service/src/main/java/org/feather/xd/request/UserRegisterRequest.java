package org.feather.xd.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: UserRegisterRequest
 * @author: feather
 * @description:
 * @since: 2024-08-11 13:27
 * @version: 1.0
 */
@ApiModel(value = "用户注册对象",description = "用户注册请求对象")
@Data
public class UserRegisterRequest    {

    @ApiModelProperty(value = "昵称",example = "Anna小姐姐",required = true)
    @NotBlank(message = "昵称不能为空")
    private String name;


    @ApiModelProperty(value = "密码 md5后再base64",name ="password",required = true)
    @NotBlank(message = "密码不能为空")
    private String pwd;


    @ApiModelProperty(value = "头像",example = "https://xdclass-1024shop-img.oss-cn-shenzhen.aliyuncs.com/user/2024/02/03/39473aa1029a430298ac2620dd819962.jpeg")
    @JsonProperty("head_img")
    private String headImg;

    @ApiModelProperty(value = "用户个人性签名",example = "人生需要动态规划，学习需要贪心算法")
    private String slogan;

    @ApiModelProperty(value = "0表示女，1表示男",example = "1",required = true)
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @ApiModelProperty(value = "邮箱",example = "1112222333338@qq.com",required = true)
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String mail;

    @ApiModelProperty(value = "验证码",example = "232343",required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;
}
