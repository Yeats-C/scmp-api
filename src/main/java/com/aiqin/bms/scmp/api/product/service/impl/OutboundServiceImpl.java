package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.converter.AllocationResVo2InboundReqVoConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.MovementResVo2InboundReqVoConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.OrderVo2OutBoundConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.ReturnSupply2outboundSaveConverter;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.order.OrderInfo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.*;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyToOutBoundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.ResponseWms;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundProductWmsReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementProductResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.*;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductBatchMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.product.service.api.SupplierApiService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 描述:
 *
 * @author Kt.w
 * @date 2019/1/5
 * @Version 1.0
 * @since 1.0
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
    private AllocationProductBatchMapper allocationProductBatchMapper;
    @Autowired
    private SupplierApiService supplierApiService;

    @Autowired
    private MovementDao movementDao;

    @Autowired
    private MovementProductDao  movementProductDao;

    @Autowired
    private OutboundBatchDao outboundBatchDao;
    /**
     * 分页查询以及搜索
     * @param vo
     * @return
     */
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

    /**
     * 查询出库信息
     *
     * @param boundRequest
     * @return
     */
    @Override
    public List<OutboundResponse> selectOutBoundInfoByBoundSearch(BoundRequest boundRequest) {
        try {
            LOGGER.info("查询出库信息");
            List<String> outboundOderCodeList = new ArrayList<>();
            List<OutboundProduct> outboundProductList = new ArrayList<>();
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
     *
     * @param stockReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Integer saveOutBoundInfo(OutboundReqVo stockReqVO) {
        int flag = 0;
        String outboundOderCode = null;
        try {
            //编码生成
            EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.OUT_BOUND_CODE);
            Outbound outbound =  new Outbound();
            BeanCopyUtils.copy(stockReqVO,outbound);
            outboundOderCode = String.valueOf(numberingType.getNumberingValue());
            outbound.setOutboundOderCode(outboundOderCode);

            List<OutboundProduct> outboundProducts = BeanCopyUtils.copyList(stockReqVO.getList(),OutboundProduct.class);
            outboundProducts.stream().forEach(outboundProduct ->outboundProduct.setOutboundOderCode(numberingType.getNumberingValue().toString()) );
            int i = outboundDao.insertSelective(outbound);

            List<OutboundBatch> outboundBatches = BeanCopyUtils.copyList(stockReqVO.getOutboundBatches(),OutboundBatch.class);
            outboundBatches.stream().forEach(outboundBatch ->outboundBatch.setOutboundOderCode(numberingType.getNumberingValue().toString()) );

            int j = outboundProductDao.insertBatch(outboundProducts);
            int m = outboundBatchDao.insertInfo(outboundBatches);
            //更新编码
            encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());

            // 保存日志
            productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.ADD_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(),stockReqVO,HandleTypeCoce.ADD_OUTBOUND_ODER.getName(),new Date(),stockReqVO.getCreateBy());

            //  调用推送接口
            OutboundServiceImpl inboundService = (OutboundServiceImpl) AopContext.currentProxy();
            inboundService.pushWms(outbound.getOutboundOderCode(),inboundService);
            // 跟新数据库状态
            if(i > 0 && j > 0 && m > 0){
                flag = 1;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroundRuntimeException("保存出库单失败");
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
        String outboundOderCode = null;
        try {
            //编码生成
            EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.OUT_BOUND_CODE);
            Outbound outbound =  new Outbound();
            BeanCopyUtils.copy(stockReqVO,outbound);
            outboundOderCode = String.valueOf(numberingType.getNumberingValue());
            outbound.setOutboundOderCode(outboundOderCode);

            List<OutboundProduct> outboundProducts = BeanCopyUtils.copyList(stockReqVO.getList(), OutboundProduct.class);
            outboundProducts.stream().forEach(outboundProduct -> outboundProduct.setOutboundOderCode(numberingType.getNumberingValue().toString()));
            int i = outboundDao.insertSelective(outbound);
            log.info("插入出库单主表返回结果", i);

            List<OutboundBatch> outboundBatches = BeanCopyUtils.copyList(stockReqVO.getOutboundBatches(), OutboundBatch.class);
            outboundBatches.stream().forEach(outboundBatch ->outboundBatch.setOutboundOderCode(numberingType.getNumberingValue().toString()) );
            int j = outboundProductDao.insertBatch(outboundProducts);
            log.info("插入出库单商品表返回结果", j);

            int m = outboundBatchDao.insertInfo(outboundBatches);
            log.info("插入出库单商品批次表返回结果", m);

            //更新编码
            encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(),numberingType.getId());
            // 保存日志
            productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.ADD_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(),stockReqVO,HandleTypeCoce.ADD_OUTBOUND_ODER.getName(),new Date(),stockReqVO.getCreateBy());

            //  调用推送接口
            OutboundServiceImpl inboundService = (OutboundServiceImpl) AopContext.currentProxy();
            inboundService.pushWms(outbound.getOutboundOderCode(),inboundService);

            return outboundOderCode;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GroundRuntimeException("保存出库单失败");
        }
    }

    /**
     * 根据原始单据号查询出库信息
     *
     * @param sourceOderCode
     * @return
     */
    @Override
    public List<UpdateStockReqVo> selectUpdateStockInfoBySourceOrderCode(String sourceOderCode, String stockStatusCode) {
        return outboundDao.selectUpdateStockInfoBySourceOrderCode(sourceOderCode,stockStatusCode);
    }

    /**
     * 更新出库信息
     * @param reqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOutBoundInfo(UpdateOutBoundReqVO reqVO) {
        try {
            //更新出库信息
            reqVO.setCurrentDate(new Date());
            outboundDao.updateOutboundInfo(reqVO);
            //如果库存状态是"减库存并解锁" 需要更新明细信息
            if(StockStatusEnum.REDUCE_UNLOCK_STOCK.getCode() == reqVO.getStockStatusCode()){
                //根据原始单据号查询出库商品信息
                List<OutboundProduct> outboundProducts = outboundProductDao.
                        selectOutboundProductInfoBySourceOrderCode(reqVO.getSourceOrderCode());
                //获取传过来的信息
                List<UpdateOutboundProductReqVO> updateOutboundProductReqVOs = reqVO.getUpdateOutboundProductReqVOs();
                for (UpdateOutboundProductReqVO updateOutboundProductReqVO : updateOutboundProductReqVOs) {
                    for (OutboundProduct outboundProduct : outboundProducts) {
                        if(updateOutboundProductReqVO.getSkuCode().equals(outboundProduct.getSkuCode())){
                            updateOutboundProductReqVO.setId(outboundProduct.getId());
                            updateOutboundProductReqVO.setCurrentDate(new Date());
                        }
                    }
                }
                outboundProductDao.updateBatch(updateOutboundProductReqVOs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ResultCode.OUTBOUND_SAVE_ERROR);
        }
    }

    /**
     * 根据id查询出库单
     * @param id
     * @return
     */
    @Override
    public OutboundResVo view(Long id) {

        OutboundResVo outboundResVo = new OutboundResVo();
        Outbound outbound = outboundDao.selectByPrimaryKey(id);
        BeanCopyUtils.copy(outbound,outboundResVo);
        if(null!=outboundResVo){
            List<OutboundProduct> list = outboundProductDao.selectByOutboundOderCode(outboundResVo.getOutboundOderCode());
            try{
                outboundResVo.setList(BeanCopyUtils.copyList(list, OutboundProductResVo.class));
                if (null != outboundResVo) {
                    //获取操作日志
                    OperationLogVo operationLogVo = new OperationLogVo();
                    operationLogVo.setPageNo(1);
                    operationLogVo.setPageSize(100);
                    operationLogVo.setObjectType(ObjectTypeCode.OUTBOUND_ODER.getStatus());
                    operationLogVo.setObjectId(outboundResVo.getOutboundOderCode());
                    List<LogData> pageList = productOperationLogService.getLogType(operationLogVo);
                    outboundResVo.setLogDataList(pageList);
                }
                return outboundResVo;
            }catch (Exception e){
                log.error("出库单sku转化实体失败");
                throw new GroundRuntimeException(e.getMessage());
            }
        }else {
            throw new GroundRuntimeException("根据id查询不到数据信息");
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
        OutboundReqVo convert = new ReturnSupply2outboundSaveConverter(skuService).convert(req);
        //保存
        return saveOutBoundInfo(convert);
    }



    /**
     * 入库单推送给wms
     * @param code
     * @return
     */
    @Override
    @Async("taskProductExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void pushWms(String  code,OutboundServiceImpl inboundService){
        log.error("异步推送给wms");
        // 通过id查询 入库单主体
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OutboundWmsReqVO outboundWmsReqVO = new OutboundWmsReqVO();
        Outbound outbound = outboundDao.selectByCode(code);
        BeanCopyUtils.copy(outbound,outboundWmsReqVO);
        List<InboundProductWmsReqVO> inboundProductWmsReqVOS =  outboundProductDao.selectMmsReqByOutboundOderCode(outbound.getOutboundOderCode());
        outboundWmsReqVO.setList(inboundProductWmsReqVOS);
        try{
            String url =urlConfig.WMS_API_URL+"/deppon/save/order";
            HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.post(url).json(outboundWmsReqVO));

            HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
            String hello= JSON.toJSONString(orderDto.getData());
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            ResponseWms entiy = mapper.readValue(hello, ResponseWms.class);
            if("0".equals(orderDto.getCode())){

                // 设置wms编号
                outbound.setWmsDocumentCode(entiy.getUniquerRequestNumber());
                //设置入库状态
//                outbound1.setOutboundStatusCode(InOutStatus.SEND_INOUT.getCode());
//                outbound1.setOutboundStatusName(InOutStatus.SEND_INOUT.getName());
                // 跟新数据库
                int s = outboundDao.updateByPrimaryKeySelective(outbound);
                OutboundCallBackReqVo outboundCallBackReqVo = new OutboundCallBackReqVo();

                outboundCallBackReqVo.setOutboundOderCode(outbound.getOutboundOderCode());

                List<OutboundProductCallBackReqVo> list = new ArrayList<>();
                for (InboundProductWmsReqVO inboundProductWmsReqVO : inboundProductWmsReqVOS) {
                    OutboundProductCallBackReqVo outboundProductCallBackReqVo = new OutboundProductCallBackReqVo();
                    outboundProductCallBackReqVo.setLinenum(inboundProductWmsReqVO.getLinenum());
                    outboundProductCallBackReqVo.setSkuCode(inboundProductWmsReqVO.getSkuCode());
                    outboundProductCallBackReqVo.setPraOutboundMainNum(inboundProductWmsReqVO.getPreInboundMainNum());
                    list.add(outboundProductCallBackReqVo);
                }
                outboundCallBackReqVo.setList(list);
                //保存日志
                productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.PULL_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(),outbound,HandleTypeCoce.PULL_OUTBOUND_ODER.getName(),new Date(),outbound.getCreateBy());

                workFlowCallBack(outboundCallBackReqVo);
                return ;
            }else{ throw new RuntimeException("入库单传入wms失败");}
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("入库单传入wms失败");
        }
    }
    /**
     * 出库单回调接口
     * @param reqVo
     * @return
     */
    @Override
    @Async("taskProductExecutor")
    public int workFlowCallBack(OutboundCallBackReqVo reqVo) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(" 出库单回传实体为 ：[{}]" + reqVo);
        try{ // 根据入库单编号查询旧的入库单主体

            Outbound outbound = outboundDao.selectByCode(reqVo.getOutboundOderCode());

            //保存日志
            productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.RETURN_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(),outbound,HandleTypeCoce.RETURN_OUTBOUND_ODER.getName(),new Date(),outbound.getCreateBy());

            //设置状态
            outbound.setOutboundStatusCode(InOutStatus.RECEIVE_INOUT.getCode());
            outbound.setOutboundStatusName(InOutStatus.RECEIVE_INOUT.getName());
            //设置出库时间
            outbound.setOutboundTime(reqVo.getOutboundTime());
            //设置实际出库数量和出库主数量
            outbound.setPraOutboundNum(0L);
            outbound.setPraMainUnitNum(0L);
            //设置实际含税总金额。税额。不含税总金额
            outbound.setPraTaxAmount(0L);
            outbound.setPraTax(0L);
            outbound.setPraAmount(0L);
            outbound.setOutboundTime(new Date());
            // 设置解锁并且减少库存

            // 减在途数并且增加库存 实体
            StockChangeRequest  stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOrderCode(outbound.getOutboundOderCode());
            stockChangeRequest.setOperationType(2);

            List<StockVoRequest> stockVoRequestList = new ArrayList<>();

            for (OutboundProductCallBackReqVo outboundProductCallBackReqVo : reqVo.getList()) {
                // 查询旧的sku，以及销项，进项税率
                ReturnOutboundProduct returnOutboundProduct = outboundProductDao.selectByLinenum(outbound.getOutboundOderCode(),outboundProductCallBackReqVo.getSkuCode(),outboundProductCallBackReqVo.getLinenum());
                OutboundProduct outboundProduct = new OutboundProduct();
                //copy 实体
                BeanCopyUtils.copy(returnOutboundProduct,outboundProduct);

                if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode())){
                    //如果是订单则使用销项税率
                    returnOutboundProduct.setTax(returnOutboundProduct.getOutputTaxRate());
                }else {
                    //调拨，退供使用进项税率
                    returnOutboundProduct.setTax(returnOutboundProduct.getInputTaxRate());
                }
                //设置实际数量，实际数量
                outboundProduct.setPraOutboundNum(outboundProductCallBackReqVo.getPraOutboundMainNum()/Long.valueOf(returnOutboundProduct.getOutboundBaseContent()));
                outboundProduct.setPraOutboundMainNum(outboundProductCallBackReqVo.getPraOutboundMainNum());
                //设置实际含税单价，实际含税总价
                outboundProduct.setPraTaxPurchaseAmount(returnOutboundProduct.getPreTaxPurchaseAmount());
                outboundProduct.setPraTaxAmount(outboundProduct.getPraOutboundMainNum()*outboundProduct.getPraTaxPurchaseAmount());

                // 修改单条 sku
                int k = outboundProductDao.updateByPrimaryKeySelective(outboundProduct);

                //累加总的出库数量，出库主数量
                outbound.setPraOutboundNum(outbound.getPraOutboundNum()+outboundProduct.getPraOutboundNum());
                outbound.setPraMainUnitNum(outbound.getPraMainUnitNum()+outboundProduct.getPraOutboundMainNum());
                //累加总的含税总金额  税额 不含税总金额
                outbound.setPraTaxAmount(outbound.getPraTaxAmount()+outboundProduct.getPraTaxAmount());
                //不含税总金额
                outbound.setPraAmount(outbound.getPraAmount()+ Calculate.computeNoTaxPrice(outboundProduct.getPraTaxPurchaseAmount(),returnOutboundProduct.getTax())*outboundProduct.getPraOutboundMainNum());
                //  设置修改减少库存sku实体
                StockVoRequest stockVoRequest = new StockVoRequest();
                //设置公司编码名称
                stockVoRequest.setCompanyCode(outbound.getCompanyCode());
                stockVoRequest.setCompanyName(outbound.getCompanyName());
                //设置物流中心编码名称
                stockVoRequest.setTransportCenterCode(outbound.getLogisticsCenterCode());
                stockVoRequest.setTransportCenterName(outbound.getLogisticsCenterName());
                //设置库房编码名称
                stockVoRequest.setWarehouseCode(outbound.getWarehouseCode());
                stockVoRequest.setWarehouseName(outbound.getWarehouseName());
                //设置sku编码名称
                stockVoRequest.setSkuCode(outboundProduct.getSkuCode());
                stockVoRequest.setSkuName(outboundProduct.getSkuName());
                //设置更改数量
                stockVoRequest.setChangeNum(outboundProduct.getPraOutboundMainNum());
                stockVoRequestList.add(stockVoRequest);
            }
            stockChangeRequest.setStockVoRequests(stockVoRequestList);

            // 解锁并且减库存
           HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
           if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){
               log.info("减并解锁库存成功，库存详情为:{}", stockChangeRequest);
           }else{
               log.error(httpResponse.getMessage());
               throw  new GroundRuntimeException("库存操作失败");
           }

            //计算税额
            outbound.setPraTax(outbound.getPraTaxAmount()-outbound.getPraAmount());
            //修改实际的入库单主体
            int k = outboundDao.updateByPrimaryKeySelective(outbound);

            // 回传类型
            returnSource(outbound.getId());
            return k;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroundRuntimeException("修改库存失败");
        }
    }

    /**
     * 根据类型回传给来源单号状态
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async("taskProductExecutor")
    public void returnSource(Long id){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Outbound outbound = outboundDao.selectByPrimaryKey(id);
        List<OutboundProduct>list = outboundProductDao.selectByOutboundOderCode(outbound.getOutboundOderCode());
        productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.COMPLETE_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(), id, HandleTypeCoce.COMPLETE_OUTBOUND_ODER.getName(), new Date(), outbound.getCreateBy());


        //如果是订单
        if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode() )){
            try {
                SupplyOrderInfoReqVO supplyOrderInfoReqVO = new SupplyOrderInfoReqVO();
                supplyOrderInfoReqVO.setOrderCode(outbound.getSourceOderCode());
                List<SupplyOrderProductItemReqVO> orderItems = BeanCopyUtils.copyList(list,SupplyOrderProductItemReqVO.class);
                supplyOrderInfoReqVO.setOrderItems(orderItems);
                // 调用订单接口
                returnOder(supplyOrderInfoReqVO);
                //修改出库单完成状态
                outbound.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
                outbound.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
                int k = outboundDao.updateByPrimaryKeySelective(outbound);
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
                throw new GroundRuntimeException("回传订单失败");
            }


        }//如果是退供
        else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode() )){
            try {
                ReturnStorageResultReqVo supplyOrderInfoReqVO = new ReturnStorageResultReqVo();
                supplyOrderInfoReqVO.setReturnSupplyCode(outbound.getSourceOderCode());
                supplyOrderInfoReqVO.setUserName("张昀童");
                supplyOrderInfoReqVO.setActualAmount(outbound.getPraTaxAmount());
                supplyOrderInfoReqVO.setActualNum(outbound.getPraOutboundNum());
                supplyOrderInfoReqVO.setSaleUnitActualNum(outbound.getPraMainUnitNum());
                supplyOrderInfoReqVO.setNoTaxActualAmount(outbound.getPraAmount());
                List<ReturnStorageResultItemReqVo> orderItems = BeanCopyUtils.copyList(list,ReturnStorageResultItemReqVo.class);
                supplyOrderInfoReqVO.setItemReqVos(orderItems);
                // 回传给退供
                returnStorageResult(supplyOrderInfoReqVO);

                outbound.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
                outbound.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
                int k = outboundDao.updateByPrimaryKeySelective(outbound);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new GroundRuntimeException("回传退供单失败");
            }

        }// 如果是调拨
        else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode() )){

            // 回传给调拨
            try {
                Allocation allocation = allocationMapper.selectByCode(outbound.getSourceOderCode());
                //设置调拨状态
                allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getStatus());
                allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getName());

                productCommonService.getInstance(allocation.getAllocationCode()+"", HandleTypeCoce.SUCCESS_OUTBOUND_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),allocation.getAllocationCode() ,HandleTypeCoce.SUCCESS_OUTBOUND_ALLOCATION.getName());

                //跟新调拨单状态
                int k = allocationMapper.updateByPrimaryKeySelective(allocation);
                //生成入库单
                createInbound(allocation.getId());
            } catch (Exception e) {
                e.printStackTrace();
                throw new GroundRuntimeException("调拨单更改出库状态失败");
            }
            outbound.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
            outbound.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
            int k = outboundDao.updateByPrimaryKeySelective(outbound);

        }else if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.MOVEMENT.getCode() )){
            // 如果是移库
            try {
                Movement allocation =movementDao .selectByCode(outbound.getSourceOderCode());
                //设置调拨状态
                allocation.setMovementStatusCode(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getStatus());
                allocation.setMovementStatusName(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getName());

                productCommonService.getInstance(allocation.getMovementCode(), HandleTypeCoce.SUCCESS_OUT_MOVEMENT.getStatus(), ObjectTypeCode.MOVEMENT_ODER.getStatus(),allocation.getMovementCode() ,HandleTypeCoce.SUCCESS_OUT_MOVEMENT.getName());

                //跟新调拨单状态
                int k = movementDao.updateByPrimaryKeySelective(allocation);
                //生成入库单
                movementCreateInbound(allocation.getId());
            } catch (Exception e) {
                e.printStackTrace();
                throw new GroundRuntimeException("调拨单更改出库状态失败");
            }
            outbound.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
            outbound.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
            int k = outboundDao.updateByPrimaryKeySelective(outbound);

        }else{
            throw new GroundRuntimeException("无法回传匹配类型");
        }

    }
    /**
     * 回传订单接口
     * @param reqVO
     */
    @Override
    @Async("taskProductExecutor")
    public void returnOder(SupplyOrderInfoReqVO reqVO) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        String url = urlConfig.PURCHASE_URL+"/purchase/order/outBoundCallBack";
