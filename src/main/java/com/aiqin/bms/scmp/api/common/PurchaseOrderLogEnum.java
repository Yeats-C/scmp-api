package com.aiqin.bms.scmp.api.common;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * @author: zhao shuai
 * @create: 2019-07-05
 **/
@Getter
@ApiModel("采购单的状态")
public enum PurchaseOrderLogEnum {

    DOWNLOAD_APPLY((int)0,"下载了采购文件"), // 下载
    INSERT_ORDER((int)1,"新增采购单"), // 新增
    UPDATE_ORDER((int)2,"修改采购单"),  // 修改

    CHECKOUT_STAY((int)3,"采购单已提交，等待审核"), // 待审核
    CHECKOUT((int)4,"采购单审核中"),  // 审核中
    CHECKOUT_ADOPT((int)5,"采购单审核通过"), // 审核通过
    STOCK_UP((int)6,"采购单供应商开始备货"), // 备货确认
    DELIVER_GOODS((int)7,"采购单供应商开始发货"), // 发货确认
    WAREHOUSING_BEGIN((int)8,""),  // 入库开始
    WAREHOUSING_IN((int)9,""), // 入库中
    WAREHOUSING_FINISH((int)10,""), // 入库完成
    ORDER_WAREHOUSING_FINISH((int)11,"采购单入库完成"), // 入库完成
    PURCHASE_FINISH((int)12,"采购单采购完成"),  // 采购完成
    REVOKE((int)13,"采购单撤销"), // 撤销
    CHECKOUT_NOT((int)14,"采购单审核不通过"), // 审核不通过
    STORAGE_STAY((int)15,"采购单仓储确认中"), // 仓储状态：确认中
    STORAGE_FINISH((int)16,"采购单仓储确认完成");  // 仓储状态：完成

    private int code;
    private String name;
    PurchaseOrderLogEnum(int code, String name){
        this.code = code;
        this.name = name;
    }
}
