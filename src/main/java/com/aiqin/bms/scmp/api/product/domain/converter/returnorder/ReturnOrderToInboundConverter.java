package com.aiqin.bms.scmp.api.product.domain.converter.returnorder;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.dto.returnorder.ReturnOrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-27
 * @time: 17:54
 */
@Component
public class ReturnOrderToInboundConverter implements Converter<ReturnOrderInfoDTO, List<InboundReqSave>> {

    @Override
    public List<InboundReqSave> convert(ReturnOrderInfoDTO source) {
        Date date = new Date();
        //根据仓分组
        List<InboundReqSave> list = Lists.newArrayList();
        List<ReturnOrderInfoInspectionItem> items = source.getItems();
        Map<String, List<ReturnOrderInfoInspectionItem>> collect = items.stream().collect(Collectors.groupingBy(ReturnOrderInfoInspectionItem::getWarehouseCode));
        AtomicInteger i = new AtomicInteger(1);
        collect.forEach((k,v)->{
            InboundReqSave save = new InboundReqSave();
            save.setCompanyCode(source.getCompanyCode());
            save.setCompanyName(source.getCompanyName());
            save.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
            save.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
            save.setInboundOderCode(source.getReturnOrderCode()+"_"+i.getAndIncrement());
            save.setInboundTypeCode(InboundTypeEnum.ORDER.getCode());
            save.setInboundTypeName(InboundTypeEnum.ORDER.getName());
            save.setSourceOderCode(source.getReturnOrderCode());
            save.setLogisticsCenterCode(source.getTransportCenterCode());
            save.setLogisticsCenterName(source.getTransportCenterName());
            save.setWarehouseCode(v.get(0).getWarehouseCode());
            save.setWarehouseName(v.get(0).getWarehouseName());
            save.setSupplierCode(source.getSupplierCode());
            save.setSupplierName(source.getSupplierName());
            save.setPreArrivalTime(DateUtils.addDay(5));
//            save.setPreInboundNum();
//            save.setPreMainUnitNum();
//            save.setPreTaxAmount();
//            save.setPreAmount();
//            save.setPreTax();
            save.setShipper(source.getConsignee());
            save.setShipperNumber(source.getConsigneePhone());
            save.setShipperRate(source.getZipCode());
            save.setProvinceCode(source.getProvinceCode());
            save.setProvinceName(source.getProvinceName());
            save.setCityCode(source.getCityCode());
            save.setCityName(source.getCityName());
            save.setCountyCode(source.getDistrictCode());
            save.setCountyName(source.getDistrictName());
            save.setDetailedAddress(source.getDetailAddress());
            save.setCreateBy(source.getOperator());
            save.setCreateTime(date);
            //todo 有些值需要单独设置
            save.setList(BeanCopyUtils.copyList(source.getItemList(), InboundProductReqVo.class));
            save.setInboundBatchReqVos(BeanCopyUtils.copyList(v, InboundBatchReqVo.class));
        });
        return list;
    }
}
