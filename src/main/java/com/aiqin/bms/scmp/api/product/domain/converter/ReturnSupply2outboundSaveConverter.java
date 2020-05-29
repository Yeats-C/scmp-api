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
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private SkuService skuService;
    @Resource
    private SupplyCompanyDao supplyCompanyDao;

    public ReturnSupply2outboundSaveConverter(SkuService skuService, SupplyCompanyDao supplyCompanyDao) {
        this.skuService = skuService;
        this.supplyCompanyDao = supplyCompanyDao;
    }

    @Override
    public OutboundReqVo convert(ReturnSupplyToOutBoundReqVo request) {
        try {
            RejectRecord rejectRecord = request.getRejectRecord();
            if (CollectionUtils.isEmpty(request.getDetailList())) {
                throw new BizException("出库单保存请求vo的商品项集合不能为空");
            }
            if (CollectionUtils.isEmpty(request.getBatchList())) {
                throw new BizException("出库单保存请求vo的批次项集合不能为空");
            }

            SupplyCompany supplyCompany = supplyCompanyDao.detailByCode(rejectRecord.getSupplierCode(), rejectRecord.getCompanyCode());

            if (null != rejectRecord) {
                OutboundReqVo outbound = new OutboundReqVo();
                outbound.setProvinceCode(rejectRecord.getProvinceId());
                outbound.setProvinceName(rejectRecord.getProvinceName());
                outbound.setCityCode(rejectRecord.getCityId());
                outbound.setCityName(rejectRecord.getCityName());
                outbound.setCountyCode(rejectRecord.getDistrictId());
                outbound.setCountyName(rejectRecord.getDistrictName());
                outbound.setConsignee(rejectRecord.getSupplierPerson());
                outbound.setConsigneeNumber(rejectRecord.getSupplierMobile());
                outbound.setConsigneeRate(supplyCompany.getZipCode());
                outbound.setDetailedAddress(rejectRecord.getDetailAddress());
                //公司
                outbound.setCompanyCode(rejectRecord.getCompanyCode());
                outbound.setCompanyName(rejectRecord.getCompanyName());
                //仓库
                outbound.setLogisticsCenterCode(rejectRecord.getTransportCenterCode());
                outbound.setLogisticsCenterName(rejectRecord.getTransportCenterName());
                //库房
                outbound.setWarehouseCode(rejectRecord.getWarehouseCode());
                outbound.setWarehouseName(rejectRecord.getWarehouseName());
                //供应单位
                outbound.setSupplierCode(rejectRecord.getSupplierCode());
                outbound.setSupplierName(rejectRecord.getSupplierName());
                //状态
                outbound.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
                outbound.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
                //类型
                outbound.setOutboundTypeCode(OutboundTypeEnum.RETURN_SUPPLY.getCode());
                outbound.setOutboundTypeName(OutboundTypeEnum.RETURN_SUPPLY.getName());
                //原始单号
                outbound.setSourceOderCode(rejectRecord.getRejectRecordCode());
                //预计出库数量
                outbound.setPreOutboundNum(rejectRecord.getTotalCount());
                //预计主出库数量
                outbound.setPreMainUnitNum(rejectRecord.getTotalCount());
                //预计含税总金额
                outbound.setPreTaxAmount(rejectRecord.getGiftTaxAmount().add(
                        rejectRecord.getReturnTaxAmount()).add(rejectRecord.getProductTaxAmount()));
                outbound.setCreateBy(rejectRecord.getCreateByName());
                outbound.setUpdateBy(rejectRecord.getUpdateByName());
                outbound.setRemark(rejectRecord.getRemark());

                List<String> skuCodes = request.getDetailList().stream().map(RejectRecordDetail::getSkuCode).collect(Collectors.toList());
                List<ProductSkuCheckout> skuCheckOuts = skuService.getSkuCheckOuts(skuCodes);
                Map<String, BigDecimal> map = skuCheckOuts.stream().collect(Collectors.toMap(ProductSkuCheckout::getSkuCode, ProductSkuCheckout::getInputTaxRate, (k1, k2) -> k2));
                Map<String, PurchaseItemRespVo> map2 = skuService.getSalesSkuList(skuCodes).stream().collect(Collectors.toMap(PurchaseItemRespVo::getSkuCode, Function.identity(), (k1, k2) -> k2));
                List<RejectRecordDetail> items = request.getDetailList();
                List<OutboundProductReqVo> productList = Lists.newArrayList();
                BigDecimal noTaxTotalAmount = BigDecimal.valueOf(0);
                for (RejectRecordDetail item : items) {
                    OutboundProductReqVo outboundProduct = new OutboundProductReqVo();
                    //税率
                    outboundProduct.setTax(item.getTaxRate());
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
                    outboundProduct.setLinenum(item.getLineCode().longValue());
                    //计算不含税单价
                    BigDecimal aLong = map.get(item.getSkuCode());
                    BigDecimal noTaxPrice = Calculate.computeNoTaxPrice(item.getProductAmount(), aLong);
                    outboundProduct.setOutboundBaseContent("1");
                    outboundProduct.setOutboundBaseUnit("1");
                    //计算不含税总价 (现在是主单位数量 * 单价）
                    BigDecimal noTaxTotalPrice = noTaxPrice.multiply(BigDecimal.valueOf(item.getProductCount())).setScale(4, BigDecimal.ROUND_HALF_UP);
                    noTaxTotalAmount = noTaxTotalAmount.add(noTaxTotalPrice);
                    productList.add(outboundProduct);
                }
                //预计无税总金额
                outbound.setPreAmount(noTaxTotalAmount);
                //预计税额
                outbound.setPreTax(outbound.getPreTaxAmount().subtract(noTaxTotalAmount));
                outbound.setList(productList);

                // 批次信息
                if(org.apache.commons.collections.CollectionUtils.isNotEmpty(request.getBatchList())){
                    List<OutboundBatch> infoBatch = BeanCopyUtils.copyList(request.getBatchList(), OutboundBatch.class);
                    outbound.setOutboundBatches(infoBatch);
                }
                return outbound;
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if (e instanceof BizException) {
                throw new BizException(e.getMessage());
            } else {
                throw new BizException("出库单保存请求reqVo转换Po异常");
            }
        }
        return null;
    }
}
