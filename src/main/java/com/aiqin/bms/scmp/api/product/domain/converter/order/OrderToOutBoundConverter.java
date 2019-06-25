package com.aiqin.bms.scmp.api.product.domain.converter.order;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-25
 * @time: 14:47
 */
@Component
public class OrderToOutBoundConverter extends BaseServiceImpl implements Converter<List<OrderInfoDTO>, List<OutboundReqVo>> {

    @Override
    public List<OutboundReqVo> convert(List<OrderInfoDTO> source) {
        Date date = new Date();
        List<OutboundReqVo> list = Lists.newArrayList();
        for (OrderInfoDTO dto : source) {
            OutboundReqVo outbound = new OutboundReqVo();
            outbound.setCompanyCode(dto.getCompanyCode());
            outbound.setCompanyName(dto.getCompanyName());
            outbound.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
            outbound.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
            outbound.setOutboundTypeCode(OutboundTypeEnum.ORDER.getCode());
            outbound.setOutboundTypeName(OutboundTypeEnum.ORDER.getName());
            outbound.setSourceOderCode(dto.getOrderCode());
            outbound.setLogisticsCenterCode(dto.getTransportCenterCode());
            outbound.setLogisticsCenterName(dto.getTransportCenterName());
            outbound.setWarehouseCode(dto.getWarehouseCode());
            outbound.setWarehouseName(dto.getWarehouseName());
            outbound.setSupplierCode(dto.getSupplierCode());
            outbound.setSupplierName(dto.getSupplierName());
            outbound.setConsignee(dto.getConsignee());
            outbound.setConsigneeNumber(dto.getConsigneePhone());
            outbound.setConsigneeRate(dto.getZipCode());
            outbound.setProvinceCode(dto.getProvinceCode());
            outbound.setProvinceName(dto.getProvinceName());
            outbound.setCityCode(dto.getCityCode());
            outbound.setCityName(dto.getCityName());
            outbound.setCountyCode(dto.getDistrictCode());
            outbound.setCountyName(dto.getDistrictName());
            outbound.setDetailedAddress(dto.getDetailAddress());
            outbound.setCreateBy(Optional.ofNullable(getUser().getPersonName()).orElse(CommonConstant.SYSTEM_AUTO));
            outbound.setCreateTime(date);
            //预计到货时间是当前时间加5天
//            outbound.setPreArrivalTime();
            outbound.setPreOutboundNum(dto.getProductNum());
            outbound.setPreMainUnitNum(dto.getProductNum());
//            outbound.setPreTaxAmount();
//            outbound.setPreAmount();
//            outbound.setPreTax();
//            outbound.setList();
//            outbound.setOutboundBatchList();
            list.add(outbound);
        }
        return list;
    }
}
