package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.converter.AllocationResVo2OutboundReqVoConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.allocation.AllocationOrderToInboundConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.allocation.AllocationOrderToOutboundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.Allocation;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProductBatch;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationProductBatchResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.QueryAllocationResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductMapper;
import com.aiqin.bms.scmp.api.product.service.AllocationService;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.product.service.OutboundService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.allocation.AllocationItemRespVo;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.json.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 描述:
 *
 * @Author: Kt.w
 * @Date: 2019/1/9
 * @Version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@WorkFlowAnnotation(WorkFlow.APPLY_ALLOCATTION)
public class AllocationServiceImpl extends BaseServiceImpl implements AllocationService, WorkFlowHelper {


    @Autowired
    private AllocationMapper allocationMapper;

    @Autowired
    private AllocationProductMapper allocationProductMapper;

    @Autowired
    private AllocationProductBatchMapper allocationProductBatchMapper;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private SupplierCommonService supplierCommonService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;

    @Autowired
    private InboundService inboundService;
    @Autowired
    private StockService stockService;


    @Autowired
    private WarehouseService supplierApiService;


    @Autowired
    private OutboundService outboundService;
    @Autowired
    private WarehouseService warehouseService;

    @Override
    public BasePage<QueryAllocationResVo> getList(QueryAllocationReqVo vo) {
        AuthToken authToken = getUser();
        vo.setCompanyCode(authToken.getCompanyCode());
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());

