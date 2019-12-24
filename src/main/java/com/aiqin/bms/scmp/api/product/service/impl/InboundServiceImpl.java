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
import com.aiqin.bms.scmp.api.product.domain.request.StockChangeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.*;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderMainReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.ResponseWms;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.*;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderDao;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseStorageRequest;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
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
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String saveInbound(InboundReqSave reqVo) {
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
            inboundService.pushWms(inbound.getInboundOderCode(), inboundService);
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
//    @Async("myTaskAsyncPool")
    @Transactional(rollbackFor = Exception.class)
    public void pushWms(String code  ,InboundServiceImpl inboundService){
        log.error("异步推送给wms");
        String url = "";
         // 通过id查询 入库单主体
        Inbound inbound = inboundDao.selectByCode(code);
        InboundWmsReqVO inboundWmsReqVO = new InboundWmsReqVO();
        BeanCopyUtils.copy(inbound, inboundWmsReqVO);
        List<InboundProductWmsReqVO> inboundProductWmsReqVOS =  inboundProductDao.selectMmsReqByInboundOderCode(inbound.getInboundOderCode());
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
        if(inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())){
            //采购日志列表
            if (inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())) {
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
            }
            PurchaseOrderDetails order = inboundDao.selectCreateById(inbound.getInboundOderCode());
            inboundWmsReqVO.setCreateById(order.getCreateById());
            inboundWmsReqVO.setCreateByName(order.getCreateByName());
            inboundWmsReqVO.setRemark(order.getRemark());
            log.info("向wms发送入库单的参数是：{}", JSON.toJSON(inboundWmsReqVO));
            url =urlConfig.WMS_API_URL+"/wms/save/purchase/inbound";
            HttpClient httpClient = HttpClient.post(url).json(inboundWmsReqVO).timeout(200000);
            HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
            if(orderDto.getCode().equals(MessageId.SUCCESS_CODE)) {
                ResponseWms responseWms = JsonUtil.fromJson(JsonUtil.toJson(orderDto.getData()), ResponseWms.class);
                if ("0".equals(responseWms.getResultCode())) {
                    //设置wms编号
//                    inbound.setWmsDocumentCode(responseWms.getUniquerRequestNumber());
                    log.info("入库单传入dl成功");
                } else {
                    log.error("入库单传入wms失败:{}", responseWms.getReason());
                    throw new GroundRuntimeException(String.format("入库单传入wms失败:%s",responseWms.getReason()));
                }
            } else {
                log.error("入库单传入wms失败:{}", orderDto.getMessage());
                throw new GroundRuntimeException(String.format("入库单传入wms失败:%s",orderDto.getMessage()));
            }
        //移库
        }else{
            if(inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode())) {
                //记录调拨待入库
                supplierCommonService.getInstance(inbound.getSourceOderCode() + "", HandleTypeCoce.ADD_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.INBOUND_ALLOCATION.getName(), null, HandleTypeCoce.ADD_ALLOCATION.getName(), "系统自动");
            }
            if(inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode() )) {
                //记录移库待入库
                supplierCommonService.getInstance(inbound.getSourceOderCode() + "", HandleTypeCoce.ADD_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), HandleTypeCoce.INBOUND_MOVEMENT.getName(), null, HandleTypeCoce.ADD_MOVEMENT.getName(), "系统自动");
            }
            if(inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())) {
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
        if(inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())){
            inboundService.workFlowCallBack(inboundCallBackReqVo);
        }
    }

    @Override
