package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.HandleTypeCoce;
import com.aiqin.bms.scmp.api.common.ObjectTypeCode;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.OutboundBatchDao;
import com.aiqin.bms.scmp.api.product.dao.OutboundDao;
import com.aiqin.bms.scmp.api.product.dao.OutboundProductDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.service.OutboundService;
import com.aiqin.bms.scmp.api.product.service.RedisLockService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemBatchMonth;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.OrderInfoItemReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.OrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemBatchMonthMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemProductBatchMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderOutboundService;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderOutboundServiceImpl implements OrderOutboundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderInfoItemMapper orderInfoItemMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchMapper;
    @Autowired
    private OrderInfoItemBatchMonthMapper orderInfoItemBatchMonthMapper;
    @Autowired
    private OutboundService outboundService;
    @Autowired
    private RedisLockService redisLockService;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private OutboundDao outboundDao;
    @Autowired
    private OutboundProductDao outboundProductDao;
    @Autowired
    private OutboundBatchDao outboundBatchDao;

    @Override
    public HttpResponse insertOutboundByOrderCode(List<String> orderCodes) {
        List<OrderInfo> orderInfos = orderInfoMapper.selectByOrderCodes(orderCodes);
        for (OrderInfo orderInfo : orderInfos) {
            // 主表
            OrderInfoReqVO vo = BeanCopyUtils.copy(orderInfo, OrderInfoReqVO.class);
            // 商品表
            List<OrderInfoItem> orderInfoItems = orderInfoItemMapper.selectListByOrderCode(orderInfo.getOrderCode());
            List<OrderInfoItemReqVO> orderItem = BeanCopyUtils.copyList(orderInfoItems, OrderInfoItemReqVO.class);
            vo.setProductList(orderItem);
            // 批次表
            List<OrderInfoItemProductBatch> productBatches = orderInfoItemProductBatchMapper.orderBatchList(null, orderInfo.getOrderCode(), null);
//            List<OrderInfoItemProductBatch> infoBtach = BeanCopyUtils.copyList(productBatches, OrderInfoItemProductBatch.class);
            vo.setItemBatchList(productBatches);
            // 月份批次
            List<OrderInfoItemBatchMonth> infoBtachMonth = orderInfoItemBatchMonthMapper.orderBatchMonthList(orderInfo.getOrderCode());
//            List<OrderInfoItemBatchMonth> infoBtachMonth = BeanCopyUtils.copyList(vo.getItemBatchMonthList(), OrderInfoItemBatchMonth.class);
            vo.setItemBatchMonthList(infoBtachMonth);
            LOGGER.info("销售单转换出库单参数：{}", JsonUtil.toJson(vo));
            insertOutbound(vo);
        }
        return null;
    }

    public String insertOutbound(OrderInfoReqVO vo) {
        OutboundReqVo outboundReqVo = new OutboundReqVo();
        // 公司
        outboundReqVo.setCompanyCode(Global.COMPANY_09);
        outboundReqVo.setCompanyName(Global.COMPANY_09_NAME);
        // 状态
        outboundReqVo.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        outboundReqVo.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
        // 出库类型
        outboundReqVo.setOutboundTypeCode(OutboundTypeEnum.ORDER.getCode());
        outboundReqVo.setOutboundTypeName(OutboundTypeEnum.ORDER.getName());
        // 仓库
        outboundReqVo.setLogisticsCenterCode(vo.getTransportCenterCode());
        outboundReqVo.setLogisticsCenterName(vo.getTransportCenterName());
        // 库房
        outboundReqVo.setWarehouseCode(vo.getWarehouseCode());
        outboundReqVo.setWarehouseName(vo.getWarehouseName());
        // 供应商
        outboundReqVo.setSupplierCode(vo.getSupplierCode());
        outboundReqVo.setSupplierName(vo.getSupplierName());
        //原始单号
        outboundReqVo.setSourceOderCode(vo.getOrderCode());
        //预计出库数量
        outboundReqVo.setPreOutboundNum(vo.getProductNum());
        outboundReqVo.setPraOutboundNum(vo.getActualProductNum());
        //预计主出库数量
        outboundReqVo.setPreMainUnitNum(vo.getProductNum());
        outboundReqVo.setPraMainUnitNum(vo.getActualProductNum());
        //预计含税总金额
        outboundReqVo.setPreTaxAmount(vo.getProductTotalAmount());
        outboundReqVo.setPraTaxAmount(vo.getActualOrderAmount());
        // 地址
        outboundReqVo.setProvinceCode(vo.getProvinceCode());
        outboundReqVo.setProvinceName(vo.getProvinceName());
        outboundReqVo.setCityCode(vo.getCityCode());
        outboundReqVo.setCityName(vo.getCityName());
        outboundReqVo.setCountyCode(vo.getCityCode());
        outboundReqVo.setCountyName(vo.getDistrictName());
        outboundReqVo.setConsignee(vo.getConsignee());
        outboundReqVo.setConsigneeNumber(vo.getConsigneePhone());
        outboundReqVo.setConsigneeRate(vo.getZipCode());
        outboundReqVo.setDetailedAddress(vo.getDetailAddress());
        outboundReqVo.setCreateBy(vo.getCreateByName());
        outboundReqVo.setUpdateBy(vo.getCreateByName());
        // 商品详情
        List<OrderInfoItemReqVO> productList = vo.getProductList();
        List<OutboundProductReqVo> outboundProductList = Lists.newArrayList();
        BigDecimal noTaxTotalAmount = BigDecimal.ZERO;
        OutboundProductReqVo outboundProduct;
        for (OrderInfoItemReqVO product : productList) {
            outboundProduct = new OutboundProductReqVo();
            // sku
            outboundProduct.setSkuCode(product.getSkuCode());
            outboundProduct.setSkuName(product.getSkuName());
            // 图片地址
            outboundProduct.setPictureUrl(product.getPictureUrl());
            //规格
            outboundProduct.setNorms(product.getSpec());
            //单位
            outboundProduct.setUnitCode(product.getUnitCode());
            outboundProduct.setUnitName(product.getUnitName());
            //进货规格
            outboundProduct.setOutboundNorms(product.getSpec());
            //预计出库数量
            outboundProduct.setPreOutboundNum(product.getNum());
            //预计含税进价
            outboundProduct.setPreTaxPurchaseAmount(product.getPrice());
            //预计含税总价
            outboundProduct.setPreTaxAmount(product.getAmount());
            outboundProduct.setPraOutboundNum(product.getNum());
            outboundProduct.setPraTaxAmount(product.getActualAmount());
            outboundProduct.setPraTaxPurchaseAmount(product.getActualPrice());
            outboundProduct.setColorCode(product.getColorCode());
            outboundProduct.setColorName(product.getColorName());
            outboundProduct.setCreateBy(vo.getCreateByName());
            outboundProduct.setUpdateBy(vo.getCreateByName());
            outboundProduct.setPreOutboundMainNum(product.getNum());
            outboundProduct.setLinenum(product.getProductLineNum());
            //计算不含税单价
            BigDecimal noTaxPrice = Calculate.computeNoTaxPrice(product.getPrice(), product.getTax());
            outboundProduct.setOutboundBaseContent("1");
            outboundProduct.setOutboundBaseUnit("1");
            outboundProduct.setTax(product.getTax());
            //计算不含税总价 (现在是主单位数量 * 单价）
            BigDecimal noTaxTotalPrice = noTaxPrice.multiply(BigDecimal.valueOf(product.getNum())).setScale(4, BigDecimal.ROUND_HALF_UP);
            noTaxTotalAmount = noTaxTotalPrice;
            outboundProductList.add(outboundProduct);

            outboundReqVo.setSupplierCode(product.getSupplierCode());
            outboundReqVo.setSupplierName(product.getSupplierName());
        }

        // 商品批次
        List<OrderInfoItemProductBatch> itemBatchList = vo.getItemBatchList();
        List<OutboundBatch> outboundBatches = new ArrayList<>();
        if(CollectionUtils.isNotEmptyCollection(itemBatchList)){
            for (OrderInfoItemProductBatch batchProduct: itemBatchList) {
                OutboundBatch outboundBatche = new OutboundBatch();
                outboundBatche.setBatchCode(batchProduct.getBatchCode());
                outboundBatche.setBatchInfoCode(batchProduct.getBatchInfoCode());
                outboundBatche.setSkuCode(batchProduct.getSkuCode());
                outboundBatche.setSkuName(batchProduct.getSkuName());
                outboundBatche.setSupplierCode(batchProduct.getSupplierCode());
                outboundBatche.setSupplierName(batchProduct.getSupplierName());
                outboundBatche.setBatchRemark(batchProduct.getBatchRemark());
                outboundBatche.setProductDate(batchProduct.getProductDate());
                outboundBatche.setBeOverdueDate(batchProduct.getBeOverdueDate());
                outboundBatche.setTotalCount(batchProduct.getTotalCount());
                outboundBatche.setActualTotalCount(batchProduct.getActualTotalCount());
                outboundBatche.setLineCode(batchProduct.getLineCode());
                outboundBatche.setLocationCode(String.valueOf(batchProduct.getLocationCode()));
                outboundBatche.setCreateTime(batchProduct.getCreateTime());
                outboundBatche.setUpdateTime(batchProduct.getUpdateTime());
                outboundBatche.setCreateById(batchProduct.getCreateById());
                outboundBatche.setCreateByName(batchProduct.getCreateByName());
                outboundBatche.setUpdateById(batchProduct.getUpdateById());
                outboundBatche.setUpdateByName(batchProduct.getUpdateByName());
                outboundBatches.add(outboundBatche);
            }
        }
        //预计无税总金额
        outboundReqVo.setPreAmount(noTaxTotalAmount);
        // 税额
        outboundReqVo.setPreTax(outboundReqVo.getPreTaxAmount().subtract(noTaxTotalAmount));
        outboundReqVo.setList(outboundProductList);
        outboundReqVo.setOutboundBatches(outboundBatches);
        LOGGER.info("销售单转换完出库单，调用出库单接口参数：{}", JsonUtil.toJson(outboundReqVo));
        String outboundOderCode = outboundService.saveOutbound(outboundReqVo);
        return outboundOderCode;
    }

    @Synchronized
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String saveOutbound(OutboundReqVo stockReqVO){
        String outboundOderCode = null;
        try {
            //编码生成
            EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.OUT_BOUND_CODE);
            // 给sku加锁
            long time = System.currentTimeMillis() + 5;
            if (!redisLockService.lock(String.valueOf(numberingType.getNumberingValue()), String.valueOf(time))) {
                LOGGER.info("redis给出库单号编码生成加锁失败：" + numberingType.getNumberingValue());
                throw new BizException("redis给出库单号编码生成加锁失败：" + numberingType.getNumberingValue());
            }
            Outbound outbound =  new Outbound();
            BeanCopyUtils.copy(stockReqVO,outbound);
            outboundOderCode = String.valueOf(numberingType.getNumberingValue());
            LOGGER.info("出库单号：" + outboundOderCode);
            outbound.setOutboundOderCode(outboundOderCode);

            List<OutboundProduct> outboundProducts = BeanCopyUtils.copyList(stockReqVO.getList(), OutboundProduct.class);
            outboundProducts.stream().forEach(outboundProduct -> outboundProduct.setOutboundOderCode(numberingType.getNumberingValue().toString()));
            int i = outboundDao.insertSelective(outbound);
            log.info("插入出库单主表返回结果", i);

            int j = outboundProductDao.insertAll(outboundProducts);
            log.info("插入出库单商品表返回结果", j);

            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(stockReqVO.getOutboundBatches())){
                List<OutboundBatch> outboundBatches = BeanCopyUtils.copyList(stockReqVO.getOutboundBatches(), OutboundBatch.class);
                outboundBatches.stream().forEach(outboundBatch -> outboundBatch.setOutboundOderCode(numberingType.getNumberingValue().toString()));
                int m = outboundBatchDao.insertAll(outboundBatches);
                log.info("插入出库单商品批次表返回结果", m);
            }

            //更新编码
            int value = encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
            LOGGER.info("变更出库单号：{}" + numberingType.getNumberingValue());
            // 给sku解锁 - redis
            redisLockService.unlock(String.valueOf(numberingType.getNumberingValue()), String.valueOf(time));
            LOGGER.info("redis解锁出库单成功：{}" + numberingType.getNumberingValue());

            return outboundOderCode;
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException(String.format("保存出库单失败%s",e.getMessage()));
        }
    }
}
