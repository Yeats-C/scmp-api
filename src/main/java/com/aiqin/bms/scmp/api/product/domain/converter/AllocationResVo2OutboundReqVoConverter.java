package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.product.service.api.SupplierApiService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-03-13
 * @time: 17:16
 */
@Component
public class AllocationResVo2OutboundReqVoConverter implements Converter<AllocationToOutboundVo, OutboundReqVo> {

    @Autowired
    private SupplierApiService supplierApiService;

    public AllocationResVo2OutboundReqVoConverter(SupplierApiService supplierApiService) {
        this.supplierApiService = supplierApiService;
    }
    @Override
    public OutboundReqVo convert(AllocationToOutboundVo source) {
            OutboundReqVo outbound = new OutboundReqVo();
        try {
            WarehouseResVo warehouseByCode = supplierApiService.getWarehouseByCode(source.getCallinWarehouseCode());
            if(Objects.isNull(warehouseByCode)){
                throw new GroundRuntimeException("调拨查询联系人信息失败");
            }
            //出库单状态
            outbound.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
            outbound.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
            //出库单类型
            outbound.setOutboundTypeCode(OutboundTypeEnum.ALLOCATE.getCode());
            outbound.setOutboundTypeName(OutboundTypeEnum.ALLOCATE.getName());
            //调拨金额
            outbound.setPreTaxAmount(source.getTaxRefundAmount());
            //数量
            outbound.setPreOutboundNum(source.getQuantity());
            //主数量
            outbound.setPreMainUnitNum(source.getQuantity());
            //公司信息
            outbound.setCompanyCode(source.getCompanyCode());
            outbound.setCompanyName(source.getCompanyName());
            //物流中心，库房
            outbound.setLogisticsCenterCode(source.getCalloutLogisticsCenterCode());
            outbound.setLogisticsCenterName(source.getCalloutLogisticsCenterName());
            outbound.setWarehouseCode(source.getCalloutWarehouseCode());
            outbound.setWarehouseName(source.getCalloutWarehouseName());
            //编码
            outbound.setSourceOderCode(source.getAllocationCode());
            //收货人信息
            outbound.setConsignee(warehouseByCode.getContact());
            outbound.setConsigneeNumber(warehouseByCode.getPhone());
            //省市区
            outbound.setProvinceCode(warehouseByCode.getProvinceCode());
            outbound.setProvinceName(warehouseByCode.getProvinceName());
            outbound.setCityCode(warehouseByCode.getCityCode());
            outbound.setCityName(warehouseByCode.getCityName());
            outbound.setCountyCode(warehouseByCode.getCountyCode());
            outbound.setCountyName(warehouseByCode.getCountyName());
            outbound.setDetailedAddress(warehouseByCode.getDetailedAddress());
            //  出库时间

            //   预计到货时间
            Date date = new Date();
            Calendar   calendar = new GregorianCalendar();
            calendar.setTime(date);
                calendar.add(Calendar.DATE,5);


            date=calendar.getTime();
             outbound.setPreArrivalTime(date);
            //创建人 时间
            outbound.setCreateBy(source.getCreateBy());
            outbound.setCreateTime(source.getCreateTime());
            outbound.setUpdateBy(source.getUpdateBy());
            outbound.setUpdateTime(source.getUpdateTime());
            List<OutboundProductReqVo> products = Lists.newArrayList();
            long totalNoRateAmount = 0L;
            for (AllocationProductToOutboundVo vo : source.getSkuList()){
                OutboundProductReqVo product = BeanCopyUtils.copy(vo, OutboundProductReqVo.class);
                // 设置图片地址
                product.setPictureUrl(vo.getPictureUrl());
                //基商品含量
                product.setOutboundBaseContent("1");
                //规格 出库规格
                product.setNorms(vo.getSpecification());
                product.setOutboundNorms(vo.getSpecification());
                // 设置单位
                product.setUnitName(vo.getUnit());
                //设置颜色
                product.setColorName(vo.getColor());
                //数量
                product.setPreOutboundNum(vo.getQuantity());
                product.setPreOutboundMainNum(vo.getQuantity());
                // 设置价格
                product.setPreTaxPurchaseAmount(vo.getTaxPrice());
                product.setPreTaxAmount(vo.getTaxAmount());
                // 设置行号
                product.setLinenum(vo.getLinenum());
                totalNoRateAmount += (Calculate.computeNoTaxPrice(vo.getTaxPrice(), vo.getTax())*vo.getQuantity());
                products.add(product);
            }
            outbound.setList(products);
            outbound.setPreAmount(totalNoRateAmount);
            outbound.setPreTax(source.getTaxRefundAmount()-totalNoRateAmount);
        }catch (Exception e){
            throw new GroundRuntimeException("调拨vo转为出库失败！");
        }
        return outbound;
    }
}
