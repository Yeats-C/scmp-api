package com.aiqin.bms.scmp.api.constant;


public final class Global {

    private Global () {

    }
    /**
     * 禁用状态
     */
    public static final Integer USER_OFF = 0;
    /**
     * 启用状态
     */
    public static final Integer USER_ON = 1;
    /**
     * 判断增删改是否成功
     */
    public static final Integer CHECK_INSERT_UPDATE_DELETE_SUCCESS = 0;
    /**
     * 判断商品分类级别
     */
    public static final Integer PRODUCT_CATEGORY_FIRST_LEVEL = 1;
    public static final Integer PRODUCT_CATEGORY_SECOND_LEVEL = 2;
    public static final Integer PRODUCT_CATEGORY_THIRD_LEVEL = 3;
    public static final Integer PRODUCT_CATEGORY_FOUTH_LEVEL = 4;
    /**
     * 商品上下架状态，0为未上架，1为上架
     */
    public static final Integer ON_SALE_NOT = 0;
    public static final Integer ON_SALE_TOP = 1;
    /**
     * 品牌排序，默认0
     */
    public static final Integer BRAND_SORT_DEFAULT = 0;
    /**
     * 商品品牌默认展示
     */
    public static final Integer BRAND_IS_SHOW_YES = 0;
    public static final Integer BRAND_IS_SHOW_NOT = 1;
    /**
     * 商品分类默认展示
     */
    public static final Integer CATEGORY_IS_SHOW_YES = 0;
    public static final Integer CATEGORY_IS_SHOW_NOT = 1;
    /**
     * 是否参加活动，参加为1，不参加为0
     */
    public static final Integer JOIN_ACTIVITY_YES = 1;
    public static final Integer JOIN_ACTIVITY_NOT = 0;
    /**
     * 判断sku是否生效，0为生效，1为失效
     */
    public static final Integer SKU_CAN_USE = 0;
    public static final Integer SKU_NOT_CAN_USE = 1;
    
    /**
     * 公司编码  01-爱亲
     */
    public static final String COMPANY_01 = "01";
    public static final String COMPANY_09 = "09";
    public static final String COMPANY_09_NAME = "宁波熙耘科技有限公司";

    public static final String CONFIG_CODE = "configCodes";
    public static final String SUPPLIER_ID = "supplierIds";

    /**
     * 未生效/生效
     */
    public static final Integer EFFECTIVE = 1;
    public static final Integer UN_EFFECTIVE = 0;

    /**
     * 显示申请SKU配置信息
     */
    public static final Byte APPLY_SKU_CONFIG_SHOW = Byte.valueOf("0");
    /**
     * 不显示申请SKU配置信息
     */
    public static final Byte APPLY_SKU_CONFIG_UN_SHOW = Byte.valueOf("1");

    public static final String DEFAULT_DEPART_CODE = "3";
    public static final String DEFAULT_DEPART_NAME = "仓储中心";

    /**
     * 商品类型  0商品 1赠品 2实物返
     */
    public static final Integer PRODUCT_TYPE_0 = 0;
    public static final Integer PRODUCT_TYPE_1 = 1;
    public static final Integer PRODUCT_TYPE_2 = 2;

    /**
     * 采购申请单类型 0 手动 1自动 ,
     * 是否提交  待提交 1 已完成 2 部分完成
     * 仓储状态 0.未开始  1.确认中 2.完成
     */
    public static final Integer PURCHASE_APPLY_TYPE_0 = 0;
    public static final Integer PURCHASE_APPLY_TYPE_1 = 1;
    public static final Integer PURCHASE_APPLY_STATUS_0 = 0;
    public static final Integer PURCHASE_APPLY_STATUS_1 = 1;
    public static final Integer PURCHASE_APPLY_STATUS_2 = 2;
    public static final Integer STORAGE_STATUS_0 = 0;
    public static final Integer STORAGE_STATUS_1 = 1;
    public static final Integer STORAGE_STATUS_2 = 2;

    /**
     *  采购单状态 0.待审核 1.审核中 2.审核通过  3.备货确认 4.发货确认  5.入库开始 6.入库中 7.已入库  8.完成 9.取消 10.审核不通过
     */
    public static final Integer PURCHASE_ORDER_0 = 0;
    public static final Integer PURCHASE_ORDER_1 = 1;
    public static final Integer PURCHASE_ORDER_2 = 2;
    public static final Integer PURCHASE_ORDER_3 = 3;
    public static final Integer PURCHASE_ORDER_4 = 4;
    public static final Integer PURCHASE_ORDER_5 = 5;
    public static final Integer PURCHASE_ORDER_6 = 6;
    public static final Integer PURCHASE_ORDER_7 = 7;
    public static final Integer PURCHASE_ORDER_8 = 8;
    public static final Integer PURCHASE_ORDER_9 = 9;
    public static final Integer PURCHASE_ORDER_10 = 10;

    /**
     *  采购申请单状态  0.待提交 1.已完成  2.待审核 3.审核中 4.审核通过 5.审核不通过 6.撤销
     */
    public static final Integer PURCHASE_APPLY_0 = 0;
    public static final Integer PURCHASE_APPLY_1 = 1;
    public static final Integer PURCHASE_APPLY_2 = 2;
    public static final Integer PURCHASE_APPLY_3 = 3;
    public static final Integer PURCHASE_APPLY_4 = 4;
    public static final Integer PURCHASE_APPLY_5 = 5;
    public static final Integer PURCHASE_APPLY_6 = 6;


    /**
     *监管仓类型
     */
    public static final Byte SUPERVISORY_WAREHOUSE_TYPE = Byte.valueOf("4");

    /**
     * 主商品
     */
    public static final Byte MAIN_PRODUCT = Byte.valueOf("1");
    public static final Byte BYTE_ZERO = Byte.valueOf("0");
    public static final Byte BYTE_ONE = Byte.valueOf("1");

    /**
     * 仓库编码
     */
    public static final String HB_CODE = "1081";
    public static final String XN_CODE = "1082";
    public static final String HD_CODE = "1083";
    public static final String HN_CODE = "1084";
    public static final String HZ_CODE = "1085";

    /**
     * 库房类型
     */
    public static final String SALE_TYPE = "销售库";
    public static final String GIFT_TYPE = "赠品库";
    public static final String SPECIAL_TYPE = "特卖库";
    public static final String DEFECTIVE_TYPE = "残品库";

    public static final Integer SALE_INFO_TYPE= 1;
    public static final Integer SPECIAL_INFO_TYPE = 2;
    public static final Integer DEFECTIVE_INFO_TYPE = 3;
    public static final Integer GIFT_INFO_TYPE = 4;

    /**
     * 统计报表数据类型
     *  0 公司 1 部门
     *  报表类型: 0 年报 1 季报 2 月报 3 周报
     */
    public static final Integer COMPANY  = 0;
    public static final Integer DEPARTMENT = 1;
    public static final Integer ANNUAL_REPORT = 0;
    public static final Integer QUARTERLY_REPORT = 1;
    public static final Integer MONTHLY_REPORT = 2;
    public static final Integer WEEKLY = 3;

    public static final String TICKET = "ticket";
    public static final String TICKET_PERSON_ID = "ticket_person_id";
    public static final String ERROR = "error";
    public static final String CREATE_BY = "CREATEBY";
    public static final String AUDITOR_BY = "AUDITORBY";
    public static final String APPLY_TYPE = "APPLYTYPE";

    /**
     * 出入库类型 出库 0 入库 1
     */
    public static final Integer OUTBOUND_TYPE = 0;
    public static final Integer INBOUND_TYPE = 1;

}
