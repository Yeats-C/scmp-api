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
     * 制造商申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applyManu}")
    public String applyManu;

    /**
     * 采购申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applyPurchase}")
    public String applyPurchase;

    /**
     * 退供申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applyRefund}")
    public String applyRefund;

    /**
     * 调拨申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applyAllocattion}")
    public String applyAllocattion;

    /**
     * 商品申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applyGoods}")
    public String applyGoods;

    /**
     * 商品配置申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applyGoodsConfig}")
    public String applyGoodsConfig;

    /**
     * 商品价格申请详情前面的url
     */
    @Value("${WorkFlowBaseUrl.supplierUrl.applyGoodsPrice}")
    public String applyGoodsPrice;


    /**
     * host
     */
    @Value("${WorkFlowBaseHost.supplierHost}")
    public String supplierHost;


    /**
     * 前端页面标识符，用于和审批流页面区分
     */
    @Value("${WorkFlowBaseUrl.productUrl.variablePrice}")
    public String variableUrl;



    @Value("${WorkFlowBaseUrl.productUrl.applySku}")
    public String applySku;

    @Value("${WorkFlowBaseUrl.productUrl.applySkuConfig}")
    public String applySkuConfig;

    /**
     * 移库查看详情页面
     */
    @Value("${WorkFlowBaseUrl.productUrl.movement}")
    public String movement;
    /**
     * 销售区域查看详情页面
     */
    @Value("${WorkFlowBaseUrl.productUrl.applySaleArea}")
    public String applySaleArea;
}
