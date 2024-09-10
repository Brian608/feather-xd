package org.feather.xd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: CartVO
 * @author: feather
 * @description:
 * @since: 2024-09-10 10:45
 * @version: 1.0
 */
@Data
public class CartVO {
    /**
     * 购物项
     */
    @JsonProperty("cart_items")
    private List<CartItemVO> cartItems;


    /**
     * 购买总件数
     */
    @JsonProperty("total_num")
    private Integer totalNum;

    /**
     * 购物车总价格
     */
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    /**
     * 购物车实际支付价格
     */
    @JsonProperty("real_pay_amount")
    private BigDecimal realPayAmount;

    /**
     * 总件数
     * @return
     */
    public Integer getTotalNum() {
        if(this.cartItems!=null){
            return cartItems.stream().mapToInt(CartItemVO::getBuyNum).sum();
        }
        return 0;
    }

    /**
     * 总价格
     * @return
     */
    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        if(this.cartItems!=null){
            for(CartItemVO cartItemVO : cartItems){
                BigDecimal itemTotalAmount =  cartItemVO.getTotalAmount();
                amount = amount.add(itemTotalAmount);
            }
        }
        return amount;
    }

    /**
     * 购物车里面实际支付的价格
     * @return
     */
    public BigDecimal getRealPayAmount() {
        BigDecimal amount = new BigDecimal("0");
        if(this.cartItems!=null){
            for(CartItemVO cartItemVO : cartItems){
                BigDecimal itemTotalAmount =  cartItemVO.getTotalAmount();
                amount = amount.add(itemTotalAmount);
            }
        }
        return amount;
    }
}
