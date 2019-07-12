package com.aiqin.bms.scmp.api.product.domain.converter.supervisorywarehouseorder;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrder;
import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrderProduct;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-27
 * @time: 17:54
 */
public class WarehouseOrderToOutboundConverter implements Converter<SupervisoryWarehouseOrder,OutboundReqVo> {

    @Override
    public OutboundReqVo convert(SupervisoryWarehouseOrder order) {
        OutboundReqVo outboundReqVo = new OutboundReqVo();
        outboundReqVo.setCompanyCode(order.getCompanyCode());
        outboundReqVo.setCompanyName(order.getCompanyName());
        outboundReqVo.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        outboundReqVo.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
        outboundReqVo.setOutboundTypeCode(OutboundTypeEnum.SUPERVISORY__WAREHOUSE_OUTBOUND.getCode());
        outboundReqVo.setOutboundTypeName(OutboundTypeEnum.SUPERVISORY__WAREHOUSE_OUTBOUND.getName());
        outboundReqVo.setSourceOderCode(order.getOrderCode());
        outboundReqVo.setLogisticsCenterCode(order.getTransportCenterCode());
        outboundReqVo.setLogisticsCenterName(order.getTransportCenterName());
        outboundReqVo.setWarehouseCode(order.getWarehouseCode());
        outboundReqVo.setWarehouseName(order.getWarehouseName());
        outboundReqVo.setSupplierCode(order.getCustomerCode());
        outboundReqVo.setSupplierName(order.getCustomerName());
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
        List<SupervisoryWarehouseOrderProduct> records = order.getRecords();
        for (SupervisoryWarehouseOrderProduct record : records) {
            OutboundProductReqVo reqVo1 = new OutboundProductReqVo();
            reqVo1.setSkuCode(record.getSkuCode());
            reqVo1.setSkuName(record.getSkuName());
            reqVo1.setPictureUrl(null);
            reqVo1.setNorms(record.getProductSpec());
            reqVo1.setColorName(record.getColorName());
            reqVo1.setColorCode(null);
            reqVo1.setModel(record.getModelNumber());
            reqVo1.setUnitCode(record.getUnitCode());
            reqVo1.setUnitName(record.getUnitName());
            reqVo1.setOutboundNorms(record.getProductSpec());
            reqVo1.setOutboundBaseContent(record.getBaseProductContent().toString());
            reqVo1.setPreOutboundNum(record.getNum().longValue());
            reqVo1.setPreOutboundMainNum(record.getNum().longValue());
            reqVo1.setPreTaxPurchaseAmount(record.getProductAmount().longValue());
            reqVo1.setPreTaxAmount(record.getProductTotalAmount().longValue());
            reqVo1.setCreateBy(record.getCreateBy());
            reqVo1.setCreateTime(record.getCreateTime());
            reqVo1.setUpdateBy(record.getUpdateBy());
            reqVo1.setUpdateTime(record.getUpdateTime());
            reqVo1.setLinenum(record.getLineNum().longValue());
            //批次
            OutboundBatch batch = new OutboundBatch();
            batch.setSkuCode(record.getSkuCode());
            batch.setSkuName(record.getSkuName());
            batch.setOutboundBatchCode(record.getSkuBatchNumber());
            batch.setManufactureTime(DateUtils.toDate(record.getProductDate()));
            batch.setBatchRemark(null);
            batch.setPreQty(record.getNum().longValue());
            batch.setCreateBy(record.getCreateBy());
            batch.setCreateTime(record.getCreateTime());
            batch.setUpdateBy(record.getUpdateBy());
            batch.setUpdateTime(record.getUpdateTime());
            batch.setLineNum(record.getLineNum().longValue());
            preInboundNum += record.getNum().intValue();
            preInboundMainNum += record.getNum().intValue();
            preTaxAmount += record.getProductTotalAmount();
            preTax += Calculate.computeNoTaxPrice(record.getProductTotalAmount().longValue(), record.getTaxRate().longValue());
            list.add(reqVo1);
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
