package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.DeliveryInfoDao;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.DeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.DeliveryInfoRespVO;
import com.aiqin.bms.scmp.api.supplier.mapper.DeliveryInformationMapper;
import com.aiqin.bms.scmp.api.supplier.service.DeliveryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @功能说明:供货单位下发货信息service实现
 * @author: wangxu
 * @date: 2018/12/15 0015 15:17
 */
@Service
public class DeliveryInfoServiceImpl implements DeliveryInfoService {
    @Autowired
    private DeliveryInformationMapper deliveryInformationMapper;
    @Autowired
    private DeliveryInfoDao deliveryInfoDao;

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int insert(DeliveryInformation deliveryInformation) {
        try {
            int num = deliveryInformationMapper.insertSelective(deliveryInformation);
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
    public int update(DeliveryInformation deliveryInformation) {
        try {
            int num = deliveryInformationMapper.updateByPrimaryKeySelective(deliveryInformation);
            if (num > 0){
                return num;
            } else {
                throw new BizException(ResultCode.UPDATE_ERROR);
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(ResultCode.UPDATE_ERROR);
        }
    }

    /**
     * 根据供应商编码查询发货/退货信息
     *
     * @param supplyCompanyCode
     * @return
     */
    @Override
    public List<DeliveryInfoRespVO> getDeliveryInfoBySupplyCompanyCode(String supplyCompanyCode) {
        return deliveryInfoDao.getDeliveryInfoBySupplyCompanyCode(supplyCompanyCode);
    }
}
