package com.aiqin.bms.scmp.api.base;

import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 *  爱亲渠道价
 *  萌贝树渠道价
 *  小红马渠道价
 *  爱亲分销价
 *  萌贝树分销价
 *  小红马分销价
 *  爱亲售价
 *  萌贝树售价
 *  小红马售价
 *  华北仓状态
 *  华东仓状态
 *  华南仓状态
 *  西南仓状态
 *  华中仓状态
 */
public class PriceAndWarehouseMap {
    public static Map<String, SkuPriceDraftReqVO> price = Maps.newHashMap();
    public static Map<String, SaveSkuConfigReqVo> warehouse = Maps.newHashMap();
    static {
        /**
         * 1083,"爱亲渠道价",2,"渠道类",2,"销售"
         * 1078,"爱亲分销价",3,"分销类",2,"销售"
         * 1089,"爱亲售价", 4,"零售类",2,"销售"
         * 1080,"萌贝树渠道价",2,"渠道类",2,"销售"
         * 1084,"萌贝树分销价",3,"分销类",2,"销售"
         * 1090,"萌贝树售价",4,"零售类",2,"销售"
         * 1077,"小红马渠道价",2,"渠道类",2,"销售"
         * 1085,"小红马分销价",3,"分销类",2,"销售"
         * 1091,"小红马售价",4,"零售类",2,"销售"
         *
         * "1081","华北仓"
         * "1082","西南仓"
         * "1083","华东仓"
         * "1084","华南仓"
         * "1085","华中仓"
         */
        price.put("爱亲渠道价",new SkuPriceDraftReqVO("1001","爱亲渠道价","2","渠道类","2","销售"));
        price.put("爱亲分销价",new SkuPriceDraftReqVO("1007","爱亲分销价","3","分销类","2","销售"));
        price.put("爱亲售价",new SkuPriceDraftReqVO("1089","爱亲售价", "4","零售类","2","销售"));
        price.put("萌贝树渠道价",new SkuPriceDraftReqVO("1002","萌贝树渠道价","2","渠道类","2","销售"));
        price.put("萌贝树分销价",new SkuPriceDraftReqVO("1008","萌贝树分销价","3","分销类","2","销售"));
        price.put("萌贝树售价",new SkuPriceDraftReqVO("1090","萌贝树售价","4","零售类","2","销售"));
        price.put("小红马渠道价",new SkuPriceDraftReqVO("1003","小红马渠道价","2","渠道类","2","销售"));
        price.put("小红马分销价",new SkuPriceDraftReqVO("1009","小红马分销价","3","分销类","2","销售"));
        price.put("小红马售价",new SkuPriceDraftReqVO("1091","小红马售价","4","零售类","2","销售"));
        price.put("售价",new SkuPriceDraftReqVO("1013","售价","4","零售类","2","销售"));
        price.put("会员价",new SkuPriceDraftReqVO("1014","会员价","4","零售类","2","销售"));

        warehouse.put("华北仓",new SaveSkuConfigReqVo("1081","华北仓"));
        warehouse.put("西南仓",new SaveSkuConfigReqVo("1082","西南仓"));
        warehouse.put("华东仓",new SaveSkuConfigReqVo("1083","华东仓"));
        warehouse.put("华南仓",new SaveSkuConfigReqVo("1084","华南仓"));
        warehouse.put("华中仓",new SaveSkuConfigReqVo("1085","华中仓"));
    }
}
