package com.aiqin.bms.scmp.api.common;

/**
 * log对象类型
 */
public enum  ObjectTypeCode {
//    /**
//     *供应商字典=====================
//     */
//    public static final Byte SUPPLIER_DICTIONARY=1;
//    public static final Byte CONTRACT=2; //合同
//    public static final Byte APPLY_CONTRACT=3; //申请合同
//    public static final Byte SUPPLIER=4; //供应商
//    public static final Byte APPLY_SUPPLIER=5;//申请供应商
//    public static final Byte SUPPLY_COMPANY=6;//供货单位
//    public static final Byte APPLY_SUPPLY_COMPANY=7;//申请供货单位
//    public static final Byte SETTLEMENT_INFORMATION=8;//结算信息
//    public static final Byte APPLY_SETTLEMENT_INFORMATION=8;//申请结算信息
//    public static final Byte DELIVERY_INFORMATION=9;//发货信息
//    public static final Byte APPLY_DELIVERY_INFORMATION=10;//申请发货信息
//    public static final Byte SUPPLY_COMPANY_ACCOUNT=11;//供货单位账号
//    public static final Byte APPLY_SUPPLY_COMPANY_ACCOUNT=12;//申请供货单位账号


    SUPPLIER_DICTIONARY((byte) 1, "供应商字典"),
    CONTRACT((byte)2, "合同"),
    APPLY_CONTRACT((byte)3, "申请合同"),
    SUPPLIER((byte)4, "供应商集团"),
    APPLY_SUPPLIER((byte)5, "申请供应商集团"),
    SUPPLY_COMPANY((byte)6, "供应商"),
    APPLY_SUPPLY_COMPANY((byte)7, "申请供应商"),
    SETTLEMENT_INFORMATION((byte)8, "结算信息"),
    APPLY_SETTLEMENT_INFORMATION((byte)9, "申请结算信息"),
    DELIVERY_INFORMATION((byte)10, "发货信息"),
    APPLY_DELIVERY_INFORMATION((byte)11, "申请发货信息"),
    SUPPLY_COMPANY_ACCOUNT((byte)12, "供货单位账号"),
    APPLY_SUPPLY_COMPANY_ACCOUNT((byte)13, "申请供货单位账号"),
    MANUFACTURER((byte)14, "制造商"),
    APPLY_MANUFACTURERT((byte)15, "申请制造商"),
    SUPPLIER_FILE((byte)16,"供应商文件"),
    APPLY_SUPPLIER_FILE((byte)17,"供应商申请文件"),

    PRODUCT_DICTIONARY((byte) 18, "商品字典"),
    PRODUCT_CATEGORY((byte)19, "商品品类"),
    PRODUCT_BRAND((byte)20, "商品品牌"),
    ALLOCATION((byte)21,"调拨单"),
    PRODUCT_MANAGEMENT((byte)22,"商品管理"),
    SKU_MANAGEMENT((byte)23,"SKU管理"),
    PRICE_MANAGEMENT((byte)24,"价格管理"),
    APPLY_SKU((byte)25,"SKU申请"),
    APPLY_PRODUCT((byte)26,"商品申请管理"),
    APPLY_SKU_COONFIG((byte)27,"SKU配置申请"),
    INBOUND_ODER((byte)28,"入库单"),
    OUTBOUND_ODER((byte)29,"出库单"),
    MOVEMENT_ODER((byte)30,"移库单"),
    CHANGE_PRICE((byte)31,"变价管理"),
    SALE_AREA((byte)32,"销售区域"),
    SCRAP((byte) 33,"报废")
    ;
    private Byte status;
    private String name;
    ObjectTypeCode(Byte status, String name) {
        this.status = status;
        this.name = name;
    }
    public Byte getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }












}
