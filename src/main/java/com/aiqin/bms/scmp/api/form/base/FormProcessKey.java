package com.aiqin.bms.scmp.api.form.base;

public interface FormProcessKey {

    /**
     * 可选审批人测试
     */
    String PARAM_TEST = "param_test";

    /**
     * 会签测试
     */
    String SIGN_TEST = "sign_test1";

    String PREFIX = "SCMP_";

    /**
     * 账户申请工作流
     */
    String APPLY_COMPANY_ACC = PREFIX + "APPLY_COMPANY_ACC";

    /**
     * 合同申请工作流
     */
    String APPLY_CONTRACT = PREFIX + "APPLY_CONTRACT";

    /**
     * 供应商集团申请工作流
     */
    String APPLY_SUPPLIER = PREFIX + "APPLY_SUPPLIER";

    /**
     * 供应商申请工作流
     */
    String APPLY_COMPANY = PREFIX + "APPLY_COMPANY";

    /**
     * 制造商申请工作流
     */
    String APPLY_MANU = PREFIX + "APPLY_MANU";

    /**
     * 采购申请工作流
     */
    String APPLY_PURCHASE = PREFIX + "APPLY_PURCHASE";

    /**
     * 退供申请工作流
     */
    String APPLY_REFUND = PREFIX + "APPLY_REFUND";

    /**
     * 调拨申请工作流
     */
    String APPLY_ALLOCATTION = PREFIX + "APPLY_ALLOCATTION";

    /**
     * 商品申请工作流
     */
    String APPLY_GOODS = PREFIX + "APPLY_GOODS";

    /**
     * 商品配置申请工作流
     */
    String APPLY_GOODS_CONFIG = PREFIX + "APPLY_GOODS_CONFIG";

    /**
     * 商品价格申请工作流
     */
    String APPLY_GOODS_PRICE = PREFIX + "APPLY_GOODS_PRICE";

    /**
     * 商品销售区域申请工作流
     */
    String APPLY_SALE_AREA = PREFIX + "APPLY_SALE_AREA";

    /**
     * 报废工作流
     */
    String APPLY_SKU_SCRAP = PREFIX + "APPLY_SKU_SCRAP";

    /**
     * 移库工作流
     */
    String APPLY_MOVE_LIBRARY = PREFIX + "APPLY_MOVE_LIBRARY";

}
