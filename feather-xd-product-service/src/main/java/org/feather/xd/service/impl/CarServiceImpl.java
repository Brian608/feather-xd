package org.feather.xd.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.feather.xd.constant.CacheKey;
import org.feather.xd.interceptor.LoginInterceptor;
import org.feather.xd.model.LoginUser;
import org.feather.xd.request.CartItemRequest;
import org.feather.xd.service.ICarService;
import org.feather.xd.service.IProductService;
import org.feather.xd.vo.CartItemVO;
import org.feather.xd.vo.CartVO;
import org.feather.xd.vo.ProductVO;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.service.impl
 * @className: CarServiceImpl
 * @author: feather
 * @description:
 * @since: 2024-09-10 11:04
 * @version: 1.0
 */
@RequiredArgsConstructor
@Service
public class CarServiceImpl implements ICarService {


    private final IProductService productService;

    private final RedisTemplate redisTemplate;
    @Override
    public void addToCart(CartItemRequest cartItemRequest) {
        long productId = cartItemRequest.getProductId();
        int buyNum = cartItemRequest.getBuyNum();
        //获取我的购物车
        BoundHashOperations<String, Object, Object> myCar = this.getMyCartOps();
        Object cacheObj = myCar.get(productId);
        String  result="";
        if (Objects.nonNull(cacheObj)){
            result =(String)cacheObj;
        }
        if(StringUtils.isBlank(result)){
            //不存在则新建一个商品
            CartItemVO cartItemVO = new CartItemVO();

            ProductVO productVO = productService.findDetailById(productId);

            cartItemVO.setAmount(productVO.getAmount());
            cartItemVO.setBuyNum(buyNum);
            cartItemVO.setProductId(productId);
            cartItemVO.setProductImg(productVO.getCoverImg());
            cartItemVO.setProductTitle(productVO.getTitle());
            myCar.put(productId,JSON.toJSONString(cartItemVO));

        }else {
            //存在商品，修改数量
            CartItemVO cartItem = JSON.parseObject(result,CartItemVO.class);
            cartItem.setBuyNum(cartItem.getBuyNum()+buyNum);
            myCar.put(productId,JSON.toJSONString(cartItem));
        }

    }

    /**
     * 抽取我的购物车，通用方法
     * @return
     */
    private BoundHashOperations<String,Object,Object> getMyCartOps(){
        String cartKey = this.getCarKey();
        return redisTemplate.boundHashOps(cartKey);
    }
    /**
     * description: 购物车key
     * @param
     * @return {@link String}
     * @author: feather
     * @since: 2024-09-10 11:10
     **/
    private  String getCarKey(){
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        return String.format(CacheKey.CART_KEY,loginUser.getId());
    }

    @Override
    public void clear() {
        redisTemplate.delete(this.getCarKey());
    }

    @Override
    public CartVO getMyCart() {
        //获取全部购物项
        List<CartItemVO> cartItemVOList = buildCartItem(false);

        //封装成cartvo
        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemVOList);

        return cartVO;
    }

    private List<CartItemVO> buildCartItem(boolean latestPrice) {
        BoundHashOperations<String,Object,Object> myCart = getMyCartOps();
        List<Object> itemList = myCart.values();
        if (CollectionUtils.isEmpty(itemList)){
            return null;
        }
        List<CartItemVO> cartItemVOList=new ArrayList<>();

        //拼接id列表查询最新价格
        List<Long> productIdList=new ArrayList<>();
         itemList.forEach(item->{
             CartItemVO cartItemVO = JSON.parseObject((String)item,CartItemVO.class);
             cartItemVOList.add(cartItemVO);

             productIdList.add(cartItemVO.getProductId());

         });
        //查询最新的商品价格
        if(latestPrice){

            setProductLatestPrice(cartItemVOList,productIdList);
        }
        return  cartItemVOList;

    }

    private void setProductLatestPrice(List<CartItemVO> cartItemVOList, List<Long> productIdList) {
        //批量查询
        List<ProductVO> productVOList = productService.findProductsByIdBatch(productIdList);

        //分组
        Map<Long,ProductVO> maps = productVOList.stream().collect(Collectors.toMap(ProductVO::getId, Function.identity()));


        cartItemVOList.forEach(item->{

            ProductVO productVO = maps.get(item.getProductId());
            item.setProductTitle(productVO.getTitle());
            item.setProductImg(productVO.getCoverImg());
            item.setAmount(productVO.getAmount());

        });
    }
}
