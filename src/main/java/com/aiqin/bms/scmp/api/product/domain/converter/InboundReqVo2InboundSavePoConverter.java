package com.aiqin.bms.scmp.api.product.domain.converter;//package com.aiqin.mgs.product.api.domain.converter;
//
//import com.aiqin.bms.scmp.api.common.*;
//import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
//import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
//import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundItemReqVo;
//import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
//import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundSavePo;
//import com.aiqin.bms.scmp.api.product.service.SkuService;
//import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
//import com.google.common.collect.Lists;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * @author HuangLong
// * @date 2019/1/17
// */
//@Component
//public class InboundReqVo2InboundSavePoConverter implements Converter<InboundReqVo, InboundSavePo> {
//    private SkuService skuService;
//
//    public InboundReqVo2InboundSavePoConverter(SkuService skuService) {
//        this.skuService=skuService;
//    }
//
//    @Override
//    public InboundSavePo convert(InboundReqVo reqVo) {
////        try {
////            if(CollectionUtils.isEmpty(reqVo.getPurchaseItemVos())){
////                throw new BizException("入库单保存请求vo的商品项集合不能为空");
////            }
////            List<String> skuCodes = reqVo.getPurchaseItemVos().stream().map(InboundItemReqVo::getSkuCode).collect(Collectors.toList());
////            Map<String, Long> map = skuService.getSkuConvertNumBySkuCodes(skuCodes);
////            InboundSavePo po = new InboundSavePo();
////            Inbound inbound = new Inbound();
////            BeanUtils.copyProperties(reqVo,inbound);
////            inbound.setCompanyCode(reqVo.getCompanyCode());
////            inbound.setCompanyName(reqVo.getCompanyName());
////            inbound.setInboundTime(new Date());
////            inbound.setSourceOderCode(reqVo.getCode());
////            inbound.setLogisticsCenterCode(reqVo.getTransportCenterCode());
////            inbound.setLogisticsCenterName(reqVo.getTransportCenterName());
////            inbound.setSupplierName(reqVo.getSupplierName());
////            inbound.setSupplierCode(reqVo.getSupplierCode());
////            inbound.setPreArrivalTime(reqVo.getExpectedTime());
////            inbound.setPreInboundNum(reqVo.getTotalNum());
////            inbound.setPreMainUnitNum(reqVo.getSaleUnitTotalNum());
////            inbound.setPreTaxAmount(reqVo.getTotalAmount());
////            inbound.setPreAmount(reqVo.getNoTaxTotalAmount());
////            inbound.setPreTax(reqVo.getTotalAmount()-reqVo.getNoTaxTotalAmount());
////            inbound.setPraTax(inbound.getPreTax());
////            inbound.setPraAmount(inbound.getPreAmount());
////            inbound.setPraTaxAmount(inbound.getPreTaxAmount());
////            inbound.setPraInboundNum(inbound.getPreInboundNum());
////            inbound.setPraMainUnitNum(inbound.getPreMainUnitNum());
////            inbound.setCreateTime(new Date());
////            inbound.setCreateBy(reqVo.getCreateBy());
//////            if(StringUtils.isNotBlank(inbound.getUpdateBy())){
//////                inbound.setUpdateTime(new Date());
//////            }
////            List<InboundProduct> products= Lists.newArrayList();
////            for (InboundItemReqVo vo : reqVo.getPurchaseItemVos()){
////                InboundProduct product = BeanCopyUtils.copy(vo, InboundProduct.class);
////                product.setInboundOderCode(inbound.getinboundOderCode());
////                product.setPreInboundNum(vo.getNum());
////                try {
////                    product.setPreInboundMainNum(vo.getNum()*map.get(vo.getSkuCode()));
////                } catch (Exception e) {
////                    log.error("error", e);
////                    throw new BizException("sku编码:"+vo.getSkuCode()+",对应的转换单位系数不存在");
////                }
////                product.setCreateTime(new Date());
////                product.setPraInboundMainNum(product.getPreInboundMainNum());
////                product.setPraInboundNum(vo.getNum());
////                product.setPreTaxAmount(vo.getTotalPrice());
////                product.setPreTaxPurchaseAmount(vo.getPrice());
////                product.setPraTaxPurchaseAmount(vo.getPrice());
////                product.setPraTaxAmount(vo.getTotalPrice());
////                product.setPurchaseNorms(vo.getSpec());
////                products.add(product);
////            }
////            po.setInbound(inbound);
////            po.setInboundProducts(products);
////            return po;
////        } catch (Exception e) {
////            log.error("error", e);
////            if(e instanceof BizException){
////                throw new BizException(e.getMessage());
////            }else {
////                throw new BizException("入库单保存请求reqVo转换Po异常");
////            }
////        }
//        return null;
//    }
//}
