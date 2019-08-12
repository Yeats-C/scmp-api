package com.aiqin.bms.scmp.api.common;

/**
 * 处理类型
 */
public enum  HandleTypeCoce {
    ADD_SUPPLIER_DICTIONARY((byte) 1, "供应商字典新增"),
    UPDATE_SUPPLIER_DICTIONARY((byte) 2, "供应商字典修改"),
    DELETE_SUPPLIER_DICTIONARY((byte) 3,"供应商字典删除"),
    ENABLED_SUPPLIER_DICTIONARY((byte) 4,"供应商字典启用"),
    NO_ENABLED_SUPPLIER_DICTIONARY((byte) 5,"供应商字典禁用"),
    ADD_SUPPLIER_DICTIONARY_INFO((byte) 110, "供应商字典详细新增"),
    UPDATE_SUPPLIER_DICTIONARY_INFO((byte) 120, "供应商字典详细修改"),
    DELETE_SUPPLIER_DICTIONARY_INFO((byte) 130,"供应商字典详细删除"),
    //合同 操作
    ADD_CONTRACT((byte)6, "合同新增"),
    UPDATE_CONTRACT((byte)7, "合同修改"),
    DELETE_CONTRACT((byte) 8,"合同修改删除"),
    UPDATE_PENDING_TRIAL_CONTRACT((byte) 9,"合同修改待审"),
    UPDATE_APPROVAL_CONTRACT((byte) 10,"合同修改审批中"),
    UPDATE_APPROVAL_SUCCESS_CONTRACT((byte) 11,"合同修改审批通过"),
    UPDATE_APPROVAL_FAIL_CONTRACT((byte) 12,"合同修改审批审批失败"),
    UPDATE_REVOKE_CONTRACT((byte) 13,"合同修改撤销"),
    /**
     * 申请合同 操作
     */
    APPLY_ADD_CONTRACT((byte)14, "申请合同新增待审"),
    APPLY_UPDATE_CONTRACT((byte)15, "申请合同修改"),
    APPLY_DELETE_CONTRACT((byte) 16,"申请合同修改删除"),
    APPLY_UPDATE_PENDING_TRIAL_CONTRACT((byte) 17,"申请合同修改待审"),
    APPLY_UPDATE_APPROVAL_CONTRACT((byte) 18,"申请合同审批中"),
    APPLY_UPDATE_APPROVAL_SUCCESS_CONTRACT((byte) 19,"申请合同修改审批通过"),
    APPLY_UPDATE_APPROVAL_FAIL_CONTRACT((byte) 20,"申请合同修改审批审批失败"),
    APPLY_UPDATE_REVOKE_CONTRACT((byte) 21,"申请合同修改撤销"),
    //供应商集团
    ADD_SUPPLIER((byte)22, "供应商集团新增"),
    UPDATE_SUPPLIER((byte)23, "供应商集团修改"),
    DELETE_SUPPLIER((byte) 24,"供应商集团删除"),
    UPDATE_PENDING_TRIAL_SUPPLIER((byte) 25,"供应商集团修改待审"),
    UPDATE_APPROVAL_SUPPLIER((byte) 26,"供应商集团审批中"),
    UPDATE_APPROVAL_SUCCESS_SUPPLIER((byte) 926,"供应商集团修改审批通过"),
    ADD_APPROVAL_SUCCESS_SUPPLIER((byte) 927,"供应商集团新增审批通过"),
    UPDATE_APPROVAL_FAIL_SUPPLIER((byte) 27,"供应商集团审批审批失败"),
    UPDATE_REVOKE_SUPPLIER((byte) 28,"供应商集团修改撤销"),
    UPLOAD_APPROVAL_FAIL_SUPPLIER((byte) 29,"供应商上传文件"),
    DOWNLOAD_REVOKE_SUPPLIER((byte) 30,"供应商下载文件"),
    //申请供应商集团
    APPLY_ADD_SUPPLIER((byte)31, "申请供应商集团新增待审"),
    APPLY_UPDATE_SUPPLIER((byte)32, "申请供应商集团修改待审"),
    APPLY_DELETE_SUPPLIER((byte) 33,"申请供应商集团删除"),
    APPLY_UPDATE_PENDING_TRIAL_SUPPLIER((byte) 34,"申请供应商集团修改待审"),
    APPLY_UPDATE_APPROVAL_SUPPLIER((byte) 35,"申请供应商集团审批中"),
    APPLY_UPDATE_APPROVAL_SUCCESS_SUPPLIER((byte) 36,"申请供应商集团审批通过"),
    APPLY_UPDATE_APPROVAL_FAIL_SUPPLIER((byte) 37,"申请供应商集团审批审批失败"),
    APPLY_UPDATE_REVOKE_SUPPLIER((byte) 38,"申请供应商集团修改撤销"),
    APPLY_UPLOAD_APPROVAL_FAIL_SUPPLIER((byte) 39,"申请供应商上传文件"),
    APPLY_DOWNLOAD_REVOKE_SUPPLIER((byte) 40,"供应商下载文件"),
    //供应商
    ADD_SUPPLY_COMPANY((byte)41, "供应商新增"),
    UPDATE_SUPPLY_COMPANY((byte)42, "供应商修改"),
    DELETE_SUPPLY_COMPANY((byte) 43,"供应商删除"),
    UPDATE_PENDING_TRIAL_SUPPLY_COMPANY((byte) 44,"供应商修改待审"),
    UPDATE_APPROVAL_SUPPLY_COMPANY((byte) 45,"供应商修改审批中"),
    UPDATE_APPROVAL_SUCCESS_SUPPLY_COMPANY((byte) 46,"供应商修改审批通过"),
    ADD_APPROVAL_SUCCESS_SUPPLY_COMPANY((byte) 946,"供应商新增审批通过"),
    update_approval_fail_supply_company((byte) 47,"供应商审批审批失败"),
    UPDATE_REVOKE_SUPPLY_COMPANY((byte) 48,"供应商修改撤销"),
    //申请供应商
    APPLY_ADD_SUPPLY_COMPANY((byte)49, "申请供应商新增待审"),
    APPLY_UPDATE_SUPPLY_COMPANY((byte)50, "申请供应商修改待审"),
    APPLY_DELETE_SUPPLY_COMPANY((byte) 51,"申请供应商删除"),
    APPLY_UPDATE_PENDING_TRIAL_SUPPLY_COMPANY((byte) 52,"申请供应商修改待审"),
    APPLY_UPDATE_APPROVAL_SUPPLY_COMPANY((byte) 53,"申请供应商审批中"),
    APPLY_UPDATE_APPROVAL_SUCCESS_SUPPLY_COMPANY((byte) 54,"申请供应商审批通过"),
    APPLY_UPDATE_APPROVAL_FAIL_SUPPLY_COMPANY((byte) 55,"申请供应商修改审批失败"),
    APPLY_UPDATE_REVOKE_SUPPLY_COMPANY((byte) 56,"申请供应商修改撤销"),
    //结算信息
    ADD_SETTLEMENT_INFORMATION((byte)57, "结算信息新增"),
    UPDATE_SETTLEMENT_INFORMATION((byte)58, "结算信息修改"),
    DELETE_SETTLEMENT_INFORMATION((byte) 59,"结算信息删除"),
    UPDATE_PENDING_TRIAL_SETTLEMENT_INFORMATION((byte) 60,"结算信息修改待审"),
    UPDATE_APPROVAL_SETTLEMENT_INFORMATION((byte) 61,"结算信息审批中"),
    UPDATE_APPROVAL_SUCCESS_SETTLEMENT_INFORMATION((byte) 62,"结算信息审批通过"),
    UPDATE_APPROVAL_FAIL_SETTLEMENT_INFORMATION((byte) 63,"结算信息审批审批失败"),
    UPDATE_REVOKE_SETTLEMENT_INFORMATION((byte) 64,"结算信息修改撤销"),
    //申请结算信息
    APPLY_ADD_SETTLEMENT_INFORMATION((byte)65, "申请结算信息新增待审"),
    APPLY_UPDATE_SETTLEMENT_INFORMATION((byte)66, "申请结算信息修改待审"),
    APPLY_DELETE_SETTLEMENT_INFORMATION((byte) 67,"申请结算信息删除"),
    APPLY_UPDATE_PENDING_TRIAL_SETTLEMENT_INFORMATION((byte) 68,"申请结算信息修改待审"),
    APPLY_UPDATE_APPROVAL_SETTLEMENT_INFORMATION((byte) 69,"申请结算信息审批中"),
    APPLY_UPDATE_APPROVAL_SUCCESS_SETTLEMENT_INFORMATION((byte) 70,"申请结算信息审批通过"),
    APPLY_UPDATE_APPROVAL_FAIL_SETTLEMENT_INFORMATION((byte) 71,"申请结算信息审批审批失败"),
    APPLY_UPDATE_REVOKE_SETTLEMENT_INFORMATION((byte) 72,"申请结算信息修改撤销"),
    //发货信息
    ADD_DELIVERY_INFORMATION((byte)73, "发货信息新增"),
    UPDATE_DELIVERY_INFORMATION((byte)74, "发货信息修改"),
    DELETE_DELIVERY_INFORMATION((byte) 75,"发货信息删除"),
    UPDATE_PENDING_TRIAL_DELIVERY_INFORMATION((byte) 76,"发货信息修改待审"),
    UPDATE_APPROVAL_DELIVERY_INFORMATION((byte) 77,"发货信息审批中"),
    UPDATE_APPROVAL_SUCCESS_DELIVERY_INFORMATION((byte) 78,"发货信息审批通过"),
    UPDATE_APPROVAL_FAIL_DELIVERY_INFORMATION((byte) 79,"发货信息审批审批失败"),
    UPDATE_REVOKE_DELIVERY_INFORMATION((byte) 80,"发货信息修改撤销"),
    //申请发货信息
    APPLY_ADD_DELIVERY_INFORMATION((byte)81, "申请发货信息新增待审"),
    APPLY_UPDATE_DELIVERY_INFORMATION((byte)82, "申请发货信息修改待审"),
    APPLY_DELETE_DELIVERY_INFORMATION((byte) 83,"申请发货信息删除"),
    APPLY_UPDATE_PENDING_TRIAL_DELIVERY_INFORMATION((byte) 84,"申请发货信息修改待审"),
    APPLY_UPDATE_APPROVAL_DELIVERY_INFORMATION((byte) 85,"申请发货信息审批中"),
    APPLY_UPDATE_APPROVAL_SUCCESS_DELIVERY_INFORMATION((byte) 86,"申请发货信息审批通过"),
    APPLY_UPDATE_APPROVAL_FAIL_DELIVERY_INFORMATION((byte) 87,"申请发货信息审批失败"),
    APPLY_UPDATE_REVOKE_DELIVERY_INFORMATION((byte) 88,"申请发货信息修改撤销"),
    //供应商账号
    ADD_SUPPLY_COMPANY_ACCOUNT((byte)89, "供应商账户新增"),
    UPDATE_SUPPLY_COMPANY_ACCOUNT((byte)90, "供应商账户修改"),
    DELETE_SUPPLY_COMPANY_ACCOUNT((byte) 91,"供应商账户删除"),
    UPDATE_PENDING_TRIAL_SUPPLY_COMPANY_ACCOUNT((byte) 92,"供应商账户修改待审"),
    UPDATE_APPROVAL_SUPPLY_COMPANY_ACCOUNT((byte) 93,"供应商账户审批中"),
    UPDATE_APPROVAL_SUCCESS_SUPPLY_COMPANY_ACCOUNT((byte) 94,"供应商账户审批通过"),
    UPDATE_APPROVAL_FAIL_SUPPLY_COMPANY_ACCOUNT((byte) 95,"供应商账户修改审批失败"),
    UPDATE_REVOKE_SUPPLY_COMPANY_ACCOUNT((byte) 96,"供应商账户修改撤销"),
    //申请供应商账号
    APPLY_ADD_SUPPLY_COMPANY_ACCOUNT((byte)97, "申请供应商账户新增待审"),
    APPLY_UPDATE_SUPPLY_COMPANY_ACCOUNT((byte)98, "申请供应商账户修改待审"),
    APPLY_DELETE_SUPPLY_COMPANY_ACCOUNT((byte) 99,"申请供应商账户删除"),
    APPLY_UPDATE_PENDING_TRIAL_SUPPLY_COMPANY_ACCOUNT((byte) 100,"申请供应商账户修改待审"),
    APPLY_UPDATE_APPROVAL_SUPPLY_COMPANY_ACCOUNT((byte) 101,"申请供应商账户审批中"),
    APPLY_UPDATE_APPROVAL_SUCCESS_SUPPLY_COMPANY_ACCOUNT((byte) 102,"申请供应商账户审批通过"),
    APPLY_UPDATE_APPROVAL_FAIL_SUPPLY_COMPANY_ACCOUNT((byte) 103,"申请供应商账户修改审批失败"),
    APPLY_UPDATE_REVOKE_SUPPLY_COMPANY_ACCOUNT((byte) 104,"申请供应商账户修改撤销"),
    UPDATE_PENDING_TRIAL_SUPPLY_COMPANY_ACCOUNT_FAIL((byte) -100,"修改申请供供应商账户到待审状态失败"),
    UPDATE_APPROVAL_SUPPLY_COMPANY_ACCOUNT_FAIL((byte) -101,"修改申请供应商账户到审批中状态失败"),
    UPDATE_APPROVAL_SUCCESS_SUPPLY_COMPANY_ACCOUNT_FAIL((byte) -102,"修改申请供应商账户到审批通过状态失败"),
    UPDATE_APPROVAL_FAIL_SUPPLY_COMPANY_ACCOUNT_FAIL((byte)-103,"修改申请供应商账户到审批驳回状态失败"),
    UPDATE_REVOKE_SUPPLY_COMPANY_ACCOUNT_FAIL((byte) -104,"修改申请供应商账户到撤销失败"),
    CALL_SUPPLY_COMPANY_ACCOUNT_WORK_FLOW_FAIL((byte) -97,"调用供应商账户审核接口失败"),
    //进货额
    ADD_CONTRACT_PURCHASE_VOLUME((byte)105, "合同进货额新增"),
    UPDATE_CONTRACT_PURCHASE_VOLUME((byte)106, "合同进货额修改待审"),
    //申请单位账号
    APPLY_ADD_CONTRACT_PURCHASE_VOLUME((byte)107, "申请合同进货额新增待审"),
    APPLY_UPDATE_CONTRACT_PURCHASE_VOLUME((byte)108, "申请合同进货额修改待审"),
    //制造商
    ADD_MANUFACTURER((byte)109, "制造商新增"),
    UPDATE_MANUFACTURER((byte)110, "制造商修改"),
    ENABLE_MANUFACTURER((byte) 111,"制造商启用"),
    DISABLE_MANUFACTURER((byte) 112,"制造商禁用"),
    ADD_SUPPLIER_FILE((byte)130,"供应商文件新增"),
    APPLY_ADD_SUPPLIER_FILE((byte)131,"供应商申请文件新增"),

