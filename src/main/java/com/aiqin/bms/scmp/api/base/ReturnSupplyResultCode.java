package com.aiqin.bms.scmp.api.base;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;

/**
 * @author HuangLong
 * @date 2019/1/4
 */

public interface ReturnSupplyResultCode {
    MessageId SYSTEM_ERROR = MessageId.create(Project.STORE_API, 500, "系统异常");
    MessageId TRANSFER_ERROR = MessageId.create(Project.PURCHASE_API, 2000, "退供接口调用异常");
    MessageId STORE_API_LIST_ERROR = MessageId.create(Project.STORE_API, 2001, "库存接口调用异常");

}
