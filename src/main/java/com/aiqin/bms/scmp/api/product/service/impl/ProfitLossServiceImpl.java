package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLoss;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLossProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLossProductBatch;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.ProfitLossBatchWmsReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.ProfitLossProductWmsReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.ProfitLossWmsReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.QueryProfitLossVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockBatchInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.DetailProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.ProfitLossProductBatchRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.ProfitLossProductRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.QueryProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProfitLossMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProfitLossProductBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProfitLossProductMapper;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.product.service.OutboundService;
import com.aiqin.bms.scmp.api.product.service.ProfitLossService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.dl.BatchRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.dl.ProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.dl.StockChangeDlRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.purchase.service.impl.OrderCallbackServiceImpl;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProfitLossServiceImpl
 * @date 2019/6/28 12:19

 */
@Service
public class ProfitLossServiceImpl extends BaseServiceImpl implements ProfitLossService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCallbackServiceImpl.class);

    /**
     * 宁波熙耘
     */
    private final static String COMPANY_CODE = "09";
    private final static String COMPANY_NAME = "宁波熙耘科技有限公司";
    private static List<String> productTypeList = Arrays.asList("商品", "赠品", "实物返");
    @Autowired
    private ProfitLossMapper profitLossMapper;
    @Autowired
    private ProfitLossProductMapper productMapper;
    @Autowired
    private ProfitLossProductBatchMapper batchProductMapper;
    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private SupplyCompanyDao supplyCompanyDao;
    @Autowired
    private StockService stockService;
    @Autowired
    private OutboundService outboundService;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private SapBaseDataService sapBaseDataService;
    @Autowired
    private WarehouseDao warehouseDao;
    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryProfitLossRespVo> findPage(QueryProfitLossVo vo) {
        PageHelper.startPage(vo.getPageNo(),vo.getPageSize());
        vo.setCompanyCode(getUser().getCompanyCode());
        List<QueryProfitLossRespVo> list = profitLossMapper.getList(vo);

        return PageUtil.getPageList(vo.getPageNo(),list);
    }

    /**
     * 根据Id查询详情
     *
     * @param id
     */
    @Override
    public DetailProfitLossRespVo view(Long id) {
        if(Objects.isNull(id)){
            throw new BizException(ResultCode.ID_EMPTY);
        }
        DetailProfitLossRespVo respVo = new DetailProfitLossRespVo();
        ProfitLoss profitLoss = profitLossMapper.selectByPrimaryKey(id);
        if(Objects.isNull(profitLoss)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        BeanCopyUtils.copy(profitLoss,respVo);
        respVo.setOrderTypeName(respVo.getOrderType() == 0 ? "指定损溢" : "盘点损溢");
        List<ProfitLossProduct> products = productMapper.getListByOrderCode(respVo.getOrderCode());
        if(CollectionUtils.isNotEmptyCollection(products)){
            List<ProfitLossProductRespVo> profitLossProductRespVos = BeanCopyUtils.copyList(products, ProfitLossProductRespVo.class);
            respVo.setProducts(profitLossProductRespVos);
        }
        List<ProfitLossProductBatch> productBatches = batchProductMapper.getListByOrderCode(respVo.getOrderCode());
        if(CollectionUtils.isNotEmptyCollection(products)){
            List<ProfitLossProductBatchRespVo> productBatchRespVos = BeanCopyUtils.copyList(productBatches, ProfitLossProductBatchRespVo.class);
            respVo.setBatchProducts(productBatchRespVos);
        }
        return respVo;
    }

    /**
     *  wms回调损溢
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse profitLossWmsEcho(ProfitLossWmsReqVo request) {
        // 查询该单据是否存在
        ProfitLoss result = profitLossMapper.selectByOrderCode(request.getOrderCode());
        if(result!=null){
            throw new GroundRuntimeException("单据已存在");
        }
        // 查询对应的批次管理
        WarehouseDTO warehouseByCode = warehouseDao.getWarehouseByCode(request.getWarehouseCode());
        LOGGER.info("wms出库回传，查询对应的库房批次管理信息：{}", JsonUtil.toJson(warehouseByCode));
        // 损溢主表
        ProfitLoss profitLoss = new ProfitLoss();
        List<ProfitLoss> profitLossList = Lists.newArrayList();
        // 损溢商品表
        List<ProfitLossDetailRequest> profitLossProductList = Lists.newArrayList();
        // 损溢商品批次表
        List<ProfitLossProductBatch> batchList = Lists.newArrayList();
        // 查询该sku商品信息
        List<String> skuList = request.getDetailList().stream().map(ProfitLossProductWmsReqVo::getSkuCode).collect(Collectors.toList());
        List<OrderProductSkuResponse> productSkuList = productSkuDao.selectStockSkuInfoList(skuList);
        Map<String, OrderProductSkuResponse> productSkuResponseMap = productSkuList.stream().collect(Collectors.toMap(OrderProductSkuResponse::getSkuCode, Function.identity()));
        OrderProductSkuResponse productSkuResponse;
        // 查询供应商信息
        List<String> supplyIds = request.getDetailList().stream().map(ProfitLossProductWmsReqVo::getSupplierCode).collect(Collectors.toList());
        Map<String, SupplyCompany> supplyCompanyMap = supplyCompanyDao.selectByCompanyCodeList(supplyIds, "09");
        //报溢数量 正数值
        Long profitQuantity;
        //报损数量 负数值
        Long lossQuantity;
        // 商品信息
        for (ProfitLossProductWmsReqVo product : request.getDetailList()) {
            profitQuantity = 0L;
            lossQuantity = 0L;
            productSkuResponse = productSkuResponseMap.get(product.getSkuCode());
            if (productSkuResponse == null) {
                throw new GroundRuntimeException("未查询到商品信息");
            }
            ProfitLossDetailRequest profitLossDetail = new ProfitLossDetailRequest();
            profitLossDetail.setLogisticsCenterCode(request.getTransportCenterCode());
            profitLossDetail.setLogisticsCenterName(request.getTransportCenterName());
            profitLossDetail.setWarehouseCode(request.getWarehouseCode());
            profitLossDetail.setWarehouseName(request.getWarehouseName());
            profitLossDetail.setLineNum(product.getLineNum());
            profitLossDetail.setOrderCode(request.getOrderCode());
            profitLossDetail.setSkuCode(product.getSkuCode());
            profitLossDetail.setQuantity(product.getQuantity());
            profitLossDetail.setReason(product.getReason());
            profitLossDetail.setSupplyCode(product.getSupplierCode());
            profitLossDetail.setSkuName(productSkuResponse.getProductName());
            profitLossDetail.setCategory(productSkuResponse.getCategoryName());
            profitLossDetail.setBrand(productSkuResponse.getBrandName());
            profitLossDetail.setColor(productSkuResponse.getColorName());
            profitLossDetail.setSpecification(productSkuResponse.getSpec());
            profitLossDetail.setModel(productSkuResponse.getModel());
            profitLossDetail.setUnit(productSkuResponse.getUnitName());
            profitLossDetail.setType(productTypeList.get(productSkuResponse.getProductType()));
            profitLossDetail.setTax(productSkuResponse.getTax());
            profitLossDetail.setPictureUrl(productSkuResponse.getPictureUrl());
//            profitLossDetail.setTaxAmount(profitLossDetail.getTaxPrice()*profitLossDetail.getQuantity());
            profitLossDetail.setCreateTime(request.getCreateTime());
            profitLossDetail.setCreateById(request.getCreateById());
            profitLossDetail.setCreateByName(request.getCreateByName());
            profitLossDetail.setUpdateTime(request.getUpdateTime());
            profitLossDetail.setUpdateById(request.getUpdateById());
            profitLossDetail.setUpdateByName(request.getUpdateByName());
            if (product.getType() == 1) {
                profitQuantity += product.getQuantity();
                profitLossDetail.setLossOrderCode(1);
                profitLossDetail.setLossOrderName("报溢-增加库存");
            } else {
                lossQuantity += product.getQuantity();
                profitLossDetail.setLossOrderCode(2);
                profitLossDetail.setLossOrderName("报损-减少库存");
            }
            profitLoss.setProfitQuantity(profitQuantity);
            profitLoss.setLossQuantity(lossQuantity);
            profitLossProductList.add(profitLossDetail);
            if(warehouseByCode.getBatchManage().equals(0)){
                ProfitLossProductBatch profitLossProductBatch = new ProfitLossProductBatch();
                profitLossProductBatch.setOrderCode(request.getOrderCode());
                String batchCode = DateUtils.currentDate().replaceAll("-","");
                profitLossProductBatch.setBatchCode(batchCode);
                String batchInfoCode = profitLossDetail.getSkuCode() + "_" + request.getWarehouseCode() + "_" +
                        batchCode + "_" + profitLossDetail.getTaxPrice().stripTrailingZeros().toPlainString();
                profitLossProductBatch.setBatchInfoCode(batchInfoCode);
                profitLossProductBatch.setLineCode(profitLossDetail.getLineNum());
                profitLossProductBatch.setSkuCode(profitLossDetail.getSkuCode());
                profitLossProductBatch.setSkuName(productSkuResponse.getProductName());
                profitLossProductBatch.setTotalCount(profitLossDetail.getQuantity());
                profitLossProductBatch.setProductDate(DateUtils.currentDate());
                profitLossProductBatch.setBatchRemark(profitLossDetail.getReason());
//                profitLossProductBatch.setSupplierCode();
//                profitLossProductBatch.setSupplierName();
                profitLossProductBatch.setCreateById(request.getCreateById());
                profitLossProductBatch.setCreateByName(request.getCreateByName());
                profitLossProductBatch.setUpdateById(request.getUpdateById());
                profitLossProductBatch.setUpdateByName(request.getUpdateByName());
                profitLossProductBatch.setCreateTime(request.getCreateTime());
                profitLossProductBatch.setUpdateTime(request.getUpdateTime());
                batchList.add(profitLossProductBatch);
            }
        }
        // 批次商品
      if(org.apache.commons.collections.CollectionUtils.isNotEmpty(request.getBatchList()) && request.getBatchList().size() > 0 && !warehouseByCode.getBatchManage().equals(Global.BATCH_MANAGE_0)){
          for (ProfitLossBatchWmsReqVo productBatch : request.getBatchList()) {
              productSkuResponse = productSkuResponseMap.get(productBatch.getSkuCode());
              if (productSkuResponse == null) {
                  throw new GroundRuntimeException("未查询到商品信息");
              }
              ProfitLossProductBatch profitLossProductBatch = new ProfitLossProductBatch();
              profitLossProductBatch.setOrderCode(request.getOrderCode());
              //       profitLossProductBatch.setLocationCode();
              //        profitLossProductBatch.setBatchInfoCode();
              profitLossProductBatch.setLineCode(productBatch.getLineCode());
              profitLossProductBatch.setSkuCode(productBatch.getSkuCode());
              profitLossProductBatch.setSkuName(productSkuResponse.getProductName());
              profitLossProductBatch.setTotalCount(productBatch.getActualTotalCount());
              profitLossProductBatch.setProductDate(productBatch.getProductDate());
              profitLossProductBatch.setBatchRemark(productBatch.getBatchRemark());
              profitLossProductBatch.setBatchCode(productBatch.getBatchCode());
              profitLossProductBatch.setSupplierCode(productBatch.getSupplierCode());
              profitLossProductBatch.setSupplierName(productBatch.getSupplierName());
              profitLossProductBatch.setBeOverdueDate(productBatch.getBeOverdueData());
              profitLossProductBatch.setCreateById(request.getCreateById());
              profitLossProductBatch.setCreateByName(request.getCreateByName());
              profitLossProductBatch.setUpdateById(request.getUpdateById());
              profitLossProductBatch.setUpdateByName(request.getUpdateByName());
              profitLossProductBatch.setCreateTime(request.getCreateTime());
              profitLossProductBatch.setUpdateTime(request.getUpdateTime());
              batchList.add(profitLossProductBatch);
          }
      }
        profitLoss.setLogisticsCenterCode(warehouseByCode.getLogisticsCenterCode());
        profitLoss.setLogisticsCenterName(warehouseByCode.getLogisticsCenterName());
        profitLoss.setProfitTotalCostRate(new BigDecimal(0));
        profitLoss.setLossTotalCostRate(new BigDecimal(0));
        profitLoss.setOrderCode(request.getOrderCode());
        if(request.getType() == 0){
            profitLoss.setOrderType(1);
        }else {
            profitLoss.setOrderType(0);
        }
        profitLoss.setWarehouseCode(warehouseByCode.getWarehouseCode());
        profitLoss.setWarehouseName(warehouseByCode.getWarehouseName());
        profitLoss.setRemark(request.getRemark());
        profitLoss.setCompanyName(COMPANY_NAME);
        profitLoss.setCompanyCode(COMPANY_CODE);
        profitLoss.setCreateBy(request.getCreateByName());
        profitLoss.setCreateTime(request.getCreateTime());
        profitLoss.setUpdateBy(request.getUpdateByName());
        profitLoss.setUpdateTime(request.getUpdateTime());
        //wms只传回完成的
        profitLoss.setOrderStatusCode(0);
        profitLoss.setOrderStatusName("完成");
        profitLossList.add(profitLoss);
        //添加损溢记录
        profitLossMapper.insertList(profitLossList);
        productMapper.insertList(profitLossProductList);
        if(CollectionUtils.isNotEmptyCollection(batchList)){
            batchProductMapper.insertBatchList(batchList);
        }
        //库存变动操作
//        Map<Integer, List<ProfitLossDetailRequest>> groupByList = profitLossProductList.stream().collect(Collectors.groupingBy(baseOrder -> {
//            if (baseOrder.getLossOrderCode().equals("1")) {
//                return 1;
//            } else {
//                return 0;
//            }
//        }));
        List<ProfitLossDetailRequest> profit = new ArrayList<>();
        List<ProfitLossDetailRequest> loss = new ArrayList<>();
        for (ProfitLossDetailRequest p:  profitLossProductList) {
            //正值减库存
            if (p.getLossOrderCode() == 2) {
                loss.add(p);
                // 减库存 保存出库 损溢不进行出入库记录
//            OutboundReqVo outboundReqVo = outbount(profitLoss, profitLossProductList, batchList);
//            outboundService.save(outboundReqVo);
            }else if (p.getLossOrderCode() == 1){
                profit.add(p);
            }
            if(CollectionUtils.isNotEmptyCollection(loss)){
                //操作类型 直接减库存 4
                ChangeStockRequest changeStockRequest = handleProfitLossStockData(loss, request.getOrderCode(), batchList, warehouseByCode);
                changeStockRequest.setOperationType(4);
                HttpResponse httpResponse = stockService.stockAndBatchChange(changeStockRequest);
                if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                    LOGGER.error("wms回调:减库存异常");
                    throw new GroundRuntimeException("wms回调:减库存异常");
                }

                // 损 操作完调用dl库存同步接口
                StockChangeDlRequest stockChangeDlRequest = new StockChangeDlRequest();
                stockChangeDlRequest.setOrderCode(profitLoss.getOrderCode());
                stockChangeDlRequest.setOrderType(Global.DL_ORDER_TYPE_5);
                stockChangeDlRequest.setOperationType(Global.DL_OPERATION_TYPE_2);
                profitSynchrdlStockChange(profitLoss, loss, batchList, stockChangeDlRequest);
                HttpResponse response = stockService.dlStockChange(stockChangeDlRequest);
                LOGGER.info("调用完库存锁定调用同步dl库存参数数据:{}", JsonUtil.toJson(stockChangeDlRequest));
                if (!response.getCode().equals(MessageId.SUCCESS_CODE)) {
                    LOGGER.info("调用完库存锁定调用同步dl库存数据异常信息:{}", response.getMessage());
                    throw new GroundRuntimeException("调用完库存锁定调用同步dl库存参数数据");
                }
            }
            if(CollectionUtils.isNotEmptyCollection(profit)){
                //操作类型 直接加库存 6
                ChangeStockRequest changeStockRequest = handleProfitLossStockData(profit, request.getOrderCode(), batchList, warehouseByCode);
                changeStockRequest.setOperationType(6);
                HttpResponse httpResponse = stockService.stockAndBatchChange(changeStockRequest);
                if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                    LOGGER.error("wms回调:加库存异常");
                    throw new GroundRuntimeException("wms回调:加库存异常");
                }

                // 溢 操作完调用dl库存同步接口
                StockChangeDlRequest stockChangeDlRequest = new StockChangeDlRequest();
                stockChangeDlRequest.setOrderCode(profitLoss.getOrderCode());
                stockChangeDlRequest.setOrderType(Global.DL_ORDER_TYPE_9);
                stockChangeDlRequest.setOperationType(Global.DL_OPERATION_TYPE_1);
                profitSynchrdlStockChange(profitLoss, profit, batchList, stockChangeDlRequest);
                HttpResponse response = stockService.dlStockChange(stockChangeDlRequest);
                LOGGER.info("调用完库存锁定调用同步dl库存参数数据:{}", JsonUtil.toJson(stockChangeDlRequest));
                if (!response.getCode().equals(MessageId.SUCCESS_CODE)) {
                    LOGGER.info("调用完库存锁定调用同步dl库存数据异常信息:{}", response.getMessage());
                    throw new GroundRuntimeException("调用完库存锁定调用同步dl库存参数数据");
                }
            }
            // 加库存 保存入库 损溢不进行出入库记录
//            InboundReqSave inboundReqSave = inbouont(profitLoss, profitLossProductList, batchList);
//            inboundService.saveInbound2(inboundReqSave);
        }
        // 损溢单完成调用sap
        sapBaseDataService.allocationAndprofitLoss(request.getOrderCode(),1);
        LOGGER.info("移库wms回传成功");
        return HttpResponse.success();
    }

    private void profitSynchrdlStockChange(ProfitLoss profitLoss, List<ProfitLossDetailRequest> profitLossProductList, List<ProfitLossProductBatch> batchLists, StockChangeDlRequest stockChangeDlRequest) {
        // 主表数据
        Long totalCount = 0L;
        stockChangeDlRequest.setWarehouseCode(profitLoss.getWarehouseCode());
        stockChangeDlRequest.setWarehouseName(profitLoss.getWarehouseName());
        stockChangeDlRequest.setOperationName(profitLoss.getCreateBy());
        stockChangeDlRequest.setOperationCode("0000");
        // 商品数据
        List<ProductRequest> productList = new ArrayList<>();
        for (ProfitLossDetailRequest product : profitLossProductList) {
            ProductRequest productRequest = new ProductRequest();
            productRequest.setLineCode(product.getLineNum().intValue());
            productRequest.setSkuCode(product.getSkuCode());
            productRequest.setSkuName(product.getSkuName());
            productRequest.setTotalCount(product.getQuantity());
            totalCount+=product.getQuantity();
//            productRequest.setUnitName(product.getUnit());
//            productRequest.setColorName(product.getColor());
//            productRequest.setModelNumber(product.getModel());
            productRequest.setProductType(0);
            productRequest.setProductAmount(product.getTaxPrice() == null ? BigDecimal.ZERO : product.getTaxPrice());
            productRequest.setTaxRate(product.getTax() == null ? BigDecimal.ZERO : product.getTax());
            BigDecimal noTaxPrice = Calculate.computeNoTaxPrice(productRequest.getProductAmount(), productRequest.getTaxRate());
            productRequest.setNotProductAmount(noTaxPrice == null ? BigDecimal.ZERO : product.getTaxPrice());
//            productRequest.setWarehouseCode(profitLoss.getWarehouseCode());
//            productRequest.setWarehouseName(profitLoss.getWarehouseName());
            // 批次数据
            List<BatchRequest> batchList = new ArrayList<>();
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(batchLists) && batchLists.size() > 0){
                for (ProfitLossProductBatch productBatch : batchLists) {
                    if(Objects.equals(product.getSkuCode() + product.getLineNum().toString(),
                            productBatch.getSkuCode() + productBatch.getLineCode().toString())){
                        BatchRequest batchRequest = new BatchRequest();
                        batchRequest.setLineCode(productBatch.getLineCode().intValue());
                        batchRequest.setSkuCode(productBatch.getSkuCode());
                        batchRequest.setBatchCode(productBatch.getBatchCode());
                        batchRequest.setProductDate(productBatch.getProductDate());
                        batchRequest.setActualTotalCount(productBatch.getTotalCount());
                        batchList.add(batchRequest);
                    }
                }
                productRequest.setBatchList(batchList);
            }
            productList.add(productRequest);
        }
        stockChangeDlRequest.setTotalCount(totalCount);
    }

    private InboundReqSave inbouont(ProfitLoss profitLoss, List<ProfitLossDetailRequest> profitLossProductList, List<ProfitLossProductBatch> batchList) {
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.IN_BOUND_CODE);
        // 更新数据库编码尺度
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),  encodingRule.getId());
        InboundReqSave inboundReqSave = new InboundReqSave();
        List<InboundProductReqVo> list = new ArrayList<>();
        List<InboundBatchReqVo> inboundBatchReqVos = new ArrayList<>();
        for (ProfitLossDetailRequest profitLossProduct : profitLossProductList) {
            InboundProductReqVo inboundProductReqVo = new InboundProductReqVo();
            inboundProductReqVo.setInboundOderCode(String.valueOf(encodingRule.getNumberingValue()));
            inboundProductReqVo.setSkuCode(profitLossProduct.getSkuCode());
            inboundProductReqVo.setSkuName(profitLossProduct.getSkuName());
            inboundProductReqVo.setPictureUrl(profitLossProduct.getPictureUrl());
            inboundProductReqVo.setNorms(profitLossProduct.getSpecification());
//            inboundProductReqVo.setColorCode(profitLossProduct.getColor());
            inboundProductReqVo.setColorName(profitLossProduct.getColor());
            inboundProductReqVo.setModel(profitLossProduct.getModel());
            inboundProductReqVo.setUnitName(profitLossProduct.getUnit());
            inboundProductReqVo.setInboundNorms(profitLossProduct.getSpecification());
            inboundProductReqVo.setInboundBaseContent(profitLossProduct.getSpecification());
            inboundProductReqVo.setPreInboundNum(profitLossProduct.getQuantity());
            inboundProductReqVo.setPreInboundMainNum(profitLossProduct.getQuantity());
            inboundProductReqVo.setPreTaxPurchaseAmount(new BigDecimal(0));
            inboundProductReqVo.setPreTaxAmount(new BigDecimal(0));
            inboundProductReqVo.setPraInboundNum(profitLossProduct.getQuantity());
            inboundProductReqVo.setPraInboundMainNum(profitLossProduct.getQuantity());
            inboundProductReqVo.setPraTaxPurchaseAmount(new BigDecimal(0));
            inboundProductReqVo.setPraTaxAmount(new BigDecimal(0));
            inboundProductReqVo.setSupplyCode(profitLossProduct.getSupplyCode());
            inboundProductReqVo.setCreateBy(profitLossProduct.getCreateByName());
            inboundProductReqVo.setUpdateBy(profitLossProduct.getUpdateByName());
            inboundProductReqVo.setCreateTime(profitLossProduct.getCreateTime());
            inboundProductReqVo.setUpdateTime(profitLossProduct.getUpdateTime());
            list.add(inboundProductReqVo);
        }
       if(batchList != null){
           for (ProfitLossProductBatch profitLossProductBatch : batchList) {
               InboundBatchReqVo inboundProductReqVo = new InboundBatchReqVo();
               inboundProductReqVo.setInboundOderCode(String.valueOf(encodingRule.getNumberingValue()));
               inboundProductReqVo.setSkuCode(profitLossProductBatch.getSkuCode());
               inboundProductReqVo.setSkuName(profitLossProductBatch.getSkuName());
               inboundProductReqVo.setBatchCode(profitLossProductBatch.getBatchCode());
//            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                Date parse = s.parse(profitLossProductBatch.getProductDate());
//                inboundProductReqVo.setProductDate(parse);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
               inboundProductReqVo.setProductDate(profitLossProductBatch.getProductDate());
               inboundProductReqVo.setBatchRemark(profitLossProductBatch.getBatchRemark());
               inboundProductReqVo.setActualTotalCount(profitLossProductBatch.getTotalCount());
               inboundProductReqVo.setLineCode(profitLossProductBatch.getLineCode());
               inboundProductReqVo.setSupplierCode(profitLossProductBatch.getSupplierCode());
               inboundProductReqVo.setSupplierName(profitLossProductBatch.getSupplierName());
               inboundProductReqVo.setCreateBy(profitLossProductBatch.getCreateByName());
               inboundProductReqVo.setUpdateBy(profitLossProductBatch.getUpdateByName());
               inboundProductReqVo.setCreateById(profitLossProductBatch.getCreateById());
               inboundProductReqVo.setUpdateById(profitLossProductBatch.getUpdateById());
               inboundProductReqVo.setCreateTime(profitLossProductBatch.getCreateTime());
               inboundProductReqVo.setUpdateTime(profitLossProductBatch.getUpdateTime());
               inboundBatchReqVos.add(inboundProductReqVo);
           }
       }

        inboundReqSave.setCompanyCode(COMPANY_CODE);
        inboundReqSave.setCompanyName(COMPANY_NAME);
        inboundReqSave.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        inboundReqSave.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        inboundReqSave.setInboundOderCode(String.valueOf(encodingRule.getNumberingValue()));
//        inboundReqSave.setInboundTypeCode(OutboundTypeEnum.PROFIT_LOSS.getCode());
//        inboundReqSave.setInboundTypeName(OutboundTypeEnum.PROFIT_LOSS.getName());
        inboundReqSave.setSourceOderCode(profitLoss.getOrderCode());
    //    inboundReqSave.setInboundTime();
        inboundReqSave.setLogisticsCenterCode(profitLoss.getLogisticsCenterCode());
        inboundReqSave.setLogisticsCenterName(profitLoss.getLogisticsCenterName());
        inboundReqSave.setWarehouseCode(profitLoss.getWarehouseCode());
        inboundReqSave.setWarehouseName(profitLoss.getWarehouseName());
//        inboundReqSave.setSupplierCode();
//        inboundReqSave.setSupplierName();
//        inboundReqSave.setPreArrivalTime();
        inboundReqSave.setWmsDocumentCode(profitLoss.getOrderCode());
        inboundReqSave.setPreInboundNum(profitLoss.getProfitQuantity());
        inboundReqSave.setPreMainUnitNum(profitLoss.getProfitQuantity());
        inboundReqSave.setPreTaxAmount(new BigDecimal(0));
        inboundReqSave.setPreTax(new BigDecimal(0));
        inboundReqSave.setPraInboundNum(profitLoss.getProfitQuantity());
        inboundReqSave.setPraMainUnitNum(profitLoss.getProfitQuantity());
        inboundReqSave.setPraTaxAmount(new BigDecimal(0));
        inboundReqSave.setPraAmount(new BigDecimal(0));
        inboundReqSave.setPraTax(new BigDecimal(0));
        inboundReqSave.setRemark(profitLoss.getRemark());
        inboundReqSave.setCreateBy(profitLoss.getCreateBy());
        inboundReqSave.setUpdateBy(profitLoss.getUpdateBy());
        inboundReqSave.setCreateTime(profitLoss.getCreateTime());
        inboundReqSave.setUpdateTime(profitLoss.getUpdateTime());

        inboundReqSave.setList(list);
        inboundReqSave.setInboundBatchReqVos(inboundBatchReqVos);
        return inboundReqSave;
    }

    private OutboundReqVo outbount(ProfitLoss profitLoss, List<ProfitLossDetailRequest> profitLossProductList, List<ProfitLossProductBatch> batchList) {
      //  EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.OUT_BOUND_CODE);
        // 更新编码库
      //  encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        OutboundReqVo outboundReqVo = new OutboundReqVo();
        List<OutboundProductReqVo> list = new ArrayList<>();
        List<OutboundBatch> outboundBatches = new ArrayList<>();
        for (ProfitLossDetailRequest profitLossDetailRequest : profitLossProductList) {
            OutboundProductReqVo outboundProductReqVo = new OutboundProductReqVo();
       //     outboundProductReqVo.setOutboundOderCode(String.valueOf(encodingRule.getNumberingValue()));
            outboundProductReqVo.setSkuCode(profitLossDetailRequest.getSkuCode());
            outboundProductReqVo.setSkuName(profitLossDetailRequest.getSkuName());
            outboundProductReqVo.setPictureUrl(profitLossDetailRequest.getPictureUrl());
            outboundProductReqVo.setNorms(profitLossDetailRequest.getSpecification());
//            outboundProductReqVo.setColorCode(profitLossDetailRequest.getColor());
            outboundProductReqVo.setColorName(profitLossDetailRequest.getColor());
            outboundProductReqVo.setModel(profitLossDetailRequest.getModel());
            outboundProductReqVo.setUnitName(profitLossDetailRequest.getUnit());
            outboundProductReqVo.setOutboundNorms(profitLossDetailRequest.getSpecification());
            outboundProductReqVo.setOutboundBaseContent(profitLossDetailRequest.getSpecification());
            outboundProductReqVo.setPreOutboundNum(profitLossDetailRequest.getQuantity());
            outboundProductReqVo.setPreOutboundMainNum(profitLossDetailRequest.getQuantity());
            outboundProductReqVo.setPreTaxPurchaseAmount(new BigDecimal(0));
            outboundProductReqVo.setPreTaxAmount(new BigDecimal(0));
            outboundProductReqVo.setPraOutboundNum(profitLossDetailRequest.getQuantity());
            outboundProductReqVo.setPraOutboundMainNum(profitLossDetailRequest.getQuantity());
            outboundProductReqVo.setPraTaxPurchaseAmount(new BigDecimal(0));
            outboundProductReqVo.setPraTaxAmount(new BigDecimal(0));
            outboundProductReqVo.setCreateBy(profitLossDetailRequest.getCreateByName());
            outboundProductReqVo.setUpdateBy(profitLossDetailRequest.getUpdateByName());
            outboundProductReqVo.setCreateTime(profitLossDetailRequest.getCreateTime());
            outboundProductReqVo.setUpdateTime(profitLossDetailRequest.getUpdateTime());
            list.add(outboundProductReqVo);
        }

        if(batchList != null){
            for (ProfitLossProductBatch profitLossProductBatch : batchList) {
                OutboundBatch outboundBatch = new OutboundBatch();
                BeanCopyUtils.copy(outboundBatch, profitLossProductBatch);
         //       outboundBatch.setOutboundOderCode(encodingRule.getNumberingValue()+"");
                outboundBatch.setActualTotalCount(profitLossProductBatch.getTotalCount());
                outboundBatches.add(outboundBatch);
            }
        }

        outboundReqVo.setCompanyCode(COMPANY_CODE);
        outboundReqVo.setCompanyName(COMPANY_NAME);
        outboundReqVo.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        outboundReqVo.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
     //   outboundReqVo.setOutboundOderCode(encodingRule.getNumberingValue()+"");
//        outboundReqVo.setOutboundTypeCode(OutboundTypeEnum.PROFIT_LOSS.getCode());
//        outboundReqVo.setOutboundTypeName(OutboundTypeEnum.PROFIT_LOSS.getName());
        outboundReqVo.setSourceOderCode(profitLoss.getOrderCode());
        outboundReqVo.setLogisticsCenterCode(profitLoss.getLogisticsCenterCode());
        outboundReqVo.setLogisticsCenterName(profitLoss.getLogisticsCenterName());
        outboundReqVo.setWarehouseCode(profitLoss.getWarehouseCode());
        outboundReqVo.setWarehouseName(profitLoss.getWarehouseName());
//        outboundReqVo.setSupplierCode();
//        outboundReqVo.setSupplierName();
//        outboundReqVo.setPreArrivalTime();
        outboundReqVo.setWmsDocumentCode(profitLoss.getOrderCode());
        outboundReqVo.setPreOutboundNum(profitLoss.getProfitQuantity());
        outboundReqVo.setPreMainUnitNum(profitLoss.getProfitQuantity());
        outboundReqVo.setPreTaxAmount(new BigDecimal(0));
        outboundReqVo.setPreTax(new BigDecimal(0));
        outboundReqVo.setPraOutboundNum(profitLoss.getProfitQuantity());
        outboundReqVo.setPraMainUnitNum(profitLoss.getProfitQuantity());
        outboundReqVo.setPraTaxAmount(new BigDecimal(0));
        outboundReqVo.setPraAmount(new BigDecimal(0));
        outboundReqVo.setPraTax(new BigDecimal(0));
        outboundReqVo.setRemark(profitLoss.getRemark());
        outboundReqVo.setCreateBy(profitLoss.getCreateBy());
        outboundReqVo.setUpdateBy(profitLoss.getUpdateBy());
        outboundReqVo.setCreateTime(profitLoss.getCreateTime());
        outboundReqVo.setUpdateTime(profitLoss.getUpdateTime());

        outboundReqVo.setList(list);
        outboundReqVo.setOutboundBatches(outboundBatches);
        return outboundReqVo;
    }

    private ChangeStockRequest handleProfitLossStockData(List<ProfitLossDetailRequest> profitLossProductList, String sourceOrderCode, List<ProfitLossProductBatch> batchLists, WarehouseDTO warehouseByCode) {
        ChangeStockRequest changeStockRequest = new ChangeStockRequest();
        List<StockInfoRequest> list = Lists.newArrayList();
        StockInfoRequest stockInfoRequest;
        List<StockBatchInfoRequest> batchList = Lists.newArrayList();
        StockBatchInfoRequest stockBatchInfoRequest;
        for (ProfitLossDetailRequest itemReqVo : profitLossProductList) {
            stockInfoRequest = new StockInfoRequest();
            stockInfoRequest.setCompanyCode(COMPANY_CODE);
            stockInfoRequest.setCompanyName(COMPANY_NAME);
            stockInfoRequest.setTransportCenterCode(warehouseByCode.getLogisticsCenterCode());
            stockInfoRequest.setTransportCenterName(warehouseByCode.getLogisticsCenterName());
            stockInfoRequest.setWarehouseCode(warehouseByCode.getWarehouseCode());
            stockInfoRequest.setWarehouseName(warehouseByCode.getWarehouseName());
            stockInfoRequest.setChangeCount(Math.abs(itemReqVo.getQuantity()));
            stockInfoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockInfoRequest.setSkuName(itemReqVo.getSkuName());
            stockInfoRequest.setDocumentType(11);
            stockInfoRequest.setDocumentCode(itemReqVo.getOrderCode());
            stockInfoRequest.setSourceDocumentType(11);
            stockInfoRequest.setSourceDocumentCode(sourceOrderCode);
            stockInfoRequest.setOperatorName(itemReqVo.getCreateByName());
            list.add(stockInfoRequest);

            if(batchLists != null){
                for (ProfitLossProductBatch profitLossProductBatch : batchLists) {
                    if(itemReqVo.getSkuCode().equals(profitLossProductBatch.getSkuCode())){
                        stockBatchInfoRequest = new StockBatchInfoRequest();
                        stockBatchInfoRequest.setCompanyCode(COMPANY_CODE);
                        stockBatchInfoRequest.setCompanyName(COMPANY_NAME);
                        stockBatchInfoRequest.setSkuCode(profitLossProductBatch.getSkuCode());
                        stockBatchInfoRequest.setSkuName(profitLossProductBatch.getSkuName());
                        stockBatchInfoRequest.setBatchCode(profitLossProductBatch.getBatchCode());
                        stockBatchInfoRequest.setBatchInfoCode(profitLossProductBatch.getBatchInfoCode());
                        stockBatchInfoRequest.setProductDate(profitLossProductBatch.getProductDate());
                        stockBatchInfoRequest.setBeOverdueDate(profitLossProductBatch.getBeOverdueDate());
                        stockBatchInfoRequest.setBatchRemark(profitLossProductBatch.getBatchRemark());
                        stockBatchInfoRequest.setSupplierCode(profitLossProductBatch.getSupplierCode());
                        stockBatchInfoRequest.setDocumentType(11);
                        stockBatchInfoRequest.setDocumentCode(profitLossProductBatch.getOrderCode());
                        stockBatchInfoRequest.setSourceDocumentType(11);
                        stockBatchInfoRequest.setSourceDocumentCode(sourceOrderCode);
                        stockBatchInfoRequest.setChangeCount(profitLossProductBatch.getTotalCount());
                        stockBatchInfoRequest.setOperatorId(profitLossProductBatch.getCreateById());
                        stockBatchInfoRequest.setOperatorName(profitLossProductBatch.getCreateByName());
                        batchList.add(stockBatchInfoRequest);
                    }
                }
            }
        }
        changeStockRequest.setStockList(list);
        changeStockRequest.setStockBatchList(batchList);
        return changeStockRequest;
    }
}
