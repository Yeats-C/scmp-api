package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SettlementInformation;
import com.aiqin.bms.scmp.api.supplier.mapper.SettlementInformationMapper;
import com.aiqin.bms.scmp.api.supplier.service.SettlementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @功能说明:供货单位下结算信息Service实现
 * @author: wangxu
 * @date: 2018/12/15 0015 15:11
 */
@Service
public class SettlementInfoServiceImpl implements SettlementInfoService {
    @Autowired
    private SettlementInformationMapper settlementInformationMapper;

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int insert(SettlementInformation settlementInformation) {
        try {
            int num = settlementInformationMapper.insertSelective(settlementInformation);
            if (num > 0){
                return num;
            } else {
                throw new BizException(ResultCode.ADD_ERROR);
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(ResultCode.ADD_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int update(SettlementInformation settlementInformation) {
        try {
            int num = settlementInformationMapper.updateByPrimaryKeySelective(settlementInformation);
            if (num > 0){
                return num;
            } else {
                throw new BizException(ResultCode.UPDATE_ERROR);
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(ResultCode.UPDATE_ERROR);
        }
    }
}
