package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductBrandRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryProductBrandRespVO;
import com.aiqin.bms.scmp.api.product.service.ProductBrandService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Description:
 * 商品品牌管理
 * @author zth
 * @date 2018-12-12
 * @time: 11:15
 */
@RestController
@Slf4j
@Api(tags = "商品品牌相关接口")
@RequestMapping("/product/brand")
public class ProductBrandController {

    @Autowired
    ProductBrandService productBrandService;

    @PostMapping("/list")
    @ApiOperation(value = "列表")
    public HttpResponse<BasePage<QueryProductBrandRespVO>> list(@RequestBody QueryProductBrandReqVO reqVO) {
        try {
            BasePage<QueryProductBrandRespVO> s = productBrandService.selectBrandListByQueryVO(reqVO);
            return HttpResponse.success(s);
        }catch (Exception e){
            log.error(e.getMessage());
            if(e instanceof GroundRuntimeException){
                return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
            }
            return HttpResponse.failure(ResultCode.LIST_PRODUCT_BRAND_ERROR);
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public HttpResponse<Integer> addBrand(@RequestBody ProductBrandReqVO reqVO) {
        try {
            Integer save = productBrandService.save(reqVO);
            return HttpResponse.success(save);
        }catch (Exception e){
            log.error(e.getMessage());
            if(e instanceof GroundRuntimeException){
                return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
            }
            return HttpResponse.failure(ResultCode.ADD_PRODUCT_BRAND_ERROR);
        }
    }

    @PutMapping("/edit")
    @ApiOperation(value = "修改")
    public HttpResponse<Integer> editBrand(@RequestBody ProductBrandReqVO reqVO) {
        try {
            Integer edit = productBrandService.edit(reqVO);
            return HttpResponse.success(edit);
        }catch (Exception e){
            log.error(e.getMessage());
            if(e instanceof GroundRuntimeException){
                return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
            }
            return HttpResponse.failure(ResultCode.EDIT_PRODUCT_BRAND_ERROR);
        }
    }
    @PutMapping("/enable")
    @ApiOperation(value = "是否启用")
    public HttpResponse<Integer> enableBrand(@RequestParam @NotNull(message = "传入id不能为空!") Long id,
                                             @RequestParam @NotNull(message = "传入状态不能为空!") Integer enable) {
        try {
            Integer enable1 = productBrandService.enable(id, enable);
            return HttpResponse.success(enable1);
        }catch (Exception e){
            log.error(e.getMessage());
            if(e instanceof GroundRuntimeException){
               return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
            }
            return HttpResponse.failure(ResultCode.ENABLE_PRODUCT_BRAND_ERROR);
        }
    }
    @GetMapping("/view")
    @ApiOperation(value = "查看")
    public HttpResponse<ProductBrandRespVO> viewBrand(@RequestParam @NotNull(message = "传入id不能为空") Long id) {
        try {
            ProductBrandRespVO respVO = productBrandService.view(id);
            return HttpResponse.success(respVO);
        }catch (Exception e){
            log.error(e.getMessage());
            if(e instanceof GroundRuntimeException){
               return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
            }
            return HttpResponse.failure(ResultCode.VIEW_PRODUCT_BRAND_ERROR);
        }
    }

    @PostMapping("/selectByBrandCodes")
    @ApiOperation(value = "通过编码集合获取品牌列表")
    public HttpResponse<List<ProductBrandType>> selectByBrandCodes(String codes) {
        try {
            List<ProductBrandType> list = productBrandService.selectByBrandCodes(Lists.newArrayList(Lists.newArrayList(codes.split(","))));
            return HttpResponse.success(list);
        }catch (Exception e){
            log.error(e.getMessage());
            if(e instanceof GroundRuntimeException){
                return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
            }
            return HttpResponse.failure(ResultCode.VIEW_PRODUCT_BRAND_ERROR);
        }
    }
}
