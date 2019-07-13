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

    CHECKOUT_STAY(1,"采购单已提交，等待审核"), // 待审核
    CHECKOUT(2,"采购单审核中"),  // 审核中
    CHECKOUT_ADOPT(3,"采购单审核通过"), // 审核通过
    STOCK_UP(4,"采购单供应商开始备货"), // 备货确认
    DELIVER_GOODS(6,"采购单供应商开始发货"), // 发货确认
    WAREHOUSING_BEGIN(7,"入库申请单，开始入库"),  // 入库开始
    WAREHOUSING_IN(8,"入库申请单，入库中"), // 入库中
    WAREHOUSING_FINISH(9,"入库申请单，入库完成"), // 入库完成
    ORDER_WAREHOUSING_FINISH(10,"采购单入库完成"), // 入库完成
    PURCHASE_FINISH(1,"采购单采购完成"),  // 采购完成
    REVOKE(12,"采购单撤销"), // 撤销
    CHECKOUT_NOT(13,"采购单审核不通过"), // 审核不通过
    STORAGE_STAY(14,"采购单仓储确认中"), // 仓储状态：确认中
    STORAGE_FINISH(15,"采购单仓储确认完成"),  // 仓储状态：完成

    DOWNLOAD_APPLY(16,"下载了采购文件"), // 下载
    INSERT_ORDER(17,"新增采购单"), // 新增
    UPDATE_ORDER(18,"修改采购单"),  // 修改
    COMPLETE(19,"完成");

    private int code;
    private String name;
    PurchaseOrderLogEnum(int code, String name){
        this.code = code;
        this.name = name;
    }
}
