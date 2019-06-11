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
//            Outbound outbound = new Outbound();
//            //公司
//            outbound.setCompanyCode(stockReqVO.getCompanyCode());
//            outbound.setCompanyName(stockReqVO.getCompanyName());
//            //物流中心
//            outbound.setLogisticsCenterCode(stockReqVO.getTransportCenterCode());
//            outbound.setLogisticsCenterName(stockReqVO.getTransportCenterName());
//            //库房
//            outbound.setWarehouseCode(stockReqVO.getWarehouseCode());
//            outbound.setWarehouseName(stockReqVO.getWarehouseName());
//            //供应单位
//            outbound.setSupplierCode(stockReqVO.getSupplyCode());
//            outbound.setSupplierName(stockReqVO.getSupplyName());
//            //状态
//            outbound.setOutboundStatusCode(stockReqVO.getStockStatusCode().getCode());
//            outbound.setOutboundStatusName(stockReqVO.getStockStatusCode().getName());
//            //类型
//            outbound.setOutboundTypeCode(stockReqVO.getOutboundTypeCode().getCode());
//            outbound.setOutboundTypeName(stockReqVO.getOutboundTypeCode().getName());
//            //原始单号
//            outbound.setSourceOderCode(stockReqVO.getSourceOderCode());
//            //出库时间
//            outbound.setOutboundTime(stockReqVO.getOutboundTime());
//            //预计出库数量
//            outbound.setPreOutboundNum(stockReqVO.getPreOutboundNum());
//            //预计主出库数量
//            outbound.setPreMainUnitNum(stockReqVO.getPreMainUnitNum());
//            //预计含税总金额
//            outbound.setPreTaxAmount(stockReqVO.getPreTaxAmount());
//            //预计无税总金额
//            outbound.setPreAmount(stockReqVO.getPreAmount());
//            //预计税额
//            outbound.setPreTax((null == stockReqVO.getPreTaxAmount() ? 0L : stockReqVO.getPreTaxAmount())  - (null ==  stockReqVO.getPreAmount() ? 0L : stockReqVO.getPreAmount()));
//            outbound.setCreateBy(stockReqVO.getOperator());
//            outbound.setUpdateBy(stockReqVO.getOperator());
//            outbound.setCreateTime(new Date());
//            outbound.setUpdateTime(new Date());
//
//
//            return outbound;
//        }
//
//        return null;
//    }
//}
