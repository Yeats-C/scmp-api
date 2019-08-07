package com.aiqin.bms.scmp.api.purchase.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Component
public class ReturnInfoToInboundConverter implements Converter<ReturnOrderInfo, List<InboundReqSave>> {

    private SkuService skuService;


    public ReturnInfoToInboundConverter(SkuService skuService) {
        this.skuService = skuService;
    }

    @Override
    public List<InboundReqSave> convert(ReturnOrderInfo reqVo) {
        List<String> skuCodes = reqVo.getDetailList().stream().map(ReturnOrderInfoItem::getSkuCode).collect(Collectors.toList());
//            Map<String, Long> map = skuService.getSkuConvertNumBySkuCodes(skuCodes);
        Map<String, PurchaseItemRespVo> map = skuService.getSalesSkuList(skuCodes).stream().collect(Collectors.toMap(PurchaseItemRespVo::getSkuCode, Function.identity(), (k1, k2) -> k2));
        List<InboundReqSave> list = Lists.newArrayList();
        InboundReqSave inbound = new InboundReqSave();
        BeanUtils.copyProperties(reqVo, inbound);
        //供应商信息
        if(StringUtils.isNotBlank(reqVo.getSupplierCode())){
            SupplyComDetailRespVO supplyComDetailRespVO = skuService.detailByCode(reqVo.getSupplierCode());
            if (Objects.isNull(supplyComDetailRespVO)) {
                throw new GroundRuntimeException("未查询到供货单位信息");
            }
            inbound.setShipperRate(supplyComDetailRespVO.getZipCode());
        }
        //入库类型
        inbound.setInboundTypeCode(InboundTypeEnum.ORDER.getCode());
        inbound.setInboundTypeName(InboundTypeEnum.ORDER.getName());
        //入库状态
        inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        //公司编码
        inbound.setCompanyCode(reqVo.getCompanyCode());
        inbound.setCompanyName(reqVo.getCompanyName());
        inbound.setInboundTime(reqVo.getDeliveryTime());
        inbound.setProvinceCode(reqVo.getProvinceCode());
        inbound.setProvinceName(reqVo.getProvinceName());
        inbound.setCityCode(reqVo.getCityCode());
        inbound.setCityName(reqVo.getCityName());
        inbound.setShipper(reqVo.getConsignee());
        inbound.setShipperNumber(reqVo.getConsigneePhone());
        inbound.setDetailedAddress(reqVo.getAddress());
        inbound.setSourceOderCode(reqVo.getOrderCode());
        //来源单据号
        inbound.setSourceOderCode(reqVo.getOrderCode());

        //物流中心编码
        inbound.setLogisticsCenterCode(reqVo.getTransportCenterCode());
        inbound.setLogisticsCenterName(reqVo.getTransportCenterName());
        //供应商名称
        inbound.setSupplierName(reqVo.getSupplierName());
        inbound.setSupplierCode(reqVo.getSupplierCode());
        //预计到货时间
        inbound.setPreArrivalTime(reqVo.getReceivingTime());
//        //预计入库数量
        inbound.setPreInboundNum(reqVo.getPreProductNum());
//        //预计入库主数量
        inbound.setPreMainUnitNum(reqVo.getPreProductNum());
//        //预计含税总金额 dl没有传单价 无法计算预计总价
//        inbound.setPreTaxAmount(reqVo.getReturnOrderAmount()*reqVo.getPreProductNum());
        //实际含税总金额
        inbound.setPraTaxAmount(reqVo.getProductTotalAmount());
        //实际入库数量
        inbound.setPraInboundNum(reqVo.getProductNum());
        //实际入库主数量
        inbound.setPraMainUnitNum(reqVo.getProductNum());
        inbound.setCreateTime(reqVo.getCreateDate());
        //todo 没有创建人呢
        //        inbound.setCreateBy(reqVo.get());
        List<InboundProductReqVo> products = Lists.newArrayList();
        InboundProductReqVo product;
//        reqVo.getDetailList().stream().collect(Collectors.groupingBy(ReturnOrderInfoItem::get))
        for (ReturnOrderInfoItem vo : reqVo.getDetailList()) {
            product = new InboundProductReqVo();
            BeanUtils.copyProperties(vo,product);
            //规格.
            product.setNorms(map.get(vo.getSkuCode()).getSpec());
            product.setPictureUrl(map.get(vo.getSkuCode()).getPicUrl());
            product.setInboundNorms(vo.getSpec());
            products.add(product);
        }
        inbound.setList(products);
        list.add(inbound);
        return list;
    }
}