//        try {
//            HttpClient client = HttpClientHelper.getCurrentClient(HttpClient.post(url).json(reqVO));
//            HttpResponse  result = client.action().result(HttpResponse.class);
//            if(!Objects.equals(result.getCode(), MsgStatus.SUCCESS)){
//                log.error("出库单回传订单失败：[{}]",reqVO);
//              throw  new GroundRuntimeException("调用采购服务失败");
//            }
//        } catch (GroundRuntimeException e) {
//            e.printStackTrace();
//            log.error("出库单回传订单失败：[{}]",reqVO);
//        }

    }
    /**
     * 退供回传接口
     * @param reqVO
     */
    @Override
    @Async("taskProductExecutor")
    public void returnStorageResult(ReturnStorageResultReqVo reqVO) {

//        String url = urlConfig.PURCHASE_URL+"/returnsupply/returnStorageResult";
//        try {
//            HttpClient client = HttpClientHelper.getCurrentClient(HttpClient.post(url).json(reqVO));
//            HttpResponse  result = client.action().result(HttpResponse.class);
//            if(!Objects.equals(result.getCode(), MsgStatus.SUCCESS)){
//                log.error("退供单回传：[{}]",reqVO);
//                throw  new GroundRuntimeException("调用采购服务失败");
//            }
//        } catch (GroundRuntimeException e) {
//            e.printStackTrace();
//            log.error("出库单回传订单失败：[{}]",reqVO);
//        }

    }

    @Override
    public void createInbound(Long id) {
        try {
            AllocationToOutboundVo allocationResVo =  new AllocationToOutboundVo();
            Allocation allocation = allocationMapper.selectByPrimaryKey(id);
            BeanCopyUtils.copy(allocation,allocationResVo);
            productCommonService.getInstance(allocation.getAllocationCode()+"", HandleTypeCoce.INBOUND_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),id ,HandleTypeCoce.INBOUND_ALLOCATION.getName());

            List<AllocationProductToOutboundVo> list = allocationProductBatchMapper.selectByPictureUrlAllocationCode(allocation.getAllocationCode());
            allocationResVo.setSkuList(list);
            // 转化成出库单
            InboundReqSave convert =  new AllocationResVo2InboundReqVoConverter(supplierApiService).convert(allocationResVo);
            String inboundOderCode = inboundService.saveInbound(convert);
            //更改调拨在途数

            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOperationType(7);
            stockChangeRequest.setOrderCode(allocation.getAllocationCode());
            // stockChangeRequest.setOrderType();
            List<StockVoRequest> list1 = new ArrayList<>();
            for (AllocationProductToOutboundVo allocationProduct : list) {
                StockVoRequest  stockVoRequest = new StockVoRequest();
                stockVoRequest.setCompanyCode(allocation.getCompanyCode());
                stockVoRequest.setCompanyName(allocation.getCompanyName());
                stockVoRequest.setTransportCenterCode(allocation.getCallInLogisticsCenterCode());
                stockVoRequest.setTransportCenterName(allocation.getCallInLogisticsCenterName());
                stockVoRequest.setWarehouseCode(allocation.getCallInWarehouseCode());
                stockVoRequest.setWarehouseName(allocation.getCallInWarehouseName());
                stockVoRequest.setPurchaseGroupCode(allocation.getPurchaseGroupCode());
                stockVoRequest.setPurchaseGroupName(allocation.getPurchaseGroupName());
                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
                stockVoRequest.setSkuName(allocationProduct.getSkuName());
                stockVoRequest.setChangeNum(allocationProduct.getQuantity());
                list1.add(stockVoRequest);
            }
            stockChangeRequest.setStockVoRequests(list1);
            // 调用锁定库存数
            HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){

            }else{
                log.error(httpResponse.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
            allocation.setInboundOderCode(inboundOderCode);
            allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_INBOUND.getStatus());
            allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_INBOUND.getName());
            allocationMapper.updateByPrimaryKeySelective(allocation);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new GroundRuntimeException("保存出库单失败");
        }
    }

    /**
     * 移库生成入库单并且改变在途数
     * @param id
     */
    @Override
    public void movementCreateInbound(Long id) {
        try {
            MovementResVo allocationResVo =  new MovementResVo();
            Movement allocation = movementDao.selectByPrimaryKey(id);
            BeanCopyUtils.copy(allocation,allocationResVo);
            productCommonService.getInstance(allocation.getMovementCode()+"", HandleTypeCoce.INBOUND_MOVEMENT.getStatus(), ObjectTypeCode.MOVEMENT_ODER.getStatus(),id ,HandleTypeCoce.INBOUND_MOVEMENT.getName());

            List<MovementProduct> list = movementProductDao.selectDetailByCode(allocation.getMovementCode());
                allocationResVo.setList(BeanCopyUtils.copyList(list, MovementProductResVo.class));

            // 转化成出库单
            InboundReqSave convert =  new MovementResVo2InboundReqVoConverter(supplierApiService).convert(allocationResVo);
            String inboundOderCode = inboundService.saveInbound(convert);
            //更改调拨在途数

            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOperationType(7);
            stockChangeRequest.setOrderCode(allocation.getMovementCode());
            // stockChangeRequest.setOrderType();
            List<StockVoRequest> list1 = new ArrayList<>();
            for (MovementProductResVo allocationProduct : allocationResVo.getList()) {
                StockVoRequest  stockVoRequest = new StockVoRequest();
                stockVoRequest.setCompanyCode(allocation.getCompanyCode());
                stockVoRequest.setCompanyName(allocation.getCompanyName());
                stockVoRequest.setTransportCenterCode(allocation.getLogisticsCenterCode());
                stockVoRequest.setTransportCenterName(allocation.getLogisticsCenterName());
                stockVoRequest.setWarehouseCode(allocation.getCallinWarehouseCode());
                stockVoRequest.setWarehouseName(allocation.getCallinWarehouseName());
                stockVoRequest.setPurchaseGroupCode(allocation.getPurchaseGroupCode());
                stockVoRequest.setPurchaseGroupName(allocation.getPurchaseGroupName());
                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
                stockVoRequest.setSkuName(allocationProduct.getSkuName());
                stockVoRequest.setChangeNum(allocationProduct.getQuantity());
                list1.add(stockVoRequest);
            }
            stockChangeRequest.setStockVoRequests(list1);
            // 调用锁定库存数
            HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){

            }else{
                log.error(httpResponse.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
            allocation.setInboundOderCode(inboundOderCode);
            allocation.setMovementStatusCode(AllocationEnum.ALLOCATION_TYPE_INBOUND.getStatus());
            allocation.setMovementStatusName(AllocationEnum.ALLOCATION_TYPE_INBOUND.getName());
            movementDao.updateByPrimaryKeySelective(allocation);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new GroundRuntimeException("保存出库单失败");
        }
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
            batchList.addAll(BeanCopyUtils.copyList(outboundReqVo.getOutboundBatches(), OutboundBatch.class));
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
        int i1 = outboundProductDao.insertBatch(productList);
        if(i1!=productList.size()){
            throw new BizException(ResultCode.SAVE_OUT_BOUND_FAILED);
        }
        Integer integer = outboundBatchDao.insertInfo(batchList);
        if(Objects.isNull(integer)||integer!=batchList.size()){
            throw new BizException(ResultCode.SAVE_OUT_BOUND_FAILED);
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
