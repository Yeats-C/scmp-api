package com.aiqin.bms.scmp.api.product.service.impl;

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
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderProductDao;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseStorageRequest;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private static final BigDecimal big = BigDecimal.valueOf(0);

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
    private SupplierCommonService supplierCommonService;
    @Autowired
    private PurchaseOrderProductDao purchaseOrderProductDao;

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
   //@Transactional(rollbackFor = GroundRuntimeException.class)
    public String saveInbound(InboundReqSave reqVo) {
        try {
            log.info("采购单入库参数：" + reqVo);
            // 入库单转化主体保存实体
            Inbound inbound = new Inbound();
            BeanCopyUtils.copy(reqVo, inbound);

            // 获取编码 尺度
            //EncodingRule rule = encodingRuleDao.getNumberingType(EncodingRuleType.IN_BOUND_CODE);
            //inbound.setInboundOderCode(rule.getNumberingValue().toString());

            //插入入库单主表
            int insert = inboundDao.insert(inbound);
            log.info("插入入库单主表返回结果:{}", insert);

            if (insert <= 0) {
                log.info("新增入库单主表数据失败");
                throw new GroundRuntimeException("新增入库单主表数据失败");
            }
            //  转化入库单sku实体
            List<InboundProduct> list = BeanCopyUtils.copyList(reqVo.getList(), InboundProduct.class);
            list.stream().forEach(inboundItemReqVo -> inboundItemReqVo.setInboundOderCode(inbound.getInboundOderCode()));

            //插入入库单商品表
            int insertProducts = inboundProductDao.insertBatch(list);
            log.info("插入入库单商品表返回结果:{}", insertProducts);
            if (insert <= 0) {
                log.info("新增入库单商品表数据失败");
                throw new GroundRuntimeException("新增入库单商品表数据失败");
            }
            List<InboundBatchReqVo> batchList = reqVo.getInboundBatchReqVos();
            if (CollectionUtils.isNotEmpty(batchList)) {
                batchList.stream().forEach(inboundBatchReqVo -> inboundBatchReqVo.setInboundOderCode(inbound.getInboundOderCode()));
                Integer count = inboundBatchDao.insertList(batchList);
                log.info("插入入库单供应商对应的商品信息返回结果:{}", count);
            }

            //更新编码表
            //encodingRuleDao.updateNumberValue(rule.getNumberingValue(),rule.getId());

            // 保存日志
            productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.ADD_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(), reqVo, HandleTypeCoce.ADD_INBOUND_ODER.getName(), new Date(), reqVo.getCreateBy(), reqVo.getRemark());
//            InboundServiceImpl inboundService = (InboundServiceImpl) AopContext.currentProxy();
//            inboundService.pushWms(inbound.getInboundOderCode(), inboundService);
            this.wms(inbound.getInboundOderCode());
            // 跟新数据库状态
            return inbound.getInboundOderCode();
        } catch (GroundRuntimeException e) {
            log.error("保存入库单接口错误:{}", e.getCause());
            throw new GroundRuntimeException(String.format("添加入库单失败:%s", e.getMessage()));
        }
    }

    public void wms(String code){
        InboundServiceImpl inboundService = (InboundServiceImpl) AopContext.currentProxy();
        inboundService.pushWms(code, inboundService);
    }

    /**
     * @param reqVo
     * @return
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

    /**
     * 入库单推送给wms
     * @param code
     * @return
     */
    @Override
    //@Async("myTaskAsyncPool")
    //@Transactional(rollbackFor = Exception.class)
    public void pushWms(String code,InboundServiceImpl inboundService) {
        log.info("异步推送给wms");
        String url = "";
        // 通过id查询 入库单主体
        Inbound inbound = inboundDao.selectByCode(code);
        InboundWmsReqVO inboundWmsReqVO = new InboundWmsReqVO();
        BeanCopyUtils.copy(inbound, inboundWmsReqVO);
        List<InboundProductWmsReqVO> inboundProductWmsReqVOS = inboundProductDao.selectMmsReqByInboundOderCode(inbound.getInboundOderCode());
        inboundWmsReqVO.setList(inboundProductWmsReqVOS);
        InboundCallBackReqVo inboundCallBackReqVo = new InboundCallBackReqVo();
        // 先更新数据库,防止事务回滚推送DL重复
        //设置入库状态
        inbound.setInboundStatusCode(InOutStatus.SEND_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.SEND_INOUT.getName());
        int s = inboundDao.updateByPrimaryKeySelective(inbound);

        //保存日志
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.PULL_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(), code, HandleTypeCoce.PULL_INBOUND_ODER.getName(), new Date(), inbound.getCreateBy(), null);
        log.info("推送保存日志修改状态,应该在回调接口前面执行");
        //采购传入wms
        if (inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())) {
            //采购日志列表
            OperationLog operationLog = new OperationLog();
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseOrderCode(inbound.getSourceOderCode());
            PurchaseOrder resultPurchaseOrder = purchaseOrderDao.purchaseOrderInfo(purchaseOrder);
            if (resultPurchaseOrder != null) {
                operationLog.setOperationId(resultPurchaseOrder.getPurchaseOrderId());
                operationLog.setCreateByName(inbound.getCreateBy());
                operationLog.setOperationType(PurchaseOrderLogEnum.WAREHOUSING_BEGIN.getCode());
                operationLog.setOperationContent("入库申请单" + inbound.getInboundOderCode() + "，开始入库");
                operationLog.setCreateTime(new Date());
                operationLog.setRemark(resultPurchaseOrder.getApplyTypeForm());
                purchaseManageService.addLog(operationLog);
            }

            PurchaseOrderDetails order = inboundDao.selectCreateById(inbound.getInboundOderCode());
            inboundWmsReqVO.setCreateById(order.getCreateById());
            inboundWmsReqVO.setCreateByName(order.getCreateByName());
            inboundWmsReqVO.setRemark(order.getRemark());
            log.info("向wms发送入库单的参数是：{}", JSON.toJSON(inboundWmsReqVO));
            url = urlConfig.WMS_API_URL + "/wms/save/purchase/inbound";
            HttpClient httpClient = HttpClient.post(url).json(inboundWmsReqVO).timeout(200000);
            HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
            if (orderDto.getCode().equals(MessageId.SUCCESS_CODE)) {
                ResponseWms responseWms = JsonUtil.fromJson(JsonUtil.toJson(orderDto.getData()), ResponseWms.class);
                purchaseOrder.setPurchaseOrderId(resultPurchaseOrder.getPurchaseOrderId());
                if ("0".equals(responseWms.getResultCode())) {
                    //设置wms编号
//                    inbound.setWmsDocumentCode(responseWms.getUniquerRequestNumber());
                    purchaseOrder.setInfoStatus(0);
                    log.info("入库单传入dl成功");
                } else {
                    purchaseOrder.setInfoStatus(1);
                    log.error("入库单传入wms失败:{}", responseWms.getReason());
                    //throw new GroundRuntimeException(String.format("入库单传入wms失败:%s", responseWms.getReason()));
                }
            } else {
                purchaseOrder.setInfoStatus(1);
                log.error("入库单传入wms失败:{}", orderDto.getMessage());
                //throw new GroundRuntimeException(String.format("入库单传入wms失败:%s", orderDto.getMessage()));
            }
            Integer update = purchaseOrderDao.update(purchaseOrder);
            log.error("更改采购单重发状态，判断是否发送DL成功：", update);
        }else {
            //移库
            if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode())) {
                //记录调拨待入库
                supplierCommonService.getInstance(inbound.getSourceOderCode() + "", HandleTypeCoce.ADD_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.INBOUND_ALLOCATION.getName(), null, HandleTypeCoce.ADD_ALLOCATION.getName(), "系统自动");
            }
            if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode())) {
                //记录移库待入库
                supplierCommonService.getInstance(inbound.getSourceOderCode() + "", HandleTypeCoce.ADD_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.INBOUND_MOVEMENT.getName(), null, HandleTypeCoce.ADD_MOVEMENT.getName(), "系统自动");
            }
            if (inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())) {
                inboundCallBackReqVo.setInboundOderCode(inbound.getInboundOderCode());
                List<InboundProductCallBackReqVo> list = new ArrayList<>();
                for (InboundProductWmsReqVO inboundProductWmsReqVO : inboundProductWmsReqVOS) {
                    InboundProductCallBackReqVo inboundProductCallBackReqVo = new InboundProductCallBackReqVo();
                    inboundProductCallBackReqVo.setLinenum(inboundProductWmsReqVO.getLinenum());
                    inboundProductCallBackReqVo.setSkuCode(inboundProductWmsReqVO.getSkuCode());
                    inboundProductCallBackReqVo.setProductType(inboundProductWmsReqVO.getProductType());
//                  Long num = 10l;
                    inboundProductCallBackReqVo.setPraInboundMainNum(inboundProductWmsReqVO.getPreInboundMainNum());
                    list.add(inboundProductCallBackReqVo);
                }
                inboundCallBackReqVo.setList(list);
            }
        }
        if (inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())) {
            //inboundService.workFlowCallBack(inboundCallBackReqVo);
        }
    }

    @Override
