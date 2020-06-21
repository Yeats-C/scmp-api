package com.aiqin.bms.scmp.api.wholesale;


import com.aiqin.bms.scmp.api.common.BaseController;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.mgs.scmp.wholesale.cart.domain.request.SpuProductReqVO;
import com.aiqin.mgs.scmp.wholesale.cart.domain.request.SpuProductReqVO2;
import com.aiqin.mgs.scmp.wholesale.cart.domain.response.SpuProductResponseVO;
import com.aiqin.mgs.scmp.wholesale.cart.service.CartService;
import com.aiqin.mgs.scmp.wholesale.conts.BasePage;
import com.aiqin.mgs.scmp.wholesale.conts.PageResData;
import com.aiqin.mgs.scmp.wholesale.wholesale.domain.pojo.WholesaleCustomers;
import com.aiqin.mgs.scmp.wholesale.wholesale.service.WholesaleCustomersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cart")
@Api(tags = "购物车相关")
public class CartController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    @Resource
    private CartService cartService;


    @PostMapping(value = "/spuList",consumes = "application/json")
    @ApiOperation("查询spu商品列表")
    public HttpResponse<BasePage<SpuProductResponseVO>> noPage2(@RequestBody SpuProductReqVO2 request) {
        try {
            return HttpResponse.success(cartService.noPage2(request));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }


    @PostMapping(value = "/spuDetail",consumes = "application/json")
    @ApiOperation("查询spu商品详情")
    public HttpResponse<SpuProductResponseVO> spuDetail(@Validated @RequestBody SpuProductReqVO request, BindingResult br) {
        try {
            checkParameters(br);
            AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(Objects.nonNull(currentAuthToken)){
                request.setCompanyCode(currentAuthToken.getCompanyCode());
            }
            return HttpResponse.success(cartService.spuDetail(request));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }




}
