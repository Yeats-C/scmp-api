package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.ProductSkuUniqueCode;
import com.aiqin.bms.scmp.api.purchase.service.ProductSkuUniqueCodeService;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Description:
 * 订单商品唯一码控制器
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:22
 */
@RestController
@Slf4j
@Api(tags = "DL单据等信息传送")
@RequestMapping("/order/unique")
public class ProductSkuUniqueCodeController {

    @Autowired
    private ProductSkuUniqueCodeService productSkuUniqueCodeService;

    @ApiOperation("订单同步")
    @PostMapping("/save")
    public HttpResponse<Boolean> saveUnique(@RequestBody @Valid List<ProductSkuUniqueCode> reqVO){
        log.info("ProductSkuUniqueCodeController---saveUnique---param：[{}]", JsonUtil.toJson(reqVO));
        try {
            return HttpResponse.success(productSkuUniqueCodeService.saveUnique(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
