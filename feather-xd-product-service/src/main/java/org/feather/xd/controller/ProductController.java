package org.feather.xd.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.feather.xd.query.ProductQuery;
import org.feather.xd.request.LockProductRequest;
import org.feather.xd.service.IProductService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.ProductVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feather
 * @since 2024-09-05
 */
@Api(tags = "产品")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product/v1")
public class ProductController {

    private final IProductService productService;


    @ApiOperation(value = "产品分页",httpMethod = "POST", produces = "application/json")
    @PostMapping("/pageProduct")
    public JsonResult<Page<ProductVO>> pageCoupon(@RequestBody @Validated ProductQuery query){
        return JsonResult.buildSuccess( productService.pageProduct(query));
    }

    @ApiOperation("商品详情")
    @GetMapping("/detail/{product_id}")
    public JsonResult<ProductVO> detail(@ApiParam(value = "商品id",required = true) @PathVariable("product_id") long productId){
        return JsonResult.buildSuccess(productService.findDetailById(productId));
    }

    @ApiOperation("锁定商品库存")
    @PostMapping("/lockProductStock")
    public JsonResult<Boolean> lockProductStock(@RequestBody LockProductRequest request){
        return JsonResult.buildSuccess(productService.lockProductStock(request));
    }

}