//    @Async("myTaskAsyncPool")
    public void workFlowCallBack(InboundCallBackReqVo reqVo) {

        log.info("入库单回调实体传入实体:[{}]",JSON.toJSONString(reqVo));
        //根据编码，查询入库单主体
//        Inbound inbound = inboundDao.selectByCode(reqVo.getInboundOderCode());
        Inbound inbound = inboundDao.selectById(reqVo.getId().toString());
        //设置默认实际数量
        inbound.setInboundTime(reqVo.getInboundTime());
        inbound.setPraInboundNum(0L);
        inbound.setPraMainUnitNum(0L);
        //实际含税总金额
        inbound.setPraTaxAmount(big);
        // 实际税额
        inbound.setPraTax(big);
        //实际不含税总金额
        inbound.setPraAmount(big);
        // 设置已回传状态
        inbound.setInboundStatusCode(InOutStatus.RECEIVE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.RECEIVE_INOUT.getName());

        //更新sku 数量
        List<InboundProductCallBackReqVo> list = reqVo.getList();
        //批次
//        List<InboundBatchCallBackReqVo> inboundBatchCallBackReqVoList = reqVo.getInboundBatchCallBackReqVos();

        // 减在途数并且增加库存 实体
        StockChangeRequest  stockChangeRequest = new StockChangeRequest();
        stockChangeRequest.setOrderCode(inbound.getInboundOderCode());
        //如果不是调拨在途数 状态则是9，退货是10.调拨是8
        if(Objects.equals( inbound.getInboundTypeCode(),InboundTypeEnum.ALLOCATE.getCode())){
             stockChangeRequest.setOperationType(8);
        }else if(Objects.equals( inbound.getInboundTypeCode(),InboundTypeEnum.MOVEMENT.getCode())){
             // 如果是移库
            stockChangeRequest.setOperationType(10);
        }else {
            stockChangeRequest.setOperationType(9);
        }

        List<StockVoRequest> stockVoRequestList = new ArrayList<>();
//        List<StockBatchVoRequest> stockBatchVoRequestList = new ArrayList<>();

        for (InboundProductCallBackReqVo inboundProductCallBackReqVo : list) {

            ReturnInboundProduct returnInboundProduct = inboundProductDao.selectByLinenum(inbound.getInboundOderCode(),inboundProductCallBackReqVo.getSkuCode() ,inboundProductCallBackReqVo.getLinenum());
            InboundProduct inboundProduct = new InboundProduct();
            // 复制旧的sku
            BeanCopyUtils.copy(returnInboundProduct,inboundProduct);
            inboundProduct.setPraInboundMainNum(inboundProductCallBackReqVo.getPraInboundMainNum());
            inboundProduct.setPraInboundNum(inboundProductCallBackReqVo.getPraInboundMainNum() / Long.valueOf(inboundProduct.getInboundBaseContent()));
            //实际含税进价
            inboundProduct.setPraTaxPurchaseAmount(inboundProduct.getPreTaxPurchaseAmount());
            //单个SKU的实际含税金额
            inboundProduct.setPraTaxAmount(inboundProduct.getPraTaxPurchaseAmount().multiply(BigDecimal.valueOf(inboundProduct.getPraInboundMainNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
            // 实际数量
            inbound.setPraInboundNum(inbound.getPraInboundNum()+inboundProduct.getPraInboundNum());
            inbound.setPraMainUnitNum(inbound.getPraMainUnitNum() + inboundProduct.getPraInboundMainNum());
            //实际含税总金额
            inbound.setPraTaxAmount(inbound.getPraTaxAmount().add(inboundProduct.getPraTaxAmount()));
            BigDecimal amount = Calculate.computeNoTaxPrice(inboundProduct.getPraTaxAmount(), returnInboundProduct.getTax());
            inbound.setPraAmount(inbound.getPraAmount().add(amount));
            //实际税额
            inbound.setPraTax(inbound.getPraTaxAmount().subtract(inbound.getPraAmount()));

            //更新sku编号
            inboundProductDao.updateByPrimaryKeySelective(inboundProduct);
            //  设置修改在途数加库存的单条sku的实体
            StockVoRequest stockVoRequest = new StockVoRequest();
            //设置公司编码名称
            stockVoRequest.setCompanyCode(inbound.getCompanyCode());
            stockVoRequest.setCompanyName(inbound.getCompanyName());
            //设置物流中心编码名称
            stockVoRequest.setTransportCenterCode(inbound.getLogisticsCenterCode());
            stockVoRequest.setTransportCenterName(inbound.getLogisticsCenterName());
            //设置库房编码名称
            stockVoRequest.setWarehouseCode(inbound.getWarehouseCode());
            stockVoRequest.setWarehouseName(inbound.getWarehouseName());
            //设置sku编码名称
            stockVoRequest.setSkuCode(inboundProduct.getSkuCode());
            stockVoRequest.setSkuName(inboundProduct.getSkuName());
            //设置更改数量
            stockVoRequest.setChangeNum(inboundProduct.getPraInboundMainNum());

            stockVoRequest.setDocumentNum(inbound.getInboundOderCode());
            stockVoRequest.setDocumentType(1);//0出库 1入库 2退供 3采购
            stockVoRequest.setSourceDocumentNum(inbound.getSourceOderCode());
            stockVoRequest.setSourceDocumentType(Integer.parseInt(inbound.getInboundTypeCode().toString()));
            stockVoRequest.setOperator(inbound.getCreateBy());
            stockVoRequest.setTaxRate(returnInboundProduct.getTax());
            if(inboundProductCallBackReqVo.getProductType() != 2){
                stockVoRequest.setNewPurchasePrice(inboundProduct.getPraTaxPurchaseAmount());
            }else{
                stockVoRequest.setNewPurchasePrice(BigDecimal.valueOf(0));
            }
            stockVoRequest.setNewDelivery(inbound.getSupplierCode());
            stockVoRequest.setNewDeliveryName(inbound.getSupplierName());
            stockVoRequestList.add(stockVoRequest);
        }
        stockChangeRequest.setStockVoRequests(stockVoRequestList);
        //保存日志
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.RETURN_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(),reqVo,HandleTypeCoce.RETURN_INBOUND_ODER.getName(),new Date(),inbound.getCreateBy(), null);

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
            HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){
                log.info("操作库存成功");
            }else{
                log.error(httpResponse.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
        } catch (Exception e) {
            log.error("入库单改变在途数，增加库存失败:[{}],异常信息:{}",stockChangeRequest,e.getMessage());
            throw  new GroundRuntimeException("库存操作失败");
        }
        //计算实际税额
        inbound.setPraTax(inbound.getPraTaxAmount().subtract(inbound.getPraAmount()));
        //实际不含税总金额
        //修改入库单主表
        int k = inboundDao.updateByPrimaryKeySelective(inbound);
        log.info("入库更新条数:{}",k);
          // 回传给来源编号
        returnSource(inbound.getId());
    }

    /**
     * 根据类型回传给来源单号状态
     * @param id
     */
    @Override
    @Async("myTaskAsyncPool")
    public void returnSource(Long id) {
         // 查询入库单主体
        Inbound inbound = inboundDao.selectByPrimaryKey(id);
        //查询sku
        List<InboundProduct> list = inboundProductDao.selectByInboundOderCode(inbound.getInboundOderCode());
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.COMPLETE_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(),id,HandleTypeCoce.COMPLETE_INBOUND_ODER.getName(),new Date(),inbound.getCreateBy(), null);
        //如果是采购
       if(inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode() )){
           try {
               StorageResultReqVo storageResultReqVo = new StorageResultReqVo();
               storageResultReqVo.setPurchaseCode(inbound.getSourceOderCode());
               storageResultReqVo.setUserName("张云童");
               storageResultReqVo.setActualAmount(inbound.getPraTaxAmount());
               storageResultReqVo.setActualNum(inbound.getPraInboundNum());
               storageResultReqVo.setNoTaxActualAmount(inbound.getPraAmount());
               storageResultReqVo.setSaleUnitActualNum(inbound.getPraMainUnitNum());
               List<StorageResultItemReqVo> list1 = BeanCopyUtils.copyList(list,StorageResultItemReqVo.class);
               storageResultReqVo.setItemReqVos(list1);
               // 调用采购回调
               returnPurchase(storageResultReqVo);
               // 将入库单状态修改为完成
               inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
               inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());

               inbound.setInboundTime(new Date());
               int k = inboundDao.updateByPrimaryKeySelective(inbound);

               OperationLog operationLog = new OperationLog();
               PurchaseOrder purchaseOrder = new PurchaseOrder();
               purchaseOrder.setPurchaseOrderCode(inbound.getSourceOderCode());
               PurchaseOrder resultPurchaseOrder = purchaseOrderDao.purchaseOrderInfo(purchaseOrder);
               if(resultPurchaseOrder != null) {
                   operationLog.setOperationId(resultPurchaseOrder.getPurchaseOrderId());
                   operationLog.setCreateByName(inbound.getCreateBy());
                   operationLog.setOperationType(PurchaseOrderLogEnum.WAREHOUSING_FINISH.getCode());
                   operationLog.setOperationContent("入库申请单" + inbound.getInboundOderCode() + "，入库完成");
                   operationLog.setCreateTime(new Date());
                   operationLog.setRemark(resultPurchaseOrder.getApplyTypeForm());
                   purchaseManageService.addLog(operationLog);
               }
           }catch (Exception e){
               log.error(Global.ERROR, e);
               log.error(e.getMessage());
               throw new GroundRuntimeException("回传采购单失败失败");
           }
       }//如果是退货
       else if(inbound.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode() )){
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
       else if(inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode() )){
           //  回传给调拨
             inBoundReturn(inbound.getSourceOderCode());
           inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
           inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
           int k = inboundDao.updateByPrimaryKeySelective(inbound);
       }else if(inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode() )){
           //如果是移库
           inBoundReturnMovement(inbound.getSourceOderCode());
           inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
           inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
           int k = inboundDao.updateByPrimaryKeySelective(inbound);
       }else  {
           throw new GroundRuntimeException("无法回传匹配类型");
       }
    }

    /**
     * 回调采购接口
     * @param storageResultReqVo
     */
    @Override
    @Async("myTaskAsyncPool")
    public void returnPurchase(StorageResultReqVo storageResultReqVo) {
        try {
            PurchaseStorageRequest purchaseStorage = new PurchaseStorageRequest();
            List<PurchaseOrderProduct> purchaseOrderProducts = new ArrayList<>();
            List<StorageResultItemReqVo> storageResultItemReqVos = storageResultReqVo.getItemReqVos();
            PurchaseOrderProduct purchaseOrderProduct;
            for(StorageResultItemReqVo storageResultItemReqVo : storageResultItemReqVos){
                purchaseOrderProduct = new PurchaseOrderProduct();
                purchaseOrderProduct.setPurchaseOrderCode(storageResultReqVo.getPurchaseCode());
                purchaseOrderProduct.setActualSingleCount(Integer.parseInt(storageResultItemReqVo.getPraInboundMainNum().toString()));
                purchaseOrderProduct.setSkuCode(storageResultItemReqVo.getSkuCode());
                purchaseOrderProduct.setLinnum(storageResultItemReqVo.getLinenum().intValue());
                purchaseOrderProducts.add(purchaseOrderProduct);
            }
            List<Inbound> inboundList = inboundDao.selectTimeAndSatusBySourchAndNum(storageResultReqVo.getPurchaseCode());
            if(CollectionUtils.isNotEmpty(inboundList)){
                Inbound inbound = inboundList.get(inboundList.size()-1);
                purchaseStorage.setCompanyName(inbound.getCompanyName());
                purchaseStorage.setCompanyCode(inbound.getCompanyCode());
                purchaseStorage.setCreateByName(inbound.getCreateBy());
                purchaseStorage.setPurchaseNum(inbound.getPurchaseNum());
            }
            purchaseStorage.setPurchaseOrderCode(storageResultReqVo.getPurchaseCode());
            purchaseStorage.setOrderList(purchaseOrderProducts);
            HttpResponse httpResponse = purchaseManageService.getWarehousing(purchaseStorage);
            if(httpResponse.getCode().equals("0")){
                log.info("入库单回传给采购接口成功");
            }else {
                log.error("入库单回传给采购接口失败");
            }
        } catch (GroundRuntimeException e) {
            log.error(Global.ERROR, e);
            log.error("入库单回传给采购接口失败+回传实体为：[{}]",storageResultReqVo);
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
        Integer integer = inboundBatchDao.insertInfo(batchList);
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
