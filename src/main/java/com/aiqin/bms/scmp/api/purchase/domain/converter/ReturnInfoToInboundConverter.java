package com.aiqin.bms.scmp.api.purchase.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;
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
        InboundReqSave inbound;
        InboundProductReqVo product;
        List<InboundProductReqVo> products;
        List<InboundBatchReqVo> batchList;
        InboundBatchReqVo inboundBatchReqVo;
        Map<String, List<ReturnOrderInfoItem>> detailMap = reqVo.getDetailList().stream().collect(Collectors.groupingBy(ReturnOrderInfoItem::getWarehouseCode));
        for (String warehouseCode : detailMap.keySet()) {
            //实际含税金额
            Long praTaxAmount = 0L;
            //实际入库数量
            Long praInboundNum = 0L;
            //实际入库主数量
            Long praMainUnitNum = 0L;
            //预计含税金额
            Long preTaxAmount = 0L;
            //预计入库数量
            Long preInboundNum = 0L;
            //预计入库主数量
            Long preMainUnitNum = 0L;
            products = Lists.newArrayList();
            batchList = Lists.newArrayList();
            inbound = new InboundReqSave();
            BeanUtils.copyProperties(reqVo, inbound);
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
            inbound.setDetailedAddress(reqVo.getDetailAddress());
            //来源单据号
            inbound.setSourceOderCode(reqVo.getReturnOrderCode());
            //物流中心编码
            inbound.setLogisticsCenterCode(reqVo.getTransportCenterCode());
            inbound.setLogisticsCenterName(reqVo.getTransportCenterName());
            //供应商名称
            inbound.setSupplierName(reqVo.getSupplierName());
            inbound.setSupplierCode(reqVo.getSupplierCode());
            //预计到货时间
            inbound.setPreArrivalTime(reqVo.getReceivingTime());
            inbound.setCreateTime(reqVo.getCreateDate());
            //创建人
            inbound.setCreateBy(reqVo.getCreateByName());
            inbound.setUpdateBy(reqVo.getUpdateByName());
            for (ReturnOrderInfoItem returnOrderInfoItem : detailMap.get(warehouseCode)) {
                product = new InboundProductReqVo();
                inboundBatchReqVo = new InboundBatchReqVo();
                inboundBatchReqVo.setSkuName(returnOrderInfoItem.getSkuName());
                inboundBatchReqVo.setSkuCode(returnOrderInfoItem.getSkuCode());
                inboundBatchReqVo.setSupplierCode(returnOrderInfoItem.getSupplyCode());
                inboundBatchReqVo.setSupplierName(returnOrderInfoItem.getSupplyName());
                inboundBatchReqVo.setPraQty(returnOrderInfoItem.getNum());
                inboundBatchReqVo.setCreateBy(reqVo.getCreateByName());
                inboundBatchReqVo.setUpdateBy(reqVo.getUpdateByName());

                BeanUtils.copyProperties(returnOrderInfoItem, product);
                product.setPreInboundMainNum(returnOrderInfoItem.getNum());
                product.setPreInboundNum(returnOrderInfoItem.getNum());
                product.setPreTaxAmount(returnOrderInfoItem.getPrice() * product.getPreInboundNum());
                product.setPraInboundMainNum(returnOrderInfoItem.getActualInboundNum().longValue());
                product.setPraInboundNum(returnOrderInfoItem.getActualInboundNum().longValue());
                product.setPraTaxAmount(returnOrderInfoItem.getPrice() * product.getPraInboundNum());
                praInboundNum += returnOrderInfoItem.getActualInboundNum();
                praMainUnitNum += returnOrderInfoItem.getActualInboundNum();
                praTaxAmount += returnOrderInfoItem.getPrice() * praInboundNum;
                preInboundNum += returnOrderInfoItem.getNum();
                preMainUnitNum += returnOrderInfoItem.getNum();
                preTaxAmount += returnOrderInfoItem.getPrice() * preInboundNum;
                inbound.setWarehouseCode(returnOrderInfoItem.getWarehouseCode());
                inbound.setWarehouseName(returnOrderInfoItem.getWarehouseName());
                //规格.
                product.setNorms(map.get(returnOrderInfoItem.getSkuCode()).getSpec());
                product.setInboundNorms(returnOrderInfoItem.getSpec());
                product.setCreateBy(reqVo.getCreateByName());
                product.setCreateTime(reqVo.getCreateDate());
                product.setInboundBaseContent("1");
                product.setInboundBaseUnit("1");
                product.setSupplyCode(returnOrderInfoItem.getSupplyCode());
                product.setSupplyName(returnOrderInfoItem.getSupplyName());
                //税率
                product.setTax(returnOrderInfoItem.getTax().intValue());
                products.add(product);
                batchList.add(inboundBatchReqVo);
                inbound.setList(products);
                inbound.setInboundBatchReqVos(batchList);
            }
            //实际含税总金额
            inbound.setPraTaxAmount(praTaxAmount);
            //实际入库数量
            inbound.setPraInboundNum(praInboundNum);
            //实际入库主数量
            inbound.setPraMainUnitNum(praMainUnitNum);
            //预计入库数量
            inbound.setPreInboundNum(preInboundNum);
            //预计入库主数量
            inbound.setPreMainUnitNum(preMainUnitNum);
            //预计含税总金额
            inbound.setPreTaxAmount(preTaxAmount);
            list.add(inbound);
        }
        return list;
    }
}
