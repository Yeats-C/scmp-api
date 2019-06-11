package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.bms.scmp.api.product.domain.request.ILockStockItemReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.ILockStockReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-03-26
 * @time: 10:23
 */
public class ReturnSupplyToStockConverter implements Converter<ILockStockReqVO,List<StockVoRequest>> {
    @Override
    public List<StockVoRequest> convert(ILockStockReqVO source) {
        List<StockVoRequest> list = Lists.newArrayList();
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
        List<? extends ILockStockItemReqVo> itemReqVos = source.getItemReqVos();
        for (ILockStockItemReqVo itemReqVo : itemReqVos) {
            StockVoRequest stockVoRequest = new StockVoRequest();
            stockVoRequest.setCompanyCode(companyCode);
//            stockVoRequest.setCompanyName(companyName);
            stockVoRequest.setTransportCenterCode(transportCenterCode);
//            stockVoRequest.setTransportCenterName(transportCenterName);
            stockVoRequest.setWarehouseCode(warehouseCode);
//            stockVoRequest.setWarehouseName(warehouseName);
            stockVoRequest.setPurchaseGroupCode(purchaseGroupCode);
//            stockVoRequest.setPurchaseGroupName(purchaseGroupName);
//            stockVoRequest.setNewDelivery(supplierCode);
//            stockVoRequest.setNewDeliveryName(supplierName);
            //库存是主单位数量 退供基商品含量默认是1
            stockVoRequest.setChangeNum(itemReqVo.getNum());
//            stockVoRequest.setNewPurchasePrice(itemReqVo.getPrice());
            stockVoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockVoRequest.setSkuName(itemReqVo.getSkuName());
//            stockVoRequest.setTaxRate(itemReqVo.getTaxRate());
            list.add(stockVoRequest);
        }
        return list;
    }
}
