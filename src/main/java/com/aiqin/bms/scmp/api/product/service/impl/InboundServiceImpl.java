package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.dao.InboundBatchDao;
import com.aiqin.bms.scmp.api.product.dao.InboundDao;
import com.aiqin.bms.scmp.api.product.dao.InboundProductDao;
import com.aiqin.bms.scmp.api.product.dao.MovementDao;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.converter.SupplyReturnOrderMainReqVO2InboundSaveConverter;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.*;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderMainReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.ResponseWms;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.*;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseStorageRequest;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private InboundDao inboundDao;

    @Autowired
    private InboundProductDao inboundProductDao;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

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
    private MovementDao movementDao;

    @Autowired
    private InboundBatchDao inboundBatchDao;

    @Autowired
    @Lazy(true)
    private PurchaseManageService purchaseManageService;
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
     *
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
            Inbound inbound =inboundDao.selectByPrimaryKey(id);
            BeanCopyUtils.copy(inbound,inboundResVo);
            List<InboundProduct> list = inboundProductDao.selectByInboundOderCode(inboundResVo.getInboundOderCode());
            try {
                inboundResVo.setList(BeanCopyUtils.copyList(list, InboundProductResVo.class));
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
     *
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String saveInbound(InboundReqSave reqVo) {
        int flag = 0;
        try {
            // 入库单转化主体保存实体
            Inbound inbound = new Inbound();
            BeanCopyUtils.copy(reqVo, inbound);
            // 获取编码 尺度
            EncodingRule rule = encodingRuleDao.getNumberingType(EncodingRuleType.IN_BOUND_CODE);
            inbound.setInboundOderCode(rule.getNumberingValue().toString());
            //插入入库单主表
            int insert = inboundDao.insert(inbound);
            log.info("插入入库单主表返回结果:{}", insert);

            //  转化入库单sku实体
            List<InboundProduct> list =BeanCopyUtils.copyList(reqVo.getList(), InboundProduct.class);
            list.stream().forEach(inboundItemReqVo -> inboundItemReqVo.setInboundOderCode(rule.getNumberingValue().toString()));
            //插入入库单商品表
            int insertProducts=inboundProductDao.insertBatch(list);
            log.info("插入入库单商品表返回结果:{}", insertProducts);

            //更新编码表
            encodingRuleDao.updateNumberValue(rule.getNumberingValue(),rule.getId());

            // 保存日志
            productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.ADD_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(),reqVo,HandleTypeCoce.ADD_INBOUND_ODER.getName(),new Date(),reqVo.getCreateBy(), reqVo.getRemark());
            InboundServiceImpl inboundService = (InboundServiceImpl) AopContext.currentProxy();
            inboundService.pushWms(inbound.getInboundOderCode(), inboundService);
               // 跟新数据库状态
            return inbound.getInboundOderCode();
        } catch (Exception e) {
            log.error("保存入库单接口错误");
            throw  new GroundRuntimeException("添加入库单失败");
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
    @Async("myTaskAsyncPool")
    @Transactional(rollbackFor = Exception.class)
    public void pushWms(String code  ,InboundServiceImpl inboundService){

        log.error("异步推送给wms");
         // 通过id查询 入库单主体
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Inbound  inbound = inboundDao.selectByCode(code);
        InboundWmsReqVO inboundWmsReqVO = new InboundWmsReqVO();
        BeanCopyUtils.copy(inbound, inboundWmsReqVO);
        List<InboundProductWmsReqVO> inboundProductWmsReqVOS =  inboundProductDao.selectMmsReqByInboundOderCode(inbound.getInboundOderCode());
        inboundWmsReqVO.setList(inboundProductWmsReqVOS);

        List<InboundBatchCallBackReqVo> inboundBatchCallBackReqVos = new ArrayList<>();
        try{
//            String url =urlConfig.WMS_API_URL+"/deppon/save/inbound";
//            HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.post(url).json(inboundWmsReqVO));
//
//            HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
//             String hello= JSON.toJSONString(orderDto.getData());
//             com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//             ResponseWms entiy = mapper.readValue(hello, ResponseWms.class);
//             if("0".equals(orderDto.getCode())){

                 // 设置wms编号
//                 inbound.setWmsDocumentCode(entiy.getUniquerRequestNumber());
                 //设置入库状态
                 inbound.setInboundStatusCode(InOutStatus.SEND_INOUT.getCode());
                 inbound.setInboundStatusName(InOutStatus.SEND_INOUT.getName());
                 // 跟新数据库

                 InboundCallBackReqVo inboundCallBackReqVo = new InboundCallBackReqVo();
                 inboundCallBackReqVo.setInboundOderCode(inbound.getInboundOderCode());
                 inboundCallBackReqVo.setInboundTime(new Date());
                 List<InboundProductCallBackReqVo> list = new ArrayList<>();
                 for (InboundProductWmsReqVO inboundProductWmsReqVO : inboundProductWmsReqVOS) {
                     InboundProductCallBackReqVo inboundProductCallBackReqVo = new InboundProductCallBackReqVo();
                     inboundProductCallBackReqVo.setLinenum(inboundProductWmsReqVO.getLinenum());
                     inboundProductCallBackReqVo.setSkuCode(inboundProductWmsReqVO.getSkuCode());
                     //TODO 入库数联改为预计数量的一半
                     Long num = 10l;
                     inboundProductCallBackReqVo.setPraInboundMainNum(num);
                     list.add(inboundProductCallBackReqVo);
                 }
                 inboundCallBackReqVo.setList(list);
//                 //TODO wms回传批次信息
//                 List<InboundBatch> inboundBatches = new ArrayList<>();
//                 //插入入库单商品表
//                 int insertBatchs=inboundBatchDao.insertInfo(inboundBatches);
//                 log.info("转化入库单sku批次实体表返回结果:{}", insertBatchs);
//                 for(InboundBatch inboundBatch : inboundBatches){
//                     InboundBatchCallBackReqVo inboundBatchCallBackReqVo = new InboundBatchCallBackReqVo();
//                     BeanUtils.copyProperties(inboundBatch, inboundBatchCallBackReqVo);
//                     inboundBatchCallBackReqVos.add(inboundBatchCallBackReqVo);
//                 }
//                 inboundCallBackReqVo.setInboundBatchCallBackReqVos(inboundBatchCallBackReqVos);

                 int s = inboundDao.updateByPrimaryKeySelective(inbound);
                 //保存日志
                 productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.PULL_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(), code, HandleTypeCoce.PULL_INBOUND_ODER.getName(), new Date(), inbound.getCreateBy(), null);

                 //调用回调接口
                 inboundService.workFlowCallBack(inboundCallBackReqVo);

                 log.error("推送保存日志修改状态,应该在回调接口前面执行");
                 return ;
//             }else{
//                 throw new RuntimeException("入库单传入wms失败");}
        }catch (Exception e){
             e.printStackTrace();
             log.error(e.getMessage());
             throw new RuntimeException("入库单传入wms失败");
        }

    }

    @Override
    @Async("myTaskAsyncPool")
    public void workFlowCallBack(InboundCallBackReqVo reqVo) {

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.error("入库单回调实体传入实体:[{}]",JSON.toJSONString(reqVo));
        //根据编码，查询入库单主体
        Inbound inbound = inboundDao.selectByCode(reqVo.getInboundOderCode());
        //设置默认实际数量
        inbound.setInboundTime(reqVo.getInboundTime());
        inbound.setPraInboundNum(0L);
        inbound.setPraMainUnitNum(0L);
        //实际含税总金额
        inbound.setPraTaxAmount(0L);
        // 实际税额
        inbound.setPraTax(0L);
        //实际不含税总金额
        inbound.setPraAmount(0L);
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
        }else if(Objects.equals( inbound.getInboundTypeCode(),InboundTypeEnum.ORDER.getCode())){

           stockChangeRequest.setOperationType(10);
        }else if(Objects.equals( inbound.getInboundTypeCode(),InboundTypeEnum.MOVEMENT.getCode())){
             // 如果是移库
            stockChangeRequest.setOperationType(8);
        }else {
            stockChangeRequest.setOperationType(9);
        }

        List<StockVoRequest> stockVoRequestList = new ArrayList<>();
//        List<StockBatchVoRequest> stockBatchVoRequestList = new ArrayList<>();

        for (InboundProductCallBackReqVo inboundProductCallBackReqVo : list) {

            ReturnInboundProduct returnInboundProduct = inboundProductDao.selectByLinenum(reqVo.getInboundOderCode(),inboundProductCallBackReqVo.getSkuCode() ,inboundProductCallBackReqVo.getLinenum());
            InboundProduct inboundProduct = new InboundProduct();
            // 复制旧的sku
            BeanCopyUtils.copy(returnInboundProduct,inboundProduct);
            inboundProduct.setPraInboundMainNum(inboundProductCallBackReqVo.getPraInboundMainNum()/Long.valueOf(inboundProduct.getInboundBaseContent()));
            inboundProduct.setPraInboundNum(inboundProductCallBackReqVo.getPraInboundMainNum());
            inboundProduct.setPraTaxPurchaseAmount(inboundProduct.getPreTaxPurchaseAmount());
            inboundProduct.setPraTaxAmount(inboundProduct.getPraTaxPurchaseAmount()*inboundProduct.getPraInboundMainNum());
            // 实际数量
            inbound.setPraInboundNum(inbound.getPraInboundNum()+inboundProduct.getPraInboundNum());
            inbound.setPraMainUnitNum(inbound.getPraMainUnitNum()+inboundProduct.getPraInboundMainNum());
            //实际含税总金额
            inbound.setPraTaxAmount(inbound.getPraTaxAmount()+inboundProduct.getPraTaxAmount());

            inbound.setPraAmount(inbound.getPraAmount()+ Calculate.computeNoTaxPrice(inboundProduct.getPraTaxPurchaseAmount(),returnInboundProduct.getTax())*inboundProduct.getPraInboundMainNum());

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
            stockVoRequest.setChangeNum(inboundProduct.getPraInboundNum());

            stockVoRequest.setDocumentNum(inbound.getInboundOderCode());
            stockVoRequest.setDocumentType(1);//0出库 1入库 2退供 3采购
            stockVoRequest.setSourceDocumentNum(inbound.getSourceOderCode());
            stockVoRequest.setSourceDocumentType(Integer.parseInt(inbound.getInboundTypeCode().toString()));
            stockVoRequest.setOperator(inbound.getUpdateBy());
            stockVoRequestList.add(stockVoRequest);
        }
        stockChangeRequest.setStockVoRequests(stockVoRequestList);

//        for(InboundBatchCallBackReqVo inboundBatchCallBackReqVo : inboundBatchCallBackReqVoList){
//            ReturnInboundBatch returnInboundBatch = inboundBatchDao.selectByLinenum(reqVo.getInboundOderCode(),inboundBatchCallBackReqVo.getSkuCode() ,inboundBatchCallBackReqVo.getLinenum());
//            InboundBatch inboundBatch = new InboundBatch();
//
//            // 复制旧的sku
//            BeanCopyUtils.copy(returnInboundBatch, inboundBatch);
//            // 实际数量
//            inboundBatch.setPraQty(inboundBatchCallBackReqVo.getPraQty());
//
//            //更新对应批次的实际数量
//            Integer i = inboundBatchDao.updateBatchInfoByInboundOderCodeAndLineNum(inboundBatch);
//            log.info("更新对应批次的实际数量返回结果:{}", i);
//            //  设置修改在途数加库存的单条sku的实体
//            StockBatchVoRequest stockBatchVoRequest = new StockBatchVoRequest();
//            //设置sku编码名称
//            stockBatchVoRequest.setSkuCode(inboundBatch.getSkuCode());
//            stockBatchVoRequest.setSkuName(inboundBatch.getSkuName());
//            //设置更改数量
//            stockBatchVoRequest.setChangeNum(inboundBatch.getPraQty());
//            stockBatchVoRequestList.add(stockBatchVoRequest);
//        }
//        stockChangeRequest.setStockBatchVoRequest(stockBatchVoRequestList);
        //保存日志
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.RETURN_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(),reqVo,HandleTypeCoce.RETURN_INBOUND_ODER.getName(),new Date(),inbound.getCreateBy(), null);

        try {
            HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){

            }else{
                log.error(httpResponse.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
        } catch (Exception e) {
            log.error("入库单改变在途数，增加库存失败:[{}]"+stockChangeRequest);
            e.printStackTrace();
            log.error(e.getMessage());
        }


        //计算实际税额
        inbound.setPraTax(inbound.getPraTaxAmount()-inbound.getPraAmount());
        //实际不含税总金额
        //修改入库单主题
          int k = inboundDao.updateByPrimaryKeySelective(inbound);

          // 回传给来源编号
        returnSource(inbound.getId());
        return ;
    }


    /**
     * 根据类型回传给来源单号状态
     * @param id
     */
    @Override
    @Async("myTaskAsyncPool")
    public void returnSource(Long id) {
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
               List< StorageResultItemReqVo> list1 = BeanCopyUtils.copyList(list,StorageResultItemReqVo.class);
               storageResultReqVo.setItemReqVos(list1);
               // 调用采购回调
               returnPurchase(storageResultReqVo);
               // 将入库单状态修改为完成
               inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
               inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
               int k = inboundDao.updateByPrimaryKeySelective(inbound);

           }catch (Exception e){
               e.printStackTrace();
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
               e.printStackTrace();
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
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            log.error("调用采购回调接口:[{}]", JSON.toJSONString(storageResultReqVo));
            e.printStackTrace();
        }
        try {
            PurchaseStorageRequest purchaseStorage = new PurchaseStorageRequest();
            List<PurchaseOrderProduct> purchaseOrderProducts = new ArrayList<>();
            List<StorageResultItemReqVo> storageResultItemReqVos = storageResultReqVo.getItemReqVos();
            PurchaseOrderProduct purchaseOrderProduct;
            for(StorageResultItemReqVo storageResultItemReqVo : storageResultItemReqVos){
                purchaseOrderProduct = new PurchaseOrderProduct();
                purchaseOrderProduct.setPurchaseOrderCode(storageResultReqVo.getPurchaseCode());
                purchaseOrderProduct.setActualSingleCount(Integer.parseInt(storageResultItemReqVo.getPraInboundNum().toString()));
                purchaseOrderProduct.setSkuCode(storageResultItemReqVo.getSkuCode());
                purchaseOrderProduct.setId(storageResultItemReqVo.getLinenum());
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
            e.printStackTrace();
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
                productCommonService.getInstance(allocationCode+"", HandleTypeCoce.SUCCESS__ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),allocationCode ,HandleTypeCoce.SUCCESS__ALLOCATION.getName());

                Allocation allocation = allocationMapper.selectByCode(allocationCode);
                //设置调拨状态
                allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
                allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
                //跟新调拨单状态
                int k = allocationMapper.updateByPrimaryKeySelective(allocation);
        } catch (Exception e) {
            e.printStackTrace();
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
//            e.printStackTrace();
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
            productCommonService.getInstance(allocationCode+"", HandleTypeCoce.SUCCESS__MOVEMENT.getStatus(), ObjectTypeCode.MOVEMENT_ODER.getStatus(),allocationCode ,HandleTypeCoce.SUCCESS__MOVEMENT.getName());

            Movement allocation = movementDao.selectByCode(allocationCode);
            //设置调拨状态
            allocation.setMovementStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
            allocation.setMovementStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
            //跟新调拨单状态
            int k = movementDao.updateByPrimaryKeySelective(allocation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroundRuntimeException("调拨单更改入库状态失败");
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

}
