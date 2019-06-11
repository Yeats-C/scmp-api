package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.service.BrandService;
import com.aiqin.bms.scmp.api.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Createed by sunx on 2018/11/22.<br/>
 */
@RestController
@RequestMapping("/dictionary")
@Slf4j
@Api(tags = "商品模块字典数据")
public class ProductDataDicController {

    @Resource
    private BrandService brandService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("/category")
    @ApiOperation(value = "a.商品分类",notes = "查寻一级分类的时候不传值，根据父级查询所有子级")
    public HttpResponse<List<ProductCategory>> category(String category_id) {
        return categoryService.selectProductCategoryByParentId(category_id);
    }

    @GetMapping("/brand")
    @ApiOperation("b.商品品牌")
    public HttpResponse<List<ProductBrandType>> brand(){
        return brandService.selectAllBrand();
    }

    @GetMapping("/top/brand")
    @ApiOperation("c.店铺优选大牌")
    public HttpResponse<List<ProductBrandType>> topBrand(){
        return brandService.selectAllTopBrand();
    }

    @GetMapping("/find/category/{distributor_id}")
    @ApiOperation(value = "d.查询门店分类列表")
    public HttpResponse selectCategoryDisInfoByDisId(@PathVariable(value = "distributor_id") String distributorId) {
        return categoryService.selectCategoryDisInfoByDisId(distributorId);
    }
}
