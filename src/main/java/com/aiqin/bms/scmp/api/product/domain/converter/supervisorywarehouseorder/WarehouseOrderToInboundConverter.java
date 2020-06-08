package com.aiqin.bms.scmp.api.product.domain.converter.supervisorywarehouseorder;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPicturesDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrder;
import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrderProduct;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-27
 * @time: 17:54
 */
@Component
public class WarehouseOrderToInboundConverter implements Converter<SupervisoryWarehouseOrder, InboundReqSave> {

    private ProductSkuPicturesDao productSkuPicturesDao;

    public WarehouseOrderToInboundConverter(ProductSkuPicturesDao productSkuPicturesDao) {
        this.productSkuPicturesDao = productSkuPicturesDao;
    }

    @Override
    public InboundReqSave convert(SupervisoryWarehouseOrder order) {
        InboundReqSave inboundReqSave = new InboundReqSave();
        inboundReqSave.setCompanyCode(order.getCompanyCode());
        inboundReqSave.setCompanyName(order.getCompanyName());
        inboundReqSave.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        inboundReqSave.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
        inboundReqSave.setInboundTypeCode(InboundTypeEnum.SUPERVISORY_WAREHOUSE_INBOUND.getCode());
        inboundReqSave.setInboundTypeName(InboundTypeEnum.SUPERVISORY_WAREHOUSE_INBOUND.getName());
        inboundReqSave.setSourceOderCode(order.getOrderCode());
        inboundReqSave.setLogisticsCenterCode(order.getTransportCenterCode());
        inboundReqSave.setLogisticsCenterName(order.getTransportCenterName());
        inboundReqSave.setWarehouseCode(order.getWarehouseCode());
        inboundReqSave.setWarehouseName(order.getWarehouseName());
        inboundReqSave.setSupplierCode(order.getCustomerCode());
        inboundReqSave.setSupplierName(order.getCustomerName());
        inboundReqSave.setPreArrivalTime(DateUtils.addDay(5));
        inboundReqSave.setCreateBy(order.getCreateBy());
        inboundReqSave.setCreateTime(order.getCreateTime());
        inboundReqSave.setUpdateBy(order.getUpdateBy());
        inboundReqSave.setUpdateTime(order.getUpdateTime());
        List<InboundProductReqVo> list = Lists.newArrayList();
        List<InboundBatchReqVo> inboundBatchReqVos = Lists.newArrayList();
        Long preInboundNum = 0L;
        Long preInboundMainNum = 0L;
        BigDecimal preTaxAmount = BigDecimal.ZERO;
        BigDecimal preNoTaxAmount = BigDecimal.ZERO;
        List<SupervisoryWarehouseOrderProduct> records = order.getRecords();
        for (SupervisoryWarehouseOrderProduct record : records) {
            InboundProductReqVo reqVo1 = new InboundProductReqVo();
            reqVo1.setSkuCode(record.getSkuCode());
            reqVo1.setSkuName(record.getSkuName());
            ProductSkuPictures productSkuPicture = productSkuPicturesDao.getPicInfoBySkuCode(record.getSkuCode());
            reqVo1.setPictureUrl(productSkuPicture.getProductPicturePath());
            reqVo1.setNorms(record.getProductSpec());
            reqVo1.setColorName(record.getColorName());
            reqVo1.setColorCode(null);
            reqVo1.setModel(record.getModelNumber());
            reqVo1.setUnitCode(record.getUnitCode());
            reqVo1.setUnitName(record.getUnitName());
            reqVo1.setInboundNorms(record.getProductSpec());
            reqVo1.setInboundBaseUnit("1");
            reqVo1.setInboundBaseContent(record.getBaseProductContent().toString());
            reqVo1.setPreInboundNum(record.getNum().longValue());
            reqVo1.setPreInboundMainNum(record.getSingleCount().longValue());
            reqVo1.setPreTaxPurchaseAmount(record.getProductAmount());
            reqVo1.setPreTaxAmount(record.getProductTotalAmount());
            reqVo1.setCreateBy(record.getCreateBy());
            reqVo1.setCreateTime(record.getCreateTime());
            reqVo1.setUpdateBy(record.getUpdateBy());
            reqVo1.setUpdateTime(record.getUpdateTime());
            reqVo1.setLinenum(record.getLineNum().longValue());
            //批次
            InboundBatchReqVo batch = new InboundBatchReqVo();
            batch.setSkuCode(record.getSkuCode());
            batch.setSkuName(record.getSkuName());
            batch.setBatchCode(record.getSkuBatchNumber());
            batch.setProductDate(record.getProductDate());
            batch.setBatchRemark(null);
            batch.setTotalCount(record.getNum().longValue());
            batch.setCreateBy(record.getCreateBy());
            batch.setCreateTime(record.getCreateTime());
            batch.setUpdateBy(record.getUpdateBy());
            batch.setUpdateTime(record.getUpdateTime());
            batch.setLineCode(record.getLineNum().longValue());
            preInboundNum += record.getNum().intValue();
            preInboundMainNum += record.getSingleCount().intValue();
            preTaxAmount = record.getProductTotalAmount().add(preTaxAmount);
            preNoTaxAmount = Calculate.computeNoTaxPrice(record.getProductTotalAmount(), BigDecimal.valueOf(record.getTaxRate().longValue())).add(preNoTaxAmount);
            list.add(reqVo1);
            inboundBatchReqVos.add(batch);
        }

        inboundReqSave.setPreInboundNum(preInboundNum);
        inboundReqSave.setPreMainUnitNum(preInboundMainNum);
        inboundReqSave.setPreTaxAmount(preTaxAmount);
        inboundReqSave.setPreAmount(preNoTaxAmount);
        inboundReqSave.setPreTax(preTaxAmount.subtract(preNoTaxAmount));
        inboundReqSave.setInboundBatchReqVos(inboundBatchReqVos);
        inboundReqSave.setRemark(order.getRemark());
        inboundReqSave.setList(list);
        return inboundReqSave;
    }
}
