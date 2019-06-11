package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementProductResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.product.service.api.SupplierApiService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.*;

/**
 * @Classname: MovementResVo2InboundReqVoConverter
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/2
 * @Version 1.0
 * @Since 1.0
 */
public class MovementResVo2InboundReqVoConverter  implements Converter<MovementResVo, InboundReqSave> {

    @Autowired
    private SupplierApiService supplierApiService;

    public MovementResVo2InboundReqVoConverter (SupplierApiService supplierApiService) {
        this.supplierApiService = supplierApiService;
    }

    @Override
    public InboundReqSave convert(MovementResVo source) {
        InboundReqSave outbound = new InboundReqSave();

        try {
            WarehouseResVo warehouseByCode = supplierApiService.getWarehouseByCode(source.getCalloutWarehouseCode());
            if(Objects.isNull(warehouseByCode)){
                throw new GroundRuntimeException("调拨查询联系人信息失败");
            }
            //出库单状态
            outbound.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
            outbound.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
            //出库单类型
            outbound.setInboundTypeCode(InboundTypeEnum.MOVEMENT.getCode());
            outbound.setInboundTypeName(InboundTypeEnum.MOVEMENT.getName());
            //调拨金额
            outbound.setPreTaxAmount(source.getTaxInventoryCost());
            //数量
            outbound.setPreInboundNum(source.getQuantity());
            //主数量
            outbound.setPreMainUnitNum(source.getQuantity());
            //公司信息
            outbound.setCompanyCode(source.getCompanyCode());
            outbound.setCompanyName(source.getCompanyName());
            //物流中心，库房
            outbound.setLogisticsCenterCode(source.getLogisticsCenterCode());
            outbound.setLogisticsCenterName(source.getLogisticsCenterName());
            outbound.setWarehouseCode(source.getCallinWarehouseCode());
            outbound.setWarehouseName(source.getCallinWarehouseName());
            //编码
            outbound.setSourceOderCode(source.getMovementCode());
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
            calendar.add(Calendar.DATE,1);


            date=calendar.getTime();
            outbound.setPreArrivalTime(date);
            //创建人 时间
            outbound.setCreateBy(source.getCreateBy());
            outbound.setCreateTime(source.getCreateTime());
            outbound.setUpdateBy(source.getUpdateBy());
            outbound.setUpdateTime(source.getUpdateTime());
            List<InboundProductReqVo> products = Lists.newArrayList();
            long totalNoRateAmount = 0L;
            for (MovementProductResVo vo : source.getList()){
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
            outbound.setPreTax(source.getTaxInventoryCost()-totalNoRateAmount);
        }catch (Exception e){
            throw new GroundRuntimeException("调拨vo转为出库失败！");
        }
        return outbound;
    }
}
