package com.aiqin.bms.scmp.api.base;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;

/**
 * @author HuangLong
 * @date 2018/12/24
 */
public interface PurchaseResultCode {
    MessageId SYSTEM_ERROR = MessageId.create(Project.STORE_API, 500, "系统异常");
    MessageId TRANSFER_ERROR = MessageId.create(Project.PURCHASE_API, 1000, "采购接口调用异常");
    MessageId PRODUCT_LIST_API_ERROR = MessageId.create(Project.PRODUCT_API, 1001, "获取商品集合接口调用异常");

}
