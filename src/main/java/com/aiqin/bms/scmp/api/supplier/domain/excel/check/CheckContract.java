package com.aiqin.bms.scmp.api.supplier.domain.excel.check;

import com.aiqin.bms.scmp.api.base.CostUndertakes;
import com.aiqin.bms.scmp.api.base.Generals;
import com.aiqin.bms.scmp.api.base.MeasurementMethods;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryProductBrandRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.excel.im.ContractImportNew;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractBrandReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractCategoryReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractPurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.util.NumberConvertUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className CheckContract
 * @date 2019/9/3 11:07
 */
public class CheckContract {

    private List<String> error;
    private ContractImportNew contractImportNew;
    private Map<String, SupplyCompany> supplyCompanyNames;
    private Map<String, SupplierDictionaryInfo> dictionaryInfoList;
    private Map<String, PurchaseGroupVo> purchaseGroupVoList;
    private Map<String, ProductCategoryRespVO> categoryList;
    private Map<String, QueryProductBrandRespVO> brandList;
    private ApplyContractReqVo reqVo;

    //构造
    public CheckContract(ContractImportNew contractImportNew, Map<String, SupplyCompany> supplyCompanyNames, Map<String, SupplierDictionaryInfo> dictionaryInfoList,
                         Map<String, PurchaseGroupVo> purchaseGroupVoList, Map<String, ProductCategoryRespVO> categoryList, Map<String, QueryProductBrandRespVO> brandList) {
        this.contractImportNew = contractImportNew;
        this.supplyCompanyNames = supplyCompanyNames;
        this.dictionaryInfoList = dictionaryInfoList;
        this.purchaseGroupVoList = purchaseGroupVoList;
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.error = Lists.newArrayList();
        this.reqVo = new ApplyContractReqVo();
    }

