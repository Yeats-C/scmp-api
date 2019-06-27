package com.aiqin.bms.scmp.api.base;

/**
 * @Classname: EncodingRuleType
 * 描述:  编码尺度类型
 * @Author: Kt.w
 * @Date: 2019/3/16
 * @Version 1.0
 * @Since 1.0
 */
public interface EncodingRuleType {
    /**
     * 采购组编码类型
     */
    String  PURCHASE_GROUP_CODE="PURCHASE_GROUP_CODE";

    /**
     * 物流中心编码类型
     */
    String  LOGISTICS_CENTER_CODE ="LOGISTICS_CENTER_CODE";

    /**
     * 库房编码类型
     */
    String WAREHOUSE_CODE = "WAREHOUSE_CODE";

    /**
     * 申请合同编码类型
     */
     String APPLY_CONTRACT_CODE = "APPLY_CONTRACT_CODE_";

    /**
     *  合同编码类型
     */
      String CONTRACT_CODE ="CONTRACT_CODE_";

    /**
     * 制造商编码类型
     */

    String  MANUFACTURER_CODE = "MANUFACTURER_CODE";

    /**
     * 供应商集团
     */
    String APPLY_SUPPLIER_CODE = "APPLY_SUPPLIER_CODE";
    String SUPPLIER_CODE = "SUPPLIER_CODE";
    /**
     * 供应商
     */
   String APPLY_SUPPLY_COM_CODE = "APPLY_SUPPLY_COM_CODE";
   String SUPPLY_COM_CODE = "SUPPLY_COM_CODE";

    /**
     * 标签编码
     */
    String TAG_INFO_CODE = "TAG_INFO_CODE";

    /**
     * 供应商评分编码
     */
    String SUPPLIER_SCORE_CODE = "SUPPLIER_SCORE_CODE";

    /**
     * 结算
     */

    String SETTLEMENT_CODE = "SETTLEMENT_CODE";
    String APPLY_SETTLEMENT_CODE = "APPLY_SETTLEMENT_CODE";

    /**
     * 供应商发货/退货
     */
    String APPLY_DELIVERY_CODE = "APPLY_DELIVERY_CODE";

    /**
     * 字典
     */
    String SUPPLIER_DICTIONARY_CODE = "SUPPLIER_DICTIONARY_CODE";
    String SUPPLIER_DICTIONARY_INFO_CODE = "SUPPLIER_DICTIONARY_INFO_CODE";

    /**
     * 供应商账户
     */
    String APPLY_SUPPLY_COM_ACCT_CODE = "APPLY_SUPPLY_COM_ACCT_CODE";

    String PRODUCT_DICTIONARY_CODE = "PRODUCT_DICTIONARY_CODE";
    String PRODUCT_BRAND_CODE = "PRODUCT_BRAND_CODE";
    String OUT_BOUND_CODE = "OUT_BOUND_CODE";
    String IN_BOUND_CODE = "IN_BOUND_CODE";
    String STOCK_CODE = "STOCK_CODE";
    String ALLOCATION_CODE = "ALLOCATION_CODE";
    String MOVEMENT_CODE = "MOVEMENT_CODE";

    String BASIC_PRICE_PROJECT = "BASIC_PRICE_PROJECT";
    String BASIC_PRICE_CHANNEL = "BASIC_PRICE_CHANNEL";
    //销售区域编码(草稿表中)
    String SALE_AREA_CODE = "SALE_AREA_CODE";
    //销售区域申请编码
    String APPLY_SALE_AREA_CODE = "APPLY_SALE_AREA_CODE";
    //销售区域正式编码
    String OFFICIAL_SALE_AREA_CODE = "OFFICIAL_SALE_AREA_CODE";
    //变价申请编码
    String CHANGE_PRICE_CODE = "CHANGE_PRICE_CODE";

    /**
     * 商品配置编码
     */
    String SKU_CONFIG_CODE = "SKU_CONFIG_CODE";
    String APPLY_SKU_CONFIG_CODE = "APPLY_SKU_CONFIG_CODE";

    /**
     * 退供单编码
     */
    String GOODS_REJECT_CODE = "GOODS_REJECT_CODE";
    /**
     * 退供申请单编码
     */
    String GOODS_REJECT_APPLY_CODE = "GOODS_REJECT_APPLY_CODE";
    /**
     * 采购申请单编码
     */
    String PURCHASE_APPLY_CODE = "PURCHASE_APPLY_CODE";

    /**
     * 采购单编码
     */
    String PURCHASE_ORDER_CODE = "PURCHASE_ORDER_CODE";
}
