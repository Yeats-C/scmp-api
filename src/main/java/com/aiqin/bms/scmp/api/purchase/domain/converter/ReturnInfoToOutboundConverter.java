package com.aiqin.bms.scmp.api.purchase.domain.converter;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderProductItemReqVO;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Component
public class ReturnInfoToOutboundConverter implements Converter<ReturnOrderInfo,InboundReqSave> {
    private SkuService skuService;
    public ReturnInfoToOutboundConverter(SkuService skuService) {
        this.skuService = skuService;
    }

    @Override
    public InboundReqSave convert(ReturnOrderInfo returnOrderInfo) {
        List<String> skuCodes = returnOrderInfo.getDetailList().stream().map(ReturnOrderInfoItem::getSkuCode).collect(Collectors.toList());
        List<ProductSkuCheckout> skuCheckOuts = skuService.getSkuCheckOuts(skuCodes);
        Map<String, Long> map = skuCheckOuts.stream().collect(Collectors.toMap(ProductSkuCheckout::getSkuCode, ProductSkuCheckout::getOutputTaxRate, (k1, k2) -> k2));
        InboundReqSave inbound = new InboundReqSave();


        return inbound;
    }
}
