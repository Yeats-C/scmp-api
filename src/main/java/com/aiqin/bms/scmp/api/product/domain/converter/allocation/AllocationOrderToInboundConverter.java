package com.aiqin.bms.scmp.api.product.domain.converter.allocation;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.AllocationTypeEnum;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPicturesDao;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProductBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
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
public class AllocationOrderToInboundConverter implements Converter<AllocationDTO, InboundReqSave> {

    private WarehouseService warehouseService;

    private AllocationTypeEnum allocationTypeEnum;

    private ProductSkuPicturesDao productSkuPicturesDao;

    public AllocationOrderToInboundConverter (WarehouseService warehouseService,AllocationTypeEnum allocationTypeEnum,ProductSkuPicturesDao productSkuPicturesDao) {
        this.warehouseService = warehouseService;
        this.allocationTypeEnum = allocationTypeEnum;
        this.productSkuPicturesDao = productSkuPicturesDao;
    }
    @Override
    public InboundReqSave convert(AllocationDTO order) {
        WarehouseResVo warehouseByCode = warehouseService.getWarehouseByCode(order.getCallInWarehouseCode());
        if(Objects.isNull(warehouseByCode)){
            throw new GroundRuntimeException("调拨查询联系人信息失败");
        }
        InboundReqSave inboundReqSave = new InboundReqSave();
        inboundReqSave.setCompanyCode(order.getCompanyCode());
        inboundReqSave.setCompanyName(order.getCompanyName());
        inboundReqSave.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        inboundReqSave.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
        inboundReqSave.setInboundTypeCode(allocationTypeEnum.getInOutType());
        inboundReqSave.setInboundTypeName(allocationTypeEnum.getTypeName());
        inboundReqSave.setSourceOderCode(order.getAllocationCode());
        inboundReqSave.setLogisticsCenterCode(warehouseByCode.getLogisticsCenterCode());
        inboundReqSave.setLogisticsCenterName(warehouseByCode.getLogisticsCenterName());
        inboundReqSave.setWarehouseCode(warehouseByCode.getWarehouseCode());
        inboundReqSave.setWarehouseName(warehouseByCode.getWarehouseName());
        inboundReqSave.setSupplierCode(null);
        inboundReqSave.setSupplierName(null);
        inboundReqSave.setShipper(warehouseByCode.getContact());
        inboundReqSave.setShipperNumber(warehouseByCode.getPhone());
        inboundReqSave.setShipperRate(null);
        inboundReqSave.setProvinceCode(warehouseByCode.getProvinceCode());
        inboundReqSave.setProvinceName(warehouseByCode.getProvinceName());
        inboundReqSave.setCityCode(warehouseByCode.getCityCode());
        inboundReqSave.setCityName(warehouseByCode.getCityName());
        inboundReqSave.setCountyCode(warehouseByCode.getCountyCode());
        inboundReqSave.setCountyName(warehouseByCode.getCountyName());
        inboundReqSave.setDetailedAddress(warehouseByCode.getDetailedAddress());
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
        List<AllocationProduct> records = order.getProducts();
        ProductSkuPictures productSkuPicture = null;
        for (AllocationProduct record : records) {
            InboundProductReqVo reqVo1 = new InboundProductReqVo();
            productSkuPicture = productSkuPicturesDao.getPicInfoBySkuCode(record.getSkuCode());
            reqVo1.setSkuCode(record.getSkuCode());
            reqVo1.setSkuName(record.getSkuName());
            if(null != productSkuPicture){
                reqVo1.setPictureUrl(productSkuPicture.getProductPicturePath());
            } else {
                reqVo1.setPictureUrl(null);
            }
            reqVo1.setNorms(record.getSpecification());
            reqVo1.setColorName(record.getColor());
            reqVo1.setColorCode(null);
            reqVo1.setModel(record.getModel());
            reqVo1.setUnitCode(null);
            reqVo1.setUnitName(record.getUnit());
            reqVo1.setInboundNorms(record.getSpecification());
            //由于调拨/移库/报废 是库存信息,故基商品含量默认为1
            reqVo1.setInboundBaseContent("1");
            reqVo1.setInboundBaseUnit("1");
            reqVo1.setPreInboundNum(record.getQuantity());
            reqVo1.setPreInboundMainNum(record.getQuantity());
            reqVo1.setPreTaxPurchaseAmount(record.getTaxPrice());
            reqVo1.setPreTaxAmount(record.getTaxAmount());
            reqVo1.setCreateBy(record.getCreateBy());
            reqVo1.setCreateTime(record.getCreateTime());
            reqVo1.setUpdateBy(record.getUpdateBy());
            reqVo1.setUpdateTime(record.getUpdateTime());
            if(!Objects.isNull(record.getLineNum()) && record.getLineNum() != null){
                reqVo1.setLinenum(record.getLineNum().longValue());
            }
            preInboundNum += record.getQuantity().intValue();
            preInboundMainNum += record.getQuantity().intValue();
            preTaxAmount = record.getTaxAmount().add(preTaxAmount);
            preNoTaxAmount = Calculate.computeNoTaxPrice(record.getTaxAmount(), BigDecimal.valueOf(record.getTax().longValue()).add(preNoTaxAmount));
            list.add(reqVo1);
        }
        List<AllocationProductBatch> list1 = order.getList();
        for (AllocationProductBatch batch1 : list1) {
            //批次
            InboundBatchReqVo batch = new InboundBatchReqVo();
            batch.setSkuCode(batch1.getSkuCode());
            batch.setSkuName(batch1.getSkuName());
            batch.setInboundBatchCode(batch1.getCallInBatchNumber());
            batch.setManufactureTime(Objects.nonNull(batch1.getProductDate())?DateUtils.toDate(batch1.getProductDate()):null);
            batch.setBatchRemark(batch.getBatchRemark());
            batch.setPreQty(batch1.getQuantity().longValue());
            batch.setCreateBy(batch1.getCreateBy());
            batch.setCreateTime(batch1.getCreateTime());
            batch.setUpdateBy(batch1.getUpdateBy());
            batch.setUpdateTime(batch1.getUpdateTime());
            if(!Objects.isNull(batch1.getLineNum()) && batch1.getLineNum() != null){
                batch.setLinenum(batch1.getLineNum().longValue());
            }
            inboundBatchReqVos.add(batch);
        }
        inboundReqSave.setPreInboundNum(preInboundNum);
        inboundReqSave.setPreMainUnitNum(preInboundMainNum);
        inboundReqSave.setPreTaxAmount(preTaxAmount);
        inboundReqSave.setPreAmount(preNoTaxAmount);
        inboundReqSave.setPreTax(preTaxAmount.subtract(preNoTaxAmount));
        inboundReqSave.setInboundBatchReqVos(inboundBatchReqVos);
        inboundReqSave.setList(list);
        return inboundReqSave;
    }
}
