package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.product.domain.converter.OrderVo2OutBoundConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.ReturnSupply2outboundSaveConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.allocation.AllocationOrderToInboundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.order.OrderInfo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.*;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyToOutBoundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockBatchInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.ResponseWms;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationProductResVo;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.wms.BatchInfo;
import com.aiqin.bms.scmp.api.product.domain.response.wms.PurchaseOutboundDetailSource;
import com.aiqin.bms.scmp.api.product.domain.response.wms.PurchaseOutboundSource;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.dao.RejectRecordDao;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordBatch;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectDetailStockRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectStockRequest;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述:
 * @author Kt.w
 * @date 2019/1/5
 */
@Slf4j
@Service
public class OutboundServiceImpl extends BaseServiceImpl implements OutboundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutboundServiceImpl.class);

    @Autowired
    private OutboundDao outboundDao;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private OutboundProductDao outboundProductDao;
    @Autowired
    private SkuService skuService;
    @Autowired
    private StockService stockService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private ProductOperationLogService productOperationLogService;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private AllocationMapper allocationMapper;
    @Autowired
    private AllocationProductMapper allocationProductMapper;
    @Autowired
    private AllocationProductBatchMapper allocationProductBatchMapper;
    @Autowired
    private OutboundBatchDao outboundBatchDao;
    @Autowired
    @Lazy(true)
    private GoodsRejectService goodsRejectService;
    @Autowired
    private SupplyCompanyDao supplyCompanyDao;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProductSkuPicturesDao productSkuPicturesDao;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private RejectRecordDao rejectRecordDao;
    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private ProductSkuStockInfoMapper productSkuStockInfoMapper;
    @Autowired
    @Lazy(true)
    private OrderCallbackService orderCallbackService;
    @Autowired
    @Lazy(true)
    private SapBaseDataService sapBaseDataService;
    @Autowired
    @Lazy(true)
    private AllocationService allocationService;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockBatchDao stockBatchDao;
    @Autowired
    private ProductSkuSalesInfoDao productSkuSalesInfoDao;
    @Autowired
    private ProductSkuBatchMapper productSkuBatchMapper;

    @Override
    public BasePage<QueryOutboundResVo> getOutboundList(QueryOutboundReqVo vo) {
        try {
            PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
            List<Outbound> list = outboundDao.getOutboundList(vo);
            BasePage<QueryOutboundResVo> basePage = PageUtil.getPageList(vo.getPageNo(),list);
            basePage.setDataList(BeanCopyUtils.copyList(list,QueryOutboundResVo.class));
            return basePage;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("分页查询失败");
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<OutboundResponse> selectOutBoundInfoByBoundSearch(BoundRequest boundRequest) {
        try {
            LOGGER.info("查询出库信息");
            List<String> outboundOderCodeList = new ArrayList<>();
            List<OutboundProduct> outboundProductList;
            List<OutboundResponse> responseList = new ArrayList<>();
            OutboundResponse outboundResponse = new OutboundResponse();
            List<Outbound> outboundList = outboundDao.selectOutboundInfoByBoundSearch(boundRequest);
            for (Outbound outbound : outboundList) {
                outboundResponse = new OutboundResponse();
                outboundOderCodeList.add(outbound.getOutboundOderCode());
                outboundResponse.setOutboundTypeCode(outbound.getOutboundTypeCode());
                outboundResponse.setOutboundTypeName(outbound.getOutboundTypeName());
                outboundResponse.setOutboundStatusCode(outbound.getOutboundStatusCode());
                outboundResponse.setOutboundStatusName(outbound.getOutboundStatusName());
                outboundResponse.setOutboundOderCode(outbound.getOutboundOderCode());
                responseList.add(outboundResponse);
            }
            if(CollectionUtils.isNotEmpty(outboundOderCodeList)){
                outboundProductList = outboundProductDao.selectOutboundProductListByOutBoundOderCodeList(outboundOderCodeList);
                if (CollectionUtils.isNotEmpty(outboundProductList)) {
                    for (OutboundProduct outboundProduct : outboundProductList) {
                        if (CollectionUtils.isNotEmpty(responseList)) {
                            for (OutboundResponse response : responseList) {
                                if (response.getOutboundOderCode().equals(outboundProduct.getOutboundOderCode())) {
                                    outboundResponse.setSkuCode(outboundProduct.getSkuCode());
                                    outboundResponse.setSkuName(outboundProduct.getSkuName());
                                    outboundResponse.setPraOutboundNum(outboundProduct.getPraOutboundNum());
                                    outboundResponse.setPraOutboundMainNum(outboundProduct.getPraOutboundMainNum());
                                    outboundResponse.setOutboundOderCode(outboundProduct.getOutboundOderCode());
                                    outboundResponse.setUpdateBy(outboundProduct.getUpdateBy());
                                    outboundResponse.setUpdateTime(outboundProduct.getUpdateTime());
                                }
                            }
                        }
                    }
                }
            }
            return responseList;
        } catch (Exception e) {
            LOGGER.error("查询出库信息失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    /**
     * 保存出库信息
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Integer saveOutBoundInfo(OutboundReqVo stockReqVO) {
        try {
            //编码生成
            EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.OUT_BOUND_CODE);
            Outbound outbound =  new Outbound();
            BeanCopyUtils.copy(stockReqVO,outbound);
            String outboundOderCode = String.valueOf(numberingType.getNumberingValue());
            outbound.setOutboundOderCode(outboundOderCode);

            List<OutboundProduct> outboundProducts = BeanCopyUtils.copyList(stockReqVO.getList(), OutboundProduct.class);
            outboundProducts.stream().forEach(outboundProduct -> outboundProduct.setOutboundOderCode(numberingType.getNumberingValue().toString()));
            int i = outboundDao.insert(outbound);
            log.info("出库主表保存结果:{}", i);
            int j = outboundProductDao.insertAll(outboundProducts);
            log.info("出库商品保存结果:{}", j);
            List<OutboundBatch> batchList = stockReqVO.getOutboundBatches();
            if(CollectionUtils.isNotEmpty(batchList)){
                batchList.stream().forEach(outBoundBatch->outBoundBatch.setOutboundOderCode(outboundOderCode));
                //添加供应商对应的商品信息
                Integer count = outboundBatchDao.insertAll(batchList);
                LOGGER.info("插入出库单批次结果:{}", count);
            }

            //更新编码
            encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
            // 保存日志
            productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.ADD_OUTBOUND_ODER.getStatus(),
                    ObjectTypeCode.OUTBOUND_ODER.getStatus(),stockReqVO,HandleTypeCoce.ADD_OUTBOUND_ODER.getName(),
                    new Date(),stockReqVO.getCreateBy(), stockReqVO.getRemark());

            // 调用推送接口
            if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())){
                // 退供调用wms
                this.pushRejectWms(outbound.getOutboundOderCode());
            }
            return j;
        } catch (GroundRuntimeException e) {
            LOGGER.error("保存出库单失败:{}",e.getMessage());
            throw new GroundRuntimeException(String.format("保存出库单失败:%s",e.getMessage()));
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse pushRejectWms(String outboundOderCode){
        LOGGER.info("开始调用退供单的wms：{}", outboundOderCode);
        // 查询出库单的信息
        Outbound outbound = outboundDao.selectByCode(outboundOderCode);
        if(outbound == null){
            LOGGER.info("调用WMS-退供的出库单信息为空:", outboundOderCode);
            return HttpResponse.failure(ResultCode.OUTBOUND_DATA_CAN_NOT_BE_NULL);
        }
        List<OutboundProduct> outboundProducts = outboundProductDao.selectByOutboundOderCode(outboundOderCode);
        if(CollectionUtils.isEmpty(outboundProducts)){
            LOGGER.info("调用WMS-退供的出库单商品信息为空:", outboundOderCode);
            return HttpResponse.failure(ResultCode.OUTBOUND_PRODUCT_INFO_NULL);
        }

        // 查询退供单的信息
        RejectRecord rejectRecord = rejectRecordDao.selectByRejectCode(outbound.getSourceOderCode());
        PurchaseOutboundSource outboundSource = BeanCopyUtils.copy(outbound, PurchaseOutboundSource.class);
        outboundSource.setRejectRecordCode(outbound.getSourceOderCode());
        outboundSource.setOperuserCode(rejectRecord.getCreateById());
        outboundSource.setOperuserName(rejectRecord.getCreateByName());
        outboundSource.setCreateDate(rejectRecord.getCreateTime());
        outboundSource.setContactsPerson(rejectRecord.getSupplierPerson());
        outboundSource.setContactsPersonPhone(rejectRecord.getSupplierMobile());
        outboundSource.setProvinceId(outbound.getProvinceCode());
        outboundSource.setCityId(outbound.getCityCode());
        outboundSource.setDistrictId(outbound.getCountyCode());
        outboundSource.setDistrictName(outbound.getCountyName());
        outboundSource.setAddress(outbound.getDetailedAddress());
        outboundSource.setRemark(rejectRecord.getRemark());
        List<PurchaseOutboundDetailSource> detailSourceList = Lists.newArrayList();
        PurchaseOutboundDetailSource detailSource;
        for (OutboundProduct product : outboundProducts){
            detailSource= BeanCopyUtils.copy(product, PurchaseOutboundDetailSource.class);
            detailSource.setLineCode(product.getLinenum().toString());
            detailSource.setTotalCount(product.getPreOutboundMainNum().intValue());
            detailSource.setModelNumber(product.getModel());
            // 查询门店条码
            PurchaseSaleStockRespVo saleInfo = productSkuSalesInfoDao.selectBarCodeBySkuCode(product.getSkuCode());
            if(saleInfo != null){
                detailSource.setSkuBarCode(saleInfo.getBarCode());
                detailSource.setStockUnitCode(saleInfo.getStockUnitCode());
                detailSource.setStockUnitName(saleInfo.getStockUnitName());
            }
            detailSourceList.add(detailSource);
        }
        outboundSource.setDetailList(detailSourceList);

        // 查询批次信息
        List<OutboundBatch> batchList = outboundBatchDao.selectByOutboundBatchOderCode(outboundOderCode);
        if(CollectionUtils.isNotEmpty(batchList)){
            List<BatchInfo> batchInfoList = BeanCopyUtils.copyList(batchList, BatchInfo.class);
            outboundSource.setBatchInfo(batchInfoList);
        }

        LOGGER.info("退供调用WMS -入参：{}", JsonUtil.toJson(outboundSource));
        String url = urlConfig.WMS_API_URL2 + "/purchase/source/outbound";
        HttpClient httpClient = HttpClient.post(url).json(outboundSource).timeout(20000);
        HttpResponse response = httpClient.action().result(HttpResponse.class);
        if (response.getCode().equals(MessageId.SUCCESS_CODE)) {
            LOGGER.info("退供出库单传入wms成功");
            return HttpResponse.success();
        } else {
            LOGGER.error("退供出库单传入wms失败:{}", response.getMessage());
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "操作退供单调用WMS失败"));
        }
    }

    /**
     * 保存出库信息
     * @param stockReqVO
     * @return  返回出库单编码
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String save(OutboundReqVo stockReqVO) {
        String outboundOderCode = this.saveOutbound(stockReqVO);
        //  调用推送接口
        OutboundServiceImpl outboundService = (OutboundServiceImpl) AopContext.currentProxy();
        outboundService.pushWms(outboundOderCode);
        return outboundOderCode;
    }

    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String saveOutbound(OutboundReqVo stockReqVO){
        String outboundOderCode = null;
        try {
            //编码生成
            EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.OUT_BOUND_CODE);
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

            if(CollectionUtils.isNotEmpty(stockReqVO.getOutboundBatches())){
                List<OutboundBatch> outboundBatches = BeanCopyUtils.copyList(stockReqVO.getOutboundBatches(), OutboundBatch.class);
                outboundBatches.stream().forEach(outboundBatch -> outboundBatch.setOutboundOderCode(numberingType.getNumberingValue().toString()));
                int m = outboundBatchDao.insertAll(outboundBatches);
                log.info("插入出库单商品批次表返回结果", m);
            }

            //更新编码
            int value = encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
            LOGGER.info("变更出库单号--------------" + value);
            // 保存日志
            productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.ADD_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(),stockReqVO,HandleTypeCoce.ADD_OUTBOUND_ODER.getName(),new Date(),stockReqVO.getCreateBy(), stockReqVO.getRemark());

            return outboundOderCode;
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException(String.format("保存出库单失败%s",e.getMessage()));
        }
    }

    /**
     * 根据id查询出库单
     * @param id
     * @return
     */
    @Override
    public OutboundResVo view(Long id) {
        Outbound outbound = outboundDao.selectByPrimaryKey(id);
        if (null == outbound) {
            throw new GroundRuntimeException("根据id查询不到数据信息");
        }
        OutboundResVo outboundResVo = new OutboundResVo();
        BeanCopyUtils.copy(outbound, outboundResVo);
        if (outboundResVo.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())) {
            RejectRecord rejectRecord = rejectRecordDao.selectByRejectCode(outboundResVo.getSourceOderCode());
            outboundResVo.setRemark(rejectRecord.getRemark());
        }
        List<OutboundProduct> list = outboundProductDao.selectByOutboundOderCode(outboundResVo.getOutboundOderCode());
        list.stream().forEach(outboundProduct -> {
            List<ReturnOutboundProduct> returnOutboundProductList = outboundProductDao.selectTax(outboundResVo.getOutboundOderCode(), outboundProduct.getSkuCode());
            ReturnOutboundProduct returnOutboundProduct = returnOutboundProductList.get(0);
            ProductSkuRespVo productSkuRespVo = productSkuDao.getSkuInfoResp(outboundProduct.getSkuCode());
            ProductSkuStockInfo productSkuStockInfo = productSkuStockInfoMapper.getBySkuCode(outboundProduct.getSkuCode());
            outboundProduct.setColorCode(productSkuRespVo.getColorCode());
            outboundProduct.setColorName(productSkuRespVo.getColorName());
            outboundProduct.setModel(productSkuRespVo.getModelNumber());
            outboundProduct.setTax(returnOutboundProduct.getInputTaxRate());
            outboundProduct.setNorms(productSkuStockInfo.getSpec());
        });
        try {
            outboundResVo.setList(BeanCopyUtils.copyList(list, OutboundProductResVo.class));
            List<OutboundProductResVo> productList = outboundResVo.getList();
            if (CollectionUtils.isNotEmpty(productList)) {
                for (OutboundProductResVo vo : productList) {
                    if (Objects.isNull(vo.getPraOutboundMainNum()) || vo.getPraOutboundMainNum() == 0) {
                        vo.setPraSingleCount(vo.getPraOutboundMainNum());
                    } else {
                        vo.setPraSingleCount(vo.getPraOutboundMainNum() % Long.valueOf(vo.getOutboundBaseContent()));
                    }

                    if (Objects.isNull(vo.getPreOutboundMainNum()) || vo.getPreOutboundMainNum() == 0) {
                        vo.setPreSingleCount(vo.getPreOutboundMainNum());
                    } else {
                        vo.setPreSingleCount(vo.getPreOutboundMainNum() % Long.valueOf(vo.getOutboundBaseContent()));
                    }
                }
            }
            List<OutboundBatch> batchList = outboundBatchDao.selectByOutboundBatchOderCode(outbound.getOutboundOderCode());
            outboundResVo.setBatchList(batchList);
            if (null != outboundResVo) {
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.OUTBOUND_ODER.getStatus());
                operationLogVo.setObjectId(outboundResVo.getOutboundOderCode());
                List<LogData> pageList = productOperationLogService.getLogType(operationLogVo);
                pageList.stream().forEach(logData -> logData.setStatus(outbound.getOutboundStatusName()));
                outboundResVo.setLogDataList(pageList);
            }
            if (Objects.isNull(outboundResVo.getPraOutboundNum()) || outboundResVo.getPraOutboundNum() == 0) {
                outboundResVo.setPraSingleCount(outboundResVo.getPraMainUnitNum());
            } else {
                outboundResVo.setPraSingleCount(outboundResVo.getPraMainUnitNum() % outboundResVo.getPraOutboundNum());
            }

            if (Objects.isNull(outboundResVo.getPreOutboundNum()) || outboundResVo.getPreOutboundNum() == 0) {
                outboundResVo.setPreSingleCount(outboundResVo.getPreMainUnitNum());
            } else {
                outboundResVo.setPreSingleCount(outboundResVo.getPreMainUnitNum() % outboundResVo.getPreOutboundNum());
            }
            return outboundResVo;
        } catch (Exception e) {
            log.error("出库单sku转化实体失败");
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    /**
     * 查看出库类型
     * @return
     */
    @Override
    public List<EnumReqVo> getOutboundType() {
        List<EnumReqVo> list =  new ArrayList<>();
        OutboundTypeEnum [] outboundTypeEnums = OutboundTypeEnum.values();
        for (OutboundTypeEnum outboundTypeEnum : outboundTypeEnums) {
            list.add(new EnumReqVo(outboundTypeEnum.getCode(),outboundTypeEnum.getName()));
        }
        return list;
    }

    @Override
    public Integer orderSave(List<OrderInfo> req) {
        Integer i = 0;
        //转换
        List<OutboundReqVo> convert = new OrderVo2OutBoundConverter(skuService).convert(req);
        //保存
        for (OutboundReqVo outboundReqVo : convert) {
            Integer integer = saveOutBoundInfo(outboundReqVo);
            i += integer;
        }
        return i;
    }

    @Override
    public Integer returnSupplySave(ReturnSupplyToOutBoundReqVo req) {
        //转换
        OutboundReqVo convert = new ReturnSupply2outboundSaveConverter(skuService, supplyCompanyDao).convert(req);
        //保存
        return saveOutBoundInfo(convert);
    }

    /**
     * 出库单推送给wms
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pushWms(String code){
        log.error("异步推送给wms");
        String url = "";
        // 通过id查询 入库单主体
        OutboundWmsResVO outboundWmsReqVO = new OutboundWmsResVO();

        Outbound outbound = outboundDao.selectByCode(code);
        BeanCopyUtils.copy(outbound,outboundWmsReqVO);
        List<OutboundProductWmsResVO> outboundProductWmsReqVOs =  outboundProductDao.selectMmsReqByOutboundOderCode(outbound.getOutboundOderCode());

        //去重
        Set<OutboundProductWmsResVO> OutboundProductWmsResVOSet = new HashSet<>(outboundProductWmsReqVOs);
        outboundProductWmsReqVOs.clear();
        outboundProductWmsReqVOs.addAll(OutboundProductWmsResVOSet);
        outboundWmsReqVO.setList(outboundProductWmsReqVOs);
        //退供出库需要请求wms
        if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())){
            //RejectRecord record = outboundDao.selectCreateById(outbound.getOutboundOderCode());
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(authToken != null){
                outboundWmsReqVO.setCreateById(authToken.getPersonId());
                outboundWmsReqVO.setCreateByName(authToken.getPersonName());
            }else {
                outboundWmsReqVO.setCreateById("0");
                outboundWmsReqVO.setCreateByName("系统");
            }
            url =urlConfig.WMS_API_URL+"/wms/save/purchase/outbound";
            log.info("向wms发送出库单的参数是：{}", JSON.toJSON(outboundWmsReqVO));
            HttpResponse orderDto = HttpClient.post(url).json(outboundWmsReqVO).timeout(10000).action().result(HttpResponse.class);
            if(orderDto.getCode().equals(MessageId.SUCCESS_CODE)){
                ResponseWms responseWms = JsonUtil.fromJson(JsonUtil.toJson(orderDto.getData()),ResponseWms.class);
                if(responseWms.getResultCode().equals(MessageId.SUCCESS_CODE)){
                    //设置wms编号
                    outbound.setWmsDocumentCode(responseWms.getUniquerRequestNumber());
                    //设置入库状态
                    outbound.setOutboundStatusCode(InOutStatus.SEND_INOUT.getCode());
                    outbound.setOutboundStatusName(InOutStatus.SEND_INOUT.getName());
                }else{
                    LOGGER.error("退供出库单传入wms失败:{}",responseWms.getReason());
                    throw new GroundRuntimeException(String.format("退供出库单传入wms失败:%s",responseWms.getReason()));
                }
            }else{
                LOGGER.error("退供出库单传入wms失败:{}",orderDto.getMessage());
                throw new GroundRuntimeException(String.format("退供出库单传入wms失败:%s",orderDto.getMessage()));
            }
        }else{
            //其他出库 移库 预计数量=实际数量
            //设置出库状态
            outbound.setOutboundStatusCode(InOutStatus.SEND_INOUT.getCode());
            outbound.setOutboundStatusName(InOutStatus.SEND_INOUT.getName());
        }
        // 更新数据库
        outboundDao.update(outbound);
        OutboundCallBackReqVo outboundCallBackReqVo = new OutboundCallBackReqVo();
        BeanCopyUtils.copy(outbound,outboundCallBackReqVo);
        List<OutboundProductCallBackReqVo> list = new ArrayList<>();
        for (OutboundProductWmsResVO outboundProductWmsReqVO : outboundProductWmsReqVOs) {
            OutboundProductCallBackReqVo outboundProductCallBackReqVo = new OutboundProductCallBackReqVo();
            outboundProductCallBackReqVo.setLineCode(outboundProductWmsReqVO.getLinenum());
            outboundProductCallBackReqVo.setSkuCode(outboundProductWmsReqVO.getSkuCode());
            //outboundProductCallBackReqVo.setPraOutboundMainNum(outboundProductWmsReqVO.getPreInboundMainNum());
            list.add(outboundProductCallBackReqVo);
        }
        outboundCallBackReqVo.setDetailList(list);
        //保存日志
        productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.PULL_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(),outbound,HandleTypeCoce.PULL_OUTBOUND_ODER.getName(),new Date(),outbound.getCreateBy(), null);
        if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.MOVEMENT.getCode())){
            workFlowCallBack(outboundCallBackReqVo);
        }
    }

    @Override
//    @Async("myTaskAsyncPool")
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse workFlowCallBack(OutboundCallBackReqVo request) {
        LOGGER.info("WMS回传出库单参数：{}", JsonUtil.toJson(request));
        Outbound outbound;
        if(request.getOutboundTypeCode().equals(Integer.valueOf(OutboundTypeEnum.RETURN_SUPPLY.getCode())) ||
                request.getOutboundTypeCode().equals(Integer.valueOf(OutboundTypeEnum.ORDER.getCode()))){
            outbound = outboundDao.selectBySourceCode(request.getOutboundOderCode(), request.getOutboundTypeCode().toString());
        }else{
            outbound = outboundDao.selectByCode(request.getOutboundOderCode());
        }
        if (outbound == null) {
            LOGGER.info("WMS回传出库单信息为空");
            return HttpResponse.failure(ResultCode.OUTBOUND_DATA_CAN_NOT_BE_NULL);
        }

        // 设置已回传默认值
        outbound.setOutboundStatusCode(InOutStatus.RECEIVE_INOUT.getCode());
        outbound.setOutboundStatusName(InOutStatus.RECEIVE_INOUT.getName());
        outbound.setPraOutboundNum(0L);
        outbound.setPraMainUnitNum(0L);
        outbound.setPraTaxAmount(BigDecimal.ZERO);
        outbound.setPraTax(BigDecimal.ZERO);
        outbound.setPraAmount(BigDecimal.ZERO);
        outbound.setCompanyCode(Global.COMPANY_09);
        outbound.setCompanyName(Global.COMPANY_09_NAME);
        outbound.setOutboundTime(request.getFinishOutboundTime());

        // 库存、批次库存变更赋值
        ChangeStockRequest changeStockRequest = new ChangeStockRequest();
        changeStockRequest.setOperationType(Global.STOCK_OPERATION_10);

        List<StockInfoRequest> stockInfoRequestList = Lists.newArrayList();
        OutboundProduct outboundProduct;
        StockInfoRequest stockInfoRequest;
        // 计算出库单总和
        Long praOutboundCount = 0L, praTotalOutboundCount = 0L;
        BigDecimal praOutboundAmount = BigDecimal.ZERO, praTotalOutboundAmount = BigDecimal.ZERO;

        // 查询对应的库房信息（包括批次管理模式）
        WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(outbound.getWarehouseCode());
        LOGGER.info("库房信息（包括批次管理信息）：{}", JsonUtil.toJson(warehouse));

        Integer typeCode = 0;
        if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())){
            typeCode = Global.DOCUMENT_TYPE_2;
        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode())){
            typeCode = Global.DOCUMENT_TYPE_4;
        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode())){
            typeCode = Global.DOCUMENT_TYPE_9;
        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.MOVEMENT.getCode())){
            typeCode = Global.DOCUMENT_TYPE_6;
        }else {
            LOGGER.info("回传类型无法匹配，回传失败");
            throw new GroundRuntimeException("回传类型无法匹配，回传失败");
        }

        OutboundBatch outboundBatch;
        List<OutboundBatch> outboundBatches = Lists.newArrayList();
        List<OutboundBatch> notOutboundBatches = Lists.newArrayList();

        // 更新出库商品信息
        for (OutboundProductCallBackReqVo detail : request.getDetailList()) {
            // 查询对应的出库单商品信息
            outboundProduct = outboundProductDao.selectByLineCode(outbound.getOutboundOderCode(), detail.getSkuCode(), detail.getLineCode());

            Long actualTotalCount = detail.getActualTotalCount();
            if(outboundProduct != null){
                Long content = outboundProduct.getOutboundBaseContent() == null ? 1L : Long.valueOf(outboundProduct.getOutboundBaseContent());
                outboundProduct.setPraOutboundNum(actualTotalCount / content);
                outboundProduct.setPraOutboundMainNum(actualTotalCount);
                outboundProduct.setPraTaxPurchaseAmount(outboundProduct.getPreTaxPurchaseAmount());
                outboundProduct.setPraTaxAmount(BigDecimal.valueOf(outboundProduct.getPraOutboundMainNum()).
                        multiply(outboundProduct.getPraTaxPurchaseAmount()).setScale(4, BigDecimal.ROUND_HALF_UP));

                Integer count = outboundProductDao.update(outboundProduct);
                LOGGER.info("更新出库单商品信息：{}", count);
            }

            // 计算出库单总实际值
            praOutboundCount += outbound.getPraOutboundNum();
            praTotalOutboundCount += outbound.getPraMainUnitNum();
            praTotalOutboundAmount = outbound.getPraTaxAmount().add(outboundProduct.getPraTaxAmount());
            praOutboundAmount = outbound.getPraAmount().add(Calculate.computeNoTaxPrice(outboundProduct.getPraTaxPurchaseAmount(), outboundProduct.getTax())
                    .multiply(BigDecimal.valueOf(outboundProduct.getPraOutboundMainNum())));

            // 设置库存商品变更值
            stockInfoRequest = new StockInfoRequest();
            stockInfoRequest.setCompanyCode(outbound.getCompanyCode());
            stockInfoRequest.setCompanyName(outbound.getCompanyName());
            stockInfoRequest.setTransportCenterCode(outbound.getLogisticsCenterCode());
            stockInfoRequest.setTransportCenterName(outbound.getLogisticsCenterName());
            stockInfoRequest.setWarehouseCode(outbound.getWarehouseCode());
            stockInfoRequest.setWarehouseName(outbound.getWarehouseName());
            stockInfoRequest.setSkuCode(outboundProduct.getSkuCode());
            stockInfoRequest.setSkuName(outboundProduct.getSkuName());
            stockInfoRequest.setChangeCount(actualTotalCount);
            stockInfoRequest.setPreLockCount(outboundProduct.getPreOutboundMainNum());
            stockInfoRequest.setTaxRate(outboundProduct.getTax());
            stockInfoRequest.setNewDelivery(outbound.getSupplierCode());
            stockInfoRequest.setNewDeliveryName(outbound.getSupplierName());
            stockInfoRequest.setDocumentCode(outbound.getOutboundOderCode());
            stockInfoRequest.setDocumentType(Global.OUTBOUND_TYPE);
            stockInfoRequest.setSourceDocumentCode(outbound.getSourceOderCode());
            stockInfoRequest.setSourceDocumentType(typeCode);
            stockInfoRequest.setOperatorId(request.getOperatorId());
            stockInfoRequest.setOperatorName(request.getOperatorName());
            stockInfoRequest.setNewPurchasePrice(outbound.getPraTaxAmount());

            stockInfoRequestList.add(stockInfoRequest);

            // 自动批次管理，新增批次信息
            if(warehouse.getBatchManage().equals(0)){
                BigDecimal amount = outboundProduct.getPreTaxPurchaseAmount() == null ? BigDecimal.ZERO : outboundProduct.getPreTaxPurchaseAmount();
                outboundBatch = new OutboundBatch();
                String batchCode = DateUtils.currentDate().replaceAll("-","");
                outboundBatch.setOutboundOderCode(outbound.getOutboundOderCode());
                outboundBatch.setBatchCode(batchCode);
                String batchInfoCode;
                if(StringUtils.isNotBlank(outbound.getSupplierCode())){
                    batchInfoCode = detail.getSkuCode() + "_" + outbound.getWarehouseCode() + "_" +
                            batchCode + "_" + outbound.getSupplierCode() + "_" +
                            amount.stripTrailingZeros().toPlainString();
                }else {
                    batchInfoCode = detail.getSkuCode() + "_" + outbound.getWarehouseCode() + "_" +
                            batchCode + "_" + amount.stripTrailingZeros().toPlainString();
                }
                outboundBatch.setBatchInfoCode(batchInfoCode);
                outboundBatch.setSkuCode(outboundProduct.getSkuCode());
                outboundBatch.setSkuName(outboundProduct.getSkuName());
                outboundBatch.setSupplierCode(outbound.getSupplierCode());
                outboundBatch.setSupplierName(outbound.getSupplierName());
                outboundBatch.setProductDate(DateUtils.currentDate());
                outboundBatch.setTotalCount(outboundProduct.getPreOutboundMainNum());
                outboundBatch.setActualTotalCount(detail.getActualTotalCount());
                outboundBatch.setLineCode(detail.getLineCode());
                outboundBatch.setCreateById(request.getOperatorId());
                outboundBatch.setCreateByName(request.getOperatorName());
                outboundBatch.setUpdateById(request.getOperatorId());
                outboundBatch.setUpdateByName(request.getOperatorName());
                outboundBatches.add(outboundBatch);
            }
        }
        outbound.setPraOutboundNum(praOutboundCount);
        outbound.setPraMainUnitNum(praTotalOutboundCount);
        outbound.setPraTaxAmount(praTotalOutboundAmount);
        outbound.setPraAmount(praOutboundAmount);
        outbound.setPraTax(praTotalOutboundAmount.subtract(praOutboundAmount));
        changeStockRequest.setStockList(stockInfoRequestList);

        StockBatchInfoRequest stockBatchInfoRequest;
        List<StockBatchInfoRequest> stockBatchVoRequestList = Lists.newArrayList();
        List<OutboundProductCallBackReqVo> productList;
        List<OutboundBatchCallBackReqVo> notStockBatches = Lists.newArrayList();

        // 设置批次扣减（自动批次管理，由批次管理按入库顺序（先入先出）扣减批次库存）
        if (warehouse.getBatchManage().equals(Global.BATCH_MANAGE_0)) {
            // 根据sku分组
            List<OutboundProductCallBackReqVo> details = request.getDetailList().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                    new TreeSet<>(Comparator.comparing(o -> o.getSkuCode()))), ArrayList::new));
            for (OutboundProductCallBackReqVo detail : details) {
                // 正序查询sku的批次库存
                List<StockBatch> batchList = stockBatchDao.stockBatchByReject(detail.getSkuCode(), outbound.getWarehouseCode(), null);
                if (CollectionUtils.isEmpty(batchList) && batchList.size() > 0) {
                    LOGGER.info("wms回传出库单，未查询到sku的批次库存信息：{}", detail.getSkuCode());
                    continue;
                }
                long sum = batchList.stream().mapToLong(StockBatch::getAvailableCount).sum();
                // 查询相同sku的集合
                productList = request.getDetailList().stream().filter(s -> s.getSkuCode().equals(detail.getSkuCode())).collect(Collectors.toList());
                long skuSum = productList.stream().mapToLong(OutboundProductCallBackReqVo::getActualTotalCount).sum();
                if (sum < skuSum) {
                    LOGGER.info("wms回传出库单，sku的实际批次库存总数大于所有sku批次的总库存，无法操作库存:{}", detail.getSkuCode());
                    continue;
                }

                for (OutboundProductCallBackReqVo product : productList) {
                    Long changeCount;
                    for (int i = 0; i <= batchList.size(); i++) {
                        stockBatchInfoRequest = new StockBatchInfoRequest();
                        stockBatchInfoRequest.setTransportCenterCode(batchList.get(i).getTransportCenterCode());
                        stockBatchInfoRequest.setTransportCenterName(batchList.get(i).getTransportCenterName());
                        stockBatchInfoRequest.setBatchInfoCode(batchList.get(i).getBatchInfoCode());
                        stockBatchInfoRequest.setBatchCode(batchList.get(i).getBatchCode());
                        stockBatchInfoRequest.setTaxRate(batchList.get(i).getTaxRate());
                        stockBatchInfoRequest.setProductDate(batchList.get(i).getProductDate());
                        stockBatchInfoRequest.setBeOverdueDate(batchList.get(i).getBeOverdueDate());
                        stockBatchInfoRequest.setBatchRemark(batchList.get(i).getBatchRemark());
                        stockBatchInfoRequest.setCompanyCode(batchList.get(i).getCompanyCode());
                        stockBatchInfoRequest.setCompanyName(batchList.get(i).getCompanyName());
                        stockBatchInfoRequest.setWarehouseCode(batchList.get(i).getWarehouseCode());
                        stockBatchInfoRequest.setWarehouseName(batchList.get(i).getWarehouseName());
                        stockBatchInfoRequest.setSkuCode(product.getSkuCode());
                        stockBatchInfoRequest.setSkuName(product.getSkuName());
                        stockBatchInfoRequest.setDocumentCode(outbound.getOutboundOderCode());
                        stockBatchInfoRequest.setDocumentType(Global.OUTBOUND_TYPE);
                        stockBatchInfoRequest.setSourceDocumentCode(outbound.getSourceOderCode());
                        stockBatchInfoRequest.setSourceDocumentType(typeCode);
                        stockBatchInfoRequest.setOperatorId(request.getOperatorId());
                        stockBatchInfoRequest.setOperatorName(request.getOperatorName());
                        stockBatchInfoRequest.setBatchCode(batchList.get(i).getBatchCode());
                        stockBatchInfoRequest.setSupplierCode(batchList.get(i).getSupplierCode());
                        stockBatchInfoRequest.setTaxCost(batchList.get(i).getTaxCost());
                        if (batchList.get(i).getAvailableCount() >= product.getActualTotalCount()) {
                            changeCount = product.getActualTotalCount();
                            stockBatchInfoRequest.setChangeCount(changeCount);
                            stockBatchVoRequestList.add(stockBatchInfoRequest);
                            break;
                        } else {
                            changeCount = product.getActualTotalCount() - batchList.get(i).getAvailableCount();
                            stockBatchInfoRequest.setChangeCount(changeCount);
                            stockBatchVoRequestList.add(stockBatchInfoRequest);
                        }
                    }
                }
            }
        } else {
            if (CollectionUtils.isEmpty(request.getBatchList()) && request.getBatchList().size() <= 0) {
                LOGGER.info("wms回传出库单，非自动批次管理，未回传批次信息:{}", JsonUtil.toJson(request));
            } else {
                OutboundBatch notOutboundBatch;
                for (OutboundBatchCallBackReqVo batch : request.getBatchList()) {
                    // 判断是否为指定批次
                    Integer exist = 0;
                    if(warehouse.getBatchManage().equals(Global.BATCH_MANAGE_2)){
                        exist = productSkuBatchMapper.productSkuBatchExist(batch.getSkuCode(), warehouse.getWarehouseCode());
                    }

                    // 指定批次
                    if(warehouse.getBatchManage().equals(Global.BATCH_MANAGE_1) || exist > 0){
                        // 先根据退货单号，批次号，sku查询批次信息
                        outboundBatch = outboundBatchDao.selectBatchInfoByLineCode(outbound.getOutboundOderCode(), batch.getBatchCode(), batch.getLineCode(), batch.getSkuCode());
                        if (outboundBatch == null) {
                            // 以上为空时，根据sku，退货单去查询
                            outboundBatch = outboundBatchDao.selectBatchInfoByLineCode(outbound.getOutboundOderCode(), null, batch.getLineCode(), batch.getSkuCode());
                        }

                        if (outboundBatch != null) {
                            outboundBatch.setActualTotalCount(batch.getActualTotalCount());
                            Integer k = outboundBatchDao.update(outboundBatch);
                            LOGGER.info("更新出库单批次信息：{}", k);
                        }else {
                            notOutboundBatch = BeanCopyUtils.copy(batch, OutboundBatch.class);
                            notOutboundBatches.add(notOutboundBatch);
                            LOGGER.info("出库单指定批次未查询到的出库批次信息：{}",  JsonUtil.toJson(notOutboundBatches));
                        }
                    }else {
                        // 先根据退货单号，批次号，sku查询批次信息
                        outboundBatch = outboundBatchDao.selectBatchInfoByLineCode(outbound.getOutboundOderCode(), batch.getBatchCode(), batch.getLineCode(), batch.getSkuCode());
                        if (outboundBatch == null) {
                            // 以上为空时，根据sku，退货单去查询
                            outboundBatch = outboundBatchDao.selectBatchInfoByLineCode(outbound.getOutboundOderCode(), null, batch.getLineCode(), batch.getSkuCode());
                        }
                        if(outboundBatch != null){
                            outboundBatch.setActualTotalCount(batch.getActualTotalCount());
                            Integer k = outboundBatchDao.update(outboundBatch);
                            LOGGER.info("更新出库单批次信息：{}", k);
                        }else {
                            // 出库单非指定批次
                            List<StockBatch> batches = stockBatchDao.stockBatchByOutbound(batch.getSkuCode(), warehouse.getWarehouseCode(), batch.getBatchCode());
                            BigDecimal amount = BigDecimal.ZERO;
                            String supplierCode = null;
                            if (CollectionUtils.isNotEmpty(batches) && batches.size() > 0) {
                                amount = batches.get(0).getTaxCost() == null ? BigDecimal.ZERO : batches.get(0).getTaxCost();
                                if(StringUtils.isNotBlank(batches.get(0).getSupplierCode())){
                                    supplierCode = batches.get(0).getSupplierCode();
                                }
                            }
                            // 新增出库单的批次信息
                            outboundBatch = BeanCopyUtils.copy(batch, OutboundBatch.class);
                            outboundBatch.setOutboundOderCode(outbound.getOutboundOderCode());
                            String batchInfoCode;
                            if(StringUtils.isBlank(supplierCode)){
                                supplierCode = outbound.getSupplierCode();
                            }

                            if (StringUtils.isNotBlank(supplierCode)) {
                                batchInfoCode = batch.getSkuCode() + "_" + outbound.getWarehouseCode() + "_" +
                                        batch.getBatchCode() + "_" + outbound.getSupplierCode() + "_" +
                                        amount.stripTrailingZeros().toPlainString();
                            } else {
                                batchInfoCode = batch.getSkuCode() + "_" + outbound.getWarehouseCode() + "_" +
                                        batch.getBatchCode() + "_" + amount.stripTrailingZeros().toPlainString();
                            }
                            outboundBatch.setBatchInfoCode(batchInfoCode);
                            outboundBatch.setSupplierCode(outbound.getSupplierCode());
                            outboundBatch.setSupplierName(outbound.getSupplierName());
                            outboundBatch.setTotalCount(batch.getActualTotalCount());
                            outboundBatch.setCreateById(request.getOperatorId());
                            outboundBatch.setCreateByName(request.getOperatorName());
                            outboundBatch.setUpdateById(request.getOperatorId());
                            outboundBatch.setUpdateByName(request.getOperatorName());
                            outboundBatches.add(outboundBatch);
                        }
                    }

                    // 查询批次数据
                    List<StockBatch> batchList = stockBatchDao.stockBatchByOutbound(batch.getSkuCode(), outbound.getWarehouseCode(), batch.getBatchCode());
                    if (CollectionUtils.isEmpty(batchList) && batchList.size() <= 0) {
                        notStockBatches.add(batch);
                        LOGGER.info("wms回传出库单，未查询到sku的批次库存信息:{}", batch.getSkuCode());
                    }else {
                        long sum = batchList.stream().mapToLong(StockBatch::getInventoryCount).sum();
                        // 查询相同sku的集合
                        if (sum < batch.getActualTotalCount()) {
                            notStockBatches.add(batch);
                            LOGGER.info("wms回传出库单，sku的实际批次库存总数大于所有sku批次的总库存:{}", batch.getSkuCode());
                        }else {
                            Long changeCount;
                            for (int i = 0; i <= batchList.size(); i++) {
                                stockBatchInfoRequest = new StockBatchInfoRequest();
                                stockBatchInfoRequest.setSkuBatchManage(Global.WAREHOUSE_BATCH_MANAGE_SKU_1);
                                stockBatchInfoRequest.setTransportCenterCode(batchList.get(i).getTransportCenterCode());
                                stockBatchInfoRequest.setTransportCenterName(batchList.get(i).getTransportCenterName());
                                stockBatchInfoRequest.setBatchInfoCode(batchList.get(i).getBatchInfoCode());
                                stockBatchInfoRequest.setBatchCode(batchList.get(i).getBatchCode());
                                stockBatchInfoRequest.setTaxRate(batchList.get(i).getTaxRate());
                                stockBatchInfoRequest.setProductDate(batchList.get(i).getProductDate());
                                stockBatchInfoRequest.setBeOverdueDate(batchList.get(i).getBeOverdueDate());
                                stockBatchInfoRequest.setBatchRemark(batchList.get(i).getBatchRemark());
                                stockBatchInfoRequest.setCompanyCode(batchList.get(i).getCompanyCode());
                                stockBatchInfoRequest.setCompanyName(batchList.get(i).getCompanyName());
                                stockBatchInfoRequest.setWarehouseCode(batchList.get(i).getWarehouseCode());
                                stockBatchInfoRequest.setWarehouseName(batchList.get(i).getWarehouseName());
                                stockBatchInfoRequest.setSkuCode(batch.getSkuCode());
                                stockBatchInfoRequest.setSkuName(batch.getSkuName());
                                stockBatchInfoRequest.setDocumentCode(outbound.getOutboundOderCode());
                                stockBatchInfoRequest.setDocumentType(Global.OUTBOUND_TYPE);
                                stockBatchInfoRequest.setSourceDocumentCode(outbound.getSourceOderCode());
                                stockBatchInfoRequest.setSourceDocumentType(typeCode);
                                stockBatchInfoRequest.setOperatorId(request.getOperatorId());
                                stockBatchInfoRequest.setOperatorName(request.getOperatorName());
                                stockBatchInfoRequest.setBatchCode(batchList.get(i).getBatchCode());
                                stockBatchInfoRequest.setSupplierCode(batchList.get(i).getSupplierCode());
                                stockBatchInfoRequest.setTaxCost(batchList.get(i).getTaxCost());

                                if (batchList.get(i).getInventoryCount() >= batch.getActualTotalCount() &&
                                        batchList.get(i).getAvailableCount() >= batch.getActualTotalCount()) {
                                    changeCount = batch.getActualTotalCount();
                                    stockBatchInfoRequest.setChangeCount(changeCount);
                                    stockBatchVoRequestList.add(stockBatchInfoRequest);
                                    break;
                                } else {
                                    changeCount = batch.getActualTotalCount() - batchList.get(i).getAvailableCount();
                                    stockBatchInfoRequest.setChangeCount(changeCount);
                                    stockBatchVoRequestList.add(stockBatchInfoRequest);
                                }
                            }
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(notStockBatches) && notStockBatches.size() > 0){
                    LOGGER.info("wms出库单回传，非指定批次模式未查询到批次库存的信息：{}", JsonUtil.toJson(notStockBatches));
                }
            }
        }
        if(CollectionUtils.isNotEmpty(stockBatchVoRequestList) && stockBatchVoRequestList.size() > 0){
            changeStockRequest.setStockBatchList(stockBatchVoRequestList);
        }
        LOGGER.info("wms回传出库单，操作库存、批次库存的参数:{}", JsonUtil.toJson(changeStockRequest));

        // 调用库存操作接口
        HttpResponse httpResponse = stockService.stockAndBatchChange(changeStockRequest);
        if (httpResponse.getCode().equals(MsgStatus.SUCCESS)) {
            LOGGER.info("回传wms出库单，操作库存、批次库存成功");
        } else {
            LOGGER.error("回传wms出库单，操作库存、批次库存失败：{}", httpResponse.getMessage());
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, httpResponse.getMessage()));
        }

        // 修改实际的入库单主表
        Integer k = outboundDao.update(outbound);
        LOGGER.error("wms回传出库单主表的实际值变更：{}", k);

        if(CollectionUtils.isNotEmpty(outboundBatches) && outboundBatches.size() > 0){
            Integer count = outboundBatchDao.insertAll(outboundBatches);
            LOGGER.error("wms回传出库单，新增出库单批次信息：{}", count);
        }

        // 保存出库单日志
        productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.RETURN_OUTBOUND_ODER.getStatus(),
                ObjectTypeCode.OUTBOUND_ODER.getStatus(), outbound, HandleTypeCoce.RETURN_OUTBOUND_ODER.getName(), new Date(),
                outbound.getCreateBy(), null);

        // 回传业务单据
        request.setBatchManage(warehouse.getBatchManage());
        returnSource(outbound.getId(), request);
        return HttpResponse.success();
    }

    /**
     * 根据类型回传给来源单号状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async("myTaskAsyncPool")
    public void returnSource(Long id, OutboundCallBackReqVo requestVo){
        // 查询出库单信息
        Outbound outbound = outboundDao.selectByPrimaryKey(id);
        List<OutboundProduct> list = outboundProductDao.selectByOutboundOderCode(outbound.getOutboundOderCode());
        List<OutboundBatch> batchList = outboundBatchDao.selectByOutboundBatchOderCode(outbound.getOutboundOderCode());

        // 添加出库完成日志
        productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.COMPLETE_OUTBOUND_ODER.getStatus(),
                ObjectTypeCode.OUTBOUND_ODER.getStatus(), id, HandleTypeCoce.COMPLETE_OUTBOUND_ODER.getName(), new Date(),
                outbound.getCreateBy(), null);

        outbound.setOutboundTime(Calendar.getInstance().getTime());
        outbound.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        outbound.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode() )){
            LOGGER.info("wms回传成功，根据出库单信息，变更对应销售单的实际值：", outbound.getSourceOderCode());
            OutboundCallBackRequest request = new OutboundCallBackRequest();
            request.setOderCode(outbound.getSourceOderCode());
            request.setDeliveryTime(outbound.getOutboundTime());
            request.setDeliveryPerson(requestVo.getOperatorName());
            request.setPersonId(requestVo.getOperatorId());
            request.setPersonName(outbound.getUpdateBy());
            request.setActualTotalCount(outbound.getPraMainUnitNum());
            request.setBatchManage(requestVo.getBatchManage());
            List<OutboundCallBackDetailRequest> orderItems = new ArrayList<>();
            for (OutboundProduct op : list) {
                OutboundCallBackDetailRequest orderItem = new OutboundCallBackDetailRequest();
                orderItem.setSkuCode(op.getSkuCode());
                orderItem.setSkuName(op.getSkuName());
                orderItem.setActualProductCount(op.getPraOutboundMainNum());
                orderItem.setLineCode(op.getLinenum());
                orderItems.add(orderItem);
            }
            List<OutboundProductCallBackReqVo> detailList = requestVo.getDetailList();
            Map<String, OutboundProductCallBackReqVo> skuMap = detailList.stream().collect(Collectors.toMap(OutboundProductCallBackReqVo::getSkuCode, input -> input, (k1, k2) -> k1));
            for (OutboundCallBackDetailRequest outboundCallBackDetailRequest : orderItems) {
                if (skuMap.containsKey(outboundCallBackDetailRequest.getSkuCode())) {
                    outboundCallBackDetailRequest.setUniqueCode(skuMap.get(outboundCallBackDetailRequest.getSkuCode()).getUniqueCode());
                }
            }
            request.setDetailList(orderItems);
            if(CollectionUtils.isNotEmpty(batchList)){
                List<OutboundCallBackBatchRequest> infoBatch = BeanCopyUtils.copyList(batchList, OutboundCallBackBatchRequest.class);
                request.setBatchList(infoBatch);
            }

            HttpResponse httpResponse = orderCallbackService.outboundOrderCallBack(request);
        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())) {
            // 退供
            try {
                LOGGER.info("wms回传成功，根据出库单信息更改对应退供单的实际值：{}", JsonUtil.toJson(outbound));
                RejectStockRequest rejectStockRequest = new RejectStockRequest();
                RejectDetailStockRequest rejectDetailStockRequest = new RejectDetailStockRequest();
                List<RejectDetailStockRequest> rejectDetailStockRequests = new ArrayList<>();
                rejectStockRequest.setRejectRecordCode(outbound.getSourceOderCode());
                for (OutboundProduct outboundProduct : list) {
                    rejectDetailStockRequest.setLineCode(outboundProduct.getLinenum().intValue());
                    rejectDetailStockRequest.setActualCount(outboundProduct.getPraOutboundMainNum());
                    rejectDetailStockRequest.setActualAmount(outboundProduct.getPraTaxAmount());
                    rejectDetailStockRequests.add(rejectDetailStockRequest);
                }
                rejectStockRequest.setDetailList(rejectDetailStockRequests);
                if(CollectionUtils.isNotEmpty(batchList) && batchList.size() > 0){
                    List<RejectRecordBatch> infoBatch = BeanCopyUtils.copyList(batchList, RejectRecordBatch.class);
                    rejectStockRequest.setBatchList(infoBatch);
                }
                // 回传给退供
                rejectStockRequest.setBatchManage(requestVo.getBatchManage());
                goodsRejectService.rejectRecordWms(rejectStockRequest);
            }catch (Exception e){
                LOGGER.error(Global.ERROR, e.getMessage());
                throw new GroundRuntimeException("wms回传退供单失败");
            }
        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode() )){
            // 调拨
            LOGGER.info("wms回传成功，根据出库单信息，变更对应调拨单的实际值：", outbound.getSourceOderCode());
            try {
                AllocationRequest request = new AllocationRequest();
                request.setAllocationCode(outbound.getSourceOderCode());
                List<AllocationDetailRequest> detailList = new ArrayList<>();
                for (OutboundProduct outboundProduct : list) {
                    AllocationDetailRequest allocationDetailRequest = new AllocationDetailRequest();
                    allocationDetailRequest.setLineCode(outboundProduct.getLinenum().intValue());
                    allocationDetailRequest.setActualCount(outboundProduct.getPraOutboundMainNum());
                    allocationDetailRequest.setActualAmount(outboundProduct.getPraTaxAmount());
                    detailList.add(allocationDetailRequest);
                }
                request.setDetailList(detailList);
                if(CollectionUtils.isNotEmpty(batchList)){
                    List<AllocationBatchRequest> infoBatch = BeanCopyUtils.copyList(batchList, AllocationBatchRequest.class);
                    request.setBatchList(infoBatch);
                }
                // 回传给调拨
                allocationService.allocationWms(request);
                // 调拨出库完成调用调拨入库 生成调拨入库单 调用wms
                Allocation allocation = allocationMapper.selectByCode(outbound.getSourceOderCode());
                createInbound(allocation.getFormNo());
                allocationService.updateWmsStatus(allocation.getAllocationCode());
            } catch (Exception e) {
                log.error(Global.ERROR, e);
                throw new GroundRuntimeException("调拨单更改出库状态失败");
            }
        }else{
            LOGGER.info("wms出库单回传无法匹配回传类型:{}", JsonUtil.toJson(outbound));
            throw new GroundRuntimeException("出库单回传无法匹配回传类型");
        }
        Integer k = outboundDao.update(outbound);
        LOGGER.info("wms回传出库单成功，变更出库单完成状态：{}", k);
    }

    /**
     * 回传订单接口
     * @param reqVO
     */
    @Override
    @Async("myTaskAsyncPool")
    public void returnOder(SupplyOrderInfoReqVO reqVO) {
//        String url = urlConfig.PURCHASE_URL+"/purchase/order/outBoundCallBack";
//        try {
//            HttpClient client = HttpClientHelper.getCurrentClient(HttpClient.post(url).json(reqVO));
//            HttpResponse  result = client.action().result(HttpResponse.class);
//            if(!Objects.equals(result.getCode(), MsgStatus.SUCCESS)){
//                log.error("出库单回传订单失败：[{}]",reqVO);
//              throw  new GroundRuntimeException("调用采购服务失败");
//            }
//        } catch (GroundRuntimeException e) {
//            log.error(Global.ERROR, e);
//            log.error("出库单回传订单失败：[{}]",reqVO);
//        }

    }

    @Override
    public String createInbound(String id) {
        try {
            AllocationToOutboundVo allocationResVo =  new AllocationToOutboundVo();
            AllocationDTO allocation = allocationMapper.selectByFormNO1(id);
            BeanCopyUtils.copy(allocation, allocationResVo);
//            productCommonService.getInstance(allocation.getAllocationCode()+"", HandleTypeCoce.INBOUND_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),id ,HandleTypeCoce.INBOUND_ALLOCATION.getName());

            List<AllocationProductToOutboundVo> list = allocationProductBatchMapper.selectByPictureUrlAllocationCode(allocation.getAllocationCode());
            allocationResVo.setSkuList(list);
            // 转化成出库单
//            InboundReqSave convert =  new AllocationResVo2InboundReqVoConverter(warehouseService).convert(allocationResVo);
            AllocationTypeEnum enumByType = AllocationTypeEnum.getAllocationTypeEnumByType(allocation.getAllocationType());
            LOGGER.info("调拨单数据转入库单参数{}", JsonUtil.toJson(allocation));
            InboundReqSave convert1 = new AllocationOrderToInboundConverter(warehouseService, enumByType,productSkuPicturesDao).convert(allocation);
            LOGGER.info("保存入库单数据参数{}", JsonUtil.toJson(convert1));
            String inboundOderCode = inboundService.saveInbound(convert1);
            //更改调拨在途数

            // 调用锁定库存数
            ChangeStockRequest stockChangeRequest = new ChangeStockRequest();
            stockChangeRequest.setOperationType(7);
            List<StockInfoRequest> list1 = new ArrayList<>();
            for (InboundProductReqVo allocationProduct : convert1.getList()) {
                StockInfoRequest stockVoRequest = new StockInfoRequest();
                // 设置公司名称编码
                stockVoRequest.setCompanyCode(allocation.getCompanyCode());
                stockVoRequest.setCompanyName(allocation.getCompanyName());
                // 设置物流中心名称编码
                //如果改在途数，需要设置为入库的仓库
                stockVoRequest.setTransportCenterCode(allocation.getCallInLogisticsCenterCode());
                stockVoRequest.setTransportCenterName(allocation.getCallInLogisticsCenterName());
                //设置库房名称编码
                stockVoRequest.setWarehouseCode(allocation.getCallInWarehouseCode());
                stockVoRequest.setWarehouseName(allocation.getCallInWarehouseName());
                //设置sku编号名称
                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
                stockVoRequest.setSkuName(allocationProduct.getSkuName());
                stockVoRequest.setChangeCount(allocationProduct.getPreInboundMainNum());
                //设置类型
                stockVoRequest.setDocumentType(AllocationTypeEnum.getAll().get(allocation.getAllocationType()).getLockType());
                stockVoRequest.setDocumentCode(allocation.getAllocationCode());
                stockVoRequest.setSourceDocumentCode(allocation.getAllocationCode());
                stockVoRequest.setSourceDocumentType(AllocationTypeEnum.getAll().get(allocation.getAllocationType()).getLockType());
                stockVoRequest.setOperatorName(allocation.getUpdateBy());
                stockVoRequest.setRemark(allocation.getRemark());
                list1.add(stockVoRequest);
            }
            WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(convert1.getWarehouseCode());
            List<StockBatchInfoRequest> batchList1 = new ArrayList<>();
            if(warehouse.getBatchManage().equals(Global.BATCH_MANAGE_1)){
                for (AllocationProductToOutboundVo allocationProduct : list) {
                    StockBatchInfoRequest stockBatchInfoRequest = new StockBatchInfoRequest();
                    stockBatchInfoRequest.setCompanyCode(allocation.getCompanyCode());
                    stockBatchInfoRequest.setCompanyName(allocation.getCompanyName());
                    stockBatchInfoRequest.setTransportCenterCode(allocation.getCallInLogisticsCenterCode());
                    stockBatchInfoRequest.setTransportCenterName(allocation.getCallInLogisticsCenterName());
                    stockBatchInfoRequest.setWarehouseCode(allocation.getCallInWarehouseCode());
                    stockBatchInfoRequest.setWarehouseName(allocation.getCallInWarehouseName());
                    stockBatchInfoRequest.setSkuCode(allocationProduct.getSkuCode());
                    stockBatchInfoRequest.setSkuName(allocationProduct.getSkuName());
                    stockBatchInfoRequest.setChangeCount(allocationProduct.getQuantity());
                    stockBatchInfoRequest.setBatchCode(allocationProduct.getCallinBatchNumber());
                    stockBatchInfoRequest.setBatchInfoCode(allocationProduct.getCallinBatchInfoCode());
                    stockBatchInfoRequest.setProductDate(allocationProduct.getProductDate());
                    stockBatchInfoRequest.setBeOverdueDate(allocationProduct.getBeOverdueDate());
                    stockBatchInfoRequest.setBatchRemark(allocationProduct.getBatchNumberRemark());
                    stockBatchInfoRequest.setDocumentType(AllocationTypeEnum.getAll().get(allocation.getAllocationType()).getLockType());
                    stockBatchInfoRequest.setDocumentCode(allocation.getAllocationCode());
                    stockBatchInfoRequest.setSourceDocumentCode(allocation.getAllocationCode());
                    stockBatchInfoRequest.setSourceDocumentType(AllocationTypeEnum.getAll().get(allocation.getAllocationType()).getLockType());
                    stockBatchInfoRequest.setOperatorName(allocation.getUpdateBy());
                }
            }

            stockChangeRequest.setStockList(list1);
            stockChangeRequest.setStockBatchList(batchList1);
            LOGGER.error("wms调拨回调:调用库存加在途: 参数{}", JsonUtil.toJson(stockChangeRequest));
            HttpResponse httpResponse= stockService.stockAndBatchChange(stockChangeRequest);
//            StockChangeRequest stockChangeRequest = new StockChangeRequest();
//            stockChangeRequest.setOperationType(7);
//            stockChangeRequest.setOrderCode(allocation.getAllocationCode());
            // stockChangeRequest.setOrderType();
//            List<StockVoRequest> list1 = new ArrayList<>();
//            for (AllocationProductToOutboundVo allocationProduct : list) {
//                StockVoRequest stockVoRequest = new StockVoRequest();
//                // 设置公司名称编码
//                stockVoRequest.setCompanyCode(allocation.getCompanyCode());
//                stockVoRequest.setCompanyName(allocation.getCompanyName());
//                // 设置物流中心名称编码
//                //如果改在途数，需要设置为入库的仓库
//                stockVoRequest.setTransportCenterCode(allocation.getCallInLogisticsCenterCode());
//                stockVoRequest.setTransportCenterName(allocation.getCallInLogisticsCenterName());
//                //设置库房名称编码
//                stockVoRequest.setWarehouseCode(allocation.getCallInWarehouseCode());
//                stockVoRequest.setWarehouseName(allocation.getCallInWarehouseName());
//                //设置采购组编码名称
//                stockVoRequest.setPurchaseGroupCode(allocation.getPurchaseGroupCode());
//                stockVoRequest.setPurchaseGroupName(allocation.getPurchaseGroupName());
//                //设置sku编号名称
//                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
//                stockVoRequest.setSkuName(allocationProduct.getSkuName());
//                stockVoRequest.setChangeNum(allocationProduct.getQuantity());
//                //设置类型
//                stockVoRequest.setDocumentType(AllocationTypeEnum.getAll().get(allocation.getAllocationType()).getLockType());
//                stockVoRequest.setDocumentNum(allocation.getAllocationCode());
//                stockVoRequest.setOperator(allocation.getUpdateBy());
//                stockVoRequest.setRemark(allocation.getRemark());
//                list1.add(stockVoRequest);
//            }
//            stockChangeRequest.setStockVoRequests(list1);
            // 调用锁定库存数
//            HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){
                supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.INBOUND_ALLOCATION.getName(), null, HandleTypeCoce.ADD_ALLOCATION.getName(), "系统自动");
            }else{
                log.error("wms调拨回调:调用库存加在途,库存操作失败", httpResponse.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
            allocation.setInboundOderCode(inboundOderCode);
            allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_INBOUND.getStatus());
            allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_INBOUND.getName());
            allocationMapper.updateByPrimaryKeySelective(allocation);
            return inboundOderCode;
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw  new GroundRuntimeException("保存出库单失败");
        }
    }

    /**
     * 移库生成入库单并且改变在途数
     * @param id
     */
    @Override
    public void movementCreateInbound(String id) {
            AllocationToOutboundVo allocationResVo =  new AllocationToOutboundVo();
//            AllocationDTO allocation = allocationMapper.selectByFormNO1(id);

            AllocationDTO allocation = allocationMapper.selectById(Long.parseLong(id));
            BeanCopyUtils.copy(allocation, allocationResVo);
//            productCommonService.getInstance(allocation.getAllocationCode()+"", HandleTypeCoce.INBOUND_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),id ,HandleTypeCoce.INBOUND_ALLOCATION.getName());
//
//            List<AllocationProductToOutboundVo> list = allocationProductBatchMapper.selectByPictureUrlAllocationCode(allocation.getAllocationCode());
//            allocationResVo.setSkuList(list);
            // 转化成出库单
//            InboundReqSave convert =  new AllocationResVo2InboundReqVoConverter(warehouseService).convert(allocationResVo);
            AllocationTypeEnum enumByType = AllocationTypeEnum.getAllocationTypeEnumByType(allocation.getAllocationType());
            InboundReqSave convert1 = new AllocationOrderToInboundConverter(warehouseService, enumByType,productSkuPicturesDao).convert(allocation);
            String inboundOderCode = inboundService.saveInbound(convert1);
            //更改调拨在途数
            allocation.setInboundOderCode(inboundOderCode);
            allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_INBOUND.getStatus());
            allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_INBOUND.getName());
            allocationMapper.updateByPrimaryKeySelective(allocation);

