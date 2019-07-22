package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.PurchaseOrderArrivalSubscribe;
import com.aiqin.bms.scmp.api.purchase.domain.request.purchase.QueryPurchaseOrderArrivalSubscribeVo;
import com.aiqin.bms.scmp.api.purchase.domain.request.purchase.SavePurchaseOrderArrivalSubscribeVo;
import com.aiqin.bms.scmp.api.purchase.domain.response.purchase.QueryPurchaseOrderArrivalSubscribeRespVo;
import com.aiqin.bms.scmp.api.purchase.mapper.PurchaseOrderArrivalSubscribeMapper;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseOrderArrivalSubscribeService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className PurchaseOrderArrivalSubscribeServieImpl
 * @date 2019/6/28 19:26
 * @description TODO
 */
@Service
public class PurchaseOrderArrivalSubscribeServieImpl implements PurchaseOrderArrivalSubscribeService {

    @Autowired
    private PurchaseOrderArrivalSubscribeMapper mapper;
    /**
     * 分页查询
     *
     * @param reqVo
     * @return
     */
    @Override
    public BasePage<QueryPurchaseOrderArrivalSubscribeRespVo> findPage(QueryPurchaseOrderArrivalSubscribeVo reqVo) {
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        List<QueryPurchaseOrderArrivalSubscribeRespVo> list = mapper.getList(reqVo);
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    /**
     * 保存预约到货
     *
     * @param reqVo
     * @return
     */
    @Override
    public int save(SavePurchaseOrderArrivalSubscribeVo reqVo) {
        if(Objects.isNull(reqVo.getInboundOderCode())){
            throw new BizException(ResultCode.INBOUND_ORDER_CODE_EMPTY);
        }
        if(Objects.isNull(reqVo.getPurchaseOrderCode())){
            throw new BizException(ResultCode.PURCHSAE_ORDER_CODE_EMPTY);
        }
        if(Objects.isNull(reqVo.getArrivalSubscribeStatus())){
            throw new BizException(ResultCode.ARRIVAL_SUBSCRIBE_STATUS_EMPTY);
        }
        int num = 0;
        //根据入库单号和采购单号查找数据
        PurchaseOrderArrivalSubscribe purchaseOrderArrivalSubscribe =
                mapper.selectByInboundAndPurchaseOrderCode(reqVo.getInboundOderCode(), reqVo.getPurchaseOrderCode());
        if(null == purchaseOrderArrivalSubscribe){
            purchaseOrderArrivalSubscribe = new PurchaseOrderArrivalSubscribe();
            BeanCopyUtils.copy(reqVo,purchaseOrderArrivalSubscribe);
            num = ((PurchaseOrderArrivalSubscribeService) AopContext.currentProxy()).insertSelective(purchaseOrderArrivalSubscribe);
        } else {
            //purchaseOrderArrivalSubscribe.setArrivalSubscribeStatus(Objects.equals(reqVo.getArrivalSubscribeStatus(),Byte.parseByte("3")) ? Byte.parseByte("1") : reqVo.getArrivalSubscribeStatus());
            purchaseOrderArrivalSubscribe.setRemark(reqVo.getRemark());
            purchaseOrderArrivalSubscribe.setArrivalSubscribeTime(reqVo.getArrivalSubscribeTime());
            purchaseOrderArrivalSubscribe.setDriverName(reqVo.getDriverName());
            purchaseOrderArrivalSubscribe.setLicensePlate(reqVo.getLicensePlate());
            purchaseOrderArrivalSubscribe.setPhoneMobile(reqVo.getPhoneMobile());
            num = ((PurchaseOrderArrivalSubscribeService) AopContext.currentProxy()).updateByPrimaryKeySelective(purchaseOrderArrivalSubscribe);
        }
        return num;
    }

    /**
     * 插入数据库
     *
     * @param record
     * @return
     */
    @Override
    @Save
    public int insertSelective(PurchaseOrderArrivalSubscribe record) {
        return mapper.insertSelective(record);
    }

    /**
     * 修改入数据库库
     *
     * @param record
     * @return
     */
    @Override
    @Update
    public int updateByPrimaryKeySelective(PurchaseOrderArrivalSubscribe record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
}