    ADD_PRODUCT_DICTIONARY((byte) 132, "商品字典新增"),
    UPDATE_PRODUCT_DICTIONARY((byte) 133, "商品字典修改"),
    DELETE_PRODUCT_DICTIONARY((byte) 134,"商品字典删除"),
    ENABLED_PRODUCT_DICTIONARY((byte) 135,"商品字典启用"),
    NO_ENABLED_PRODUCT_DICTIONARY((byte) 136,"商品字典禁用"),
    ADD_PRODUCT_DICTIONARY_INFO((byte) 137, "商品详细新增"),
    UPDATE_PRODUCT_DICTIONARY_INFO((byte) 138, "商品字典详细修改"),
    DELETE_PRODUCT_DICTIONARY_INFO((byte) 139,"商品字典详细删除"),
    //品类
    ADD_PRODUCT_CATEGORY((byte)140,"品类新增"),
    UPDATE_PRODUCT_CATEGORY((byte)141,"品类修改"),
    DELETE_PRODUCT_CATEGORY((byte)142,"品类删除"),
    //品牌
    ADD_PRODUCT_BRAND((byte)143,"品牌新增"),
    UPDATE_PRODUCT_BRAND((byte)144,"品牌修改"),
    DELETE_PRODUCT_BRAND((byte)145,"品牌删除"),
    ENABLED_PRODUCT_BRAND((byte) 146,"品牌启用"),
    NO_ENABLED_PRODUCT_BRAND((byte) 147,"品牌禁用"),
    //申请商品
    APPLY_ADD_PRODUCT((byte)148,"商品申请新增"),
    APPLY_UPDATE_PRODUCT((byte)149,"商品申请修改"),
    APPLY_UPDATE_PRODUCT_TO_EXAMINE((byte)150,"商品申请审核成功"),
    APPLY_UPDATE_PRODUCT_TO_EXAMINE_FAIL((byte)151,"商品申请审核失败"),
    //商品
    ADD_PRODUCT((byte)152,"商品新增"),
    UPDATE_PRODUCT((byte)153,"商品修改"),
    //价格 //保存后状态是待提交，提交后状态是带审核
    ADD_VARIABLE_PRICE((byte)154,"待提交变价"),
    ADD_TO_EXAMINE_VARIABLE_PRICE((byte)155,"提交待审核变价"),
    //价格 //保存后状态是待提交，提交后状态是带审核
    ADD_VARIABLE_PRICE_LIST((byte)156,"待提交变价列表"),
    ADD_TO_EXAMINE_VARIABLE_PRICE_LIST((byte)157,"提交待审核变价列表"),
    ADD_VARIABLE_PRICE_LIST_TO_EXAMINE_SUCCESS((byte)158,"变价审核成功"),
    ADD_VARIABLE_PRICE_LIST_TO_EXAMINE_FAIL((byte)159,"变价审核失败"),
    //变价撤销
    ADD_VARIABLE_PRICE_PRICE_REVOKE((byte)160,"撤销变价"),
    //sku
    ADD_PRODUCT_SKU((byte)161,"商品sku新增"),
    UPDATE_PRODUCT_SKU((byte)162,"商品sku修改"),
    ADD_SKU((byte)163,"sku新增"),
    UPDATE_SKU((byte)164,"sku修改"),
    APPLY_APPROVAL_SUCCESS_SKU((byte) 165,"申请SKU审批通过"),
    APPLY_APPROVAL_FAIL_SKU((byte) 166,"申请SKU审批审批失败"),

