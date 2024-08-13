package org.feather.xd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: UserVo
 * @author: feather
 * @description:
 * @since: 2024-08-13 17:04
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVO {

    private Long id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String name;


    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @JsonProperty("head_img")
    private String headImg;

    /**
     * 用户签名
     */
    @ApiModelProperty(value = "用户签名")
    private String slogan;

    /**
     * 0表示女，1表示男
     */
    @ApiModelProperty(value = "0表示女，1表示男")
    private Integer sex;

    /**
     * 积分
     */
    @ApiModelProperty(value = "积分")
    private Integer points;


    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String mail;
}