//            StockChangeRequest stockChangeRequest = new StockChangeRequest();
//            stockChangeRequest.setOperationType(7);
//            stockChangeRequest.setOrderCode(allocation.getAllocationCode());
//            // stockChangeRequest.setOrderType();
//            List<StockVoRequest> list1 = new ArrayList<>();
//            for (AllocationProductToOutboundVo allocationProduct : list) {
//                StockVoRequest stockVoRequest = new StockVoRequest();
//                // 设置公司名称编码
//                stockVoRequest.setCompanyCode(allocation.getCompanyCode());
//                stockVoRequest.setCompanyName(allocation.getCompanyName());
//                // 设置物流中心名称编码
//                //如果改在途数，需要设置为入库的仓库
//                stockVoRequest.setTransportCenterCode(allocation.getCallInLogisticsCenterCode());
//                stockVoRequest.setTransportCenterName(allocation.getCallInLogisticsCenterName());
//                //设置库房名称编码
//                stockVoRequest.setWarehouseCode(allocation.getCallInWarehouseCode());
//                stockVoRequest.setWarehouseName(allocation.getCallInWarehouseName());
//                //设置采购组编码名称
//                stockVoRequest.setPurchaseGroupCode(allocation.getPurchaseGroupCode());
//                stockVoRequest.setPurchaseGroupName(allocation.getPurchaseGroupName());
//                //设置sku编号名称
//                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
//                stockVoRequest.setSkuName(allocationProduct.getSkuName());
//                stockVoRequest.setChangeNum(allocationProduct.getQuantity());
//                //设置类型
//                stockVoRequest.setDocumentType(AllocationTypeEnum.getAll().get(allocation.getAllocationType()).getLockType());
//                stockVoRequest.setDocumentNum(allocation.getAllocationCode());
//                stockVoRequest.setOperator(allocation.getUpdateBy());
//                stockVoRequest.setRemark(allocation.getRemark());
//                list1.add(stockVoRequest);
//            }
//            stockChangeRequest.setStockVoRequests(list1);
//            // 调用锁定库存数
//            HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
//            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){
//                supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.INBOUND_MOVEMENT.getName(), null, HandleTypeCoce.ADD_MOVEMENT.getName(), "系统自动");
//            }else{
//                log.error(httpResponse.getMessage());
//                throw  new GroundRuntimeException("库存操作失败");
//            }
    }
    
    @Override
    public HttpResponse selectOutboundBatchInfoByOutboundOderCode(OutboundBatch outboundBatch){
        try{
            List<OutboundBatch> outboundBatchList = outboundBatchDao.selectOutboundBatchInfoByOutboundOderCode(outboundBatch);
            Integer total = outboundBatchDao.countOutboundBatchInfoByOutboundOderCode(outboundBatch.getOutboundOderCode());
            return HttpResponse.success(new PageResData<>(total, outboundBatchList));
        }catch (Exception e){
            log.error("根据出库单号查询出库商品批次详情失败", e);
            throw new GroundRuntimeException("根据出库单号查询出库商品批次详情失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveList(List<OutboundReqVo> outboundReqVoList) {
        //数据处理
        outboundReqVoList = dealData(outboundReqVoList);
        //保存数据
        List<Outbound> outbounds = BeanCopyUtils.copyList(outboundReqVoList, Outbound.class);
        List<OutboundProduct> productList = Lists.newArrayList();
        List<OutboundBatch> batchList = Lists.newArrayList();
        for (OutboundReqVo outboundReqVo : outboundReqVoList) {
            productList.addAll(BeanCopyUtils.copyList(outboundReqVo.getList(), OutboundProduct.class));
            if(CollectionUtils.isNotEmpty(batchList)){
                batchList.addAll(BeanCopyUtils.copyList(outboundReqVo.getOutboundBatches(), OutboundBatch.class));
            }
        }
        saveData(outbounds,productList,batchList);
        //TODo 保存日志
        //推送订单到wms
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(List<Outbound> list, List<OutboundProduct> productList, List<OutboundBatch> batchList) {
        int i = outboundDao.insertBatch(list);
        if(i!=list.size()){
            throw new BizException(ResultCode.SAVE_OUT_BOUND_FAILED);
        }
        int i1 = outboundProductDao.insertAll(productList);
        if(i1!=productList.size()){
            throw new BizException(ResultCode.SAVE_OUT_BOUND_PRODUCT_FAILED);
        }
        if (CollectionUtils.isNotEmpty(batchList)){
            Integer integer = outboundBatchDao.insertAll(batchList);
            if(Objects.isNull(integer)||integer!=batchList.size()){
                throw new BizException(ResultCode.SAVE_OUT_BOUND_BATCH_FAILED);
            }
        }

    }
    /**
     * 补充出库单数据
     * @author NullPointException
     * @date 2019/6/26
     * @param outboundReqVoList
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo>
     */
    private List<OutboundReqVo> dealData(List<OutboundReqVo> outboundReqVoList) {
        if(CollectionUtils.isEmpty(outboundReqVoList)){
            throw new BizException(ResultCode.OUTBOUND_DATA_CAN_NOT_BE_NULL);
        }
        synchronized (OutboundServiceImpl.class) {
            for (OutboundReqVo outboundReqVo : outboundReqVoList) {
                String ck = getCode("ck", EncodingRuleType.OUT_BOUND_CODE);
                outboundReqVo.setOutboundOderCode(ck);
                outboundReqVo.getList().forEach(o -> o.setOutboundOderCode(ck));
                outboundReqVo.getOutboundBatches().forEach(o -> o.setOutboundOderCode(ck));
            }
        }
        return outboundReqVoList;
    }

}
