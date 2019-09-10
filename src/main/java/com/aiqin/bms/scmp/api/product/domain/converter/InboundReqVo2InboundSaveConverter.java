package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundItemReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 采购生成入库单
 * @author HuangLong
 * @date 2019/1/17
 */
@Component
@Slf4j
public class InboundReqVo2InboundSaveConverter implements Converter<InboundReqVo, InboundReqSave> {
    private SkuService skuService;

    public InboundReqVo2InboundSaveConverter(SkuService skuService) {
        this.skuService=skuService;
    }

    @Override
    public InboundReqSave convert(InboundReqVo reqVo) {
        try {
            if(CollectionUtils.isEmpty(reqVo.getPurchaseItemVos())){
                throw new BizException("入库单保存请求vo的商品项集合不能为空");
            }
            List<String> skuCodes = reqVo.getPurchaseItemVos().stream().map(InboundItemReqVo::getSkuCode).collect(Collectors.toList());
//            Map<String, Long> map = skuService.getSkuConvertNumBySkuCodes(skuCodes);
            Map<String, PurchaseItemRespVo> map = skuService.getSalesSkuList(skuCodes).stream().collect(Collectors.toMap(PurchaseItemRespVo::getSkuCode, Function.identity(),(k1,k2)->k2));
            SupplyComDetailRespVO supplyComDetailRespVO = skuService.detailByCode(reqVo.getSupplyCode());
            if(Objects.isNull(supplyComDetailRespVO)){
                throw new GroundRuntimeException("获取供货单位信息失败");
            }
//            InboundSavePo po = new InboundSavePo();
            InboundReqSave inbound = new InboundReqSave();
            BeanUtils.copyProperties(reqVo,inbound);
            //入库类型
            inbound.setInboundTypeCode(InboundTypeEnum.RETURN_SUPPLY.getCode());
            inbound.setInboundTypeName(InboundTypeEnum.RETURN_SUPPLY.getName());
            //入库状态
            inbound.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
            inbound.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
            //公司编码
            inbound.setCompanyCode(reqVo.getCompanyCode());
            inbound.setCompanyName(reqVo.getCompanyName());

//            inbound.setInboundTime(new Date());
            inbound.setProvinceCode(supplyComDetailRespVO.getProvinceId());
            inbound.setProvinceName(supplyComDetailRespVO.getProvinceName());
            inbound.setCityCode(supplyComDetailRespVO.getCityId());
            inbound.setCityName(supplyComDetailRespVO.getCityName());
            inbound.setCountyCode(supplyComDetailRespVO.getDistrictId());
            inbound.setCountyName(supplyComDetailRespVO.getDistrictName());
            inbound.setShipper(supplyComDetailRespVO.getContactName());
            inbound.setShipperNumber(supplyComDetailRespVO.getMobilePhone());
            inbound.setShipperRate(supplyComDetailRespVO.getZipCode());
            inbound.setDetailedAddress(supplyComDetailRespVO.getAddress());
            inbound.setSourceOderCode(reqVo.getCode());
            //物流中心编码
            inbound.setLogisticsCenterCode(reqVo.getTransportCenterCode());
            inbound.setLogisticsCenterName(reqVo.getTransportCenterName());
            //供应商名称
            inbound.setSupplierName(reqVo.getSupplyName());
            inbound.setSupplierCode(reqVo.getSupplyCode());
            //预计到货时间
            inbound.setPreArrivalTime(reqVo.getExpectedTime());
            //预计入库数量
            inbound.setPreInboundNum(reqVo.getTotalNum());
            //预计入库主数量
            inbound.setPreMainUnitNum(reqVo.getSaleUnitTotalNum());
            //预计含税总金额
            inbound.setPreTaxAmount(reqVo.getTotalAmount());
            //预计无税总金额
            inbound.setPreAmount(reqVo.getNoTaxTotalAmount());
            //预计税额
            inbound.setPreTax(reqVo.getTotalAmount()-reqVo.getNoTaxTotalAmount());
            //下面是实际的，现在不用
//            inbound.setPraTax(inbound.getPreTax());
//            inbound.setPraAmount(inbound.getPreAmount());
//            inbound.setPraTaxAmount(inbound.getPreTaxAmount());
//            inbound.setPraInboundNum(inbound.getPreInboundNum());
//            inbound.setPraMainUnitNum(inbound.getPreMainUnitNum());
            inbound.setCreateTime(new Date());
            inbound.setCreateBy(reqVo.getCreateBy());
//            if(StringUtils.isNotBlank(inbound.getUpdateBy())){
//                inbound.setUpdateTime(new Date());
//            }
            List<InboundProductReqVo> products= Lists.newArrayList();
            for (InboundItemReqVo vo : reqVo.getPurchaseItemVos()){
                InboundProductReqVo product = BeanCopyUtils.copy(vo, InboundProductReqVo.class);
//                product.setInboundOderCode(inbound.getInboundOderCode());
                product.setPreInboundNum(vo.getNum());
                try {
//                    product.setPreInboundMainNum(vo.getNum()*map.get(vo.getSkuCode()));
//                    product.setInboundBaseContent(map.get(vo.getSkuCode()).toString());
                    product.setPreInboundMainNum(vo.getNum()*vo.getConvertNum());
                    product.setInboundBaseContent(vo.getConvertNum().toString());
                } catch (Exception e) {
                    log.error(Global.ERROR, e);
                    throw new BizException("sku编码:"+vo.getSkuCode()+",对应的转换单位系数不存在");
                }
                product.setCreateTime(new Date());
//                product.setPraInboundMainNum(product.getPreInboundMainNum());
//                product.setPraInboundNum(vo.getNum());
                product.setPreTaxAmount(vo.getTotalPrice());
                product.setPreTaxPurchaseAmount(vo.getPrice());
//                product.setPraTaxPurchaseAmount(vo.getPrice());
//                product.setPraTaxAmount(vo.getTotalPrice());
                //规格
                product.setNorms(map.get(vo.getSkuCode()).getSpec());
                product.setPictureUrl(map.get(vo.getSkuCode()).getPicUrl());
                product.setInboundNorms(vo.getSpec());
                //型号
                product.setModel(vo.getModelNumber());
                products.add(product);
            }
//            po.setInbound(inbound);
            inbound.setList(products);
            return inbound;
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if(e instanceof BizException){
                throw new BizException(e.getMessage());
            }else {
                throw new BizException("入库单保存请求reqVo转换Po异常");
            }
        }
    }
}
