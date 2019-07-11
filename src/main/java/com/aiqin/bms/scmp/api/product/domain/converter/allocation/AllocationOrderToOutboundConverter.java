package com.aiqin.bms.scmp.api.product.domain.converter.allocation;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.AllocationTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProductBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-27
 * @time: 17:54
 */
@Component
public class AllocationOrderToOutboundConverter implements Converter<AllocationDTO,OutboundReqVo> {

    private WarehouseService warehouseService;

    private AllocationTypeEnum allocationTypeEnum;

    public AllocationOrderToOutboundConverter (WarehouseService warehouseService,AllocationTypeEnum allocationTypeEnum) {
        this.warehouseService = warehouseService;
        this.allocationTypeEnum = allocationTypeEnum;
    }

    @Override
    public OutboundReqVo convert(AllocationDTO order) {
        WarehouseResVo warehouseByCode = warehouseService.getWarehouseByCode(order.getCallInWarehouseCode());
        OutboundReqVo outboundReqVo = new OutboundReqVo();
        outboundReqVo.setCompanyCode(order.getCompanyCode());
        outboundReqVo.setCompanyName(order.getCompanyName());
        outboundReqVo.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        outboundReqVo.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
        outboundReqVo.setOutboundTypeCode(allocationTypeEnum.getInOutType());
        outboundReqVo.setOutboundTypeName(allocationTypeEnum.getTypeName());
        outboundReqVo.setSourceOderCode(order.getAllocationCode());
        outboundReqVo.setLogisticsCenterCode(warehouseByCode.getLogisticsCenterCode());
        outboundReqVo.setLogisticsCenterName(warehouseByCode.getLogisticsCenterName());
        outboundReqVo.setWarehouseCode(warehouseByCode.getWarehouseCode());
        outboundReqVo.setWarehouseName(warehouseByCode.getWarehouseName());
        outboundReqVo.setSupplierCode(null);
        outboundReqVo.setSupplierName(null);
        outboundReqVo.setConsignee(warehouseByCode.getContact());
        outboundReqVo.setConsigneeNumber(warehouseByCode.getPhone());
        outboundReqVo.setConsigneeRate(null);
        outboundReqVo.setProvinceCode(warehouseByCode.getProvinceCode());
        outboundReqVo.setProvinceName(warehouseByCode.getProvinceName());
        outboundReqVo.setCityCode(warehouseByCode.getCityCode());
        outboundReqVo.setCityName(warehouseByCode.getCityName());
        outboundReqVo.setCountyCode(warehouseByCode.getCountyCode());
        outboundReqVo.setCountyName(warehouseByCode.getCountyName());
        outboundReqVo.setDetailedAddress(warehouseByCode.getDetailedAddress());
        outboundReqVo.setPreArrivalTime(DateUtils.addDay(5));
        outboundReqVo.setCreateBy(order.getCreateBy());
        outboundReqVo.setCreateTime(order.getCreateTime());
        outboundReqVo.setUpdateBy(order.getUpdateBy());
        outboundReqVo.setUpdateTime(order.getUpdateTime());
        List<OutboundProductReqVo> list = Lists.newArrayList();
        List<OutboundBatch> outboundBatchReqVos = Lists.newArrayList();
        Long preInboundNum = 0L;
        Long preInboundMainNum = 0L;
        Long preTaxAmount = 0L;
        Long preTax = 0L;
        List<AllocationProduct> records = order.getProducts();
        for (AllocationProduct record : records) {
            OutboundProductReqVo reqVo1 = new OutboundProductReqVo();
            reqVo1.setSkuCode(record.getSkuCode());
            reqVo1.setSkuName(record.getSkuName());
            reqVo1.setPictureUrl(null);
            reqVo1.setNorms(record.getSpecification());
            reqVo1.setColorName(record.getColor());
            reqVo1.setColorCode(null);
            reqVo1.setModel(record.getModel());
            reqVo1.setUnitCode(null);
            reqVo1.setUnitName(record.getUnit());
            reqVo1.setOutboundNorms(record.getSpecification());
            reqVo1.setOutboundBaseContent(null);
            reqVo1.setPreOutboundNum(record.getQuantity().longValue());
            reqVo1.setPreOutboundMainNum(record.getQuantity().longValue());
            reqVo1.setPreTaxPurchaseAmount(record.getTaxPrice().longValue());
            reqVo1.setPreTaxAmount(record.getTaxAmount().longValue());
            reqVo1.setCreateBy(record.getCreateBy());
            reqVo1.setCreateTime(record.getCreateTime());
            reqVo1.setUpdateBy(record.getUpdateBy());
            reqVo1.setUpdateTime(record.getUpdateTime());
            reqVo1.setLinenum(record.getLineNum().longValue());
            preInboundNum += record.getQuantity().intValue();
            preInboundMainNum += record.getQuantity().intValue();
            preTaxAmount += record.getTaxAmount();
            preTax += Calculate.computeNoTaxPrice(record.getTaxAmount().longValue(), record.getTax().longValue());
            list.add(reqVo1);
        }
        List<AllocationProductBatch> list1 = order.getList();
        for (AllocationProductBatch batch1 : list1) {
            //批次
            OutboundBatch batch = new OutboundBatch();
            batch.setSkuCode(batch1.getSkuCode());
            batch.setSkuName(batch1.getSkuName());
            batch.setOutboundBatchCode(batch1.getCallInBatchNumber());
            batch.setManufactureTime(Objects.nonNull(batch1.getProductDate())?DateUtils.toDate(batch1.getProductDate()):null);
            batch.setBatchRemark(batch.getBatchRemark());
            batch.setPreQty(batch1.getQuantity().longValue());
            batch.setCreateBy(batch1.getCreateBy());
            batch.setCreateTime(batch1.getCreateTime());
            batch.setUpdateBy(batch1.getUpdateBy());
            batch.setUpdateTime(batch1.getUpdateTime());
            batch.setLineNum(batch1.getLineNum().longValue());
            outboundBatchReqVos.add(batch);
        }
        outboundReqVo.setPreOutboundNum(preInboundNum);
        outboundReqVo.setPreMainUnitNum(preInboundMainNum);
        outboundReqVo.setPreTaxAmount(preTaxAmount);
        outboundReqVo.setPreAmount(preTaxAmount-preTax);
        outboundReqVo.setPreTax(preTax);
        outboundReqVo.setOutboundBatches(outboundBatchReqVos);
        outboundReqVo.setList(list);
        return outboundReqVo;
    }
}
