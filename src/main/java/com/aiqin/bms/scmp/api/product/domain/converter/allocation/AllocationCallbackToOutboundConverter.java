package com.aiqin.bms.scmp.api.product.domain.converter.allocation;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.AllocationTypeEnum;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPicturesDao;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationCallbackReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductCallbackReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuStockInfoMapper;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Objects;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-27
 * @time: 17:54
 */
public class AllocationCallbackToOutboundConverter implements Converter<AllocationCallbackReqVo,OutboundReqVo> {

    private WarehouseService warehouseService;

    private ProductSkuPicturesDao productSkuPicturesDao;

    private ProductSkuStockInfoMapper productSkuStockInfoMapper;

    private ProductSkuDao productSkuDao;

    public AllocationCallbackToOutboundConverter(WarehouseService warehouseService, ProductSkuPicturesDao productSkuPicturesDao, ProductSkuStockInfoMapper productSkuStockInfoMapper, ProductSkuDao productSkuDao) {
        this.warehouseService = warehouseService;
        this.productSkuPicturesDao = productSkuPicturesDao;
        this.productSkuStockInfoMapper = productSkuStockInfoMapper;
        this.productSkuDao = productSkuDao;
    }

    @Override
    public OutboundReqVo convert(AllocationCallbackReqVo reqVo) {
        WarehouseResVo warehouseByCode = warehouseService.getWarehouseByCode(reqVo.getCallOutWarehouseCode());
        OutboundReqVo outboundReqVo = new OutboundReqVo();
        outboundReqVo.setCompanyCode("09");
        outboundReqVo.setCompanyName("宁波熙耘");
        outboundReqVo.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        outboundReqVo.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
        outboundReqVo.setOutboundTypeCode((byte)4);
        outboundReqVo.setOutboundTypeName("移库");
        outboundReqVo.setSourceOderCode(reqVo.getSourceOderCode());
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
        outboundReqVo.setCreateBy(reqVo.getCreateBy());
        outboundReqVo.setCreateTime(reqVo.getCreateTime());
        outboundReqVo.setUpdateBy(reqVo.getCreateBy());
        outboundReqVo.setUpdateTime(reqVo.getCreateTime());
        List<OutboundProductReqVo> list = Lists.newArrayList();
        List<OutboundBatch> outboundBatchReqVos = Lists.newArrayList();
        Long preInboundNum = 0L;
        Long preInboundMainNum = 0L;
        Long preTaxAmount = 0L;
        Long preNoTaxAmount = 0L;
        ProductSkuPictures productSkuPicture = null;
        List<AllocationProductCallbackReqVo> allocationProductCallbackReqVos = reqVo.getList();
        for (AllocationProductCallbackReqVo allocationProductCallbackReqVo : allocationProductCallbackReqVos) {
            OutboundProductReqVo reqVo1 = new OutboundProductReqVo();
            productSkuPicture = productSkuPicturesDao.getPicInfoBySkuCode(allocationProductCallbackReqVo.getSkuCode());
            if(null != productSkuPicture){
                reqVo1.setPictureUrl(productSkuPicture.getProductPicturePath());
            } else {
                reqVo1.setPictureUrl( null);
            }

            reqVo1.setSkuCode(allocationProductCallbackReqVo.getSkuCode());
            //规格信息
            ProductSkuStockInfo productSkuStockInfo = productSkuStockInfoMapper.getBySkuCode(allocationProductCallbackReqVo.getSkuCode());
            //基本信息
            ProductSkuRespVo productSkuRespVo = productSkuDao.getSkuInfoResp(allocationProductCallbackReqVo.getSkuCode());
            reqVo1.setSkuName(productSkuStockInfo.getProductSkuName());
            reqVo1.setNorms(productSkuStockInfo.getSpec());
            reqVo1.setColorName(productSkuRespVo.getColorName());
            reqVo1.setColorCode(null);
            reqVo1.setModel(productSkuRespVo.getModelNumber());
            reqVo1.setUnitCode(null);
            reqVo1.setUnitName(productSkuStockInfo.getUnitName());
            reqVo1.setOutboundNorms(productSkuStockInfo.getSpec());
            //由于调拨/移库/报废 是库存信息,故基商品含量默认为1
            reqVo1.setOutboundBaseContent("1");
            reqVo1.setOutboundBaseUnit("1");
            reqVo1.setPreOutboundNum(allocationProductCallbackReqVo.getQuantity().longValue());
            reqVo1.setPreOutboundMainNum(allocationProductCallbackReqVo.getQuantity().longValue());
            reqVo1.setPraOutboundNum(allocationProductCallbackReqVo.getQuantity().longValue());
            reqVo1.setPraOutboundMainNum(allocationProductCallbackReqVo.getQuantity().longValue());
//            reqVo1.setPreTaxPurchaseAmount(record.getTaxPrice().longValue());
//            reqVo1.setPreTaxAmount(record.getTaxAmount().longValue());
            reqVo1.setCreateBy(reqVo.getCreateBy());
            reqVo1.setCreateTime(reqVo.getCreateTime());
            reqVo1.setUpdateBy(reqVo.getCreateBy());
            reqVo1.setUpdateTime(reqVo.getCreateTime());
//            reqVo1.setLinenum(record.getLineNum().longValue());
            preInboundNum += allocationProductCallbackReqVo.getQuantity().intValue();
            preInboundMainNum += allocationProductCallbackReqVo.getQuantity().intValue();
//            preTaxAmount += record.getTaxAmount();
//            preNoTaxAmount += Calculate.computeNoTaxPrice(record.getTaxAmount().longValue(), record.getTax().longValue());
            list.add(reqVo1);
        }
//        List<AllocationProductBatch> list1 = order.getList();
//        for (AllocationProductBatch batch1 : list1) {
//            //批次
//            OutboundBatch batch = new OutboundBatch();
//            batch.setSkuCode(batch1.getSkuCode());
//            batch.setSkuName(batch1.getSkuName());
//            batch.setOutboundBatchCode(batch1.getCallInBatchNumber());
//            batch.setManufactureTime(Objects.nonNull(batch1.getProductDate())?DateUtils.toDate(batch1.getProductDate()):null);
//            batch.setBatchRemark(batch.getBatchRemark());
//            batch.setPreQty(batch1.getQuantity().longValue());
//            batch.setCreateBy(batch1.getCreateBy());
//            batch.setCreateTime(batch1.getCreateTime());
//            batch.setUpdateBy(batch1.getUpdateBy());
//            batch.setUpdateTime(batch1.getUpdateTime());
//            batch.setLineNum(batch1.getLineNum().longValue());
//            outboundBatchReqVos.add(batch);
//        }
        outboundReqVo.setPreOutboundNum(preInboundNum);
        outboundReqVo.setPreMainUnitNum(preInboundMainNum);
        outboundReqVo.setPraOutboundNum(preInboundNum);
        outboundReqVo.setPraMainUnitNum(preInboundMainNum);
        outboundReqVo.setPreTaxAmount(preTaxAmount);
        outboundReqVo.setPreAmount(preNoTaxAmount);
        outboundReqVo.setPreTax(preTaxAmount-preNoTaxAmount);
        outboundReqVo.setOutboundBatches(outboundBatchReqVos);
        outboundReqVo.setList(list);
        return outboundReqVo;
    }
}
