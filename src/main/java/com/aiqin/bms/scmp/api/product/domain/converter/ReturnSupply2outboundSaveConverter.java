package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.product.domain.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupply;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyItem;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyToOutBoundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author HuangLong
 * @date 2019/1/17
 */
@Component
public class ReturnSupply2outboundSaveConverter implements Converter<ReturnSupplyToOutBoundReqVo, OutboundReqVo> {
//    private SkuService skuService;

//    public ReturnSupply2outboundSaveConverter(SkuService skuService) {
//        this.skuService=skuService;
//    }
    private SkuService skuService;

    public ReturnSupply2outboundSaveConverter(SkuService skuService) {
        this.skuService=skuService;
    }
    @Override
    public OutboundReqVo convert(ReturnSupplyToOutBoundReqVo reqMainVo) {
        try {
            ReturnSupply reqVo = reqMainVo.getReturnSupply();
            if(CollectionUtils.isEmpty(reqMainVo.getReturnSupplyItems())){
                throw new BizException("出库单保存请求vo的商品项集合不能为空");
            }
            SupplyComDetailRespVO supplyComDetailRespVO = skuService.detailByCode(reqVo.getSupplyCode());
            if(Objects.isNull(supplyComDetailRespVO)){
                throw new GroundRuntimeException("获取供货单位信息失败");
            }

            if(null != reqVo){
                OutboundReqVo outbound = new OutboundReqVo();
                outbound.setProvinceCode(supplyComDetailRespVO.getProvinceId());
                outbound.setProvinceName(supplyComDetailRespVO.getProvinceName());
                outbound.setCityCode(supplyComDetailRespVO.getCityId());
                outbound.setCityName(supplyComDetailRespVO.getCityName());
                outbound.setCountyCode(supplyComDetailRespVO.getDistrictId());
                outbound.setCountyName(supplyComDetailRespVO.getDistrictName());
                outbound.setConsignee(supplyComDetailRespVO.getContactName());
                outbound.setConsigneeNumber(supplyComDetailRespVO.getMobilePhone());
                outbound.setConsigneeRate(supplyComDetailRespVO.getZipCode());
                outbound.setDetailedAddress(supplyComDetailRespVO.getAddress());
                //公司
                outbound.setCompanyCode(reqVo.getCompanyCode());
                outbound.setCompanyName(reqVo.getCompanyName());
                //物流中心
                outbound.setLogisticsCenterCode(reqVo.getTransportCenterCode());
                outbound.setLogisticsCenterName(reqVo.getTransportCenterName());
                //库房
                outbound.setWarehouseCode(reqVo.getWarehouseCode());
                outbound.setWarehouseName(reqVo.getWarehouseName());
                //供应单位
                outbound.setSupplierCode(reqVo.getSupplyCode());
                outbound.setSupplierName(reqVo.getSupplyName());
                //状态
                outbound.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
                outbound.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
                //类型
                outbound.setOutboundTypeCode(OutboundTypeEnum.RETURN_SUPPLY.getCode());
                outbound.setOutboundTypeName(OutboundTypeEnum.RETURN_SUPPLY.getName());
                //原始单号
                outbound.setSourceOderCode(reqVo.getCode());
                //出库时间
//                outbound.setOutboundTime(reqVo.getOutboundTime());
                //预计出库数量
                outbound.setPreOutboundNum(reqVo.getTotalNum());
                //预计主出库数量
                outbound.setPreMainUnitNum(reqVo.getTotalNum());
                //预计含税总金额
                outbound.setPreTaxAmount(reqVo.getTotalAmount());
                //预计无税总金额
                outbound.setPreAmount(reqVo.getTotalAmount());
                //预计税额
//                outbound.setPreTax(reqVo.getTotalAmount()-reqVo.getTotalAmount());
                outbound.setCreateBy(reqVo.getCreateBy());
                outbound.setUpdateBy(reqVo.getUpdateBy());
                outbound.setCreateTime(new Date());
                outbound.setUpdateTime(new Date());

                List<String> skuCodes = reqMainVo.getReturnSupplyItems().stream().map(ReturnSupplyItem::getSkuCode).collect(Collectors.toList());
                List<ProductSkuCheckout> skuCheckOuts = skuService.getSkuCheckOuts(skuCodes);
                Map<String, Long> map = skuCheckOuts.stream().collect(Collectors.toMap(ProductSkuCheckout::getSkuCode, ProductSkuCheckout::getInputTaxRate, (k1, k2) -> k2));
                Map<String, PurchaseItemRespVo> map2 = skuService.getSalesSkuList(skuCodes).stream().collect(Collectors.toMap(PurchaseItemRespVo::getSkuCode, Function.identity(),(k1, k2)->k2));
                List<ReturnSupplyItem> items = reqMainVo.getReturnSupplyItems();
                List<OutboundProductReqVo> parts =Lists.newArrayList();
                long noTaxTotalAmount = 0;
                for (ReturnSupplyItem item : items) {
                    OutboundProductReqVo outboundProduct = new OutboundProductReqVo();
                    //sku
                    outboundProduct.setSkuCode(item.getSkuCode());
                    outboundProduct.setSkuName(item.getSkuName());
                    //图片
                    outboundProduct.setPictureUrl(map2.get(item.getSkuCode()).getPicUrl());
                    //规格
                    outboundProduct.setNorms(map2.get(item.getSkuCode()).getSpec());
                    outboundProduct.setOutboundNorms(item.getSpec());
                    //单位
                    outboundProduct.setUnitCode(item.getUnitCode());
                    outboundProduct.setUnitName(item.getUnitName());
                    //进货规格
//                    outboundProduct.setPurchaseNorms(iLockStockItemReqVo.getPurchaseNorms());
                    //预计出库数量
                    outboundProduct.setPreOutboundNum(item.getNum());
                    //预计含税进价
                    outboundProduct.setPreTaxPurchaseAmount(item.getPrice());
                    //预计含税总价
                    outboundProduct.setPreTaxAmount(item.getTotalPrice());
                    outboundProduct.setColorCode(item.getColorCode());
                    outboundProduct.setColorName(item.getColorName());
                    outboundProduct.setCreateBy(outbound.getCreateBy());
                    outboundProduct.setUpdateBy(outbound.getUpdateBy());
                    outboundProduct.setPreOutboundMainNum(item.getNum());
                    outboundProduct.setCreateTime(new Date());
                    outboundProduct.setUpdateTime(new Date());
                    //计算不含税单价
                    Long aLong = map.get(item.getSkuCode());
                    Long noTaxPrice = Calculate.computeNoTaxPrice(item.getPrice(), aLong);
                    outboundProduct.setOutboundBaseContent("1");
                    //计算不含税总价 (现在是主单位数量 * 单价）
//                long noTaxTotalPrice = noTaxPrice * o.getNum();
                    long noTaxTotalPrice = noTaxPrice * item.getNum();
                    noTaxTotalAmount = noTaxTotalPrice;
                    parts.add(outboundProduct);
                }
                outbound.setPreAmount(noTaxTotalAmount);
                outbound.setPreTax(reqVo.getTotalAmount()-noTaxTotalAmount);
                outbound.setList(parts);
                return outbound;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof BizException){
                throw new BizException(e.getMessage());
            }else {
                throw new BizException("出库单保存请求reqVo转换Po异常");
            }
        }
        return null;
    }
}
