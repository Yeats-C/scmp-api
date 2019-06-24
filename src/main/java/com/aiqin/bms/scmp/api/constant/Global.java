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

    String PRODUCT_CODE = "productCodes";
    String SKU_CODE = "skuCodes";

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
}
