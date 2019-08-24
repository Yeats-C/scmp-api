package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderMainReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderProductItemReqVO;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 退供
 * @author HuangLong
 * @date 2019/1/17
 */
@Component
@Slf4j
public class SupplyReturnOrderMainReqVO2InboundSaveConverter implements Converter<SupplyReturnOrderMainReqVO, InboundReqSave> {

    private SkuService skuService;
    public SupplyReturnOrderMainReqVO2InboundSaveConverter(SkuService skuService) {
        this.skuService = skuService;
    }

    @Override
    public InboundReqSave convert(SupplyReturnOrderMainReqVO reqVo) {
        try {
            if(CollectionUtils.isEmpty(reqVo.getOrderItems())){
                throw new BizException("入库单保存请求vo的商品项集合不能为空");
            }
            List<String> skuCodes = reqVo.getOrderItems().stream().map(SupplyReturnOrderProductItemReqVO::getSkuCode).collect(Collectors.toList());
//            Map<String, Long> map = skuService.getSkuConvertNumBySkuCodes(skuCodes);
//            InboundSavePo po = new InboundSavePo();
            List<ProductSkuCheckout> skuCheckOuts = skuService.getSkuCheckOuts(skuCodes);
            Map<String, Long> map = skuCheckOuts.stream().collect(Collectors.toMap(ProductSkuCheckout::getSkuCode, ProductSkuCheckout::getOutputTaxRate, (k1, k2) -> k2));
            InboundReqSave inbound = new InboundReqSave();
            SupplyReturnOrderInfoReqVO mainOrderInfo = reqVo.getMainOrderInfo();
            BeanUtils.copyProperties(mainOrderInfo,inbound);
            //入库类型
            inbound.setInboundTypeCode(InboundTypeEnum.ORDER.getCode());
            inbound.setInboundTypeName(InboundTypeEnum.ORDER.getName());
            //入库状态
            inbound.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
            inbound.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
            inbound.setCompanyCode(mainOrderInfo.getCompanyCode());
            inbound.setCompanyName(mainOrderInfo.getCompanyName());
            inbound.setInboundTime(new Date());
            inbound.setSourceOderCode(mainOrderInfo.getReturnOrderCode());
            inbound.setLogisticsCenterCode(mainOrderInfo.getTransportCenterCode());
            inbound.setLogisticsCenterName(mainOrderInfo.getTransportCenterName());
            inbound.setSupplierName(mainOrderInfo.getSupplierName());
            inbound.setSupplierCode(mainOrderInfo.getSupplierCode());
            inbound.setPreArrivalTime(mainOrderInfo.getReceivingTime());
            inbound.setPreInboundNum(mainOrderInfo.getProductNum());
            inbound.setPreMainUnitNum(mainOrderInfo.getProductNum());
            inbound.setPreTaxAmount(mainOrderInfo.getProductTotalAmount());
            inbound.setPreAmount(mainOrderInfo.getProductTotalAmount());
            inbound.setPreTax(0L);
//            inbound.setPraTax(inbound.getPreTax());
//            inbound.setPraAmount(inbound.getPreAmount());
//            inbound.setPraTaxAmount(inbound.getPreTaxAmount());
//            inbound.setPraInboundNum(inbound.getPreInboundNum());
//            inbound.setPraMainUnitNum(inbound.getPreMainUnitNum());

            // 发货人
            inbound.setShipper(mainOrderInfo.getConsignee());
            inbound.setShipperNumber(mainOrderInfo.getConsigneePhone());
            inbound.setShipperRate(mainOrderInfo.getZipCode());
            // 地址
            inbound.setProvinceCode(mainOrderInfo.getProvinceCode());
            inbound.setProvinceName(mainOrderInfo.getProvinceName());
            inbound.setCityCode(mainOrderInfo.getCityCode());
            inbound.setCityName(mainOrderInfo.getCityName());
            inbound.setCountyCode(mainOrderInfo.getDistrictCode());
            inbound.setCountyName(mainOrderInfo.getDistrictName());

            // 详细地址
            inbound.setDetailedAddress(mainOrderInfo.getDetailAddress());
            inbound.setCreateTime(new Date());
            inbound.setCreateBy(mainOrderInfo.getOperator());
//            if(StringUtils.isNotBlank(inbound.getUpdateBy())){
//                inbound.setUpdateTime(new Date());
//            }
            long noTaxTotalAmount = 0L;
            List<InboundProductReqVo> products= Lists.newArrayList();
            for (SupplyReturnOrderProductItemReqVO vo : reqVo.getOrderItems()){
//                InboundProduct product = BeanCopyUtils.copy(vo, InboundProduct.class);
                InboundProductReqVo product = BeanCopyUtils.copy(vo, InboundProductReqVo.class);
//                product.setInboundOderCode(inbound.getinboundOderCode());
                product.setPreInboundNum(vo.getNum());
                try {
                    product.setPreInboundMainNum(vo.getNum());
                    //计算不含税单价
                    Long aLong = map.get(vo.getSkuCode());
                    Long noTaxPrice = Calculate.computeNoTaxPrice(vo.getPrice(), aLong);

                    //计算不含税总价 (现在是主单位数量 * 单价）
//                long noTaxTotalPrice = noTaxPrice * o.getNum();
                    long noTaxTotalPrice = noTaxPrice * vo.getNum();
                    noTaxTotalAmount += noTaxTotalPrice;
                } catch (Exception e) {
                    log.error("error", e);
                    throw new BizException("sku编码:"+vo.getSkuCode()+",对应的转换单位系数不存在");
                }
                product.setCreateTime(new Date());
                product.setPreInboundMainNum(product.getPreInboundMainNum());
//                product.setPraInboundNum(vo.getNum());
                product.setPreTaxAmount(vo.getAmount());
                product.setPreTaxPurchaseAmount(vo.getPrice());
//                product.setPraTaxPurchaseAmount(vo.getPrice());
//                product.setPraTaxAmount(vo.getAmount());
                product.setInboundNorms(vo.getSpec());
                product.setInboundBaseContent("1");
                product.setLinenum(vo.getProductLineNum());
                products.add(product);
            }
//            po.setInbound(inbound);
            inbound.setPreAmount(noTaxTotalAmount);
            inbound.setPreTax(mainOrderInfo.getProductTotalAmount()-noTaxTotalAmount);
            inbound.setList(products);
            return inbound;
        } catch (Exception e) {
            log.error("error", e);
            if(e instanceof BizException){
                throw new BizException(e.getMessage());
            }else {
                throw new BizException("入库单保存请求reqVo转换Po异常");
            }
        }
    }
}
