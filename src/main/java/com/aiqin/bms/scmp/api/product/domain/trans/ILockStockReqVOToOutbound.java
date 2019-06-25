package com.aiqin.bms.scmp.api.product.domain.trans;//package com.aiqin.mgs.product.api.domain.trans;
//
//import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
//import com.aiqin.bms.scmp.api.product.domain.request.ILockStockReqVO;
//import com.google.common.base.Function;
//
//import javax.annotation.Nullable;
//import java.math.BigDecimal;
//import java.util.Date;
//
///**
// * @author knight.xie
// * @version 1.0
// * @className ILockStockReqVOToOutbound
// * @date 2019/1/9 16:13
// * @description TODO
// */
//public class ILockStockReqVOToOutbound implements Function<ILockStockReqVO, Outbound> {
//    @Nullable
//    @Override
//    public Outbound apply(@Nullable ILockStockReqVO stockReqVO) {
//        if(null != stockReqVO){
//            Outbound order = new Outbound();
//            //公司
//            order.setCompanyCode(stockReqVO.getCompanyCode());
//            order.setCompanyName(stockReqVO.getCompanyName());
//            //物流中心
//            order.setLogisticsCenterCode(stockReqVO.getTransportCenterCode());
//            order.setLogisticsCenterName(stockReqVO.getTransportCenterName());
//            //库房
//            order.setWarehouseCode(stockReqVO.getWarehouseCode());
//            order.setWarehouseName(stockReqVO.getWarehouseName());
//            //供应单位
//            order.setSupplierCode(stockReqVO.getSupplyCode());
//            order.setSupplierName(stockReqVO.getSupplyName());
//            //状态
//            order.setOutboundStatusCode(stockReqVO.getStockStatusCode().getCode());
//            order.setOutboundStatusName(stockReqVO.getStockStatusCode().getName());
//            //类型
//            order.setOutboundTypeCode(stockReqVO.getOutboundTypeCode().getCode());
//            order.setOutboundTypeName(stockReqVO.getOutboundTypeCode().getName());
//            //原始单号
//            order.setSourceOderCode(stockReqVO.getSourceOderCode());
//            //出库时间
//            order.setOutboundTime(stockReqVO.getOutboundTime());
//            //预计出库数量
//            order.setPreOutboundNum(stockReqVO.getPreOutboundNum());
//            //预计主出库数量
//            order.setPreMainUnitNum(stockReqVO.getPreMainUnitNum());
//            //预计含税总金额
//            order.setPreTaxAmount(stockReqVO.getPreTaxAmount());
//            //预计无税总金额
//            order.setPreAmount(stockReqVO.getPreAmount());
//            //预计税额
//            order.setPreTax((null == stockReqVO.getPreTaxAmount() ? 0L : stockReqVO.getPreTaxAmount())  - (null ==  stockReqVO.getPreAmount() ? 0L : stockReqVO.getPreAmount()));
//            order.setCreateBy(stockReqVO.getOperator());
//            order.setUpdateBy(stockReqVO.getOperator());
//            order.setCreateTime(new Date());
//            order.setUpdateTime(new Date());
//
//
//            return order;
//        }
//
//        return null;
//    }
//}