//    @Async("myTaskAsyncPool")
    public void workFlowCallBack(InboundCallBackRequest request) {
        log.info("入库单回调实体传入实体:[{}]",JSON.toJSONString(request));
        // 根据入库单号，查询入库单主体
        Inbound inbound = inboundDao.selectByCode(request.getInboundOderCode());
        if(inbound == null){
            log.info("WMS入库单回传，耘链未查询到此入库单，回传失败：" + request);
            throw  new GroundRuntimeException("WMS入库单回传，耘链未查询到此入库单，回传失败");
        }

        //inbound.setInboundTime(Calendar.getInstance().getTime());
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

        // 更新入库商品的信息
        Long praInboundNum = 0L, praMainUnitNum = 0L;
        BigDecimal praTaxAmount = BigDecimal.ZERO, praAmount = BigDecimal.ZERO;
        for (InboundProductCallBackRequest inboundProduct : request.getProductList()) {
            // 查询对应订单的sku
            key = String.format("%s,%s,%s", inbound.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLineCode());
            InboundProduct product = products.get(key);
            Long actualTotalCount = inboundProduct.getActualTotalCount();
            product.setPraInboundMainNum(actualTotalCount);
            product.setPraInboundNum(actualTotalCount / Long.valueOf(product.getInboundBaseContent()));
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
            BigDecimal amount = Calculate.computeNoTaxPrice(product.getPraTaxAmount(), product.getTax());
            praAmount = inbound.getPraAmount().add(amount);

            // 设置修改在途数加库存参数
            StockInfoRequest stockInfo = new StockInfoRequest();
            stockInfo.setCompanyCode(inbound.getCompanyCode());
            stockInfo.setCompanyName(inbound.getCompanyName());
            stockInfo.setTransportCenterCode(inbound.getLogisticsCenterCode());
            stockInfo.setTransportCenterName(inbound.getLogisticsCenterName());
            stockInfo.setWarehouseCode(inbound.getWarehouseCode());
            stockInfo.setWarehouseName(inbound.getWarehouseName());
            //stockInfo.setWarehouseType();
            stockInfo.setSkuCode(product.getSkuCode());
            stockInfo.setSkuName(product.getSkuName());
            stockInfo.setChangeCount(inboundProduct.getActualTotalCount());
            stockInfo.setTaxRate(product.getTax());
            stockInfo.setNewDelivery(inbound.getSupplierCode());
            stockInfo.setNewDeliveryName(inbound.getSupplierName());
            stockInfo.setDocumentCode(inbound.getInboundOderCode());
            stockInfo.setDocumentType(1);//0出库 1入库 2退供 3采购
            stockInfo.setSourceDocumentCode(inbound.getSourceOderCode());
            stockInfo.setSourceDocumentType(Integer.parseInt(inbound.getInboundTypeCode().toString()));
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
                if(purchase.getInboundLine() == inbound.getPurchaseNum()){
                    Long actualSingleCount = purchaseOrderProduct.getActualSingleCount() == null ? 0L : purchaseOrderProduct.getActualSingleCount();
                    stockInfo.setPreWayCount(purchaseOrderProduct.getSingleCount().longValue() - actualSingleCount);
                }
            }
            productList.add(stockInfo);
        }
        changeStockRequest.setStockList(productList);

        // 新增入库批次的信息
        List<InboundBatch> InboundBatchList = Lists.newArrayList();
        InboundBatch productBatch ;
        if(CollectionUtils.isEmpty(request.getBatchList())){
            for (InboundProductCallBackRequest inboundProduct : request.getProductList()) {
                key = String.format("%s,%s,%s", inbound.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLineCode());
                InboundProduct product = products.get(key);
                String productDate = DateUtils.currentDate();
                String batchCode = DateUtils.currentDate().replaceAll("-","");
                String batchInfoCode = inboundProduct.getSkuCode() + "_" + inbound.getWarehouseCode() + "_" +
                        batchCode + "_" + inbound.getSupplierCode() + "_" + product.getPreTaxPurchaseAmount();

                // 添加批次库存
                stockBatchInfo = new StockBatchInfoRequest();
                this.addStockBatch(stockBatchInfo, product, inbound);
                stockBatchInfo.setProductDate(productDate);
                stockBatchInfo.setBatchCode(batchCode);
                stockBatchInfo.setChangeCount(inboundProduct.getActualTotalCount());
                stockBatchInfo.setOperatorId(request.getOperatorId());
                stockBatchInfo.setOperatorName(request.getOperatorName());
                batchList.add(stockBatchInfo);

                // 根据批次编号查询批次是否存在
                productBatch = inboundBatchDao.inboundBatchByInfoCode(batchInfoCode, inbound.getInboundOderCode(), inboundProduct.getLineCode());
                if(productBatch != null){
                    productBatch.setUpdateById(request.getOperatorId());
                    productBatch.setUpdateByName(request.getOperatorName());
                    productBatch.setActualTotalCount(product.getPraInboundMainNum() + productBatch.getActualTotalCount());
                    Integer count = inboundBatchDao.update(productBatch);
                    log.info("更新批次库存实际数量:" + productBatch + ",条数:", count);
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
                key = String.format("%s,%s,%s", inbound.getInboundOderCode(), batchInfo.getSkuCode(), batchInfo.getLineCode());
                InboundProduct product = products.get(key);
                // 根据批次编号查询批次是否存在
                String batchInfoCode = batchInfo.getSkuCode() + "_" + inbound.getWarehouseCode() + "_" +
                        batchInfo.getBatchCode() + "_" + inbound.getSupplierCode() + "_" + product.getPreTaxPurchaseAmount();
                productBatch = inboundBatchDao.inboundBatchByInfoCode(batchInfoCode, inbound.getInboundOderCode(), batchInfo.getLineCode());
                productBatch.setUpdateById(request.getOperatorId());
                productBatch.setUpdateByName(request.getOperatorName());

                // 添加批次库存
                stockBatchInfo = new StockBatchInfoRequest();
                this.addStockBatch(stockBatchInfo, product, inbound);
                stockBatchInfo.setProductDate(batchInfo.getProductDate());
                stockBatchInfo.setBeOverdueDate(batchInfo.getBeOverdueDate());
                stockBatchInfo.setBatchRemark(batchInfo.getBatchRemark());
                stockBatchInfo.setBatchCode(batchInfo.getBatchCode());
                stockBatchInfo.setOperatorId(request.getOperatorId());
                stockBatchInfo.setOperatorName(request.getOperatorName());
                stockBatchInfo.setChangeCount(batchInfo.getActualTotalCount());
                batchList.add(stockBatchInfo);

                if(productBatch != null){
                    productBatch.setActualTotalCount(product.getPraInboundMainNum() + batchInfo.getActualTotalCount());
                    Integer count = inboundBatchDao.update(productBatch);
                    log.info("更新批次库存实际数量:" + productBatch + ",条数:", count);
                    continue;
                }
                // 查询对应订单的sku
                productBatch.setInboundOderCode(product.getInboundOderCode());
                productBatch.setBatchCode(batchInfo.getBatchCode());
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
                productBatch.setCreateById(request.getOperatorId());
                productBatch.setCreateByName(request.getOperatorName());
                InboundBatchList.add(productBatch);
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
        inbound.setUpdateBy(request.getOperatorName());
        // 保存日志
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.RETURN_INBOUND_ODER.getStatus(),
                ObjectTypeCode.INBOUND_ODER.getStatus(), request, HandleTypeCoce.RETURN_INBOUND_ODER.getName(), new Date(), request.getOperatorName(), request.getRemark());

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
    private StockBatchInfoRequest addStockBatch(StockBatchInfoRequest stockBatchInfo, InboundProduct product, Inbound inbound){
        stockBatchInfo.setCompanyCode(inbound.getCompanyCode());
        stockBatchInfo.setCompanyName(inbound.getCompanyName());
        stockBatchInfo.setTransportCenterCode(inbound.getLogisticsCenterCode());
        stockBatchInfo.setTransportCenterName(inbound.getLogisticsCenterName());
        stockBatchInfo.setWarehouseCode(inbound.getWarehouseCode());
        stockBatchInfo.setWarehouseName(inbound.getWarehouseName());
        //stockBatchInfo.setWarehouseType();
        stockBatchInfo.setSkuCode(product.getSkuCode());
        stockBatchInfo.setSkuName(product.getSkuName());
        stockBatchInfo.setCompanyCode(inbound.getCompanyCode());
        stockBatchInfo.setCompanyName(inbound.getCompanyName());
        stockBatchInfo.setTransportCenterCode(inbound.getLogisticsCenterCode());
        stockBatchInfo.setTransportCenterName(inbound.getLogisticsCenterName());
        stockBatchInfo.setWarehouseCode(inbound.getWarehouseCode());
        stockBatchInfo.setWarehouseName(inbound.getWarehouseName());
        //stockBatchInfo.setWarehouseType();
        stockBatchInfo.setSkuCode(product.getSkuCode());
        stockBatchInfo.setSkuName(product.getSkuName());
        stockBatchInfo.setTaxCost(product.getPreTaxPurchaseAmount());
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
                log.error(e.getMessage());
                throw new GroundRuntimeException("回传采购单失败失败");
            }
        }//如果是退货
        else if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode())) {
            try {
                //回传给退货
                SupplyReturnOrderMainReqVOReturn supplyReturnOrderMainReqVO = new SupplyReturnOrderMainReqVOReturn();
                SupplyReturnOrderInfoReqVOReturn supplyReturnOrderInfoReqVO = new SupplyReturnOrderInfoReqVOReturn();
                supplyReturnOrderInfoReqVO.setReturnOrderCode(inbound.getSourceOderCode());
                List<SupplyReturnOrderProductItemReqVOReturn> supplyReturnOrderProductItemReqVOS = BeanCopyUtils.copyList(list, SupplyReturnOrderProductItemReqVOReturn.class);
                supplyReturnOrderMainReqVO.setOrderItems(supplyReturnOrderProductItemReqVOS);
                supplyReturnOrderMainReqVO.setMainOrderInfo(supplyReturnOrderInfoReqVO);
                returnOder(supplyReturnOrderMainReqVO);
                inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
                inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
                int k = inboundDao.updateByPrimaryKeySelective(inbound);
            } catch (Exception e) {
                log.error(Global.ERROR, e);
            }

        }// 如果是调拨
        else if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode())) {
            //  回传给调拨
            inBoundReturn(inbound.getSourceOderCode());
            inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
            inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
            int k = inboundDao.updateByPrimaryKeySelective(inbound);
        } else if (inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())) {
            //如果是移库
            inBoundReturnMovement(inbound.getSourceOderCode());
            inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
            inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
            int k = inboundDao.updateByPrimaryKeySelective(inbound);
        } else {
            throw new GroundRuntimeException("无法回传匹配类型");
        }
    }

    /**
     * 回调采购接口
     */
    //@Override
    @Async("myTaskAsyncPool")
    public void returnPurchase(String sourceOderCode, List<InboundProduct> list, List<InboundBatch> batchList) {
        PurchaseStorageRequest purchaseStorage = new PurchaseStorageRequest();
        try {
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
            Inbound inbound = inboundDao.inboundCodeOrderLast(sourceOderCode);
            purchaseStorage.setCompanyName(inbound.getCompanyName());
            purchaseStorage.setCompanyCode(inbound.getCompanyCode());
            purchaseStorage.setCreateByName(inbound.getCreateBy());
            purchaseStorage.setPurchaseNum(inbound.getPurchaseNum());
            purchaseStorage.setPurchaseOrderCode(sourceOderCode);
            HttpResponse httpResponse = purchaseManageService.getWarehousing(purchaseStorage);
            if(httpResponse.getCode().equals("0")){
                log.info("入库单回传给采购接口成功");
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
            supplierCommonService.getInstance(allocationCode + "", HandleTypeCoce.ADD_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.SUCCESS__ALLOCATION.getName(), null, HandleTypeCoce.ADD_ALLOCATION.getName(), "系统自动");
                Allocation allocation = allocationMapper.selectByCode(allocationCode);
                //设置调拨状态
                allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
                allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
                //跟新调拨单状态
                int k = allocationMapper.updateByPrimaryKeySelective(allocation);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException("调拨单更改入库状态失败");
        }
    }

    /**
     * 回调采购接口
     * @param storageResultItemReqVo
     */
    @Override
    @Async("myTaskAsyncPool")
    public void returnOder(SupplyReturnOrderMainReqVOReturn storageResultItemReqVo) {
        log.error("异步回调采购接口");
        log.error("调用采购回调接口:[{}]",JSON.toJSONString(storageResultItemReqVo));
//        String url = urlConfig.PURCHASE_URL+"/purchase/returnorder/inBoundCallBack";
//        try {
//            HttpClient client = HttpClientHelper.getCurrentClient(HttpClient.post(url).json(storageResultItemReqVo));
//            HttpResponse result = client.action().result(HttpResponse.class);
//            if(!Objects.equals(result.getCode(), MsgStatus.SUCCESS)){
//                log.error("入库单回传给退货接口失败+回传实体为：[{}]",storageResultItemReqVo);
//                throw  new GroundRuntimeException("调用采购服务失败");
//            }
//        } catch (GroundRuntimeException e) {
//            log.error(Global.ERROR, e);
//            log.error("入库单回传给退货接口失败+回传实体为：[{}]",storageResultItemReqVo);
//        }
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
            supplierCommonService.getInstance(allocationCode + "", HandleTypeCoce.ADD_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.SUCCESS__MOVEMENT.getName(), null, HandleTypeCoce.ADD_MOVEMENT.getName(), "系统自动");
            Allocation allocation = allocationMapper.selectByCode(allocationCode);
            //设置调拨状态
            allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
            allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
            //跟新调拨单状态
            int k = allocationMapper.updateByPrimaryKeySelective(allocation);
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
        return Boolean.TRUE;
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
