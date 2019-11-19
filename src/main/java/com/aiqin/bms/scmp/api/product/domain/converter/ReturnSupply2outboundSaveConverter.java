package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyToOutBoundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author HuangLong
 * @date 2019/1/17
 */
@Component
@Slf4j
public class ReturnSupply2outboundSaveConverter implements Converter<ReturnSupplyToOutBoundReqVo, OutboundReqVo> {
    private SkuService skuService;

    private SupplyCompanyDao supplyCompanyDao;

    public ReturnSupply2outboundSaveConverter(SkuService skuService, SupplyCompanyDao supplyCompanyDao) {
        this.skuService = skuService;
        this.supplyCompanyDao = supplyCompanyDao;
    }

    @Override
    public OutboundReqVo convert(ReturnSupplyToOutBoundReqVo reqMainVo) {
        try {
            RejectRecord reqVo = reqMainVo.getRejectRecord();
            if (CollectionUtils.isEmpty(reqMainVo.getRejectRecordDetails())) {
                throw new BizException("出库单保存请求vo的商品项集合不能为空");
            }
            SupplyCompany supplyCompany = supplyCompanyDao.detailByCode(reqVo.getSupplierCode(), reqVo.getCompanyCode());

            if (null != reqVo) {
                OutboundReqVo outbound = new OutboundReqVo();
                outbound.setProvinceCode(reqVo.getProvinceId());
                outbound.setProvinceName(reqVo.getProvinceName());
                outbound.setCityCode(reqVo.getCityId());
                outbound.setCityName(reqVo.getCityName());
                outbound.setCountyCode(reqVo.getDistrictId());
                outbound.setCountyName(reqVo.getDistrictName());
                outbound.setConsignee(reqVo.getContactsPerson());
                outbound.setConsigneeNumber(reqVo.getContactsPersonPhone());
                outbound.setConsigneeRate(supplyCompany.getZipCode());
                outbound.setDetailedAddress(reqVo.getAddress());
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
                outbound.setSupplierCode(reqVo.getSupplierCode());
                outbound.setSupplierName(reqVo.getSupplierName());
                //状态
                outbound.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
                outbound.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
                //类型
                outbound.setOutboundTypeCode(OutboundTypeEnum.RETURN_SUPPLY.getCode());
                outbound.setOutboundTypeName(OutboundTypeEnum.RETURN_SUPPLY.getName());
                //原始单号
                outbound.setSourceOderCode(reqVo.getRejectRecordCode());
                //出库时间
//                order.setOutboundTime(reqVo.getOutboundTime());
                //预计出库数量
                outbound.setPreOutboundNum(Long.parseLong(reqVo.getSingleCount().toString()));
                //预计主出库数量
                outbound.setPreMainUnitNum(Long.parseLong(reqVo.getSingleCount().toString()));
                //预计含税总金额
                outbound.setPreTaxAmount(reqVo.getGiftAmount().add(reqVo.getReturnAmount()).add(reqVo.getProductAmount()));
                //预计无税总金额
                outbound.setPreAmount(reqVo.getUntaxedAmount());
                //预计税额
//                outbound.setPreTax(reqVo.getSumAmount()-reqVo.getSumAmount());
                outbound.setCreateBy(reqVo.getCreateByName());
                outbound.setUpdateBy(reqVo.getUpdateByName());
                outbound.setCreateTime(new Date());
                outbound.setUpdateTime(new Date());

                outbound.setRemark(reqVo.getRemark());

                List<String> skuCodes = reqMainVo.getRejectRecordDetails().stream().map(RejectRecordDetail::getSkuCode).collect(Collectors.toList());
                List<ProductSkuCheckout> skuCheckOuts = skuService.getSkuCheckOuts(skuCodes);
                Map<String, Long> map = skuCheckOuts.stream().collect(Collectors.toMap(ProductSkuCheckout::getSkuCode, ProductSkuCheckout::getInputTaxRate, (k1, k2) -> k2));
                Map<String, PurchaseItemRespVo> map2 = skuService.getSalesSkuList(skuCodes).stream().collect(Collectors.toMap(PurchaseItemRespVo::getSkuCode, Function.identity(), (k1, k2) -> k2));
                List<RejectRecordDetail> items = reqMainVo.getRejectRecordDetails();
                List<OutboundProductReqVo> parts = Lists.newArrayList();
                List<OutboundBatch> batchReqVos = Lists.newArrayList();
                BigDecimal noTaxTotalAmount = BigDecimal.valueOf(0);
                for (RejectRecordDetail item : items) {
                    OutboundProductReqVo outboundProduct = new OutboundProductReqVo();
                    //税率
                    outboundProduct.setTax(item.getTaxRate().longValue());
                    //sku
                    outboundProduct.setSkuCode(item.getSkuCode());
                    outboundProduct.setSkuName(item.getSkuName());
                    //图片
                    outboundProduct.setPictureUrl(map2.get(item.getSkuCode()).getPicUrl());
                    //规格
                    outboundProduct.setNorms(map2.get(item.getSkuCode()).getSpec());
                    //单位
                    outboundProduct.setUnitCode(map2.get(item.getSkuCode()).getUnitCode());
                    outboundProduct.setUnitName(map2.get(item.getSkuCode()).getUnitName());
                    //进货规格
                    outboundProduct.setOutboundNorms(map2.get(item.getSkuCode()).getSpec());
                    //预计出库数量
                    outboundProduct.setPreOutboundNum(item.getProductCount());
                    //预计含税进价
                    outboundProduct.setPreTaxPurchaseAmount(item.getProductAmount());
                    //预计含税总价
                    outboundProduct.setPreTaxAmount(item.getProductTotalAmount());
                    outboundProduct.setColorCode(map2.get(item.getSkuCode()).getColorCode());
                    outboundProduct.setColorName(map2.get(item.getSkuCode()).getColorName());
                    outboundProduct.setCreateBy(outbound.getCreateBy());
                    outboundProduct.setUpdateBy(outbound.getUpdateBy());
                    outboundProduct.setPreOutboundMainNum(item.getProductCount());
                    outboundProduct.setCreateTime(new Date());
                    outboundProduct.setUpdateTime(new Date());
                    outboundProduct.setLinenum(item.getLinnum().longValue());
                    //计算不含税单价
                    Long aLong = map.get(item.getSkuCode());
                    BigDecimal noTaxPrice = Calculate.computeNoTaxPrice(item.getProductAmount(), aLong);
                    outboundProduct.setOutboundBaseContent("1");
                    outboundProduct.setOutboundBaseUnit("1");
                    //计算不含税总价 (现在是主单位数量 * 单价）
//                long noTaxTotalPrice = noTaxPrice * o.getNum();
                    BigDecimal noTaxTotalPrice = noTaxPrice.multiply(BigDecimal.valueOf(item.getProductCount())).setScale(4, BigDecimal.ROUND_HALF_UP);
                    noTaxTotalAmount = noTaxTotalPrice;
                    parts.add(outboundProduct);
                    OutboundBatch outboundBatch = new OutboundBatch();
                    //sku
                    outboundBatch.setSkuCode(item.getSkuCode());
                    outboundBatch.setSkuName(item.getSkuName());
                    outboundBatch.setOutboundBatchCode(item.getBatchNo());
                    outboundBatch.setManufactureTime(item.getBatchCreateTime());
                    outboundBatch.setBatchRemark(item.getBatchRemark());
                    outboundBatch.setPraQty(item.getProductCount());
                    outboundBatch.setCreateBy(outbound.getCreateBy());
                    outboundBatch.setUpdateBy(outbound.getUpdateBy());
                    outboundBatch.setCreateTime(new Date());
                    outboundBatch.setUpdateTime(new Date());
                    outboundBatch.setSupplierName(reqVo.getSupplierName());
                    outboundBatch.setSupplierCode(reqVo.getSupplierCode());
                    batchReqVos.add(outboundBatch);
                }
                outbound.setPreAmount(noTaxTotalAmount);
                outbound.setPreTax(outbound.getPreTaxAmount().subtract(noTaxTotalAmount));
                outbound.setList(parts);
                outbound.setOutboundBatches(batchReqVos);
                return outbound;
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if(e instanceof BizException){
                throw new BizException(e.getMessage());
            } else {
                throw new BizException("出库单保存请求reqVo转换Po异常");
            }
        }
        return null;
    }
}
