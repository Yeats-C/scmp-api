package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundItemReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-03-25
 * @time: 19:21
 */
public class PurchaseToStockConverter implements Converter<InboundReqVo, List<StockVoRequest>> {
    @Override
    public List<StockVoRequest> convert(InboundReqVo source) {
        List<StockVoRequest> list = Lists.newArrayList();
        String companyCode = source.getCompanyCode();
        String companyName = source.getCompanyName();
        String transportCenterCode = source.getTransportCenterCode();
        String transportCenterName = source.getTransportCenterName();
        String warehouseCode = source.getWarehouseCode();
        String warehouseName = source.getWarehouseName();
        String purchaseGroupCode = source.getPurchaseGroupCode();
        String purchaseGroupName = source.getPurchaseGroupName();
        String supplierCode = source.getSupplyCode();
        String supplierName = source.getSupplyName();
        List<InboundItemReqVo> purchaseItemVos = source.getPurchaseItemVos();
        for (InboundItemReqVo purchaseItemVo : purchaseItemVos) {
            StockVoRequest stockVoRequest = new StockVoRequest();
            stockVoRequest.setCompanyCode(companyCode);
            stockVoRequest.setCompanyName(companyName);
            stockVoRequest.setTransportCenterCode(transportCenterCode);
            stockVoRequest.setTransportCenterName(transportCenterName);
            stockVoRequest.setWarehouseCode(warehouseCode);
            stockVoRequest.setWarehouseName(warehouseName);
            stockVoRequest.setPurchaseGroupCode(purchaseGroupCode);
            stockVoRequest.setPurchaseGroupName(purchaseGroupName);
            stockVoRequest.setNewDelivery(supplierCode);
            stockVoRequest.setNewDeliveryName(supplierName);
            //库存是主单位数量
            stockVoRequest.setChangeNum(purchaseItemVo.getNum()*purchaseItemVo.getConvertNum());
            stockVoRequest.setNewPurchasePrice(purchaseItemVo.getPrice());
            stockVoRequest.setSkuCode(purchaseItemVo.getSkuCode());
            stockVoRequest.setSkuName(purchaseItemVo.getSkuName());
            stockVoRequest.setTaxRate(purchaseItemVo.getTaxRate());
            list.add(stockVoRequest);
        }
        return list;
    }
}
