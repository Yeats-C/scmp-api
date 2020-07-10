package com.aiqin.bms.scmp.api.product.domain.converter.allocation;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.AllocationTypeEnum;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPicturesDao;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProductBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-27
 * @time: 17:54
 */
public class AllocationOrderToOutboundConverter implements Converter<AllocationDTO,OutboundReqVo> {

    private WarehouseService warehouseService;

    private AllocationTypeEnum allocationTypeEnum;

    private ProductSkuPicturesDao productSkuPicturesDao;

    public AllocationOrderToOutboundConverter (WarehouseService warehouseService,AllocationTypeEnum allocationTypeEnum,ProductSkuPicturesDao productSkuPicturesDao) {
        this.warehouseService = warehouseService;
        this.allocationTypeEnum = allocationTypeEnum;
        this.productSkuPicturesDao = productSkuPicturesDao;
    }

    @Override
    public OutboundReqVo convert(AllocationDTO order) {
        WarehouseResVo warehouseByCode = warehouseService.getWarehouseByCode(order.getCallOutWarehouseCode());
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
//        outboundReqVo.setSupplierCode(null);
//        outboundReqVo.setSupplierName(null);
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
        BigDecimal preTaxAmount = BigDecimal.ZERO;
        BigDecimal preNoTaxAmount = BigDecimal.ZERO;
        ProductSkuPictures productSkuPicture = null;
        List<AllocationProduct> records = order.getProducts();
        for (AllocationProduct record : records) {
            OutboundProductReqVo reqVo1 = new OutboundProductReqVo();
            productSkuPicture = productSkuPicturesDao.getPicInfoBySkuCode(record.getSkuCode());
            if(null != productSkuPicture){
                reqVo1.setPictureUrl(productSkuPicture.getProductPicturePath());
            } else {
                reqVo1.setPictureUrl( null);
            }

            reqVo1.setSkuCode(record.getSkuCode());
            reqVo1.setSkuName(record.getSkuName());
            reqVo1.setNorms(record.getSpecification());
            reqVo1.setColorName(record.getColor());
            reqVo1.setColorCode(null);
            reqVo1.setModel(record.getModel());
            reqVo1.setUnitCode(null);
            reqVo1.setUnitName(record.getUnit());
            reqVo1.setOutboundNorms(record.getSpecification());
            //由于调拨/移库/报废 是库存信息,故基商品含量默认为1
            reqVo1.setOutboundBaseContent("1");
            reqVo1.setOutboundBaseUnit("1");
            reqVo1.setPreOutboundNum(record.getQuantity().longValue());
            reqVo1.setPreOutboundMainNum(record.getQuantity().longValue());
            reqVo1.setPreTaxPurchaseAmount(record.getTaxPrice());
            reqVo1.setPreTaxAmount(record.getTaxAmount());
            reqVo1.setCreateBy(record.getCreateBy());
            reqVo1.setCreateTime(record.getCreateTime());
            reqVo1.setUpdateBy(record.getUpdateBy());
            reqVo1.setUpdateTime(record.getUpdateTime());
            if(record.getLineNum() != null){
                reqVo1.setLinenum(record.getLineNum().longValue());
            }
            preInboundNum += record.getQuantity().intValue();
            preInboundMainNum += record.getQuantity().intValue();
            preTaxAmount = record.getTaxAmount().add(preTaxAmount);
            preNoTaxAmount = Calculate.computeNoTaxPrice(record.getTaxAmount(), BigDecimal.valueOf(record.getTax().longValue())).add(preNoTaxAmount);
            list.add(reqVo1);
        }
        List<AllocationProductBatch> list1 = order.getList();
        for (AllocationProductBatch batch1 : list1) {
            //批次
            OutboundBatch batch = new OutboundBatch();
            batch.setSkuCode(batch1.getSkuCode());
            batch.setSkuName(batch1.getSkuName());
            batch.setBatchCode(batch1.getCallInBatchNumber());
          //  batch.setProductDate(Objects.nonNull(batch1.getProductDate())?DateUtils.toDate(batch1.getProductDate()):null);
            batch.setProductDate(Objects.nonNull(batch1.getProductDate())?batch1.getProductDate():null);
            batch.setBatchRemark(batch.getBatchRemark());
            batch.setTotalCount(batch1.getQuantity().longValue());
            batch.setCreateByName(batch1.getCreateBy());
            batch.setCreateTime(batch1.getCreateTime());
            batch.setUpdateByName(batch1.getUpdateBy());
            batch.setUpdateTime(batch1.getUpdateTime());
            batch.setLineCode(batch1.getLineNum().longValue());
            outboundBatchReqVos.add(batch);
            if(batch1.getSupplierCode() != null){
                outboundReqVo.setSupplierCode(batch1.getSupplierCode());
            }
        }
        outboundReqVo.setPreOutboundNum(preInboundNum);
        outboundReqVo.setPreMainUnitNum(preInboundMainNum);
        outboundReqVo.setPreTaxAmount(preTaxAmount);
        outboundReqVo.setPreAmount(preNoTaxAmount);
        outboundReqVo.setPreTax(preTaxAmount.subtract(preNoTaxAmount));
        outboundReqVo.setOutboundBatches(outboundBatchReqVos);
        outboundReqVo.setList(list);
        return outboundReqVo;
    }
}
