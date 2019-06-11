package com.aiqin.bms.scmp.api.product.domain.trans;//package com.aiqin.mgs.product.api.domain.trans;
//
//import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;
//import com.aiqin.bms.scmp.api.product.domain.request.ILockStockItemReqVo;
//import com.google.common.base.Function;
//
//import javax.annotation.Nullable;
//import java.util.Date;
//
///**
// * @author knight.xie
// * @version 1.0
// * @className ILockStockItemReqVoToOutboundProduct
//// * @date 2019/1/9 17:07
// * @description TODO
// */
//public class ILockStockItemReqVoToOutboundProduct implements Function<ILockStockItemReqVo, OutboundProduct> {
//
//    private String outboundOderCode;
//    public ILockStockItemReqVoToOutboundProduct(String outboundOderCode){
//        this.outboundOderCode = outboundOderCode;
//    }
//    @Nullable
//    @Override
//    public OutboundProduct apply(@Nullable ILockStockItemReqVo iLockStockItemReqVo) {
//        if(null != iLockStockItemReqVo){
//            OutboundProduct outboundProduct = new OutboundProduct();
//            //出库单号
//            outboundProduct.setOutboundOderCode(outboundOderCode);
//            //sku
//            outboundProduct.setSkuCode(iLockStockItemReqVo.getSkuCode());
//            outboundProduct.setSkuName(iLockStockItemReqVo.getSkuName());
//            //图片
//            outboundProduct.setPictureUrl(iLockStockItemReqVo.getPictureUrl());
//            //规格
//            outboundProduct.setNorms(iLockStockItemReqVo.getNorms());
//            //单位
//            outboundProduct.setUnitCode(iLockStockItemReqVo.getUnitCode());
//            outboundProduct.setUnitName(iLockStockItemReqVo.getUnitName());
//            //进货规格
//            outboundProduct.setPurchaseNorms(iLockStockItemReqVo.getPurchaseNorms());
//            //预计出库数量
//            outboundProduct.setPreOutboundNum(iLockStockItemReqVo.getPreOutboundNum());
//            //预计含税进价
//            outboundProduct.setPreTaxPurchaseAmount(iLockStockItemReqVo.getPreTaxPurchaseAmount());
//            //预计含税总价
//            outboundProduct.setPreTaxAmount(iLockStockItemReqVo.getPreTaxAmount());
//            outboundProduct.setColorCode(iLockStockItemReqVo.getColorCode());
//            outboundProduct.setColorName(iLockStockItemReqVo.getColorName());
//            outboundProduct.setCreateBy(iLockStockItemReqVo.getOperator());
//            outboundProduct.setUpdateBy(iLockStockItemReqVo.getOperator());
//            outboundProduct.setPreOutboundMainNum(iLockStockItemReqVo.getPreOutboundMainNum());
//            outboundProduct.setCreateTime(new Date());
//            outboundProduct.setUpdateTime(new Date());
//            return outboundProduct;
//        }
//        return null;
//    }
//}
