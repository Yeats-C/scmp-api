package com.aiqin.bms.scmp.api.common;

/**
 * 魔法值异常描述code
 */
public class HandlingExceptionCode {
    public static String PRODUCT="新增商品失败";
    public static String PRODUCT_ID="商品id不能为空";
    public static String PRODUCT_UPDATE="商品更新失败";
    public static Long PRODUCT_CODE= Long.valueOf(3);
    public static String FLOW_CALL_BACK_SUCCESS="success";
    public static String FLOW_CALL_BACK_FALSE="false";
    public static Byte ZERO= 0;
    public static Byte ONE= 1;
    public static String RESULTTYPE="0";

    public static String VARIABLE_PRICE="新建变价失败";
    public static String UPDATE_VARIABLE_PRICE="修改变价失败";
    public static String LIST_VARIABLE_PRICE="新增变价批量失败";

    public static String PRODUCT_PRODUCTCODE="商品编码不能为空";

    public static String PRODUCT_IS_EXIST="商品已存在";

    public static String APPLY_PRODUCT_UPDATE="申请商品修改失败";

    public static String APPLY_PRODUCT_ADD="申请商品新增失败";

    public static Long ONE_PRODUCT_CODE= Long.valueOf(1);
    //采购价
    public final static String  PURCHASE_PRICE="1";
    public final static String  PURCHASE_PRICE_NAME="采购价";
    //分销价
    public final static String  DISTRIBUTION_PRICE="2";
    public final static String  DISTRIBUTION_PRICE_NAME="分销价";
    //临时分销价
    public final static String  TEMPORARY_DISTRIBUTION_PRICE="12";
    public final static String  TEMPORARY_DISTRIBUTION_PRICE_NAME="临时分销价";
    //售价
    public final static String  PRICE="4";
    public final static String PRICE_NAME="售价";
    //临时售价
    public final static String  TEMPORARY_SELLING_PRICE="13";
    public final static String TEMPORARY_SELLING_PRICE_NAME="临时售价";
    //会员价
    public final static String  MEMBERSHIP_PRICE="5";
    public final static String  MEMBERSHIP_PRICE_NAME="会员价";
    //大效期价
    public final static String  LARGE_EFFECT_PRICE="3";
    public final static String LARGE_EFFECT_PRICE_NAME="大效期价";


}