    ADD_SKU_CONFIG((byte)167,"sku配置新增"),
    UPDATE_SKU_CONFIG((byte)168,"sku配置修改"),
    APPLY_APPROVAL_SUCCESS_SKU_CONFIG((byte) 169,"申请SKU审批通过"),
    APPLY_APPROVAL_FAIL_SKU_CONFIG((byte) 170,"申请SKU审批审批失败"),


    // 入库单
    ADD_INBOUND_ODER((byte)171,"新建入库单"),
    PULL_INBOUND_ODER((byte)172,"推送入库单"),
    RETURN_INBOUND_ODER((byte)173,"回传入库单"),
    COMPLETE_INBOUND_ODER((byte)174,"完成入库单"),

    // 出库单
    ADD_OUTBOUND_ODER((byte)175,"新建出库单"),
    PULL_OUTBOUND_ODER((byte)176,"推送出库单"),
    RETURN_OUTBOUND_ODER((byte)177,"回传出库单"),
    COMPLETE_OUTBOUND_ODER((byte)178,"完成出库单"),

    //调拨
    ADD_ALLOCATION((byte)179,"调拨单新增"),
    FLOW_ALLOCATION((byte)180,"调拨单提交"),
    REVOCATION_ALLOCATION((byte)181,"调拨单撤销"),
    FLOW_SUCCESS_ALLOCATION((byte)182,"调拨单审批通过"),
    FLOW_FALSE_ALLOCATION((byte)183,"调拨单审批不通过"),
    OUTBOUND_ALLOCATION((byte)184,"调拨单待出库"),
    SUCCESS_OUTBOUND_ALLOCATION((byte)185,"调拨单已出库"),
    INBOUND_ALLOCATION((byte)186,"调拨单待入库"),
    SUCCESS__ALLOCATION((byte)187,"调拨单已完成"),
    // 移库单
    ADD_MOVEMENT((byte)188,"新建移库单"),
    SUBMIT_MOVEMENT((byte)189,"提交移库单"),
    PASS_MOVEMENT((byte)190,"审批通过移库单"),
    NOPASS_MOVEMENT((byte)191,"审批不通过移库单"),
    RECOBER_MOVEMENT((byte)192,"撤销移库单"),
    OUTBOUND_MOVEMENT((byte)193,"移库单待出库"),
    SUCCESS_OUT_MOVEMENT((byte)194,"移库单已出库"),
    INBOUND_MOVEMENT((byte)195,"移库单待入库"),
    SUCCESS__MOVEMENT((byte)196,"移库单已完成"),

