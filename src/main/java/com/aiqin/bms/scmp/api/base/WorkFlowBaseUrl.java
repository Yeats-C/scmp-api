package com.aiqin.bms.scmp.api.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-15
 * @time: 15:08
 */
@Component
public class WorkFlowBaseUrl {
    /**
     * 前端页面标识符，用于和审批流页面区分
     */
    @Value("${BaseParam.authority}")
    public String authority;
    /**
     * 前端页面标识符，用于和审批流页面区分
     */
    @Value("${WorkFlowBaseUrl.callBackBaseUrl}")
    public String callBackBaseUrl;
    /**
     * 合同申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applyContract}")
    public String applyContractUrl;
    /**
     * 供应商申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applySupplier}")
    public String applySupplierUrl;

    /**
     * 供应商集团申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applySupplierGroup}")
    public String applySupplierGroupUrl;

    /**
     * 供应商账户申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applySupplierAccount}")
    public String applySupplierAccountUrl;

    /**
     * 采购申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.purchaseUrl.applyPurchase}")
    public String applyPurchase;

    /**
     * 退供申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.purchaseUrl.applyRefund}")
    public String applyRefund;

    /**
     * 变价
     */
    @Value("${WorkFlowBaseUrl.productUrl.variablePrice}")
    public String variableUrl;
    /**
     * sku审批
     */
    @Value("${WorkFlowBaseUrl.productUrl.applySku}")
    public String applySku;
    /**
     * 商品配置
     */
    @Value("${WorkFlowBaseUrl.productUrl.applySkuConfig}")
    public String applySkuConfig;
    /**
     * 移库查看详情页面
     */
    @Value("${WorkFlowBaseUrl.productUrl.movement}")
    public String movement;

    /**
     * 调拨申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.productUrl.applyAllocattion}")
    public String applyAllocattion;
    /**
     * 报废
     */
    @Value("${WorkFlowBaseUrl.productUrl.scrap}")
    public String scrap;

    /**
     * 销售区域查看详情页面
     */
    @Value("${WorkFlowBaseUrl.productUrl.applySaleArea}")
    public String applySaleArea;

    /**
     * host
     */
    @Value("${WorkFlowBaseHost.supplierHost}")
    public String supplierHost;
}
