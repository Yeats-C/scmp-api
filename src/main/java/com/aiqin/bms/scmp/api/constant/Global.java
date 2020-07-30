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

    /**
     *  订单类型  1配送、2直送、3货架直送、4采购直送
     */
    public static final Integer ORDER_TYPE_1 = 1;
    public static final Integer ORDER_TYPE_2 = 2;
    public static final Integer ORDER_TYPE_3 = 3;
    public static final Integer ORDER_TYPE_4 = 4;

    /**
     *  库房配置管理  移库类型  0.wms发起移库   1.分别发起移库   2.同时发起移库
     */
    public static final Integer MOVEMENT_TYPE_0 = 0;
    public static final Integer MOVEMENT_TYPE_1 = 1;
    public static final Integer MOVEMENT_TYPE_2 = 2;

    /**
     *  平台类型 0.爱亲(新系统)，1.DL
     */
    public static final Integer PLATFORM_TYPE_0 = 0;
    public static final Integer PLATFORM_TYPE_1 = 1;

    /**
     *  库存单据类型 0出库 1入库 2退供 3采购 4调拨 5退货 6移库 7监管仓入库 8报废 9订单 10监管仓出库 11损溢
     */
    public static final Integer DOCUMENT_TYPE_0 = 0;
    public static final Integer DOCUMENT_TYPE_1 = 1;
    public static final Integer DOCUMENT_TYPE_2 = 2;
    public static final Integer DOCUMENT_TYPE_3 = 3;
    public static final Integer DOCUMENT_TYPE_4 = 4;
    public static final Integer DOCUMENT_TYPE_5 = 5;
    public static final Integer DOCUMENT_TYPE_6 = 6;
    public static final Integer DOCUMENT_TYPE_7 = 7;
    public static final Integer DOCUMENT_TYPE_8 = 8;
    public static final Integer DOCUMENT_TYPE_9 = 9;
    public static final Integer DOCUMENT_TYPE_10 = 10;
    public static final Integer DOCUMENT_TYPE_11 = 11;

    /**
     *  库存操作类型  1.锁定库存 2.减库存并解锁 3.解锁库存. 4.减库存 5.加并锁定库存 6.加库存
     *             7.加在途 8.减在途 9.锁转移(锁定库存移入/移出) 10.只减锁库存、总库存、可用库存
     */
    public static final Integer STOCK_OPERATION_1 = 1;
    public static final Integer STOCK_OPERATION_2 = 2;
    public static final Integer STOCK_OPERATION_3 = 3;
    public static final Integer STOCK_OPERATION_4 = 4;
    public static final Integer STOCK_OPERATION_5 = 5;
    public static final Integer STOCK_OPERATION_6 = 6;
    public static final Integer STOCK_OPERATION_7 = 7;
    public static final Integer STOCK_OPERATION_8 = 8;
    public static final Integer STOCK_OPERATION_9 = 9;
    public static final Integer STOCK_OPERATION_10 = 10;


    /**
     *  平台类型 批次管理 0：自动批次管理 1：全部制定批次模式 2：部分指定批次模式
     *   3:全部指定月份模式 4:部分指定月份模式  5:全部指定月份转换日期模式 6:部分指定月份转换日期模式
     */
    public static final Integer BATCH_MANAGE_0 = 0;
    public static final Integer BATCH_MANAGE_1 = 1;
    public static final Integer BATCH_MANAGE_2 = 2;
    public static final Integer BATCH_MANAGE_3 = 3;
    public static final Integer BATCH_MANAGE_4 = 4;
    public static final Integer BATCH_MANAGE_5 = 5;
    public static final Integer BATCH_MANAGE_6 = 6;

    /**
     *  批次管理 2：部分指定批次模式  商品sku是否指定批次 (0:指定 1:不指定)
     */
    public static final Integer WAREHOUSE_BATCH_MANAGE_SKU_0 = 0;
    public static final Integer WAREHOUSE_BATCH_MANAGE_SKU_1 = 1;

    /**
     *  调用DL 单据类型 1.采购 2.退供 3.调拨出 7调拨入 4.移库出 8移库入 5.报损 9报溢 6.预定订单
     */
    public static final Integer DL_ORDER_TYPE_1 = 1;
    public static final Integer DL_ORDER_TYPE_2 = 2;
    public static final Integer DL_ORDER_TYPE_3 = 3;
    public static final Integer DL_ORDER_TYPE_4 = 4;
    public static final Integer DL_ORDER_TYPE_5 = 5;
    public static final Integer DL_ORDER_TYPE_6 = 6;
    public static final Integer DL_ORDER_TYPE_7 = 7;
    public static final Integer DL_ORDER_TYPE_8 = 8;
    public static final Integer DL_ORDER_TYPE_9 = 9;

    /**
     *  调用DL 操作类型类型 1. 加库存  2.减库存
     */
    public static final Integer DL_OPERATION_TYPE_1 = 1;
    public static final Integer DL_OPERATION_TYPE_2 = 2;

    /**
     *  运输管理:送货至 1. 加库存  2.减库存
     */
    public static final String DELIVERTO_1 = "收货人地址";
    public static final String DELIVERTO_2 = "货运站";

    /**
     *  调用wms发运。服务方式: 1. 派送  2.站点自提  3.中心自提  4.自提
     */
    public static final String SERVICE_MODE_1 = "派送";
    public static final String SERVICE_MODE_2 = "站点自提";
    public static final String SERVICE_MODE_3 = "中心自提";
    public static final String SERVICE_MODE_4 = "自提";

    /**
     *  调用wms发运。支付方式: 1. 寄付  2.到付  3.月结
     */
    public static final String SETTLE_TYPE_1 = "寄付";
    public static final String SETTLE_TYPE_2 = "到付";
    public static final String SETTLE_TYPE_3 = "月结";

    /**
     *  Dl与耘链的推送类型  1.sku 2.供应商 3.库存 4.门店 5.质检报告
     */
    public static final Integer SKU_TYPE  = 1;
    public static final Integer SUPPLIER_TYPE = 2;
    public static final Integer STOCK_TYPE  = 3;
    public static final Integer STORE_TYPE = 4;
    public static final Integer INSPECTION_TYPE = 5;

    /**
     *  Dl与耘链的推送单据类型 1.销售 2.退货  3.物流单 4.取消
     */
    public static final Integer ORDER_TYPE  = 1;
    public static final Integer RETURN_INFO_TYPE = 2;
    public static final Integer ORDER_TRANSPORT_TYPE  = 3;
    public static final Integer CANCEL_ORDER_TYPE = 4;

    /**
     *  Dl与耘链的推送 业务单据类型 0 推送(DL->熙耘)  1.回传(熙耘->DL)
     */
    public static final Integer PUSH_TYPE  = 0;
    public static final Integer ECHO_TYPE = 1;

    /**
     *  0:成功 1:失败
     */
    public static final Integer SUCCESS  = 0;
    public static final Integer FAIL = 1;

    /**
     *  sku价格表 新建变价 是否批次： 0.商品价格 1.批次价格 2.月份价格
     */
    public static final Integer IS_BATCH_PRICE_0 = 0;
    public static final Integer IS_BATCH_PRICE_1 = 1;
    public static final Integer IS_BATCH_PRICE_2 = 2;

    /**
     * 同步状态
     */
    public static final Integer NOT_SYNCHR = 0;
    public static final Integer SYNCHR = 1;

}
