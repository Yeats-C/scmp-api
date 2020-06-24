package com.aiqin.bms.scmp.api.wholesale;


import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BaseController;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.AuthUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.mgs.scmp.wholesale.cart.domain.request.SpuProductReqVO;
import com.aiqin.mgs.scmp.wholesale.cart.domain.request.SpuProductReqVO2;
import com.aiqin.mgs.scmp.wholesale.cart.domain.request.cart.ErpCartAddRequest;
import com.aiqin.mgs.scmp.wholesale.cart.domain.request.cart.ErpCartQueryRequest;
import com.aiqin.mgs.scmp.wholesale.cart.domain.response.SpuProductResponseVO;
import com.aiqin.mgs.scmp.wholesale.cart.domain.response.cart.ErpCartQueryResponse;
import com.aiqin.mgs.scmp.wholesale.cart.domain.response.cart.ErpOrderCartAddResponse;
import com.aiqin.mgs.scmp.wholesale.cart.service.CartService;
import com.aiqin.mgs.scmp.wholesale.cart.service.ProductService;
import com.aiqin.mgs.scmp.wholesale.conts.BasePage;
import com.aiqin.mgs.scmp.wholesale.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@RequestMapping("/cart")
@Api(tags = "购物车相关")
public class CartController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    @Resource
    private ProductService productService;

    @Resource
    private CartService cartService;


    @PostMapping(value = "/spuList", consumes = "application/json")
    @ApiOperation("查询spu商品列表")
    public HttpResponse<BasePage<SpuProductResponseVO>> noPage2(@RequestBody SpuProductReqVO2 request) {
        try {
            return HttpResponse.success(productService.noPage2(request));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }


    @PostMapping(value = "/spuDetail", consumes = "application/json")
    @ApiOperation("查询spu商品详情")
    public HttpResponse<SpuProductResponseVO> spuDetail(@Validated @RequestBody SpuProductReqVO request, BindingResult br) {
        try {
            checkParameters(br);
            AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
            if (Objects.nonNull(currentAuthToken)) {
                request.setCompanyCode(currentAuthToken.getCompanyCode());
            }
            return HttpResponse.success(productService.spuDetail(request));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }


    @PostMapping("/addProduct")
    @ApiOperation(value = "添加商品到购物车")
    public HttpResponse<ErpOrderCartAddResponse> addProduct(@Validated @RequestBody ErpCartAddRequest erpCartAddRequest) {
        LOGGER.info("添加商品到购物车：{}", erpCartAddRequest);
        HttpResponse<ErpOrderCartAddResponse> response = HttpResponse.success();
        try {
            AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
            com.aiqin.mgs.scmp.wholesale.conts.AuthToken authToken = new com.aiqin.mgs.scmp.wholesale.conts.AuthToken();
            BeanUtils.copyProperties(currentAuthToken, authToken);

            cartService.addProduct(erpCartAddRequest, authToken);
        } catch (BusinessException e) {
            LOGGER.error("添加商品到购物车失败：{}", e);
            response = HttpResponse.failure(MessageId.create(Project.ORDER_API, 99, e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("添加商品到购物车失败：{}", e);
            response = HttpResponse.failure(ResultCode.ADD_ERROR);
        }
        return response;
    }


    @PostMapping("/queryCartList")
    @ApiOperation(value = "erp查询购物车列表")
    public HttpResponse<ErpCartQueryResponse> queryErpCartList(@Validated @RequestBody ErpCartQueryRequest erpCartQueryRequest) {
        HttpResponse<ErpCartQueryResponse> response = HttpResponse.success();
        try {
            AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
            com.aiqin.mgs.scmp.wholesale.conts.AuthToken authToken = new com.aiqin.mgs.scmp.wholesale.conts.AuthToken();
            BeanUtils.copyProperties(currentAuthToken, authToken);

            ErpCartQueryResponse queryResponse = cartService.queryErpCartList(erpCartQueryRequest, authToken);
            response.setData(queryResponse);
        } catch (BusinessException e) {
            LOGGER.error("查询购物车列表：{}", e);
            response = HttpResponse.failure(MessageId.create(Project.ORDER_API, 99, e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("查询购物车列表：{}", e);
            response = HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
        return response;
    }


}
