package org.feather.xd.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: NewUserCouponRequest
 * @author: feather
 * @description:
 * @since: 2024-09-05 10:41
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewUserCouponRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户Id",example = "19")
    @NotNull(message = "用户id不能为空")
    private long userId;


    @ApiModelProperty(value = "名称",example = "Anna小姐姐")
    @JsonProperty("name")
    private String name;

}
