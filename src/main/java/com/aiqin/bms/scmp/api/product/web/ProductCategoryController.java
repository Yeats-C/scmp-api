package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryAddReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryRespVO;
import com.aiqin.bms.scmp.api.product.service.ProductCategoryService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @功能说明:品类管理
 * @author wangxu
 * @date 2018/12/12 0012 17:21
 */
@RestController
@Api(tags = "品类管理")
@RequestMapping("/product/category")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @PostMapping("/add")
    @ApiOperation("新增品类")
    public HttpResponse add(@RequestBody ProductCategoryAddReqVO productCategoryAddReqVO){
        try {
            productCategoryService.saveProductCategory(productCategoryAddReqVO);
            return HttpResponse.success();
        } catch (Exception e){
            return HttpResponse.failure(ResultCode.ADD_PRODUCT_CATEGORY_ERROR);
        }
    }

    @PutMapping("/update")
    @ApiModelProperty("修改")
    public HttpResponse<Integer> update(@RequestBody ProductCategoryReqVO productCategoryReqVO){
        try {
            Integer num = productCategoryService.updateProductCategory(productCategoryReqVO);
            return HttpResponse.success(num);
        } catch (Exception e){
            if(e instanceof GroundRuntimeException){
                return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
            }
            return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_CATEGORY_ERROR);
        }
    }

    @DeleteMapping("/delete")
    @ApiModelProperty("禁用/启用")
    public HttpResponse<Integer> delete(@RequestParam @ApiParam("id,必传") Long id){
        try {
            Integer num = productCategoryService.deleteProductCategory(id);
            return HttpResponse.success(num);
        } catch (Exception e){
            if(e instanceof GroundRuntimeException){
                return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
            }
            return HttpResponse.failure(ResultCode.DELETE_PRODUCT_CATEGORY_ERROR);
        }
    }

    @GetMapping("/list")
    @ApiModelProperty("查询品类")
    @ApiImplicitParams({
            @ApiImplicitParam(name="categoryStatus",value = "查询全部还是启用,0为启用,4为全部")
    })
    public HttpResponse<List<ProductCategoryRespVO>> getProductCategory(Byte categoryStatus){
        try {
            List<ProductCategoryRespVO> productCategoryRespVOS = productCategoryService.getList(categoryStatus);
            return HttpResponse.success(productCategoryRespVOS);
        } catch (Exception e){
            return HttpResponse.failure(ResultCode.GET_PRODUCT_CATEGORY_ERROR);
        }
    }


    @GetMapping("/tree")
    @ApiModelProperty("查询品类")
    @ApiImplicitParams({
            @ApiImplicitParam(name="categoryStatus",value = "查询全部还是启用,0为启用,4为全部"),
            @ApiImplicitParam(name="parentId",value = "列表第一次加载parentId=0")
    })
    public HttpResponse<List<ProductCategoryRespVO>> getTree(Byte categoryStatus,String parentId){
        try {
            List<ProductCategoryRespVO> productCategoryRespVOS = productCategoryService.getTree(categoryStatus,parentId);
            return HttpResponse.success(productCategoryRespVOS);
        } catch (Exception e){
            return HttpResponse.failure(ResultCode.GET_PRODUCT_CATEGORY_ERROR);
        }
    }

}
