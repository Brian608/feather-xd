package org.feather.xd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: LoginInfo
 * @author: feather
 * @description:
 * @since: 2024-08-12 9:58
 * @version: 1.0
 */
@Data
@ApiModel(value = "用户登录返回信息",description = "用户登录返回信息")
public class LoginInfo {
    private Long id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "123456")
    private String name;


    /**
     * 头像
     */
    @ApiModelProperty(value = "头像",example = "https://xdclass-1024shop-img.oss-cn-shenzhen.aliyuncs.com/user/2024/02/03/39473aa1029a430298ac2620dd819962.jpeg")
    @JsonProperty("head_img")
    private String headImg;

    /**
     * 用户签名
     */
    @ApiModelProperty(value = "用户个人性签名",example = "人生需要动态规划，学习需要贪心算法")
    private String slogan;

    /**
     * 0表示女，1表示男
     */
    @ApiModelProperty(value = "0表示女，1表示男",example = "1")
    private Integer sex;

    /**
     * 积分
     */
    @ApiModelProperty(value = "积分",example = "1")
    private Integer points;


    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱",example = "1112222333338@qq.com")
    private String mail;

    @ApiModelProperty(value = "token",example = "xxxxxx")
    private String token;
}