        List<QueryAllocationResVo> list = allocationMapper.getList(vo);
        BasePage<QueryAllocationResVo> basePage = PageUtil.getPageList(vo.getPageNo(),list);
        return basePage;

    }


    /**
     * 转化保存实体
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(AllocationReqVo vo) {

        Allocation  allocation = new Allocation();
        BeanCopyUtils.copy(vo,allocation);
        AllocationTypeEnum allocationTypeEnum = AllocationTypeEnum.getAllocationTypeEnumByType(vo.getAllocationType());
        // 获取编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.ALLOCATION_CODE);
        allocation.setAllocationCode(encodingRule.getNumberingValue()+"");
        // 更新编码库
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        String content = "";
        if(Objects.equals(vo.getAllocationType(),AllocationTypeEnum.ALLOCATION.getType())){
            content = HandleTypeCoce.ADD_ALLOCATION.getName();
        } else if (Objects.equals(vo.getAllocationType(),AllocationTypeEnum.MOVE.getType())) {
            content = HandleTypeCoce.ADD_MOVEMENT.getName();
        } else {
            content = HandleTypeCoce.ADD_SCRAP.getName();
        }
        //保存日志
        supplierCommonService.getInstance(allocation.getAllocationCode()+"", HandleTypeCoce.ADD.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),content ,null,HandleTypeCoce.ADD.getName());
        //设置状态(未审核)
        allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_TOCHECK.getStatus());
        allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_TOCHECK.getName());
        //获取FormNo
        String form = allocationTypeEnum.getGenerateCode() + IdSequenceUtils.getInstance().nextId();
        allocation.setFormNo(form);
        Long k  = ((AllocationService) AopContext.currentProxy()).insertSelective(allocation);
        if(k <= 0){
            throw new GroundRuntimeException("调拨单保存失败");
        }
        //转化调拨单sku列表
        List<AllocationProductBatch>list = BeanCopyUtils.copyList(vo.getList(), AllocationProductBatch.class);
        list.stream().forEach(allocationProduct->{
            allocationProduct.setTaxAmount(null != allocationProduct.getTaxAmount()?allocationProduct.getTaxAmount():0L);
            allocationProduct.setTax(null != allocationProduct.getTax()?allocationProduct.getTax():0L);
            allocationProduct.setTaxPrice(null != allocationProduct.getTaxPrice()?allocationProduct.getTaxPrice():0L);
            allocationProduct.setAllocationCode(allocation.getAllocationCode());
        });
         int kp =  ((AllocationService) AopContext.currentProxy()).saveListBatch(list);
         if(kp>0){
             List<AllocationProduct> products = productbatchTransProduct(list);
             ((AllocationService) AopContext.currentProxy()).saveList(products);
             //TODO 库存锁定
            /* StockChangeRequest stockChangeRequest = new StockChangeRequest();
             stockChangeRequest.setOperationType(1);
             stockChangeRequest.setOrderCode(allocation.getAllocationCode());
             List<StockVoRequest> list1 = allocationProductTransStock(allocation,list);
             stockChangeRequest.setStockVoRequests(list1);
             // 调用锁定库存数
             HttpResponse httpResponse= stockService.changeStock(stockChangeRequest);
             if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){

             }else{
                 log.error(httpResponse.getMessage());
                 throw  new GroundRuntimeException("库存操作失败");
             }*/
             //调用审批流
             workFlow(k,form);
             return  k;
         }else {
             log.error("调拨单sku保存失败");
             throw new GroundRuntimeException("调拨单sku保存失败");
         }
    }

    /**
     * 撤销调拨单转化实体
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int revocation(Long id) {

        Allocation allocation = allocationMapper.selectByPrimaryKey(id);
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormNo(allocation.getFormNo());
        // 调用审批流的撤销接口
        WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
        if(workFlowRespVO.getSuccess().equals(true)){
            return 1;
        }else {
            throw  new GroundRuntimeException("撤销失败");
        }

    }

    /**
     * 查看调拨单详情
     * @param id
     * @return
     */
    @Override
    public AllocationResVo view(Long id) {
        AllocationResVo allocationResVo = new AllocationResVo();
        Allocation allocation = allocationMapper.selectByPrimaryKey(id);
        if(null == allocation){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        BeanCopyUtils.copy(allocation,allocationResVo);
        allocationResVo.setSkuList(allocationProductMapper.selectByAllocationCode(allocationResVo.getAllocationCode()));
        allocationResVo.setBatchSkuList(allocationProductBatchMapper.selectByAllocationCode(allocationResVo.getAllocationCode()));
        // 获取日志
        if (null != allocationResVo) {
            //获取操作日志
            OperationLogVo operationLogVo = new OperationLogVo();
            operationLogVo.setPageNo(1);
            operationLogVo.setPageSize(100);
            operationLogVo.setObjectType(ObjectTypeCode.ALLOCATION.getStatus());
            operationLogVo.setObjectId(allocationResVo.getAllocationCode());
            BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo,62);
            List<LogData> logDataList = new ArrayList<>();
            if (null != pageList.getDataList() && pageList.getDataList().size() > 0){
                logDataList = pageList.getDataList();
            }
            allocationResVo.setLogDataList(logDataList);
        }
        return  allocationResVo;

    }



    /**
     * 保存调拨单主体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public Long insertSelective(Allocation record) {
       try{
           record.setCompanyCode(getUser().getCompanyCode());
           record.setCompanyName(getUser().getCompanyName());
            long k = allocationMapper.insertSelective(record);
            if(k>0){
                return record.getId();
            }else{
                log.error("调拨单主体保存失败");
                throw new GroundRuntimeException("调拨单主体保存失败");
            }
       }catch ( Exception e){
           log.error(e.getMessage());
           log.error("调拨单主体保存失败");
           throw new GroundRuntimeException(e.getMessage());
       }
    }


    /**
     * 批量插入调拨单sku
     *
     * @param record
     * @return
     */
    @Override
    public int saveList(List<AllocationProduct> record) {
        try{
            return  allocationProductMapper.saveList(record);
        } catch (Exception e){
            log.error(e.getMessage());
            log.error("调拨单Sku保存失败");
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    /**
     * 批量插入调拨单sku批次
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @SaveList
    public int saveListBatch(List<AllocationProductBatch> record) {
        try{
            int m =   allocationProductBatchMapper.saveList(record);
            return m;
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("调拨单Sku批次保存失败");
            throw new GroundRuntimeException(e.getMessage());
        }
    }


    /**
     * 有选择的更新调拨单实体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateByPrimaryKeySelective(Allocation record) {
        try{
            return allocationMapper.updateByPrimaryKeySelective(record);
        }catch ( Exception e){
            log.error(e.getMessage());
            log.error("调拨单更新失败");
            throw new GroundRuntimeException(e.getMessage());
        }
    }


    @Async("myTaskAsyncPool")
    public void workFlow(Long id,String formNo) {
        Allocation allocation1 = allocationMapper.selectByPrimaryKey(id);
        log.info("AllocationServiceImplProduct-workFlow-传入参数是：[{}]", JSON.toJSONString(id));
        try {
            AllocationTypeEnum allocationTypeEnum = AllocationTypeEnum.getAllocationTypeEnumByType(allocation1.getAllocationType());
            WorkFlowVO workFlowVO = new WorkFlowVO();
            //判断类型
            String baseUrl;
            if (allocationTypeEnum.equals(AllocationTypeEnum.SCRAP)) {
                baseUrl = workFlowBaseUrl.scrap;
            } else if (allocationTypeEnum.equals(AllocationTypeEnum.MOVE)) {
                baseUrl = workFlowBaseUrl.movement;
            } else if (allocationTypeEnum.equals(AllocationTypeEnum.ALLOCATION)) {
                baseUrl = workFlowBaseUrl.applyAllocattion;
            }else {
                throw new BizException(ResultCode.DATA_ERROR);
            }

            workFlowVO.setFormUrl( baseUrl+"?id="+id+"&"+workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setTitle(allocation1.getCreateBy()+"创建"+allocationTypeEnum.getTypeName()+"单");
            workFlowVO.setFormNo(formNo);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("auditPersonId",allocation1.getDirectSupervisorCode());
            workFlowVO.setVariables(jsonObject.toString());
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+ allocationTypeEnum.getWorkFlow().getNum());
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, allocationTypeEnum.getWorkFlow());
            if(workFlowRespVO.getSuccess()){
                 // 更新调拨单审核状态
                  Allocation allocation = new Allocation();
                  allocation.setId(id);
                  allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_CHECK.getName());
                  allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_CHECK.getStatus());
                 ((AllocationService)AopContext.currentProxy()).updateByPrimaryKeySelective(allocation);
                //存日志
                String applyTypeTitle = "";
                if(Objects.equals(allocation1.getAllocationType(),AllocationTypeEnum.ALLOCATION.getType())){
                    applyTypeTitle = "调拨";
                } else if (Objects.equals(allocation1.getAllocationType(),AllocationTypeEnum.MOVE.getType())) {
                    applyTypeTitle = "移库";
                } else {
                    applyTypeTitle = "报废";
                }
                String content = ApplyStatus.APPROVAL.getContent().replace("CREATEBY", allocation1.getUpdateBy()).replace("APPLYTYPE", applyTypeTitle);
                supplierCommonService.getInstance(
                        allocation1.getAllocationCode()+"",
                        HandleTypeCoce.APPROVAL.getStatus(),
                        ObjectTypeCode.ALLOCATION.getStatus(),
                        content, null,
                        HandleTypeCoce.APPROVAL.getName()
                );

            }else {
                log.error("上传审批接口失败");
                log.error("失败原因是"+workFlowRespVO.getMsg());
                throw new GroundRuntimeException(workFlowRespVO.getMsg());
            }
        }catch (Exception e) {
            throw new GroundRuntimeException(e.getMessage());
        }
    }
    /**
     * 查询调拨状态
     *
     * @return
     */
    @Override
    public List<EnumReqVo> getAllocationStatus() {
        List<EnumReqVo> list = new ArrayList<>();
        AllocationEnum [] allocationEnums =AllocationEnum.values();
        for (AllocationEnum allocationEnum : allocationEnums) {
            list.add(new EnumReqVo(allocationEnum.getStatus(),allocationEnum.getName()));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSubmit(Byte status, String formNo) {
        Allocation allocation = new Allocation();
        Long id  = allocationMapper.findIdByFormNo(formNo);
        allocation.setId(id);
        allocation.setAllocationStatusCode(status);
        allocation.setAllocationStatusName(AllocationEnum.getAllocationEnumNameByCode(status));
        return allocationMapper.updateByPrimaryKeySelective(allocation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo1) {
        return nativeWorkFlowCallback(vo1);
    }

    /**
     *  调拨单生成出库单
     * @param id  调拨单id
     * @return   出库单编码
     */
    public String createOutbound(Long  id){
        try {
            AllocationToOutboundVo allocationResVo =  new AllocationToOutboundVo();
            Allocation allocation = allocationMapper.selectByPrimaryKey(id);
            BeanCopyUtils.copy(allocation,allocationResVo);

            List<AllocationProductToOutboundVo> list = allocationProductBatchMapper.selectByPictureUrlAllocationCode(allocation.getAllocationCode());
            allocationResVo.setSkuList(list);
            // 转化成出库单
            OutboundReqVo convert =  new AllocationResVo2OutboundReqVoConverter(supplierApiService).convert(allocationResVo);

              return  outboundService.save(convert);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new GroundRuntimeException("保存出库单失败");
        }
    }

    private List<StockVoRequest> allocationProductTransStock(Allocation allocation, List<AllocationProductBatchResVo> products) {
        List<StockVoRequest> stockVoRequests = Lists.newArrayList();
        if(CollectionUtils.isNotEmptyCollection(products)){
            StockVoRequest stockVoRequest = null;
            for (AllocationProductBatchResVo allocationProduct : products) {
                stockVoRequest = new StockVoRequest();
                // 设置公司名称编码
                stockVoRequest.setCompanyCode(allocation.getCompanyCode());
                stockVoRequest.setCompanyName(allocation.getCompanyName());
                // 设置物流中心名称编码
                stockVoRequest.setTransportCenterCode(allocation.getCallOutLogisticsCenterCode());
                stockVoRequest.setTransportCenterName(allocation.getCallOutLogisticsCenterName());
                //设置库房名称编码
                stockVoRequest.setWarehouseCode(allocation.getCallOutWarehouseCode());
                stockVoRequest.setWarehouseName(allocation.getCallOutWarehouseName());
                //设置采购组编码名称
                stockVoRequest.setPurchaseGroupCode(allocation.getPurchaseGroupCode());
                stockVoRequest.setPurchaseGroupName(allocation.getPurchaseGroupName());
                //设置sku编号名称
                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
                stockVoRequest.setSkuName(allocationProduct.getSkuName());
                stockVoRequest.setChangeNum(allocationProduct.getQuantity());
                stockVoRequests.add(stockVoRequest);
            }
        }
        return stockVoRequests;
    }

    public List<AllocationProduct> productbatchTransProduct(List<AllocationProductBatch> batches){
        Map<String, List<AllocationProductBatch>> batchMap = batches.stream().collect(Collectors.groupingBy(AllocationProductBatch::getSkuCode));
        List<AllocationProduct> products = Lists.newArrayList();
        batchMap.forEach((k,v)->{
            AllocationProduct product = BeanCopyUtils.copy(v.get(0),AllocationProduct.class);
            //合并数量
            Long totalNum = v.stream().mapToLong(AllocationProductBatch::getQuantity).sum();
            product.setQuantity(totalNum);
            //合并含税总成本
            Long totalTaxAmount = v.stream().mapToLong(AllocationProductBatch::getTaxAmount).sum();
            product.setTaxAmount(totalTaxAmount);
            products.add(product);
        });

        return products;
    }

    /**
     * 审核回调接口
     *
     * @param vo1
     * @return
     * @author zth
     * @date 2019/1/15
     */
    @Override
    public String nativeWorkFlowCallback(WorkFlowCallbackVO vo1) {
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(vo1);
        // 通过流水编码查询调拨单实体
        Allocation oldAllocation = new Allocation();
        AllocationDTO allocation  = allocationMapper.selectByFormNO1(vo1.getFormNo());
        oldAllocation.setId(allocation.getId());
        if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            String content = ApplyStatus.APPROVAL_SUCCESS.getContent().replace("CREATEBY", allocation.getUpdateBy()).replace("AUDITORBY", vo.getApprovalUserName());
            supplierCommonService.getInstance(
                    allocation.getAllocationCode()+"",
                    HandleTypeCoce.APPROVAL_SUCCESS.getStatus(),
                    ObjectTypeCode.ALLOCATION.getStatus(),
                    content, null,
                    HandleTypeCoce.APPROVAL_SUCCESS.getName(),
                    vo.getApprovalUserName()
            );
            //审批成功
            //生成出库单并且返回出库单编码
            //生成入库单
            String outboundCode = null;
            String inboundCode = null;
            AllocationTypeEnum enumByType = AllocationTypeEnum.getAllocationTypeEnumByType(allocation.getAllocationType());
            OutboundReqVo convert = new AllocationOrderToOutboundConverter(warehouseService, enumByType).convert(allocation);
            outboundCode =outboundService.save(convert);
            if(!AllocationTypeEnum.SCRAP.getType().equals(allocation.getAllocationType())){
                InboundReqSave convert1 = new AllocationOrderToInboundConverter(warehouseService, enumByType).convert(allocation);
                inboundCode = inboundService.saveInbound(convert1);
            }
//            String outboundOderCode = createOutbound(allocation.getId());
            oldAllocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getStatus());
            oldAllocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getName());
            oldAllocation.setOutboundOderCode(outboundCode);
            oldAllocation.setInboundOderCode(inboundCode);
            //更新审核状态
            int  k = ((AllocationService)AopContext.currentProxy()).updateByPrimaryKeySelective(oldAllocation);
            if(k!=1){
                throw new BizException(ResultCode.UPDATE_ERROR);
            }

            String content2 = "";
            if(Objects.equals(allocation.getAllocationType(),AllocationTypeEnum.ALLOCATION.getType())){
                content2 = HandleTypeCoce.OUTBOUND_ALLOCATION.getName();
            } else if (Objects.equals(allocation.getAllocationType(),AllocationTypeEnum.MOVE.getType())) {
                content2 = HandleTypeCoce.OUTBOUND_MOVEMENT.getName();
            } else {
                content2 = HandleTypeCoce.OUTBOUND_SCRAP.getName();
            }
            supplierCommonService.getInstance(allocation.getAllocationCode()+"", AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), content2,null, AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getName(),vo.getApprovalUserName());
            return "success";
        }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){

            String content = ApplyStatus.APPROVAL_FAILED.getContent().replace("CREATEBY", allocation.getUpdateBy()).replace("AUDITORBY", vo.getApprovalUserName());
            supplierCommonService.getInstance(
                    allocation.getAllocationCode()+"",
                    HandleTypeCoce.APPROVAL_FAILED.getStatus(),
                    ObjectTypeCode.ALLOCATION.getStatus(),
                    content, null,
                    HandleTypeCoce.APPROVAL_SUCCESS.getName(),
                    vo.getApprovalUserName()
            );
            // 审核不通过
            //  通过编码查询sku
            // 解锁被锁的sku 编码
//            StockChangeRequest stockChangeRequest = new StockChangeRequest();
//            stockChangeRequest.setOperationType(3);
//            stockChangeRequest.setOrderCode(allocation.getAllocationCode());
//            List<StockVoRequest> list1 = allocationProductTransStock(allocation,list);
//            stockChangeRequest.setStockVoRequests(list1);
            oldAllocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_REJECTED.getStatus());
            oldAllocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_REJECTED.getName());
            //更新状态
            ((AllocationService)AopContext.currentProxy()).updateByPrimaryKeySelective(oldAllocation);
            return "success";
        } else if(vo.getApplyStatus().intValue()==ApplyStatus.REVOKED.getNumber()){
            oldAllocation .setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_CANCEL.getStatus());
            oldAllocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_CANCEL.getName());
            ((AllocationService) AopContext.currentProxy()).updateByPrimaryKeySelective(oldAllocation);
            // 打印撤销的日志
            String content = ApplyStatus.APPROVAL_FAILED.getContent().replace("CREATEBY", allocation.getUpdateBy()).replace("AUDITORBY", vo.getApprovalUserName());
            supplierCommonService.getInstance(
                    allocation.getAllocationCode()+"",
                    HandleTypeCoce.REVOKED.getStatus(),
                    ObjectTypeCode.ALLOCATION.getStatus(),
                    content, null,
                    HandleTypeCoce.REVOKED.getName(),
                    vo.getApprovalUserName()
            );
