package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SettlementInformation;

/**
 * @功能说明:供货单位下结算信息Service
 * @author: wangxu
 * @date: 2018/12/15 0015 15:09
 */
public interface SettlementInfoService {
    /**
     * 调用dao层修改供货单位下结算信息
     * @param settlementInformation
     * @return
     */
    int update(SettlementInformation settlementInformation);

    /**
     * 调用dao层新增供货单位下结算信息
     * @param settlementInformation
     * @return
     */
    int insert(SettlementInformation settlementInformation);
}
