package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Description:
 *
 * @date: 2019-03-26
 * @time: 10:23
 */
public class ReturnSupplyToStockBatchConverter implements Converter<ILockStockBatchReqVO,List<StockBatchVoRequest>> {
    @Override
    public List<StockBatchVoRequest> convert(ILockStockBatchReqVO source) {
        List<StockBatchVoRequest> list = Lists.newArrayList();
        String companyCode = source.getCompanyCode();
//        String companyName = source.getCompanyName();
        String transportCenterCode = source.getTransportCenterCode();
//        String transportCenterName = source.getTransportCenterName();
        String warehouseCode = source.getWarehouseCode();
//        String warehouseName = source.getWarehouseName();
        String purchaseGroupCode = source.getPurchaseGroupCode();
//        String purchaseGroupName = source.getPurchaseGroupName();
//        String supplierCode = source.getSupplierCode();
//        String supplierName = source.getSupplierName();
        List<? extends ILockStockBatchItemReqVo> itemReqVos = source.getItemReqVos();
        for (ILockStockBatchItemReqVo itemReqVo : itemReqVos) {
            StockBatchVoRequest stockBatchVoRequest = new StockBatchVoRequest();
            stockBatchVoRequest.setCompanyCode(companyCode);
//            stockBatchVoRequest.setCompanyName(companyName);
            stockBatchVoRequest.setTransportCenterCode(transportCenterCode);
//            stockBatchVoRequest.setTransportCenterName(transportCenterName);
            stockBatchVoRequest.setWarehouseCode(warehouseCode);
//            stockBatchVoRequest.setWarehouseName(warehouseName);
//            stockBatchVoRequest.setPurchaseGroupCode(purchaseGroupCode);
//            stockBatchVoRequest.setPurchaseGroupName(purchaseGroupName);
//            stockBatchVoRequest.setNewDelivery(supplierCode);
//            stockBatchVoRequest.setNewDeliveryName(supplierName);
            //库存是主单位数量 退供基商品含量默认是2
            stockBatchVoRequest.setChangeNum(itemReqVo.getNum());
//            stockBatchVoRequest.setNewPurchasePrice(itemReqVo.getPrice());
            stockBatchVoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockBatchVoRequest.setSkuName(itemReqVo.getSkuName());
            stockBatchVoRequest.setSkuCode(itemReqVo.getBatchCode());
//            stockBatchVoRequest.setTaxRate(itemReqVo.getTaxRate());
            list.add(stockBatchVoRequest);
        }
        return list;
    }
}
