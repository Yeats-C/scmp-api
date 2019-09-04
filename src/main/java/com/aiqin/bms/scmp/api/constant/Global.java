package com.aiqin.bms.scmp.api.constant;


public interface Global {

    /**
     * 禁用状态
     */
    Integer USER_OFF = 0;
    /**
     * 启用状态
     */
    Integer USER_ON = 1;
    /**
     * 判断增删改是否成功
     */
    Integer CHECK_INSERT_UPDATE_DELETE_SUCCESS = 0;
    /**
     * 判断商品分类级别
     */
    Integer PRODUCT_CATEGORY_FIRST_LEVEL = 1;
    Integer PRODUCT_CATEGORY_SECOND_LEVEL = 2;
    Integer PRODUCT_CATEGORY_THIRD_LEVEL = 3;
    Integer PRODUCT_CATEGORY_FOUTH_LEVEL = 4;
    /**
     * 商品上下架状态，0为未上架，1为上架
     */
    Integer ON_SALE_NOT = 0;
    Integer ON_SALE_TOP = 1;
    /**
     * 品牌排序，默认0
     */
    Integer BRAND_SORT_DEFAULT = 0;
    /**
     * 商品品牌默认展示
     */
    Integer BRAND_IS_SHOW_YES = 0;
    Integer BRAND_IS_SHOW_NOT = 1;
    /**
     * 商品分类默认展示
     */
    Integer CATEGORY_IS_SHOW_YES = 0;
    Integer CATEGORY_IS_SHOW_NOT = 1;
    /**
     * 是否参加活动，参加为1，不参加为0
     */
    Integer JOIN_ACTIVITY_YES = 1;
    Integer JOIN_ACTIVITY_NOT = 0;
    /**
     * 判断sku是否生效，0为生效，1为失效
     */
    Integer SKU_CAN_USE = 0;
    Integer SKU_NOT_CAN_USE = 1;
    
    /**
     * 公司编码  01-爱亲
     */
    String COMPANY_01 = "01";

    String SKU = "SKU";
    String SPU = "SPU";

    String CONFIG_CODE = "configCodes";
    String SUPPLIER_ID = "supplierIds";

    /**
     * 未生效/生效
     */
    Integer EFFECTIVE = 1;
    Integer UN_EFFECTIVE = 0;

    /**
     * 显示申请SKU配置信息
     */
    Byte APPLY_SKU_CONFIG_SHOW = Byte.valueOf("0");
    /**
     * 不显示申请SKU配置信息
     */
    Byte APPLY_SKU_CONFIG_UN_SHOW = Byte.valueOf("1");

    String DEFAULT_DEPART_CODE = "3";
    String DEFAULT_DEPART_NAME = "仓储中心";

    /**
     * 商品类型  0商品 1赠品 2实物返
     */
    Integer PRODUCT_TYPE_0 = 0;
    Integer PRODUCT_TYPE_1 = 1;
    Integer PRODUCT_TYPE_2 = 2;

    /**
     * 采购申请单类型 0 手动 1自动 ,
     * 是否提交  待提交 1 已完成 2 部分完成
     * 仓储状态 0.未开始  1.确认中 2.完成
     */
    Integer PURCHASE_APPLY_TYPE_0 = 0;
    Integer PURCHASE_APPLY_TYPE_1 = 1;
    Integer PURCHASE_APPLY_STATUS_0 = 0;
    Integer PURCHASE_APPLY_STATUS_1 = 1;
    Integer PURCHASE_APPLY_STATUS_2 = 2;
    Integer STORAGE_STATUS_0 = 0;
    Integer STORAGE_STATUS_1 = 1;
    Integer STORAGE_STATUS_2 = 2;

    /**
     *  采购单状态 0.待审核 1.审核中 2.审核通过  3.备货确认 4.发货确认  5.入库开始 6.入库中 7.已入库  8.完成 9.取消 10.审核不通过
     */
    Integer PURCHASE_ORDER_0 = 0;
    Integer PURCHASE_ORDER_1 = 1;
    Integer PURCHASE_ORDER_2 = 2;
    Integer PURCHASE_ORDER_3 = 3;
    Integer PURCHASE_ORDER_4 = 4;
    Integer PURCHASE_ORDER_5 = 5;
    Integer PURCHASE_ORDER_6 = 6;
    Integer PURCHASE_ORDER_7 = 7;
    Integer PURCHASE_ORDER_8 = 8;
    Integer PURCHASE_ORDER_9 = 9;
    Integer PURCHASE_ORDER_10 = 10;

    /**
     *监管仓类型
     */
    Byte SUPERVISORY_WAREHOUSE_TYPE = Byte.valueOf("4");

    /**
     * 主商品
     */
    Byte MAIN_PRODUCT = Byte.valueOf("1");
    Byte BYTE_ZERO = Byte.valueOf("0");
    Byte BYTE_ONE = Byte.valueOf("1");

    /**
     * 仓库编码
     */
    String HB_CODE = "1081";
    String XN_CODE = "1082";
    String HD_CODE = "1083";
    String HN_CODE = "1084";
    String HZ_CODE = "1085";

    /**
     * 库房类型
     */
    String SALE_TYPE = "销售库";
    String GIFT_TYPE = "赠品库";
    String SPECIAL_TYPE = "特卖库";
    String DEFECTIVE_TYPE = "残品库";

    /**
     * 统计报表数据类型
     *  0 公司 1 部门
     *  报表类型: 0 年报 1 季报 2 月报 3 周报
     */
    Integer COMPANY  = 0;
    Integer DEPARTMENT = 1;
    Integer ANNUAL_REPORT = 0;
    Integer QUARTERLY_REPORT = 1;
    Integer MONTHLY_REPORT = 2;
    Integer WEEKLY = 3;



}
