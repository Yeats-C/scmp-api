package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.InboundBatchDao;
import com.aiqin.bms.scmp.api.product.dao.InboundDao;
import com.aiqin.bms.scmp.api.product.dao.InboundProductDao;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.converter.SupplyReturnOrderMainReqVO2InboundSaveConverter;
import com.aiqin.bms.scmp.api.product.domain.pojo.Allocation;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.*;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderMainReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockBatchInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.ResponseWms;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.wms.BatchInfo;
import com.aiqin.bms.scmp.api.product.domain.response.wms.PurchaseInboundDetailSource;
import com.aiqin.bms.scmp.api.product.domain.response.wms.PurchaseInboundSource;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuDistributionInfoMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderProductDao;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseStorageRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.wms.ReturnOrderChildSourceInit;
import com.aiqin.bms.scmp.api.purchase.domain.request.wms.ReturnOrderPrimarySourceInit;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.bms.scmp.api.purchase.service.impl.GoodsRejectServiceImpl;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.id.IdUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname: InboundServiceImpl
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/4
 * @Version 1.0
 * @Since 1.0
 */
@Service
@Slf4j
public class InboundServiceImpl implements InboundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectServiceImpl.class);

    @Autowired
    private InboundDao inboundDao;
    @Autowired
    private InboundProductDao inboundProductDao;
    @Autowired
    private SkuService skuService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private StockService stockService;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private ProductOperationLogService productOperationLogService;
    @Autowired
    private AllocationMapper allocationMapper;
    @Autowired
    private InboundBatchDao inboundBatchDao;
    @Autowired
    @Lazy(true)
    private PurchaseManageService purchaseManageService;
    @Autowired
    private PurchaseOrderDao purchaseOrderDao;
    @Autowired
    private SupplyCompanyDao supplyCompanyDao;
    @Autowired
    private PurchaseOrderProductDao purchaseOrderProductDao;
    @Autowired
    @Lazy(true)
    private ReturnGoodsService returnGoodsService;
    @Resource
    private SapBaseDataService sapBaseDataService;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private WarehouseDao warehouseDao;
    @Resource
    private ReturnOrderInfoMapper returnOrderInfoDao;
    @Resource
    private ProductSkuDistributionInfoMapper productSkuDistributionInfoMapper;
    @Resource
    private ProductSkuBatchMapper productSkuBatchDao;

    /**
     * 分页查询以及列表搜索
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryInboundResVo> getInboundList(QueryInboundReqVo vo) {
        try {
            PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
            List<Inbound> list = inboundDao.getInboundList(vo);
            BasePage<QueryInboundResVo> basePage = PageUtil.getPageList(vo.getPageNo(),list);
            List<QueryInboundResVo> queryInboundResVoList= BeanCopyUtils.copyList(list,QueryInboundResVo.class);
            basePage.setDataList(queryInboundResVoList);
            return basePage;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("分页查询失败");
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 查询入库信息
     * @param boundRequest
     * @return
     */
    @Override
    public List<InboundResponse> selectInBoundInfoByBoundSearch(BoundRequest boundRequest) {
        try {
            log.info("查询入库信息");
            List<String> inboundOderCodeList = new ArrayList<>();
            List<InboundProduct> inboundProductList = new ArrayList<>();
            InboundResponse inboundResponse = new InboundResponse();
            List<InboundResponse> responseList = new ArrayList<>();
            List<Inbound> inboundList = inboundDao.selectInboundInfoByBoundSearch(boundRequest);
            for (Inbound inbound : inboundList) {
                inboundResponse = new InboundResponse();
                inboundOderCodeList.add(inbound.getInboundOderCode());
                inboundResponse.setInboundTypeCode(Integer.valueOf(inbound.getInboundTypeCode()));
                inboundResponse.setInboundTypeName(inbound.getInboundTypeName());
                inboundResponse.setInboundStatusCode(Integer.valueOf(inbound.getInboundStatusCode()));
                inboundResponse.setInboundStatusName(inbound.getInboundStatusName());
                inboundResponse.setInboundOderCode(inbound.getInboundOderCode());
                responseList.add(inboundResponse);
            }
            if(CollectionUtils.isNotEmpty(inboundOderCodeList)){
                inboundProductList = inboundProductDao.selectInboundProductListByInboundOderCodeList(inboundOderCodeList);
                if (CollectionUtils.isNotEmpty(inboundProductList)) {
                    for (InboundProduct inboundProduct : inboundProductList) {
                        if (CollectionUtils.isNotEmpty(responseList)) {
                            for (InboundResponse response : responseList) {
                                if (response.getInboundOderCode().equals(inboundProduct.getInboundOderCode())) {
                                    inboundResponse.setSkuCode(inboundProduct.getSkuCode());
                                    inboundResponse.setSkuName(inboundProduct.getSkuName());
                                    inboundResponse.setPraInboundNum(inboundProduct.getPraInboundNum());
                                    inboundResponse.setPraInboundMainNum(inboundProduct.getPraInboundMainNum());
                                    inboundResponse.setInboundOderCode(inboundProduct.getInboundOderCode());
                                    inboundResponse.setUpdateBy(inboundProduct.getUpdateBy());
                                    inboundResponse.setUpdateTime(inboundProduct.getUpdateTime());
                                }
                            }
                        }
                    }
                }
            }
            return responseList;
        } catch (Exception e) {
            log.error("查询入库信息失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    /**
     * 查看入库单详情
     * @param id
     * @return
     */
    @Override
    public InboundResVo view(Long id) {
        InboundResVo inboundResVo = new InboundResVo();
        Inbound inbound = inboundDao.selectByPrimaryKey(id);
        BeanCopyUtils.copy(inbound,inboundResVo);
        if(inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())){
            String supplyCode = inbound.getSupplierCode();
            SupplyCompany supplyCompany = supplyCompanyDao.selectAddress(supplyCode);
            if(supplyCompany != null){
                inboundResVo.setProvinceCode(supplyCompany.getSendProvinceId());
                inboundResVo.setProvinceName(supplyCompany.getSendProvinceName());
                inboundResVo.setCityCode(supplyCompany.getSendCityId());
                inboundResVo.setCityName(supplyCompany.getSendCityName());
                inboundResVo.setCountyCode(supplyCompany.getSendDistrictId());
                inboundResVo.setCountyName(supplyCompany.getSendDistrictName());
                inboundResVo.setDetailedAddress(supplyCompany.getSendingAddress());
                inboundResVo.setShipper(supplyCompany.getContactName());
                inboundResVo.setShipperNumber(supplyCompany.getMobilePhone());
                inboundResVo.setShipperRate(supplyCompany.getZipCode());
            }
        }
        List<InboundProduct> list = inboundProductDao.selectByInboundOderCode(inboundResVo.getInboundOderCode());
        try {
            list.stream().forEach(inboundProduct -> {
                List<ReturnInboundProduct> returnInboundProductList = inboundProductDao.selectTax(inboundResVo.getInboundOderCode(), inboundProduct.getSkuCode());
                ReturnInboundProduct returnInboundProduct = returnInboundProductList.get(0);
                inboundProduct.setTax(returnInboundProduct.getTax());
            });
            inboundResVo.setList(BeanCopyUtils.copyList(list, InboundProductResVo.class));
            List<InboundProductResVo> productList = inboundResVo.getList();
            if(CollectionUtils.isNotEmpty(productList)){
                for(InboundProductResVo vo:productList){
                    if(Objects.isNull(vo.getPraInboundMainNum()) || vo.getPraInboundMainNum() == 0){
                        vo.setPraSingleCount(vo.getPraInboundMainNum());
                    }else{
                        vo.setPraSingleCount(vo.getPraInboundMainNum() % Long.valueOf(vo.getInboundBaseContent()));
                    }

                    if(Objects.isNull(vo.getPreInboundMainNum()) || vo.getPreInboundMainNum() == 0){
                        vo.setPreSingleCount(vo.getPreInboundMainNum());
                    }else{
                        vo.setPreSingleCount(vo.getPreInboundMainNum() % Long.valueOf(vo.getInboundBaseContent()));
                    }
                }
            }
            List<InboundBatch> batchList = inboundBatchDao.selectInboundBatchList(inboundResVo.getInboundOderCode());
            inboundResVo.setBatchList(batchList);
            if (null != inboundResVo) {
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.INBOUND_ODER.getStatus());
                operationLogVo.setObjectId(inboundResVo.getInboundOderCode());
                List<LogData> pageList = productOperationLogService.getLogType(operationLogVo);
                pageList.stream().forEach(logData -> logData.setStatus(inbound.getInboundStatusName()));
                inboundResVo.setLogDataList(pageList);
            }
            return inboundResVo;
        } catch (Exception e) {
            log.error("sku查询类型转化错误", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    /**
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String saveInbound(InboundReqSave reqVo) {
        try {
            log.info("单据调用入库单参数：" + reqVo);
            // 入库单转化主体保存实体
            Inbound inbound = new Inbound();
            BeanCopyUtils.copy(reqVo, inbound);

            EncodingRule rule = null;
            if(!reqVo.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())){
                // 获取编码
                rule = encodingRuleDao.getNumberingType(EncodingRuleType.IN_BOUND_CODE);
                inbound.setInboundOderCode(rule.getNumberingValue().toString());
            }

            //插入入库单主表
            int insert = inboundDao.insert(inbound);
            log.info("插入入库单主表返回结果:{}", insert);

            //  转化入库单sku实体
            List<InboundProduct> list = BeanCopyUtils.copyList(reqVo.getList(), InboundProduct.class);
            list.stream().forEach(inboundItemReqVo -> inboundItemReqVo.setInboundOderCode(inbound.getInboundOderCode()));
            int insertProducts = inboundProductDao.insertBatch(list);
            log.info("插入入库单商品表返回结果:{}", insertProducts);

            List<InboundBatch> batchList = reqVo.getInboundBatchList();
            if (CollectionUtils.isNotEmpty(batchList)) {
                batchList.stream().forEach(inboundBatchReqVo -> inboundBatchReqVo.setInboundOderCode(inbound.getInboundOderCode()));
                Integer count = inboundBatchDao.insertAll(batchList);
                log.info("插入入库单供应商对应的商品信息返回结果:{}", count);
            }

            //更新编码表
            if(!reqVo.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())) {
                encodingRuleDao.updateNumberValue(rule.getNumberingValue(),rule.getId());
            }

            // 保存日志
            productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.ADD_INBOUND_ODER.getStatus(),
                    ObjectTypeCode.INBOUND_ODER.getStatus(), reqVo, HandleTypeCoce.ADD_INBOUND_ODER.getName(), new Date(),
                    reqVo.getCreateBy(), reqVo.getRemark());
            // 调用wms
            if(reqVo.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())){
                // 采购
                this.pushWms(inbound.getInboundOderCode());
            }else if(reqVo.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode())){
                // 退货
                this.returnOrderWms(inbound);
            }
            return inbound.getInboundOderCode();
        } catch (GroundRuntimeException e) {
            log.error("保存入库单接口错误:{}", e.getCause());
            throw new GroundRuntimeException(String.format("添加入库单失败:%s", e.getMessage()));
        }
    }

    /**
     * @param reqVo
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String saveInbound2(InboundReqSave reqVo) {
        try {
            // 入库单转化主体保存实体
            Inbound inbound = new Inbound();
            BeanCopyUtils.copy(reqVo, inbound);
            // 获取编码 尺度
            //EncodingRule rule = encodingRuleDao.getNumberingType(EncodingRuleType.IN_BOUND_CODE);
            //inbound.setInboundOderCode(rule.getNumberingValue().toString());
            //插入入库单主表
            int insert = inboundDao.insert(inbound);
            log.info("插入入库单主表返回结果:{}", insert);
            if(insert <= 0){
                log.info("新增入库单主表数据失败");
                throw new GroundRuntimeException("新增入库单主表数据失败");
            }
            //  转化入库单sku实体
            List<InboundProduct> list =BeanCopyUtils.copyList(reqVo.getList(), InboundProduct.class);
            list.stream().forEach(inboundItemReqVo -> inboundItemReqVo.setInboundOderCode(inbound.getInboundOderCode()));
            //插入入库单商品表
            int insertProducts=inboundProductDao.insertBatch(list);
            log.info("插入入库单商品表返回结果:{}", insertProducts);
            if(insert <= 0){
                log.info("新增入库单商品表数据失败");
                throw new GroundRuntimeException("新增入库单商品表数据失败");
            }
            List<InboundBatchReqVo> batchList = reqVo.getInboundBatchReqVos();
            if(CollectionUtils.isNotEmpty(batchList)){
                batchList.stream().forEach(inboundBatchReqVo -> inboundBatchReqVo.setInboundOderCode(inbound.getInboundOderCode()));
                Integer count = inboundBatchDao.insertList(batchList);
                log.info("插入入库单供应商对应的商品信息返回结果:{}", count);
            }
            //更新编码表
            //encodingRuleDao.updateNumberValue(rule.getNumberingValue(),rule.getId());

            // 保存日志
            productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.ADD_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(),reqVo,HandleTypeCoce.ADD_INBOUND_ODER.getName(),new Date(),reqVo.getCreateBy(), reqVo.getRemark());
            InboundServiceImpl inboundService = (InboundServiceImpl) AopContext.currentProxy();
            // 跟新数据库状态
            return inbound.getInboundOderCode();
        } catch (GroundRuntimeException e) {
            log.error("保存入库单接口错误:{}",e.getCause());
            throw new GroundRuntimeException(String.format("添加入库单失败:%s",e.getMessage()));
        }
    }
    /**
     * 获取入库类型
     * @return
     */
    @Override
    public List<EnumReqVo> getInboundType() {
        List<EnumReqVo> list =  new ArrayList<>();
        InboundTypeEnum [] inboundTypeEnums = InboundTypeEnum.values();
        for (InboundTypeEnum inboundTypeEnum : inboundTypeEnums) {
            list.add(new EnumReqVo(inboundTypeEnum.getCode(),inboundTypeEnum.getName()));
        }
        return list;
    }

    /**
     * 获取出入库状态类型
     * @return
     */
    @Override
    public List<EnumReqVo> getInboundOutboundStatus() {
        List<EnumReqVo> list = new ArrayList<>();
        InOutStatus[] stockStatusEnums = InOutStatus.values();
        for (InOutStatus stockStatusEnum : stockStatusEnums) {
            list.add(new EnumReqVo(stockStatusEnum.getCode(), stockStatusEnum.getName()));
        }
        return list;
    }

    @Override
    public String saveReturnGoodsToInbound(SupplyReturnOrderMainReqVO reqVo) {
        //转换
        InboundReqSave convert = new SupplyReturnOrderMainReqVO2InboundSaveConverter(skuService).convert(reqVo);
        //保存
        return saveInbound(convert);
    }

    /** 入库单采购推送给wms */
    @Override
    //@Async("myTaskAsyncPool")
    @Transactional(rollbackFor = Exception.class)
    public void pushWms(String inboundOderCode) {
        log.info("调用wms开始");
        // 查询入库单
        Inbound inbound = inboundDao.selectByCode(inboundOderCode);
        PurchaseInboundSource inboundSource = BeanCopyUtils.copy(inbound, PurchaseInboundSource.class);
        List<PurchaseInboundDetailSource> inboundProductList = inboundProductDao.wmsByInboundProduct(inbound.getInboundOderCode());
        inboundSource.setDetailList(inboundProductList);
        List<InboundBatch> inboundBatches = inboundBatchDao.selectInboundBatchList(inbound.getInboundOderCode());
        if(CollectionUtils.isNotEmpty(inboundBatches)){
            List<BatchInfo> batchList = BeanCopyUtils.copyList(inboundBatches, BatchInfo.class);
            inboundSource.setBatchInfo(batchList);
        }

        //设置入库状态
        inbound.setInboundStatusCode(InOutStatus.SEND_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.SEND_INOUT.getName());
        PurchaseOrder purchaseOrder = null;
        if(inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())){
          // 查询对应的采购信息
            purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseOrderCode(inbound.getSourceOderCode());
            purchaseOrder = purchaseOrderDao.purchaseOrderInfo(purchaseOrder);
            inboundSource.setContractCode(purchaseOrder.getContractCode());
            inboundSource.setOperuserCode(purchaseOrder.getCreateById());
            inboundSource.setOperuserName(purchaseOrder.getCreateByName());
            inboundSource.setOperuserDate(inbound.getCreateTime());
            inboundSource.setPreArrivalTime(purchaseOrder.getPreArrivalTime());
            inboundSource.setPurchaseOrderCode(purchaseOrder.getPurchaseOrderCode());
            inboundSource.setRemark(purchaseOrder.getRemark());
        }
        Integer s = inboundDao.updateByPrimaryKeySelective(inbound);
        LOGGER.info("开始调用wms，更改入库单的转态：", s);

        //保存日志
        productCommonService.instanceThreeParty(inboundOderCode, HandleTypeCoce.PULL_INBOUND_ODER.getStatus(),
                ObjectTypeCode.INBOUND_ODER.getStatus(), inboundOderCode, HandleTypeCoce.PULL_INBOUND_ODER.getName(),
                new Date(), inbound.getCreateBy(), null);

        // 采购
        if (inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())) {
            // 添加采购开始入库日志
            OperationLog operationLog = new OperationLog();
            if (purchaseOrder != null) {
                operationLog.setOperationId(purchaseOrder.getPurchaseOrderId());
                operationLog.setCreateByName(inbound.getCreateBy());
                operationLog.setOperationType(PurchaseOrderLogEnum.WAREHOUSING_BEGIN.getCode());
                operationLog.setOperationContent("入库申请单" + inbound.getInboundOderCode() + "，开始入库");
                operationLog.setCreateTime(new Date());
                operationLog.setRemark(purchaseOrder.getApplyTypeForm());
                purchaseManageService.addLog(operationLog);
            }

            log.info("向wms发送入库单的参数是：{}",  JsonUtil.toJson(inboundSource));
            String url = urlConfig.WMS_API_URL2 + "/purchase/source/inbound";
            HttpClient httpClient = HttpClient.post(url).json(inboundSource).timeout(20000);
            HttpResponse response = httpClient.action().result(HttpResponse.class);
            if (response.getCode().equals(MessageId.SUCCESS_CODE)) {
                log.info("入库单推送wms成功");
            } else {
                log.error("入库单推送wms失败:{}", response.getMessage());
            }
        }
    }

    @Override
//    @Async("myTaskAsyncPool")
    @Transactional(rollbackFor = Exception.class)
    public void workFlowCallBack(InboundCallBackRequest request) {
        log.info("wms入库单回调实体传入实体:[{}]",JSON.toJSONString(request));
        // 根据入库单号，查询入库单主体
        Inbound inbound = inboundDao.selectByCode(request.getInboundOderCode());
        if(inbound == null){
            log.info("WMS入库单回传，耘链未查询到此入库单，回传失败：" + request);
            throw  new GroundRuntimeException("WMS入库单回传，耘链未查询到此入库单，回传失败");
        }

        // 设置默认实际数量，实际数量与金额
        inbound.setPraInboundNum(0L);
        inbound.setPraMainUnitNum(0L);
        inbound.setPraTaxAmount(BigDecimal.ZERO);
        inbound.setPraTax(BigDecimal.ZERO);
        inbound.setPraAmount(BigDecimal.ZERO);
        // 设置已回传状态
        inbound.setInboundStatusCode(InOutStatus.RECEIVE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.RECEIVE_INOUT.getName());

        // 变更库存与批次库存
        ChangeStockRequest changeStockRequest = new ChangeStockRequest();
        // 采购与调拨加库存并减在途 - 入库类型编码 1.采购 2.调拨 3.退货 4.移库
        if(Objects.equals(inbound.getInboundTypeCode(), InboundTypeEnum.RETURN_SUPPLY.getCode()) ||
                Objects.equals(inbound.getInboundTypeCode(), InboundTypeEnum.ALLOCATE.getCode())){
            changeStockRequest.setOperationType(8);
        }else if(Objects.equals(inbound.getInboundTypeCode(), InboundTypeEnum.ORDER.getCode()) ||
                Objects.equals(inbound.getInboundTypeCode(), InboundTypeEnum.MOVEMENT.getCode())){
            // 退货、移库 - 加库存批次库存
            changeStockRequest.setOperationType(6);
        }

        // 查询对应库房的批次管理
        WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(inbound.getWarehouseCode());
        LOGGER.info("wms回传入库单的库房批次管理信息：{}", JsonUtil.toJson(warehouse));

        String key;
        Map<String, InboundProduct> products = new HashMap<>();
        for (InboundProductCallBackRequest inboundProduct : request.getProductList()) {
            key = String.format("%s,%s,%s", inbound.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLineCode());
            if (products.get(key) == null) {
                products.put(key, inboundProductDao.inboundByLineCode(inbound.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLineCode()));
            }
        }

        List<StockInfoRequest> productList = Lists.newArrayList();
        List<StockBatchInfoRequest> batchList = Lists.newArrayList();
        StockBatchInfoRequest stockBatchInfo;
        PurchaseOrder purchase = new PurchaseOrder();
        if(inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())){
            purchase.setPurchaseOrderCode(inbound.getSourceOderCode());
            purchase = purchaseOrderDao.purchaseOrderInfo(purchase);
        }

        // 1.采购 2.调拨 3.退货  4.移库
        Integer sourceDocumentType;
        if(inbound.getInboundTypeCode().intValue() == 1){
            sourceDocumentType = 3;
        }else if(inbound.getInboundTypeCode().intValue() == 2){
            sourceDocumentType = 4;
        }else if(inbound.getInboundTypeCode().intValue() == 3){
            sourceDocumentType = 5;
        }else {
            sourceDocumentType = 6;
        }

        // 更新入库商品的信息
        StockInfoRequest stockInfo;
        Long praInboundNum = 0L, praMainUnitNum = 0L;
        BigDecimal praTaxAmount = BigDecimal.ZERO, praAmount = BigDecimal.ZERO;
        for (InboundProductCallBackRequest inboundProduct : request.getProductList()) {
            // 查询对应订单的sku
            key = String.format("%s,%s,%s", inbound.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLineCode());
            InboundProduct product = products.get(key);
            Long actualTotalCount = inboundProduct.getActualTotalCount();
            product.setPraInboundMainNum(actualTotalCount);
            Long baseContent = product.getInboundBaseContent() == null ? 1L : Long.valueOf(product.getInboundBaseContent());
            product.setPraInboundNum(actualTotalCount / baseContent);
            //实际含税进价
            product.setPraTaxPurchaseAmount(product.getPreTaxPurchaseAmount());
            //单个SKU的实际含税金额
            product.setPraTaxAmount(product.getPraTaxPurchaseAmount().multiply(BigDecimal.valueOf(actualTotalCount))
                    .setScale(4, BigDecimal.ROUND_HALF_UP));
            product.setUpdateBy(request.getOperatorName());
            // 更新每个商品实际数量，金额
            Integer count = inboundProductDao.update(product);
            log.info("更新入库单商品信息" + product, count);

            // 计算入库单总和 - 实际数量
            praInboundNum = inbound.getPraInboundNum() + product.getPraInboundNum();
            praMainUnitNum = inbound.getPraMainUnitNum() + product.getPraInboundMainNum();
            //实际含税总金额
            praTaxAmount = inbound.getPraTaxAmount().add(product.getPraTaxAmount());
            if(product.getTax() != null){
                BigDecimal amount = Calculate.computeNoTaxPrice(product.getPraTaxAmount(), product.getTax());
                praAmount = inbound.getPraAmount().add(amount);
            }

            // 设置修改在途数加库存参数
            stockInfo = new StockInfoRequest();
            stockInfo.setCompanyCode(inbound.getCompanyCode());
            stockInfo.setCompanyName(inbound.getCompanyName());
            stockInfo.setTransportCenterCode(inbound.getLogisticsCenterCode());
            stockInfo.setTransportCenterName(inbound.getLogisticsCenterName());
            stockInfo.setWarehouseCode(inbound.getWarehouseCode());
            stockInfo.setWarehouseName(inbound.getWarehouseName());
            stockInfo.setWarehouseType(warehouse.getWarehouseTypeCode().intValue());
            stockInfo.setSkuCode(product.getSkuCode());
            stockInfo.setSkuName(product.getSkuName());
            stockInfo.setChangeCount(inboundProduct.getActualTotalCount());
            stockInfo.setTaxRate(product.getTax());
            stockInfo.setNewDelivery(inbound.getSupplierCode());
            stockInfo.setNewDeliveryName(inbound.getSupplierName());
            stockInfo.setDocumentCode(inbound.getInboundOderCode());
            stockInfo.setDocumentType(Global.INBOUND_TYPE); //0出库 1入库
            stockInfo.setSourceDocumentCode(inbound.getSourceOderCode());
            stockInfo.setSourceDocumentType(sourceDocumentType);
            stockInfo.setOperatorId(request.getOperatorId());

            stockInfo.setOperatorName(request.getOperatorName());
            if(inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())){
                // 查询对应的采购商品
                PurchaseOrderProduct purchaseOrderProduct = purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource
                        (inbound.getSourceOderCode(), product.getSkuCode(), product.getLinenum().intValue());
                if(purchaseOrderProduct.getProductType() != 2){
                    stockInfo.setNewPurchasePrice(product.getPraTaxPurchaseAmount());
                }
                // 判断是否为最后一次采购
                Long actualSingleCount = purchaseOrderProduct.getActualSingleCount() == null ? 0L : purchaseOrderProduct.getActualSingleCount();
                Integer actualCount = purchaseOrderProduct.getSingleCount() - actualSingleCount.intValue() - inboundProduct.getActualTotalCount().intValue();
                if(purchase.getInboundLine() == inbound.getPurchaseNum()){
                    if(actualCount == 0){
                        stockInfo.setPreWayCount(inboundProduct.getActualTotalCount());
                    }else {
                        stockInfo.setPreWayCount(purchaseOrderProduct.getSingleCount().longValue() - actualSingleCount);
                    }
                }else {
                    if(actualCount >= 0){
                        stockInfo.setPreWayCount(purchaseOrderProduct.getSingleCount() - actualSingleCount);
                    }else {
                        stockInfo.setPreWayCount(inboundProduct.getActualTotalCount());
                    }
                }
            }
            productList.add(stockInfo);
        }
        changeStockRequest.setStockList(productList);

        // 新增入库批次的信息
        List<InboundBatch> InboundBatchList = Lists.newArrayList();
        InboundBatch productBatch;
        if(warehouse.getBatchManage().equals(Global.BATCH_MANAGE_0)){
            for (InboundProductCallBackRequest inboundProduct : request.getProductList()) {
                key = String.format("%s,%s,%s", inbound.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLineCode());
                InboundProduct product = products.get(key);
                String productDate = DateUtils.currentDate();
                String batchCode = DateUtils.currentDate().replaceAll("-","");
                String batchInfoCode;
                if(StringUtils.isBlank(inbound.getSupplierCode())){
                    batchInfoCode = inboundProduct.getSkuCode() + "_" + inbound.getWarehouseCode() + "_" +
                            batchCode  + "_" + product.getPreTaxPurchaseAmount().stripTrailingZeros().toPlainString();
                }else {
                    batchInfoCode = inboundProduct.getSkuCode() + "_" + inbound.getWarehouseCode() + "_" +
                            batchCode + "_" + inbound.getSupplierCode() + "_" +
                            product.getPreTaxPurchaseAmount().stripTrailingZeros().toPlainString();
                }

                // 添加批次库存
                stockBatchInfo = new StockBatchInfoRequest();
                this.addStockBatch(stockBatchInfo, inbound);
                stockBatchInfo.setProductDate(productDate);
                stockBatchInfo.setBatchCode(batchCode);
                stockBatchInfo.setChangeCount(inboundProduct.getActualTotalCount());
                stockBatchInfo.setOperatorId(request.getOperatorId());
                stockBatchInfo.setWarehouseType(warehouse.getWarehouseTypeCode().toString());
                stockBatchInfo.setOperatorName(request.getOperatorName());
                stockBatchInfo.setSourceDocumentType(sourceDocumentType);
                stockBatchInfo.setBatchInfoCode(batchInfoCode);
                stockBatchInfo.setSkuCode(product.getSkuCode());
                stockBatchInfo.setSkuName(product.getSkuName());
                stockBatchInfo.setTaxCost(product.getPreTaxPurchaseAmount());
                stockBatchInfo.setTaxRate(product.getTax());
                batchList.add(stockBatchInfo);

                // 根据批次编号查询批次是否存在
                productBatch = inboundBatchDao.inboundBatchByInfoCode(batchCode, inbound.getInboundOderCode(), inboundProduct.getLineCode());
                if(productBatch == null){
                    productBatch = inboundBatchDao.inboundBatchByInfoCode(null, inbound.getInboundOderCode(), inboundProduct.getLineCode());
                }
                if(productBatch != null){
                    productBatch.setUpdateById(request.getOperatorId());
                    productBatch.setUpdateByName(request.getOperatorName());
                    productBatch.setActualTotalCount(product.getPraInboundMainNum() + productBatch.getActualTotalCount());
                    Integer count = inboundBatchDao.update(productBatch);
                    log.info("更新批次库存实际数量:{}", count);
                    continue;
                }
                // 查询对应订单的sku
                productBatch = new InboundBatch();
                productBatch.setUpdateById(request.getOperatorId());
                productBatch.setUpdateByName(request.getOperatorName());
                productBatch.setInboundOderCode(product.getInboundOderCode());
                productBatch.setBatchCode(batchCode);
                productBatch.setBatchInfoCode(batchInfoCode);
                productBatch.setSupplierCode(inbound.getSupplierCode());
                productBatch.setSupplierName(inbound.getSupplierName());
                productBatch.setSkuCode(product.getSkuCode());
                productBatch.setSkuName(product.getSkuName());
                productBatch.setProductDate(productDate);
                productBatch.setTotalCount(product.getPreInboundMainNum());
                productBatch.setActualTotalCount(product.getPraInboundMainNum());
                productBatch.setLineCode(product.getLinenum().intValue());
                productBatch.setCreateById(request.getOperatorId());
                productBatch.setCreateByName(request.getOperatorName());
                InboundBatchList.add(productBatch);
            }
            if(CollectionUtils.isNotEmpty(InboundBatchList)){
                Integer count = inboundBatchDao.insertAll(InboundBatchList);
                log.info("入库单添加批次信息", count);
            }
        }else {
            // 更新入库批次的信息
            for (InboundBatchCallBackRequest batchInfo : request.getBatchList()){
                // 查询入库商品的信息
                InboundProduct product = inboundProductDao.inboundByLineCode(inbound.getInboundOderCode(), batchInfo.getSkuCode(), batchInfo.getLineCode());

                // 查询是否指定批次信息
                Integer exist = 0;
                if(warehouse.getBatchManage().equals(Global.BATCH_MANAGE_2)){
                    exist = productSkuBatchDao.productSkuBatchExist(batchInfo.getSkuCode(), inbound.getWarehouseCode());
                }
                // 指定批次信息
                if(warehouse.getBatchManage().equals(Global.BATCH_MANAGE_1) || exist > 0){
                    productBatch = inboundBatchDao.inboundBatchByInfoCode(batchInfo.getBatchCode(), inbound.getInboundOderCode(), batchInfo.getLineCode());
                    if(productBatch == null){
                        productBatch = inboundBatchDao.inboundBatchByInfoCode(null, inbound.getInboundOderCode(), batchInfo.getLineCode());
                    }

                    if(productBatch == null){
                        LOGGER.info("未查询到指定批次的信息：{}", JsonUtil.toJson(request));
                        throw new GroundRuntimeException("未查询到指定批次的信息");
                    }
                    if(StringUtils.isNotBlank(request.getOperatorId())){
                        productBatch.setUpdateById(request.getOperatorId());
                    }
                    if(StringUtils.isNotBlank(request.getOperatorName())){
                        productBatch.setUpdateByName(request.getOperatorName());
                    }
                    productBatch.setActualTotalCount(productBatch.getActualTotalCount() + batchInfo.getActualTotalCount());
                    Integer count = inboundBatchDao.update(productBatch);
                    LOGGER.info("更新批次库存实际数量:{}", count);
                    continue;
                }else {
                    // 查询对应订单的sku
                    productBatch = new InboundBatch();
                    productBatch.setInboundOderCode(inbound.getInboundOderCode());
                    productBatch.setBatchCode(batchInfo.getBatchCode());
                    String batchInfoCode;
                    BigDecimal amount = product.getPreTaxPurchaseAmount() == null ? BigDecimal.ZERO : product.getPreTaxPurchaseAmount();
                    if(StringUtils.isBlank(inbound.getSupplierCode())){
                        batchInfoCode = batchInfo.getSkuCode() + "_" + inbound.getWarehouseCode() + "_" +
                                batchInfo.getBatchCode()  + "_" + amount.stripTrailingZeros().toPlainString();
                    }else {
                        batchInfoCode = batchInfo.getSkuCode() + "_" + inbound.getWarehouseCode() + "_" +
                                batchInfo.getBatchCode() + "_" + inbound.getSupplierCode() + "_" +
                                amount.stripTrailingZeros().toPlainString();
                    }
                    productBatch.setBatchInfoCode(batchInfoCode);
                    productBatch.setSupplierCode(inbound.getSupplierCode());
                    productBatch.setSupplierName(inbound.getSupplierName());
                    productBatch.setSkuCode(batchInfo.getSkuCode());
                    productBatch.setSkuName(batchInfo.getSkuName());
                    productBatch.setProductDate(batchInfo.getProductDate());
                    productBatch.setBeOverdueDate(batchInfo.getBeOverdueDate());
                    productBatch.setTotalCount(product.getPreInboundMainNum());
                    productBatch.setActualTotalCount(batchInfo.getActualTotalCount());
                    productBatch.setLineCode(batchInfo.getLineCode().intValue());
                    productBatch.setLocationCode(batchInfo.getLocationCode());
                    if(StringUtils.isNotBlank(request.getOperatorId())){
                        productBatch.setCreateById(request.getOperatorId());
                    }
                    if(StringUtils.isNotBlank(request.getOperatorName())){
                        productBatch.setCreateByName(request.getOperatorName());
                    }
                    InboundBatchList.add(productBatch);
                }

                // 添加批次库存
                stockBatchInfo = new StockBatchInfoRequest();
                this.addStockBatch(stockBatchInfo, inbound);
                stockBatchInfo.setProductDate(batchInfo.getProductDate());
                stockBatchInfo.setBeOverdueDate(batchInfo.getBeOverdueDate());
                stockBatchInfo.setBatchRemark(batchInfo.getBatchRemark());
                stockBatchInfo.setBatchCode(batchInfo.getBatchCode());
                stockBatchInfo.setSourceDocumentType(sourceDocumentType);
                stockBatchInfo.setSkuCode(batchInfo.getSkuCode());
                stockBatchInfo.setSkuName(batchInfo.getSkuName());
                stockBatchInfo.setTaxCost(product.getPreTaxPurchaseAmount());
                stockBatchInfo.setTaxRate(product.getTax());
                if(StringUtils.isNotBlank(request.getOperatorId())){
                    stockBatchInfo.setOperatorId(request.getOperatorId());
                }
                if(StringUtils.isNotBlank(request.getOperatorName())){
                    stockBatchInfo.setOperatorName(request.getOperatorName());
                }
                stockBatchInfo.setChangeCount(batchInfo.getActualTotalCount());
                stockBatchInfo.setWarehouseType(warehouse.getWarehouseTypeCode().toString());
                batchList.add(stockBatchInfo);
            }
            if(CollectionUtils.isNotEmpty(InboundBatchList)){
                Integer count = inboundBatchDao.insertAll(InboundBatchList);
                log.info("入库单添加批次信息", count);
            }
        }
        changeStockRequest.setStockBatchList(batchList);

        // 实际数量
        inbound.setPraInboundNum(praInboundNum);
        inbound.setPraMainUnitNum(praMainUnitNum);
        //实际含税总金额
        inbound.setPraTaxAmount(praTaxAmount);
        inbound.setPraAmount(praAmount);
        //实际税额
        inbound.setPraTax(praTaxAmount.subtract(praAmount));
        if(StringUtils.isNotBlank(request.getOperatorName())){
            inbound.setUpdateBy(request.getOperatorName());
        }
        // 保存日志
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.RETURN_INBOUND_ODER.getStatus(),
                ObjectTypeCode.INBOUND_ODER.getStatus(), request, HandleTypeCoce.RETURN_INBOUND_ODER.getName(), new Date(),
                request.getOperatorName() == null ? "" : request.getOperatorName(), request.getRemark());

        // 如果是采购添加采购日志
        if(inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode() )){
            OperationLog operationLog = new OperationLog();
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseOrderCode(inbound.getSourceOderCode());
            PurchaseOrder resultPurchaseOrder = purchaseOrderDao.purchaseOrderInfo(purchaseOrder);
            if(resultPurchaseOrder != null) {
                operationLog.setOperationId(resultPurchaseOrder.getPurchaseOrderId());
                operationLog.setCreateByName(inbound.getCreateBy());
                operationLog.setOperationType(PurchaseOrderLogEnum.WAREHOUSING_IN.getCode());
                operationLog.setOperationContent("入库申请单" + inbound.getInboundOderCode() + "，入库中");
                operationLog.setCreateTime(new Date());
                operationLog.setRemark(resultPurchaseOrder.getApplyTypeForm());
                purchaseManageService.addLog(operationLog);
            }
        }

        try {
            HttpResponse response = stockService.stockAndBatchChange(changeStockRequest);
            if(response.getCode().equals(MsgStatus.SUCCESS)){
                log.info("操作库存成功");
            }else{
                log.error("操作库存失败", response.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
        } catch (Exception e) {
            log.error("回传入库单操作失败:[{}],异常信息:{}", changeStockRequest, e.getMessage());
            throw  new GroundRuntimeException("回传入库单失败，库存操作失败");
        }

        //修改入库单主表
        Integer k = inboundDao.updateByPrimaryKeySelective(inbound);
        log.info("入库单更新条数:{}",k);
        // 回传给来源编号
        returnSource(inbound.getId());
    }

    // 添加批次库存
    private StockBatchInfoRequest addStockBatch(StockBatchInfoRequest stockBatchInfo, Inbound inbound){
        stockBatchInfo.setCompanyCode(inbound.getCompanyCode());
        stockBatchInfo.setCompanyName(inbound.getCompanyName());
        stockBatchInfo.setTransportCenterCode(inbound.getLogisticsCenterCode());
        stockBatchInfo.setTransportCenterName(inbound.getLogisticsCenterName());
        stockBatchInfo.setWarehouseCode(inbound.getWarehouseCode());
        stockBatchInfo.setWarehouseName(inbound.getWarehouseName());
        stockBatchInfo.setCompanyCode(inbound.getCompanyCode());
        stockBatchInfo.setCompanyName(inbound.getCompanyName());
        stockBatchInfo.setTransportCenterCode(inbound.getLogisticsCenterCode());
        stockBatchInfo.setTransportCenterName(inbound.getLogisticsCenterName());
        stockBatchInfo.setWarehouseCode(inbound.getWarehouseCode());
        stockBatchInfo.setWarehouseName(inbound.getWarehouseName());
        stockBatchInfo.setSupplierCode(inbound.getSupplierCode());
        stockBatchInfo.setDocumentCode(inbound.getInboundOderCode());
        stockBatchInfo.setDocumentType(Global.INBOUND_TYPE);
        stockBatchInfo.setSourceDocumentCode(inbound.getSourceOderCode());
        return stockBatchInfo;
    }

    /**
     * 根据类型回传给来源单号状态
     * @param id
     */
    @Override
    @Async("myTaskAsyncPool")
    public void returnSource(Long id) {
        // 查询入库单信息
        Inbound inbound = inboundDao.selectByPrimaryKey(id);
        List<InboundProduct> list = inboundProductDao.selectByInboundOderCode(inbound.getInboundOderCode());
        List<InboundBatch> batchList = inboundBatchDao.selectInboundBatchList(inbound.getInboundOderCode());
        // 添加日志
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.COMPLETE_INBOUND_ODER.getStatus(),
                ObjectTypeCode.INBOUND_ODER.getStatus(), id, HandleTypeCoce.COMPLETE_INBOUND_ODER.getName(), new Date(), inbound.getCreateBy(), null);
        // 采购
        if (inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())) {
            try {
                // 调用采购回调
                returnPurchase(inbound.getSourceOderCode(), list, batchList);

                // 将入库单状态修改为完成
                inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
                inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
                inbound.setInboundTime(new Date());
                int k = inboundDao.updateByPrimaryKeySelective(inbound);

                OperationLog operationLog = new OperationLog();
                PurchaseOrder purchaseOrder = new PurchaseOrder();
                purchaseOrder.setPurchaseOrderCode(inbound.getSourceOderCode());
                PurchaseOrder resultPurchaseOrder = purchaseOrderDao.purchaseOrderInfo(purchaseOrder);
                if (resultPurchaseOrder != null) {
                    operationLog.setOperationId(resultPurchaseOrder.getPurchaseOrderId());
                    operationLog.setCreateByName(inbound.getCreateBy());
                    operationLog.setOperationType(PurchaseOrderLogEnum.WAREHOUSING_FINISH.getCode());
                    operationLog.setOperationContent("入库申请单" + inbound.getInboundOderCode() + "，入库完成");
                    operationLog.setCreateTime(new Date());
                    operationLog.setRemark(resultPurchaseOrder.getApplyTypeForm());
                    purchaseManageService.addLog(operationLog);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new GroundRuntimeException("回传采购单失败失败");
            }
        }else if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode())) {
            // 退货 - 调用退货回调
            returnGoodsService.recordWMS(inbound.getInboundOderCode());

        }// 如果是调拨
        else if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode())) {
            //  回传给调拨
            inBoundReturn(inbound.getSourceOderCode());
        } else if (inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())) {
            //如果是移库
            inBoundReturnMovement(inbound.getSourceOderCode());
//            inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
//            inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
//            inbound.setInboundTime(new Date());
//            int k = inboundDao.updateByPrimaryKeySelective(inbound);
        } else {
            throw new GroundRuntimeException("无法回传匹配类型");
        }
        inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        inbound.setInboundTime(Calendar.getInstance().getTime());
        int k = inboundDao.updateByPrimaryKeySelective(inbound);
    }

    /**
     *  回调退货接口
     * @param sourceOderCode
     * @param list
     * @param batchList
     */
    @Async("myTaskAsyncPool")
    public void returnGoods(String sourceOderCode, List<InboundProduct> list, List<InboundBatch> batchList) {
        SupplyReturnOrderMainReqVOReturn returnOrderMain = new SupplyReturnOrderMainReqVOReturn();

        Long productNum = 0L;
        BigDecimal productTotalAmount = new BigDecimal(0);
        BigDecimal orderAmount = new BigDecimal(0);
        // 退货商品信息
        List<SupplyReturnOrderProductItemReqVOReturn> returnOrderProducts = new ArrayList<>();
        for (InboundProduct inboundProduct : list) {
            SupplyReturnOrderProductItemReqVOReturn returnOrderProduct = new SupplyReturnOrderProductItemReqVOReturn();
            returnOrderProduct.setReturnOrderCode(sourceOderCode);
            returnOrderProduct.setReturnNum(inboundProduct.getPraInboundMainNum());
            returnOrderProduct.setPrice(inboundProduct.getPraTaxPurchaseAmount());
            returnOrderProduct.setAmount(inboundProduct.getPraTaxAmount());
            returnOrderProduct.setSkuCode(inboundProduct.getSkuCode());
            returnOrderProduct.setLinenum(inboundProduct.getLinenum());
            returnOrderProducts.add(returnOrderProduct);

            productNum += inboundProduct.getPraInboundMainNum().longValue();
            productTotalAmount.add(inboundProduct.getPraTaxPurchaseAmount());
            orderAmount.add(inboundProduct.getPraTaxAmount());
        }
        // 退货批次信息
        List<SupplyReturnOrderProductBatchItemReqVOReturn> returnOrderProductBatchs = new ArrayList<>();
        for (InboundBatch inboundBatch: batchList) {
            SupplyReturnOrderProductBatchItemReqVOReturn returnOrderProductBatch  = new SupplyReturnOrderProductBatchItemReqVOReturn();
            BeanUtils.copyProperties(returnOrderProductBatch, inboundBatch);
            returnOrderProductBatch.setReturnOderCode(sourceOderCode);
            returnOrderProductBatchs.add(returnOrderProductBatch);
        }

        // 退货订单信息
        SupplyReturnOrderInfoReqVOReturn returnOrderInfo = new SupplyReturnOrderInfoReqVOReturn();
        Inbound inbound = inboundDao.inboundCodeOrderLast(sourceOderCode, String.valueOf(InboundTypeEnum.ORDER.getCode()));
        returnOrderInfo.setCompanyName(inbound.getCompanyName());
        returnOrderInfo.setCompanyCode(inbound.getCompanyCode());
        returnOrderInfo.setOperator(inbound.getCreateBy());
        returnOrderInfo.setOrderStatus(ReturnOrderStatus.REFUND_COMPLETED.getStatusCode());
        returnOrderInfo.setReturnOrderCode(sourceOderCode);
        returnOrderInfo.setProductNum(productNum);
        returnOrderInfo.setProductTotalAmount(productTotalAmount);
        returnOrderInfo.setOrderAmount(orderAmount);

        returnOrderMain.setOrderBatchItems(returnOrderProductBatchs);
        returnOrderMain.setOrderItems(returnOrderProducts);
        returnOrderMain.setMainOrderInfo(returnOrderInfo);
        //returnGoodsService.recordWMS(returnOrderMain);
    }

    /**
     * 回调采购接口
     */
    @Async("myTaskAsyncPool")
    public void returnPurchase(String sourceOderCode, List<InboundProduct> list, List<InboundBatch> batchList) {
        PurchaseStorageRequest purchaseStorage = new PurchaseStorageRequest();
        try {
            // 查询对应的采购单
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseOrderCode(sourceOderCode);
            PurchaseOrder order = purchaseOrderDao.purchaseOrderInfo(purchaseOrder);
            List<PurchaseOrderProduct> purchaseOrderProducts = Lists.newArrayList();
            PurchaseOrderProduct purchaseOrderProduct;
            // 传送采购商品信息
            for(InboundProduct product : list){
                purchaseOrderProduct = new PurchaseOrderProduct();
                purchaseOrderProduct.setPurchaseOrderCode(sourceOderCode);
                purchaseOrderProduct.setActualSingleCount(product.getPraInboundMainNum().intValue());
                purchaseOrderProduct.setSkuCode(product.getSkuCode());
                purchaseOrderProduct.setLinnum(product.getLinenum().intValue());
                purchaseOrderProducts.add(purchaseOrderProduct);
            }
            purchaseStorage.setOrderList(purchaseOrderProducts);
            // 回传采购批次信息
            List<PurchaseBatch> orderItem = BeanCopyUtils.copyList(batchList, PurchaseBatch.class);
            purchaseStorage.setBatchList(orderItem);
            Inbound inbound = inboundDao.inboundCodeOrderLast(sourceOderCode, String.valueOf(InboundTypeEnum.RETURN_SUPPLY.getCode()));
            purchaseStorage.setCompanyName(inbound.getCompanyName());
            purchaseStorage.setCompanyCode(inbound.getCompanyCode());
            purchaseStorage.setCreateByName(inbound.getCreateBy());
            purchaseStorage.setPurchaseNum(inbound.getPurchaseNum());
            purchaseStorage.setPurchaseOrderCode(sourceOderCode);
            HttpResponse httpResponse = purchaseManageService.getWarehousing(purchaseStorage);
            if(httpResponse.getCode().equals("0")){
                log.info("入库单回传给采购接口成功");
                // 回传成功之后，调用sap
                //sapBaseDataService.purchaseAndReject(order.getPurchaseOrderId(), 0);
            }else {
                log.error("入库单回传给采购接口失败");
            }
        } catch (GroundRuntimeException e) {
            log.error(Global.ERROR, e);
            log.error("入库单回传给采购接口失败+回传实体为：[{}]", purchaseStorage);
        }
    }

    /**
     *入库单回传给调拨
     * @param allocationCode
     */
    @Override
    @Async("myTaskAsyncPool")
    public void inBoundReturn(String allocationCode) {
        try {
//          productCommonService.getInstance(allocationCode+"", HandleTypeCoce.SUCCESS__ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),allocationCode ,HandleTypeCoce.SUCCESS__ALLOCATION.getName());
//            supplierCommonService.getInstance(allocationCode + "", HandleTypeCoce.ADD_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.SUCCESS__ALLOCATION.getName(), null, HandleTypeCoce.ADD_ALLOCATION.getName(), "系统自动");
                Allocation allocation = allocationMapper.selectByCode(allocationCode);
                //设置调拨状态
                allocation.setInStockTime(Calendar.getInstance().getTime());
                allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
                allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
                //跟新调拨单状态
                int count = allocationMapper.updateByPrimaryKeySelective(allocation);
            if(count > 0){
                // 调用sap 传送调拨单的数据给sap
//                sapBaseDataService.allocationAndprofitLoss(allocationCode,0);
//                LOGGER.info("调拨wms回传,调用sap成功：{}", JsonUtil.toJson(allocation));
            }
        } catch (Exception e) {
            log.error(Global.ERROR, JsonUtil.toJson(allocationCode));
            throw new GroundRuntimeException("调拨单更改入库状态失败");
        }
    }

    /**
     *入库单回传移库
     * @param allocationCode
     */
    @Override
    @Async("myTaskAsyncPool")
    public void inBoundReturnMovement(String allocationCode) {
        try {
//          productCommonService.getInstance(allocationCode+"", HandleTypeCoce.SUCCESS__MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),allocationCode ,HandleTypeCoce.SUCCESS__MOVEMENT.getName());
//            supplierCommonService.getInstance(allocationCode + "", HandleTypeCoce.ADD_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.SUCCESS__MOVEMENT.getName(), null, HandleTypeCoce.ADD_MOVEMENT.getName(), "系统自动");
            Allocation allocation = allocationMapper.selectByCode(allocationCode);
            //设置调拨状态
            allocation.setInStockTime(Calendar.getInstance().getTime());
            allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
            allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
            //更新调拨单状态
            int count = allocationMapper.updateByPrimaryKeySelective(allocation);
            if(count > 0){
                // 调用sap 传送调拨单的数据给sap
                sapBaseDataService.allocationAndprofitLoss(allocationCode,0);
                LOGGER.info("移库wms回传成功");
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException("移库单更改入库状态失败");
        }
    }

    @Override
    public HttpResponse selectInboundBatchInfoByInboundOderCode(InboundBatch inboundBatch){
        try{
            List<InboundBatch> inboundBatchList = inboundBatchDao.selectInboundBatchInfoByInboundOderCode(inboundBatch);
            int total = inboundBatchDao.countInboundBatchInfoByInboundOderCode(inboundBatch.getInboundOderCode());
            return HttpResponse.success(new PageResData<>(total, inboundBatchList));
        }catch (Exception e){
            log.error("根据入库单号查询入库商品批次详情失败", e);
            throw new GroundRuntimeException("根据入库单号查询入库商品批次详情失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveList(List<InboundReqSave> list) {
        //批量保存
        List<Inbound> inboundList = BeanCopyUtils.copyList(list,Inbound.class);
        List<InboundProduct> productList = Lists.newArrayList();
        List<InboundBatch> batchList = Lists.newArrayList();
        for (InboundReqSave save : list) {
            productList.addAll(BeanCopyUtils.copyList(save.getList(), InboundProduct.class));
            batchList.addAll(BeanCopyUtils.copyList(save.getInboundBatchReqVos(), InboundBatch.class));
        }
        saveData(inboundList,productList,batchList);
        //存日志 todo
        //推送到wms
        //returnOrderWms(list);
        return Boolean.TRUE;
    }

    private HttpResponse returnOrderWms(Inbound inbound) {
        LOGGER.info("开始调用退货单的wms：{}", inbound);
        // 查询退货单
        ReturnOrderInfo returnOrderInfo = returnOrderInfoDao.selectByCode(inbound.getSourceOderCode());

        ReturnOrderPrimarySourceInit returnOder = BeanCopyUtils.copy(inbound, ReturnOrderPrimarySourceInit.class);
        returnOder.setCreateById(returnOrderInfo.getCreateById());
        returnOder.setCreateByName(returnOrderInfo.getCreateByName());
        returnOder.setLogisticsCenterCode(returnOrderInfo.getTransportCompanyCode());
        returnOder.setLogisticsCenterName(returnOrderInfo.getTransportCompany());
        returnOder.setTransportNumber(returnOrderInfo.getTransportNumber());
        returnOder.setCustomerCode(returnOrderInfo.getCustomerCode());
        returnOder.setCustomerName(returnOrderInfo.getCustomerName());
        returnOder.setShipper(returnOrderInfo.getConsignee());
        returnOder.setShipperNumber(returnOrderInfo.getConsigneePhone());
        returnOder.setRemake(returnOrderInfo.getRemake());
        returnOder.setReturnOrderCode(inbound.getInboundOderCode());
        returnOder.setOrderCode(returnOrderInfo.getOrderCode());
        returnOder.setSupplierCode(returnOrderInfo.getSupplierCode());
        returnOder.setSupplierName(returnOrderInfo.getSupplierName());
        returnOder.setCountry("中国");
        returnOder.setProvince(returnOrderInfo.getProvinceName());
        returnOder.setCity(returnOrderInfo.getCityName());
        returnOder.setCounty(returnOrderInfo.getDistrictName());
        returnOder.setStreet(returnOrderInfo.getDetailAddress());
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        returnOder.setCreateTime(sdf.format(inbound.getCreateTime()));

        List<ReturnOrderChildSourceInit> detailList = new ArrayList();
        ReturnOrderChildSourceInit source;
        // 查询退货单退货单商品信息
        List<InboundProduct> inboundProducts = inboundProductDao.selectByInboundOderCode(inbound.getInboundOderCode());
        if(CollectionUtils.isEmpty(inboundProducts)){
            LOGGER.info("入库单的商品信息未查询到：{}", JsonUtil.toJson(inboundProducts));
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 400, "退货单- 入库单未查询商品信息"));
        }
        List<PurchaseSaleStockRespVo> distributionInfoList;
        for (InboundProduct product : inboundProducts) {
            source= new ReturnOrderChildSourceInit();
            source.setSkuCode(product.getSkuCode());
            source.setSkuName(product.getSkuName());
            source.setLineCode(product.getLinenum().toString());
            source.setTotalCount(product.getPreInboundMainNum().toString());
            source.setUnitCode(product.getUnitCode());
            source.setUnitName(product.getUnitName());
            source.setColorCode(product.getColorName());
            source.setModel(product.getModel());
            // 查询分销单位  条形码
            distributionInfoList = productSkuDistributionInfoMapper.getList(product.getSkuCode());
            if(CollectionUtils.isNotEmpty(distributionInfoList) && distributionInfoList.size() > 0){
                source.setSkuBarCode(distributionInfoList.get(0).getBarCode());
                source.setPackgeUnit(distributionInfoList.get(0).getUnitName());
            }
            detailList.add(source);
        }
        returnOder.setDetails(detailList);

        // 商品批次信息
        List<InboundBatch> batchList = inboundBatchDao.selectInboundBatchList(inbound.getInboundOderCode());
        if(CollectionUtils.isNotEmpty(batchList) && batchList.size() > 0){
            List<BatchInfo> batchInfoList = Lists.newArrayList();
            BatchInfo batchInfo;
            for (InboundBatch batch : batchList) {
                batchInfo = BeanCopyUtils.copy(batch, BatchInfo.class);
                batchInfo.setBatchId(IdUtil.uuid());
                batchInfoList.add(batchInfo);
            }
            returnOder.setBatchInfoList(batchInfoList);
        }
        LOGGER.info("退货单开始调用wms，参数：{}", JsonUtil.toJson(returnOder));
        String url = urlConfig.WMS_API_URL2 + "/infoPushAndInquiry/source/returnOrderInfoPush";
        HttpClient httpClient = HttpClient.post(url).json(returnOder).timeout(20000);
        HttpResponse response = httpClient.action().result(HttpResponse.class);
        if (response.getCode().equals(MessageId.SUCCESS_CODE)) {
            LOGGER.info("退货单调用wms成功");
            return HttpResponse.success();
        }else {
            LOGGER.info("退货单调用wms失败：{}", response.getMessage());
           return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单调用wms失败"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(List<Inbound> inboundList, List<InboundProduct> productList, List<InboundBatch> batchList) {
        int i = inboundDao.insertBatch(inboundList);
        if(i!=inboundList.size()){
            throw new BizException(ResultCode.SAVE_IN_BOUND_FAILED);
        }
        int i1 = inboundProductDao.insertBatch(productList);
        if(i1!=productList.size()){
            throw new BizException(ResultCode.SAVE_IN_BOUND_PRODUCT_FAILED);
        }
        Integer integer = inboundBatchDao.insertAll(batchList);
        if(Objects.isNull(integer)||integer!=batchList.size()){
            throw new BizException(ResultCode.SAVE_IN_BOUND_BATCH_FAILED);
        }
    }

    @Override
    public String repealOrder(String orderId, String createById, String createByName, String cancel){
        //TODO wms发送撤销订单
        RepealOrderRequest repealOrderRequest = new RepealOrderRequest();
        repealOrderRequest.setRepealEmpId(createById);
        repealOrderRequest.setRepealOrderId(orderId);
        repealOrderRequest.setDescription(cancel == null ? "" : cancel);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        repealOrderRequest.setRepealTime(formatter.format(Calendar.getInstance().getTime()));
        try{
            String url =urlConfig.WMS_API_URL+"/wms/repeal/inbound";
            HttpClient httpClient = HttpClient.post(url).json(repealOrderRequest);
            HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
            String data= JSON.toJSONString(orderDto.getData());
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            ResponseWms entiy = mapper.readValue(data, ResponseWms.class);
            log.info("撤销采购单的dl回调参数：{}" + entiy);
            if("0".equals(orderDto.getCode())) {
                if ("0".equals(entiy.getResultCode())) {
                    log.info("向dl发送撤销订单请求成功");
                }
                return entiy.getResultCode();
            }else{
                log.error("向dl发送撤销订单请求失败，参数为：{}", repealOrderRequest);
            }
        } catch (Exception e){
            log.error("向dl发送撤销订单请求失败，原因为：{}", e);
        }
        return "";
    }

}
