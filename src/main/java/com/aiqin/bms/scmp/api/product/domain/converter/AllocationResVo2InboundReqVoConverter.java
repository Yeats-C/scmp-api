package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.ground.util.exception.GroundRuntimeException;
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
public class AllocationResVo2InboundReqVoConverter implements Converter<AllocationToOutboundVo, InboundReqSave> {

    @Autowired
    private WarehouseService warehouseService;

   public AllocationResVo2InboundReqVoConverter (WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Override
    public InboundReqSave convert(AllocationToOutboundVo source) {
            InboundReqSave outbound = new InboundReqSave();

        try {
            WarehouseResVo warehouseByCode = warehouseService.getWarehouseByCode(source.getCalloutWarehouseCode());
            if(Objects.isNull(warehouseByCode)){
                throw new GroundRuntimeException("调拨查询联系人信息失败");
            }
            //出库单状态
            outbound.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
            outbound.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
            //出库单类型
            outbound.setInboundTypeCode(InboundTypeEnum.ALLOCATE.getCode());
            outbound.setInboundTypeName(InboundTypeEnum.ALLOCATE.getName());
            //调拨金额
            outbound.setPreTaxAmount(source.getTaxRefundAmount());
            //数量
            outbound.setPreInboundNum(source.getQuantity());
            //主数量
            outbound.setPreMainUnitNum(source.getQuantity());
            //公司信息
            outbound.setCompanyCode(source.getCompanyCode());
            outbound.setCompanyName(source.getCompanyName());
            //物流中心，库房
            outbound.setLogisticsCenterCode(source.getCallinLogisticsCenterCode());
            outbound.setLogisticsCenterName(source.getCallinLogisticsCenterName());
            outbound.setWarehouseCode(source.getCallinWarehouseCode());
            outbound.setWarehouseName(source.getCallinWarehouseName());
            //编码
            outbound.setSourceOderCode(source.getAllocationCode());
            //收货人信息
            outbound.setShipper(warehouseByCode.getContact());
            outbound.setShipperNumber(warehouseByCode.getPhone());
            //省市区
            outbound.setProvinceCode(warehouseByCode.getProvinceCode());
            outbound.setProvinceName(warehouseByCode.getProvinceName());
            outbound.setCityCode(warehouseByCode.getCityCode());
            outbound.setCityName(warehouseByCode.getCityName());
            outbound.setCountyCode(warehouseByCode.getCountyCode());
            outbound.setCountyName(warehouseByCode.getCountyName());
            outbound.setDetailedAddress(warehouseByCode.getDetailedAddress());
            //
            Date date = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
                calendar.add(Calendar.DATE,5);


            date=calendar.getTime();
            outbound.setPreArrivalTime(date);
            //创建人 时间
            outbound.setCreateBy(source.getCreateBy());
            outbound.setCreateTime(source.getCreateTime());
            outbound.setUpdateBy(source.getUpdateBy());
            outbound.setUpdateTime(source.getUpdateTime());
            List<InboundProductReqVo> products = Lists.newArrayList();
            long totalNoRateAmount = 0L;
            for (AllocationProductToOutboundVo vo : source.getSkuList()){
                InboundProductReqVo product = BeanCopyUtils.copy(vo, InboundProductReqVo.class);
                // 设置图片地址
                product.setPictureUrl(vo.getPictureUrl());
                //基商品含量
                product.setInboundBaseContent("1");
                //规格 出库规格
                product.setNorms(vo.getSpecification());
                product.setInboundNorms(vo.getSpecification());
                // 设置单位
                product.setUnitName(vo.getUnit());
                //设置颜色
                product.setColorName(vo.getColor());
                //数量
                product.setPreInboundNum(vo.getQuantity());
                product.setPreInboundMainNum(vo.getQuantity());
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
