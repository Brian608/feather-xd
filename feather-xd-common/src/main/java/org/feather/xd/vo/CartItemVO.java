package org.feather.xd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: CartItemVO
 * @author: feather
 * @description:
 * @since: 2024-09-10 10:46
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemVO {
    /**
     * 商品id
     */
    @JsonProperty("product_id")
    private Long productId;


    /**
     * 购买数量
     */
    @JsonProperty("buy_num")
    private Integer buyNum;

    /**
     * 商品标题
     */
    @JsonProperty("product_title")
    private String productTitle;

    /**
     * 图片
     */
    @JsonProperty("product_img")
    private String productImg;

    /**
     * 商品单价
     */
    private BigDecimal amount;

    /**
     * 总价格，单价+数量
     */
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    /**
     * 商品单价 * 购买数量
     * @return
     */
    public BigDecimal getTotalAmount() {

        return this.amount.multiply(new BigDecimal(this.buyNum));
    }
}
