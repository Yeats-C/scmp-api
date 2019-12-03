package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryAddReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.service.ProductBrandService;
import com.aiqin.bms.scmp.api.product.service.ProductCategoryService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: zhao shuai
 * @create: 2019-11-26
 **/

@RestController
@Api(value = "对接dl的接口")
@RequestMapping("/dl")
public class DlAbutmentController {

    @Resource
    private ProductBrandService productBrandService;
    @Resource
    private ProductCategoryService productCategoryService;

    @PostMapping("/product/brand/add")
    @ApiOperation(value = "新增商品的品牌信息")
    public HttpResponse<Integer> addBrand(@RequestBody ProductBrandReqVO reqVO) {
        if (StringUtils.isBlank(reqVO.getCompanyCode()) || StringUtils.isBlank(reqVO.getCreateBy())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        return HttpResponse.success(productBrandService.save(reqVO));
    }

    @PutMapping("/product/brand/edit")
    @ApiOperation(value = "修改商品的品牌信息")
    public HttpResponse<Integer> editBrand(@RequestBody ProductBrandReqVO reqVO) {
        if (StringUtils.isBlank(reqVO.getCompanyCode()) || StringUtils.isBlank(reqVO.getUpdateBy())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Integer edit = productBrandService.edit(reqVO);
        return HttpResponse.success(edit);
    }

    @PostMapping("/product/category/add")
    @ApiOperation("新增品类")
    public HttpResponse addCategory(@RequestBody ProductCategoryAddReqVO productCategoryAddReqVO){
        if (StringUtils.isBlank(productCategoryAddReqVO.getCompanyCode())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        productCategoryService.saveProductCategory(productCategoryAddReqVO);
        return HttpResponse.success();
    }

    @PutMapping("/product/category/edit")
    @ApiModelProperty("修改")
    public HttpResponse<Integer> editCategory(@RequestBody ProductCategoryReqVO productCategoryReqVO) {
        if (StringUtils.isBlank(productCategoryReqVO.getCompanyCode()) || StringUtils.isBlank(productCategoryReqVO.getUpdateBy())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Integer num = productCategoryService.updateProductCategory(productCategoryReqVO);
        return HttpResponse.success(num);
    }

}