    //检查必填项
    public CheckContract checkCommonData() {
        //合同名称
        if (StringUtils.isBlank(contractImportNew.getYearName())) {
            error.add("合同名称不能为空");
        } else {
            reqVo.setYearName(contractImportNew.getYearName().trim());
        }
        //年度
        if (StringUtils.isBlank(contractImportNew.getYear())) {
            error.add("年度不能为空");
        } else {
            reqVo.setYear(contractImportNew.getYear().trim());
        }
        //供应商
        if (StringUtils.isBlank(contractImportNew.getSupplierName().trim())) {
            error.add("供应商名称不能为空");
        } else {
            SupplyCompany supplyCompany = supplyCompanyNames.get(contractImportNew.getSupplierName().trim());
            if (null == supplyCompany) {
                error.add("未找到对应名称的供应商");
            } else {
                reqVo.setSupplierName(supplyCompany.getSupplyName());
                reqVo.setSupplierCode(supplyCompany.getSupplyCode());
            }
        }
        //合同类型
        if (StringUtils.isBlank(contractImportNew.getContractTypeName())) {
            error.add("合同类型不能为空");
        } else {
            SupplierDictionaryInfo info = dictionaryInfoList.get(contractImportNew.getContractTypeName().trim());
            if (null == info) {
                error.add("未找到对应的合同类型");
            } else {
                reqVo.setContractTypeCode(info.getSupplierDictionaryValue());
                reqVo.setContractTypeName(info.getSupplierContent());
            }
        }
        //合同开始日期
        Boolean flag = true;
        if (StringUtils.isBlank(contractImportNew.getStartTime())) {
            error.add("合同开始时间不能为空");
            flag = false;
        }
        //合同结束日期
        if (StringUtils.isBlank(contractImportNew.getEndTime())) {
            error.add("合同结束时间不能为空");
            flag = false;
        }
        if (flag) {
            //比较开始时间和结束时间
            Long startTime = Long.parseLong(contractImportNew.getStartTime().trim().replace("-", ""));
            Long endTime = Long.parseLong(contractImportNew.getEndTime().trim().replace("-", ""));
            if (startTime > endTime) {
                error.add("合同开始时间不能晚于合同结束时间");
            } else {
                try {
                    reqVo.setStartTime(Date.from(LocalDate.parse(contractImportNew.getStartTime().trim()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                    reqVo.setEndTime(Date.from(LocalDate.parse(contractImportNew.getEndTime().trim()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                } catch (Exception e) {
                    error.add("时间格式不正确");
                }
            }
        }
        //最低起订金额
        if (StringUtils.isBlank(contractImportNew.getMinAmount())) {
            error.add("最低起订金额不能为空");
        } else {
            try {
                BigDecimal minAmount = new BigDecimal(contractImportNew.getMinAmount().trim());
                if (minAmount.compareTo(BigDecimal.ZERO) < -1) {
                    error.add("最低起订金额不能小于0");
                } else {
                    reqVo.setMinAmount(minAmount);
                }
            } catch (Exception e) {
                error.add("最低起订金额格式不正确");
            }
        }
        //结账日
        if (StringUtils.isBlank(contractImportNew.getCheckoutDate())) {
            error.add("结账日不能为空");
        } else {
            try {
                Long checkoutDate = Long.parseLong(contractImportNew.getCheckoutDate().trim());
                if (checkoutDate <= 1 && checkoutDate <= 31) {
                    error.add("结账日只能在1至31之间");
                } else {
                    reqVo.setCheckoutDate(contractImportNew.getCheckoutDate());
                }
            } catch (Exception e) {
                error.add("结账日格式不正确");
            }
        }
        //送货周期
        if (StringUtils.isNotBlank(contractImportNew.getDeliveryCycle())) {
            try {
                Integer deliveryCycle = Integer.parseInt(contractImportNew.getDeliveryCycle().trim());
                reqVo.setDeliveryCycle(deliveryCycle);
            } catch (NumberFormatException e) {
                error.add("送货周期格式不正确");
            }
        }
        //税率
        if (StringUtils.isBlank(contractImportNew.getTaxRate())) {
            error.add("税率不能为空");
        } else {
            try {
                reqVo.setTaxRate(new BigDecimal(contractImportNew.getTaxRate().trim()));
            } catch (Exception e) {
                error.add("税率格式不正确");
            }
        }
        //折扣
        if (StringUtils.isNotBlank(contractImportNew.getDiscount())) {
            try {
                reqVo.setDiscount(contractImportNew.getDiscount().trim());
            } catch (NumberFormatException e) {
                error.add("折扣格式不正确");
            }
        }
        //退换货保证
        if (StringUtils.isBlank(contractImportNew.getReturnGuarantee())) {
            error.add("退换货保证不能为空");
        } else {
            Generals generals = Generals.getAll().get(contractImportNew.getReturnGuarantee().trim());
            if (null == generals) {
                error.add("未找到对应的退换货保证");
            }
            reqVo.setReturnGuarantee(generals.getType());
            //如果选择的是,那么退换货保证天数和质保金不能为空
            if (Objects.equals(Generals.YES, generals)) {
                if (StringUtils.isBlank(contractImportNew.getReturnGuaranteeDay())) {
                    error.add("退换货保证天数不能为空");
                } else {
                    try {
                        Integer returnGuaranteeDay = Integer.parseInt(contractImportNew.getReturnGuaranteeDay().trim());
                        reqVo.setReturnGuaranteeDay(returnGuaranteeDay);
                    } catch (NumberFormatException e) {
                        error.add("退换货保证天数格式不正确");
                    }
                }
                if (StringUtils.isBlank(contractImportNew.getWarranty())) {
                    error.add("质保金不能为空");
                } else {
                    try {
                        reqVo.setWarranty(new BigDecimal(contractImportNew.getWarranty().trim()));
                    } catch (NumberFormatException e) {
                        error.add("质保金数格式不正确");
                    }
                }
            }
        }
        //供货渠道类别
        if (StringUtils.isBlank(contractImportNew.getCategoriesSupplyChannelsName())) {
            error.add("供货渠道类别不能为空");
        } else {
            SupplierDictionaryInfo info = dictionaryInfoList.get(contractImportNew.getCategoriesSupplyChannelsName().trim());
            if (null == info) {
                error.add("未找到对应的供货渠道类别");
            } else {
                reqVo.setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
                reqVo.setCategoriesSupplyChannelsName(info.getSupplierContent());
            }
        }
        //合同费用
        if (StringUtils.isNotBlank(contractImportNew.getContractCost())) {
            try {
                reqVo.setContractCost(NumberConvertUtils.stringParseBigDecimal(contractImportNew.getContractCost().trim()));
            } catch (NumberFormatException e) {
                error.add("合同费用格式不正确");
            }
        }
        //平均毛利率
        if (StringUtils.isNotBlank(contractImportNew.getAverageGrossMargin())) {
            try {
                reqVo.setAverageGrossMargin(NumberConvertUtils.stringParseBigDecimal(contractImportNew.getAverageGrossMargin().trim()));
            } catch (NumberFormatException e) {
                error.add("平均毛利率格式不正确");
            }
        }
        //合同属性
        if (StringUtils.isNotBlank(contractImportNew.getContractProperty())) {
            reqVo.setContractProperty(contractImportNew.getContractProperty().trim());
        }
        //品类
        if (StringUtils.isBlank(contractImportNew.getCaReqVos())) {
            error.add("品类不能为空");
        } else {
            String[] categoris = contractImportNew.getCaReqVos().trim().split(",");
            List<ApplyContractCategoryReqVo> categoryReqVos = Lists.newArrayList();
            ApplyContractCategoryReqVo contractCategoryReqVo = null;
            for (String s : categoris) {
                ProductCategoryRespVO productCategoryRespVO = categoryList.get(s);
                if (null == productCategoryRespVO) {
                    error.add(s + "品类不存在");
                } else {
                    contractCategoryReqVo = new ApplyContractCategoryReqVo();
                    contractCategoryReqVo.setCategoryCode(productCategoryRespVO.getCategoryId());
                    contractCategoryReqVo.setCategoryName(productCategoryRespVO.getCategoryName());
                    categoryReqVos.add(contractCategoryReqVo);
                }
            }
            reqVo.setCategoryReqVos(categoryReqVos);
        }
        //品牌
        if (StringUtils.isBlank(contractImportNew.getBrandReqVos())) {
            error.add("品牌不能为空");
        } else {
            String[] brands = contractImportNew.getBrandReqVos().trim().split(",");
            List<ApplyContractBrandReqVo> brandReqVos = Lists.newArrayList();
            ApplyContractBrandReqVo brandReqVo = null;
            for (String s : brands) {
                QueryProductBrandRespVO productBrandRespVO = brandList.get(s);
                if (null == productBrandRespVO) {
                    error.add(s + "品牌不存在");
                } else {
                    brandReqVo = new ApplyContractBrandReqVo();
                    brandReqVo.setBrandCode(productBrandRespVO.getBrandId());
                    brandReqVo.setBrandName(productBrandRespVO.getBrandName());
                    brandReqVos.add(brandReqVo);
                }
            }
            reqVo.setBrandReqVos(brandReqVos);
        }
        //采购组
        if (StringUtils.isBlank(contractImportNew.getPurchaseGroupReqVos())) {
            error.add("采购组不能为空");
        } else {
            String[] purchaseGroups = contractImportNew.getPurchaseGroupReqVos().trim().split(",");
            List<ApplyContractPurchaseGroupReqVo> purchaseGroupReqVos = Lists.newArrayList();
            ApplyContractPurchaseGroupReqVo purchaseGroupReqVo = null;
            for (String s : purchaseGroups) {
                PurchaseGroupVo purchaseGroupVo = purchaseGroupVoList.get(s);
                if (null == purchaseGroupVo) {
                    error.add(s + "采购组不存在");
                } else {
                    purchaseGroupReqVo = new ApplyContractPurchaseGroupReqVo();
                    purchaseGroupReqVo.setPurchasingGroupCode(purchaseGroupVo.getPurchaseGroupCode());
                    purchaseGroupReqVo.setPurchasingGroupName(purchaseGroupVo.getPurchaseGroupName());
                    purchaseGroupReqVos.add(purchaseGroupReqVo);
                }
            }
            reqVo.setPurchaseGroupReqVos(purchaseGroupReqVos);
        }
        //备注
        reqVo.setRemark(contractImportNew.getRemark());
        //付款方式
        if (StringUtils.isBlank(contractImportNew.getSettlementMethodName())) {
            error.add("付款方式不能为空");
        } else {
            SupplierDictionaryInfo info = dictionaryInfoList.get(contractImportNew.getSettlementMethodName().trim());
            if (null == info) {
                error.add("未找到对应的付款方式");
            } else {
                reqVo.setSettlementMethod(Byte.parseByte(info.getSupplierDictionaryValue()));
                reqVo.setSettlementMethodName(info.getSupplierContent());
            }
        }
        //付款期
        if (StringUtils.isBlank(contractImportNew.getPaymentPeriod())) {
            error.add("付款期不能为空");
        } else {
            try {
                Integer paymentPeriod = Integer.parseInt(contractImportNew.getPaymentPeriod().trim());
                reqVo.setPaymentPeriod(paymentPeriod);
            } catch (NumberFormatException e) {
                error.add("付款期格式不正确");
            }
        }
        //配送费
        if (StringUtils.isBlank(contractImportNew.getShippingFee())) {
            error.add("配送费不能为空");
        } else {
            try {
                reqVo.setShippingFee(new BigDecimal(contractImportNew.getShippingFee().trim()));
            } catch (NumberFormatException e) {
                error.add("配送费格式不正确");
            }
        }
        //送货费承担方
        if (StringUtils.isBlank(contractImportNew.getDeliveryCharges())) {
            error.add("送货费承担方不能为空");
        } else {
            CostUndertakes costUndertakes = CostUndertakes.getAll().get(contractImportNew.getDeliveryCharges().trim());
            if (null == costUndertakes) {
                error.add("未找到对应的送货费承担方");
            }
            reqVo.setDeliveryCharges(costUndertakes.getType());
        }
        //卸货费承担方
        if (StringUtils.isBlank(contractImportNew.getUnloadingFee())) {
            error.add("卸货费承担方不能为空");
        } else {
            CostUndertakes costUndertakes = CostUndertakes.getAll().get(contractImportNew.getUnloadingFee().trim());
            if (null == costUndertakes) {
                error.add("未找到对应的卸货费承担方");
            }
            reqVo.setUnloadingFee(costUndertakes.getType().longValue());
        }
        //固定返利比例
        if (StringUtils.isBlank(contractImportNew.getReturnRate())) {
            error.add("固定返利比例不能为空");
        } else {
            try {
                reqVo.setReturnRate(new BigDecimal(contractImportNew.getReturnRate().trim()));
            } catch (NumberFormatException e) {
                error.add("固定返利比例格式不正确");
            }
        }
        //计量方式
        if (StringUtils.isBlank(contractImportNew.getFixedRebateType())) {
            error.add("计量方式不能为空");
        } else {
            MeasurementMethods measurementMethods = MeasurementMethods.getAll().get(contractImportNew.getFixedRebateType().trim());
            if (null == measurementMethods) {
                error.add("未找到对应的计量方式");
            }
            reqVo.setFixedRebateType(measurementMethods.getType());
        }
        //备注
        reqVo.setComment(contractImportNew.getComment());
        return this;
    }

    public List<String> getError() {
        return error;
    }

    public ApplyContractReqVo getReqVo() {
        return reqVo;
    }
}