    ADD_CHANGEPRICE((byte)197,"新建变价"),
    EDIT_CHANGEPRICE((byte)198,"修改变价"),
    WAIT_CHANGEPRICE((byte)199,"变价待审"),
    UNDER_CHANGEPRICE((byte)200,"变价审批中"),
    PASS_CHANGEPRICE((byte)200,"变价审批通过"),
    NOPASS_CHANGEPRICE((byte)201,"变价审批不通过"),
    RECOBER_CHANGEPRICE((byte)202,"变价撤销"),

    ADD_SALE_AREA((byte)203,"新建销售区域"),
    EDIT_SALE_AREA((byte)204,"修改销售区域"),
    WAIT_SALE_AREA((byte)205,"销售区域待审"),
    UNDER_SALE_AREA((byte)206,"销售区域审批中"),
    PASS_SALE_AREA((byte)207,"销售区域审批通过"),
    NOPASS_SALE_AREA((byte)208,"销售区域审批不通过"),
    RECOBER_SALE_AREA((byte)209,"销售区域撤销"),

    //报废单
    ADD_SCRAP((byte)188,"新建报废单"),
    SUBMIT_SCRAP((byte)189,"提交报废单"),
    PASS_SCRAP((byte)190,"审批通过报废单"),
    NOPASS_SCRAP((byte)191,"审批不通过报废单"),
    RECOBER_SCRAP((byte)192,"撤销报废单"),
    OUTBOUND_SCRAP((byte)193,"报废单待出库"),
    SUCCESS_OUT_SCRAP((byte)194,"报废单已出库"),
    INBOUND_SCRAP((byte)195,"报废单待入库"),
    SUCCESS__SCRAP((byte)196,"报废单已完成"),

    ADD((byte)1000,"新增"),
    UPDATE((byte)10001,"修改"),
    DOWNLOAD((byte)10002,"下载"),
    PENDING((byte)10004, "待审"),
    APPROVAL((byte)10005, "审批中"),
    APPROVAL_SUCCESS((byte)10006, "审批通过"),
    APPROVAL_FAILED((byte)10007, "审批失败"),
    REVOKED((byte)10008, "已撤销"),
    PENDING_SUBMISSION((byte)10009, "待提交"),
    DELETE((byte)100010, "删除"),
    ;
    private Byte status;
    private String name;
    HandleTypeCoce(Byte status, String name) {
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
