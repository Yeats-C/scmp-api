package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.converter.OrderVo2OutBoundConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.ReturnSupply2outboundSaveConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.allocation.AllocationOrderToInboundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationToOutboundVo;
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
import com.aiqin.bms.scmp.api.product.domain.response.wms.BatchInfo;
import com.aiqin.bms.scmp.api.product.domain.response.wms.PurchaseOutboundDetailSource;
import com.aiqin.bms.scmp.api.product.domain.response.wms.PurchaseOutboundSource;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuStockInfoMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.dao.RejectRecordDao;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordBatch;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectDetailStockRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectStockRequest;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
            //OutboundServiceImpl outboundService = (OutboundServiceImpl) AopContext.currentProxy();
            //outboundService.pushWms(outbound.getOutboundOderCode());
            // 跟新数据库状态
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
        List<OutboundBatch> batchList = outboundBatchDao.selectByOutboundBatchOderCode(outboundOderCode);
        if(CollectionUtils.isEmpty(batchList)){
            LOGGER.info("调用WMS-退供的出库单批次信息为空:", outboundOderCode);
            return HttpResponse.failure(ResultCode.OUTBOUND_BATCH_INFO_NULL);
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
            detailSource.setStockUnitCode(product.getUnitCode());
            detailSource.setStockUnitName(product.getUnitName());
            detailSource.setModelNumber(product.getModel());
            detailSource.setSkuBarCode(product.getBarCode());
            detailSourceList.add(detailSource);
        }
        outboundSource.setDetailList(detailSourceList);
        List<BatchInfo> batchInfoList = BeanCopyUtils.copyList(batchList, BatchInfo.class);
        outboundSource.setBatchInfo(batchInfoList);
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
            LOGGER.info("出库单号---------------------：" + outboundOderCode);
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
    public HttpResponse workFlowCallBack(OutboundCallBackReqVo request) {
        LOGGER.info("出库单回调WMS开始，传入实体为：{}", JsonUtil.toJson(request));
         // 根据入库单编号查询出库单
         Outbound outbound = outboundDao.selectByCode(request.getOutboundOderCode());
         if (outbound == null) {
             LOGGER.info("WMS出库单回传，出库单信息为空：", JsonUtil.toJson(request));
             return HttpResponse.failure(ResultCode.OUTBOUND_DATA_CAN_NOT_BE_NULL);
         }

         if (CollectionUtils.isEmpty(request.getDetailList())) {
             LOGGER.info("WMS出库单回传，出库单商品信息为空：", JsonUtil.toJson(request.getDetailList()));
             return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "WMS出库单回传，出库单商品信息为空"));
         }
         //设置已回传状态
         outbound.setOutboundStatusCode(InOutStatus.RECEIVE_INOUT.getCode());
         outbound.setOutboundStatusName(InOutStatus.RECEIVE_INOUT.getName());
         //设置实际出库数量和出库主数量
         outbound.setPraOutboundNum(0L);
         outbound.setPraMainUnitNum(0L);
         //设置实际含税总金额。税额。不含税总金额
         outbound.setPraTaxAmount(BigDecimal.ZERO);
         outbound.setPraTax(BigDecimal.ZERO);
         outbound.setPraAmount(BigDecimal.ZERO);

         // 操作库存、批次库存    1.退供 2.调拨 3.订单 4.移库
         ChangeStockRequest changeStockRequest = new ChangeStockRequest();
         // 采购与调拨减库存并加在途 - 出库类型编码
         if (Objects.equals(outbound.getOutboundTypeCode(), OutboundTypeEnum.ORDER.getCode()) ||
                 Objects.equals(outbound.getOutboundTypeCode(), OutboundTypeEnum.ALLOCATE.getCode()) ||
                 Objects.equals(outbound.getOutboundTypeCode(), OutboundTypeEnum.MOVEMENT.getCode())) {
             changeStockRequest.setOperationType(2);
         } else if (Objects.equals(outbound.getOutboundTypeCode(), OutboundTypeEnum.RETURN_SUPPLY.getCode())) {
             // 退供wms回传 - 库减并解锁库存、批次库存
             LOGGER.info("退供开始操作库减并解锁库存、批次库存：", outbound.getSourceOderCode());
             changeStockRequest.setOperationType(2);
         }

         List<StockInfoRequest> stockInfoRequestList = Lists.newArrayList();
         OutboundProduct outboundProduct;
         StockInfoRequest stockInfoRequest;
         Long praOutboundCount = 0L, praTotalOutboundCount = 0L;
         BigDecimal praOutboundAmount = BigDecimal.ZERO, praTotalOutboundAmount = BigDecimal.ZERO;

         // 更新出库商品信息
         for (OutboundProductCallBackReqVo detail : request.getDetailList()) {
             // 查询对应的出库单商品信息
             outboundProduct = outboundProductDao.selectByLineCode(outbound.getOutboundOderCode(), detail.getSkuCode(), detail.getLineCode());

             //设置实际数量，实际数量/实际含税单价，实际含税总价
             Long actualTotalCount = detail.getActualTotalCount();
             outboundProduct.setPraOutboundNum(actualTotalCount / Long.valueOf(outboundProduct.getOutboundBaseContent()));
             outboundProduct.setPraOutboundMainNum(actualTotalCount);
             outboundProduct.setPraTaxPurchaseAmount(outboundProduct.getPreTaxPurchaseAmount());
             outboundProduct.setPraTaxAmount(BigDecimal.valueOf(outboundProduct.getPraOutboundMainNum()).multiply(outboundProduct.getPraTaxPurchaseAmount()).
                     setScale(4, BigDecimal.ROUND_HALF_UP));

             // 修改出库单对应的回传数据
             Integer count = outboundProductDao.update(outboundProduct);
             log.info("更新出库单商品信息：" + outboundProduct, count);

             // 计算出库单总的出库数量、主数量/总的含税总金额、税额、不含税总金额
             praOutboundCount += outbound.getPraOutboundNum();
             praTotalOutboundCount += outbound.getPraMainUnitNum();
             praTotalOutboundAmount = outbound.getPraTaxAmount().add(outboundProduct.getPraTaxAmount());
             praOutboundAmount = outbound.getPraAmount().add(Calculate.computeNoTaxPrice(outboundProduct.getPraTaxPurchaseAmount(), outboundProduct.getTax())
                     .multiply(BigDecimal.valueOf(outboundProduct.getPraOutboundMainNum())));

             // 设置修改减少库存sku实体
             stockInfoRequest = new StockInfoRequest();
             stockInfoRequest.setCompanyCode(outbound.getCompanyCode());
             stockInfoRequest.setCompanyName(outbound.getCompanyName());
             stockInfoRequest.setTransportCenterCode(outbound.getLogisticsCenterCode());
             stockInfoRequest.setTransportCenterName(outbound.getLogisticsCenterName());
             stockInfoRequest.setWarehouseCode(outbound.getWarehouseCode());
             stockInfoRequest.setWarehouseName(outbound.getWarehouseName());
             stockInfoRequest.setSkuCode(outboundProduct.getSkuCode());
             stockInfoRequest.setSkuName(outboundProduct.getSkuName());
             stockInfoRequest.setChangeCount(outboundProduct.getPraOutboundNum());
             stockInfoRequest.setTaxRate(outboundProduct.getTax());
             stockInfoRequest.setNewDelivery(outbound.getSupplierCode());
             stockInfoRequest.setNewDeliveryName(outbound.getSupplierName());
             stockInfoRequest.setDocumentCode(outbound.getOutboundOderCode());
             stockInfoRequest.setDocumentType(Global.OUTBOUND_TYPE);
             stockInfoRequest.setSourceDocumentCode(outbound.getSourceOderCode());
             stockInfoRequest.setSourceDocumentType(outbound.getOutboundTypeCode().intValue());
             stockInfoRequest.setOperatorId(request.getOperatorId());
             stockInfoRequest.setOperatorName(request.getOperatorName());
             // 调拨设置减在途数
             if (outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode())) {
                 AllocationProductResVo allocationProductResVo = allocationProductMapper.selectQuantityBySkuCodeAndSource(outbound.getSourceOderCode(), outboundProduct.getSkuCode(), outboundProduct.getLinenum().intValue());
                 stockInfoRequest.setPreWayCount(allocationProductResVo.getQuantity());
             }
             stockInfoRequest.setNewPurchasePrice(outbound.getPraTaxAmount());
             stockInfoRequestList.add(stockInfoRequest);
         }
         outbound.setPraOutboundNum(praOutboundCount);
         outbound.setPraMainUnitNum(praTotalOutboundCount);
         outbound.setPraTaxAmount(praTotalOutboundAmount);
         outbound.setPraAmount(praOutboundAmount);
         outbound.setPraTax(praTotalOutboundAmount.subtract(praOutboundAmount));
         changeStockRequest.setStockList(stockInfoRequestList);

         // 更新出库批次商品信息
         List<StockBatchInfoRequest> stockBatchVoRequestList = Lists.newArrayList();
         OutboundBatch outboundBatch;
         StockBatchInfoRequest stockBatchInfoRequest;
         if (CollectionUtils.isNotEmpty(request.getBatchList())) {
             for (OutboundBatchCallBackReqVo batch : request.getBatchList()) {
                 outboundBatch = outboundBatchDao.selectBatchInfoByLineCode(outbound.getOutboundOderCode(), batch.getBatchInfoCode());

                 //设置实际数量
                 outboundBatch.setActualTotalCount(batch.getActualTotalCount());
                 // 修改单条 批次
                 int k = outboundBatchDao.updateBatchInfoByOutboundOderCodeAndLineNum(outboundBatch);
                 log.info("更新出库单批次信息：" , k);

                 //  设置修改减少库存sku实体
                 stockBatchInfoRequest = new StockBatchInfoRequest();
                 //设置sku编码名称 批次号
                 stockBatchInfoRequest.setSkuCode(outboundBatch.getSkuCode());
                 stockBatchInfoRequest.setSkuName(outboundBatch.getSkuName());
                 stockBatchInfoRequest.setBatchCode(outboundBatch.getBatchCode());
                 stockBatchInfoRequest.setDocumentCode(outbound.getOutboundOderCode());
                 stockBatchInfoRequest.setDocumentType(Global.OUTBOUND_TYPE);
                 stockBatchInfoRequest.setSourceDocumentCode(outbound.getSourceOderCode());
                 stockBatchInfoRequest.setSourceDocumentType(outbound.getOutboundTypeCode().intValue());
                 stockBatchInfoRequest.setOperatorId(request.getOperatorId());
                 stockBatchInfoRequest.setOperatorName(request.getOperatorName());
                 stockBatchInfoRequest.setChangeCount(outboundBatch.getActualTotalCount());
                 stockBatchVoRequestList.add(stockBatchInfoRequest);
             }
             changeStockRequest.setStockBatchList(stockBatchVoRequestList);
         }
         LOGGER.info("回传wms-操作库存、批次库存的参数:{}", JsonUtil.toJson(changeStockRequest));
         // 调用库存操作接口
         HttpResponse httpResponse = stockService.stockAndBatchChange(changeStockRequest);
         if (httpResponse.getCode().equals(MsgStatus.SUCCESS)) {
             LOGGER.info("回传wms操作库存成功，库存详情为:{}", JsonUtil.toJson(changeStockRequest));
         } else {
             LOGGER.error("回传wms操作库存失败", httpResponse.getMessage());
             return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "回传wms操作库存失败"));
         }
         //修改实际的入库单主体
         Integer k = outboundDao.update(outbound);
         LOGGER.error("回传wms-操作出库单实际数量、金额的变更", k);
         // 保存日志
         productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.RETURN_OUTBOUND_ODER.getStatus(),
                 ObjectTypeCode.OUTBOUND_ODER.getStatus(),outbound,HandleTypeCoce.RETURN_OUTBOUND_ODER.getName(),new Date(),
                 outbound.getCreateBy(), null);

         // 回传类型
         returnSource(outbound.getId());
         return HttpResponse.success();
    }

    /**
     * 根据类型回传给来源单号状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async("myTaskAsyncPool")
    public void returnSource(Long id){
        // 查询出库信息
        Outbound outbound = outboundDao.selectByPrimaryKey(id);
        List<OutboundProduct> list = outboundProductDao.selectByOutboundOderCode(outbound.getOutboundOderCode());
        List<OutboundBatch> batchList = outboundBatchDao.selectByOutboundBatchOderCode(outbound.getOutboundOderCode());
        productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.COMPLETE_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(), id, HandleTypeCoce.COMPLETE_OUTBOUND_ODER.getName(), new Date(), outbound.getCreateBy(), null);
        // 设置出库时间
        outbound.setOutboundTime(Calendar.getInstance().getTime());
        // 修改出库单完成状态
        outbound.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        outbound.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        //如果是订单
        if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode() )){
            try {
                SupplyOrderInfoReqVO supplyOrderInfoReqVO = new SupplyOrderInfoReqVO();
                supplyOrderInfoReqVO.setOrderCode(outbound.getSourceOderCode());
                List<SupplyOrderProductItemReqVO> orderItems = BeanCopyUtils.copyList(list,SupplyOrderProductItemReqVO.class);
                supplyOrderInfoReqVO.setOrderItems(orderItems);
                // 调用订单接口
                //returnOder(supplyOrderInfoReqVO);
            }catch (Exception e){
                log.error(Global.ERROR, e);
                log.error(e.getMessage());
                throw new GroundRuntimeException("回传订单失败");
            }
        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())) {
            LOGGER.info("wms回传成功，根据出库单信息，变更对应退供单的实际值：", outbound.getSourceOderCode());
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
            if(CollectionUtils.isNotEmpty(batchList)){
                List<RejectRecordBatch> infoBatch = BeanCopyUtils.copyList(batchList, RejectRecordBatch.class);
                rejectStockRequest.setBatchList(infoBatch);
            }
            // 回传给退供
            goodsRejectService.rejectRecordWms(rejectStockRequest);

        }// 如果是调拨
        else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode() )){

            // 回传给调拨
            try {
                Allocation allocation = allocationMapper.selectByCode(outbound.getSourceOderCode());
                //设置调拨状态
                allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getStatus());
                allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getName());

//                productCommonService.getInstance(allocation.getAllocationCode()+"", HandleTypeCoce.SUCCESS_OUTBOUND_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),allocation.getAllocationCode() ,HandleTypeCoce.SUCCESS_OUTBOUND_ALLOCATION.getName());
                supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.SUCCESS_OUTBOUND_ALLOCATION.getName(), null, HandleTypeCoce.ADD_ALLOCATION.getName(), "系统自动");

                //跟新调拨单状态
                int k = allocationMapper.updateByPrimaryKeySelective(allocation);
                //生成入库单
                createInbound(allocation.getFormNo());
            } catch (Exception e) {
                log.error(Global.ERROR, e);
                throw new GroundRuntimeException("调拨单更改出库状态失败");
            }

        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.MOVEMENT.getCode() )){
            // 如果是移库
            try {
                Allocation allocation = allocationMapper.selectByCode(outbound.getSourceOderCode());
                //设置调拨状态
                allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getStatus());
                allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getName());

//                productCommonService.getInstance(allocation.getAllocationCode(), HandleTypeCoce.SUCCESS_OUT_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),allocation.getAllocationCode() ,HandleTypeCoce.SUCCESS_OUT_MOVEMENT.getName());
                supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.SUCCESS_OUT_MOVEMENT.getName(), null, HandleTypeCoce.ADD_MOVEMENT.getName(), "系统自动");
                //跟新调拨单状态
                int k = allocationMapper.updateByPrimaryKeySelective(allocation);
                //生成入库单
                movementCreateInbound(allocation.getId().toString());
            } catch (Exception e) {
                log.error(Global.ERROR, e);
                throw new GroundRuntimeException("调拨单更改出库状态失败");
            }
        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.scrap.getCode() )){
            // 如果是报废
                Allocation allocation =allocationMapper .selectByCode(outbound.getSourceOderCode());
                //设置调拨状态
                allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_OUTBOUND.getStatus());
                allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_OUTBOUND.getName());

//                productCommonService.getInstance(allocation.getAllocationCode(), HandleTypeCoce.SUCCESS_OUT_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),allocation.getAllocationCode() ,HandleTypeCoce.SUCCESS_OUT_MOVEMENT.getName());
                supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD_SCRAP.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.SUCCESS__SCRAP.getName(), null, HandleTypeCoce.ADD_SCRAP.getName(), "系统自动");
                //跟新调拨单状态
                int k1 = allocationMapper.updateByPrimaryKeySelective(allocation);
        }else{
            throw new GroundRuntimeException("无法回传匹配类型");
        }

        int k = outboundDao.update(outbound);
        LOGGER.info("wms回传成功，变更出库单完成状态：", k);
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
    public void createInbound(String id) {
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
            InboundReqSave convert1 = new AllocationOrderToInboundConverter(warehouseService, enumByType,productSkuPicturesDao).convert(allocation);
            String inboundOderCode = inboundService.saveInbound(convert1);
            //更改调拨在途数

            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOperationType(7);
            stockChangeRequest.setOrderCode(allocation.getAllocationCode());
            // stockChangeRequest.setOrderType();
            List<StockVoRequest> list1 = new ArrayList<>();
            for (AllocationProductToOutboundVo allocationProduct : list) {
                StockVoRequest stockVoRequest = new StockVoRequest();
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
                //设置采购组编码名称
                stockVoRequest.setPurchaseGroupCode(allocation.getPurchaseGroupCode());
                stockVoRequest.setPurchaseGroupName(allocation.getPurchaseGroupName());
                //设置sku编号名称
                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
                stockVoRequest.setSkuName(allocationProduct.getSkuName());
                stockVoRequest.setChangeNum(allocationProduct.getQuantity());
                //设置类型
                stockVoRequest.setDocumentType(AllocationTypeEnum.getAll().get(allocation.getAllocationType()).getLockType());
                stockVoRequest.setDocumentNum(allocation.getAllocationCode());
                stockVoRequest.setOperator(allocation.getUpdateBy());
                stockVoRequest.setRemark(allocation.getRemark());
                list1.add(stockVoRequest);
            }
            stockChangeRequest.setStockVoRequests(list1);
            // 调用锁定库存数
            HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){
                supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.INBOUND_ALLOCATION.getName(), null, HandleTypeCoce.ADD_ALLOCATION.getName(), "系统自动");
            }else{
                log.error(httpResponse.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
            allocation.setInboundOderCode(inboundOderCode);
            allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_INBOUND.getStatus());
            allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_INBOUND.getName());
            allocationMapper.updateByPrimaryKeySelective(allocation);
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
