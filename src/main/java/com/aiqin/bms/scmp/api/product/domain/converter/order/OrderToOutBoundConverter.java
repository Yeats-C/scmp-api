package com.aiqin.bms.scmp.api.product.domain.converter.order;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-25
 * @time: 14:47
 */
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
            outbound.setCreateBy(dto.getOperator());
            outbound.setCreateTime(date);
            //预计到货时间是当前时间加5天
            outbound.setPreArrivalTime(DateUtils.addDay(5));
            outbound.setPreOutboundNum(dto.getProductNum());
            outbound.setPreMainUnitNum(dto.getProductNum());
            outbound.setPreTaxAmount(dto.getProductTotalAmount());
            outbound.setPreAmount(Calculate.computeNoTaxPrice(dto.getProductTotalAmount(), Long.valueOf(101)));
            outbound.setPreTax(outbound.getPreTaxAmount().subtract(outbound.getPreAmount()));
            List<OutboundProductReqVo> reqVos = BeanCopyUtils.copyList(dto.getItemList(), OutboundProductReqVo.class);
            outbound.setList(reqVos);
            List<OutboundBatch> batches = BeanCopyUtils.copyList(dto.getBatchList(), OutboundBatch.class);
            outbound.setOutboundBatches(batches);
            //TODO 个别字段需要单独set，这里暂时不做处理
            list.add(outbound);
        }
        return list;
    }
}