//            StockChangeRequest stockChangeRequest = new StockChangeRequest();
//            stockChangeRequest.setOperationType(3);
//            stockChangeRequest.setOrderCode(allocation.getAllocationCode());
//            List<StockVoRequest> list1 = allocationProductTransStock(allocation,list);
//            stockChangeRequest.setStockVoRequests(list1);
            return "success";
        } else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
            //审批中
            return "success";
        } else {
            return "false";
        }
    }

    /**
     * 添加是导入sku
     *
     * @param reqVo
     * @return
     */
    @Override
    public List<AllocationItemRespVo> importAllocationSku(AllocationImportSkuReqVo reqVo) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        reqVo.setCompanyCode(currentAuthToken.getCompanyCode());
        try {
            List<AllocationItemRespVo> list = Lists.newArrayList();
            List<Object[]> excel = ExcelUtil.getExcelAll(reqVo.getFile());
            if(excel.size()<=1){
                return list;
            }
            List<String> codes = excel.stream().map(p -> {
                return String.valueOf(p[0]);
            }).collect(Collectors.toList());
            if(CollectionUtils.isEmptyCollection(codes)){
                return Lists.newArrayList();
            }
            //移除表头数据
            codes.remove(0);
            //调用接口获取sku的详细数据
            QueryStockSkuReqVo copy = BeanCopyUtils.copy(reqVo, QueryStockSkuReqVo.class);
            copy.setSkuList(codes);
            List<QueryStockSkuRespVo> queryStockSkuRespVos = stockService.selectStockSkus(copy);
            List<AllocationItemRespVo> data =JSON.parseArray(JsonUtil.toJson(queryStockSkuRespVos),AllocationItemRespVo.class);
            //key为sku编码
            Map<String, AllocationItemRespVo> map = data.stream().collect(Collectors.toMap(AllocationItemRespVo::getSkuCode, Function.identity()));
            SkuBatchReqVO skuBatchReqVO = new SkuBatchReqVO();
            skuBatchReqVO.setSkuCodes(codes);
            skuBatchReqVO.setTransportCenterCode(reqVo.getTransportCenterCode());
            skuBatchReqVO.setWarehouseCode(reqVo.getWarehouseCode());
            List<SkuBatchRespVO> skuBatchRespVOS = stockService.querySkuBatchList(skuBatchReqVO);
            Map<String, List<SkuBatchRespVO>> skuBatchRespVOMap = skuBatchRespVOS.stream().collect(Collectors.groupingBy(SkuBatchRespVO::getSkuCode));
            //错误信息
            for(int i=1;i<excel.size();i++){
                Object[] objects = excel.get(i);
                String skuCode =String.valueOf(objects[0]);
                String skuName = ExcelUtil.convertNumToString(objects[1]);
                Long num=0L;
                try {
                    String s = ExcelUtil.convertNumToString(objects[3]);
                    if(StringUtils.isNotBlank(s)){
                        num = Long.valueOf(s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    list.add(new AllocationItemRespVo(skuCode,skuName, "数量数据格式错误"));
                    continue;
                }
                //验证是否重复导入
                if(null!=skuCode){
                    List<String> skus =list.stream().map(AllocationItemRespVo::getSkuCode).collect(Collectors.toList());
                    if(skus.contains(skuCode)==true){
                        {
                            list.add(new AllocationItemRespVo(skuCode, skuName,"导入重复"));
                            continue;
                        }
                    }
                }
                //验证该编码是否存在
                if(Objects.isNull(map.get(skuCode))){
                    list.add(new AllocationItemRespVo(skuCode, skuName,"sku编码不存在或者已被禁用"));
                    continue;
                }
                AllocationItemRespVo allocationItemRespVo = map.get(skuCode);
                if(num !=null&&num>allocationItemRespVo.getStockNum()){
                    list.add(new AllocationItemRespVo(skuCode, skuName,"数量超出库存范围"));
                    continue;
                }
                if(num !=null&&num<=allocationItemRespVo.getStockNum()){
                    allocationItemRespVo.setNumber(num);
                    allocationItemRespVo.setTotalPrice(allocationItemRespVo.getPrice() * num);
                }
                if(allocationItemRespVo == null){
                    list.add(new AllocationItemRespVo(skuCode, skuName,"该sku没有足够的库存"));
                    continue;
                }
                list.add(allocationItemRespVo);
//                //验证是否存在批次存库
//                if(skuBatchRespVOMap.containsKey(skuCode)){
//                    allocationItemRespVo.setSkuBatchRespVOS(skuBatchRespVOMap.get(skuCode));
//                } else {
//                    allocationItemRespVo.setErrorReason("sku在库房中不存在批次号");
//                }


            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroundRuntimeException("导入异常");
        }
    }
}
