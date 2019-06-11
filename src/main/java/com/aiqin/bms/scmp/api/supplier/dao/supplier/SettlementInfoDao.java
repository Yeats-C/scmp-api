package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SettlementInformation;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/23 0023 17:45
 */
public interface SettlementInfoDao {
    /**
     * 根据申请编码获取结算正式信息
     * @param applyCode
     * @return
     */
    SettlementInformation getSetInfoByApplyCode(String applyCode);
}
