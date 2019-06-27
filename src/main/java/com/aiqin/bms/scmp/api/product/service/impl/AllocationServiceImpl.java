package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.converter.AllocationResVo2OutboundReqVoConverter;
import com.aiqin.bms.scmp.api.product.domain.pojo.Allocation;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProductBatch;
import com.aiqin.bms.scmp.api.product.domain.request.ApplyStatus;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.product.domain.request.StockChangeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationToOutboundVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.QueryAllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationProductResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.QueryAllocationResVo;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.product.service.api.SupplierApiService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class AllocationServiceImpl extends BaseServiceImpl implements AllocationService  {


    @Autowired
    private AllocationMapper allocationMapper;

    @Autowired
    private AllocationProductMapper allocationProductMapper;

    @Autowired
    private AllocationProductBatchMapper allocationProductBatchMapper;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private ProductCommonService productCommonService;

    @Autowired
    private ProductOperationLogService productOperationLogService;

    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;


    @Autowired
    private StockService stockService;


    @Autowired
    private SupplierApiService supplierApiService;


    @Autowired
    private OutboundService outboundService;
    @Override
    public BasePage<QueryAllocationResVo> getList(QueryAllocationReqVo vo) {
        AuthToken authToken = getUser();
        vo.setCompanyCode(authToken.getCompanyCode());
        vo.setCompanyName(authToken.getCompanyName());
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
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Long save(AllocationReqVo vo) {
        Allocation  allocation = new Allocation();
        BeanCopyUtils.copy(vo,allocation);
        // 获取编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.ALLOCATION_CODE);
        allocation.setAllocationCode(encodingRule.getNumberingValue()+"");
        // 更新编码库
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        //保存日志
        productCommonService.getInstance(allocation.getAllocationCode()+"", HandleTypeCoce.ADD_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), vo,HandleTypeCoce.ADD_ALLOCATION.getName());
        //设置状态(未审核)
        allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_TOCHECK.getStatus());
        allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_TOCHECK.getName());
        //获取FormNo
        String form = "DB" + new IdSequenceUtils().nextId();
        allocation.setFormNo(form);
        Long k  = ((AllocationService) AopContext.currentProxy()).insertSelective(allocation);
        if(k <= 0){
            throw new GroundRuntimeException("调拨单保存失败");
        }
        //转化调拨单sku列表
        List<AllocationProductBatch>list = BeanCopyUtils.copyList(vo.getList(), AllocationProductBatch.class);
        list.stream().forEach(allocationProduct->{
            allocationProduct.setAllocationCode(allocation.getAllocationCode());
        });
         int kp =  ((AllocationService) AopContext.currentProxy()).saveListBatch(list);
         if(kp>0){
             List<AllocationProduct> products = productbatchTransProduct(list);
             ((AllocationService) AopContext.currentProxy()).saveList(products);
             //TODO 库存锁定
             StockChangeRequest stockChangeRequest = new StockChangeRequest();
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
             }
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
        try {
        Allocation allocation = allocationMapper.selectByPrimaryKey(id);
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormNo(allocation.getFormNo());
        // 调用审批流的撤销接口
        WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
        if(workFlowRespVO.getSuccess().equals(true)){
            return 1;
        }else {
            log.error("调拨单id为"+id+"撤销失败");
            throw  new GroundRuntimeException("撤销失败");
        }

    } catch ( Exception e){
            e.printStackTrace();
            log.error("撤销申请合同id为空");
            throw new GroundRuntimeException("id 不能为空");
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
        BeanCopyUtils.copy(allocation,allocationResVo);
        try {
            allocationResVo.setSkuList(BeanCopyUtils.copyList(allocationProductMapper.selectByAllocationCode(allocationResVo.getAllocationCode()), AllocationProductResVo.class));
            // 获取日志
            if (null != allocationResVo) {
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.ALLOCATION.getStatus());
                operationLogVo.setObjectId(allocationResVo.getAllocationCode());
                List<LogData> pageList = productOperationLogService.getLogType(operationLogVo);
                allocationResVo.setLogDataList(pageList);
            }
            return  allocationResVo;
        } catch (Exception e) {
            log.error("调拨查看sku转化实体失败");
            throw new GroundRuntimeException("调拨查看sku转化实体失败");
        }

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
        try {
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("AllocationServiceImplProduct-workFlow-传入参数是：[{}]", JSON.toJSONString(id));
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO();
            productCommonService.getInstance(allocation1.getAllocationCode()+"", HandleTypeCoce.FLOW_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), id,HandleTypeCoce.FLOW_ALLOCATION.getName());

            workFlowVO.setFormUrl(workFlowBaseUrl.applyAllocattion +"?id="+id+"&"+workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setTitle("申请从"+allocation1.getCallOutWarehouseName()+"到"+allocation1.getCallInWarehouseName()+"调拨");
            workFlowVO.setFormNo(formNo);
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+ WorkFlow.APPLY_ALLOCATTION.getNum());
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_ALLOCATTION);
            if(workFlowRespVO.getSuccess()){
                 // 更新调拨单审核状态
                  Allocation allocation = new Allocation();
                  allocation.setId(id);
                  allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_CHECK.getName());
                  allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_CHECK.getStatus());
                  int  k = ((AllocationService)AopContext.currentProxy()).updateByPrimaryKeySelective(allocation);
                if (k > 0) {

                }else {
                    log.error("调拨单撤销失败");
                    throw new GroundRuntimeException("调拨单撤销失败");
                }
            }else {
                log.error("上传审批接口失败");
                log.error("失败原因是"+workFlowRespVO.getMsg());
                throw new GroundRuntimeException();
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
    public int updateSubmit(Byte status, String formNo) {
        Allocation allocation = new Allocation();
        Long id  = allocationMapper.findIdByFormNo(formNo);
        allocation.setId(id);
        allocation.setAllocationStatusCode(status);
        allocation.setAllocationStatusName(AllocationEnum.getAllocationEnumNameByCode(status));
        return allocationMapper.updateByPrimaryKeySelective(allocation);
    }

    @Override
    public String workFlowCallback(WorkFlowCallbackVO vo1) {
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(vo1);
        // 通过流水编码查询调拨单实体
        Allocation oldAllocation = new Allocation();
        Allocation allocation  = allocationMapper.selectByFormNO(vo1.getFormNo());
        oldAllocation.setId(allocation.getId());
        List<AllocationProductBatch> list =  allocationProductBatchMapper.selectByAllocationCode(allocation.getAllocationCode());
        if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())) {

            productCommonService.instanceThreeParty(allocation.getAllocationCode()+"", HandleTypeCoce.FLOW_SUCCESS_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), vo1,HandleTypeCoce.FLOW_SUCCESS_ALLOCATION.getName(),new Date(),vo.getApprovalUserName());

            //审批成功
            //生成出库单并且返回出库单编码
            String outboundOderCode = createOutbound(allocation.getId());
            oldAllocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getStatus());
            oldAllocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getName());
            oldAllocation.setOutboundOderCode(outboundOderCode);
            //更新审核状态
            int  k = ((AllocationService)AopContext.currentProxy()).updateByPrimaryKeySelective(oldAllocation);
            productCommonService.getInstance(allocation.getAllocationCode()+"", HandleTypeCoce.OUTBOUND_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), vo1,HandleTypeCoce.OUTBOUND_ALLOCATION.getName());

            if (k > 0) {
                return "success";
            }else {
                log.error("调拨单撤销失败");
                throw new GroundRuntimeException("调拨单撤销失败");
            }
        }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){
            productCommonService.instanceThreeParty(allocation.getAllocationCode()+"", HandleTypeCoce.FLOW_FALSE_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), vo1,HandleTypeCoce.FLOW_FALSE_ALLOCATION.getName(),new Date(),vo.getApprovalUserName());
            // 审核不通过
            //  通过编码查询sku
            // 解锁被锁的sku 编码
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOperationType(3);
            stockChangeRequest.setOrderCode(allocation.getAllocationCode());
            List<StockVoRequest> list1 = allocationProductTransStock(allocation,list);
            stockChangeRequest.setStockVoRequests(list1);
            // 调用锁定库存数
            HttpResponse httpResponse= null;
            try {
                httpResponse = stockService.changeStock(stockChangeRequest);

            } catch (Exception e) {
                e.printStackTrace();
                throw  new GroundRuntimeException("库存操作失败");
            }
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){

            }else{
                log.error(httpResponse.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
            oldAllocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_REJECTED.getStatus());
            oldAllocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_REJECTED.getName());
            //更新状态
            int  k = ((AllocationService)AopContext.currentProxy()).updateByPrimaryKeySelective(oldAllocation);
            if (k > 0) {
                return "success";
            }else {
                log.error("调拨单撤销失败");
                throw new GroundRuntimeException("调拨单撤销失败");
            }
        } else if(vo.getApplyStatus().intValue()==ApplyStatus.REVOKED.getNumber()){
            oldAllocation .setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_CANCEL.getStatus());
            oldAllocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_CANCEL.getName());
            ((AllocationService) AopContext.currentProxy()).updateByPrimaryKeySelective(oldAllocation);
            // 打印撤销的日志
            productCommonService.getInstance(oldAllocation.getAllocationCode()+"", HandleTypeCoce.REVOCATION_ALLOCATION.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),oldAllocation ,HandleTypeCoce.REVOCATION_ALLOCATION.getName());

            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOperationType(3);
            stockChangeRequest.setOrderCode(allocation.getAllocationCode());
            List<StockVoRequest> list1 = allocationProductTransStock(allocation,list);
            stockChangeRequest.setStockVoRequests(list1);
            // 调用锁定库存数
            HttpResponse httpResponse= null;
            try {
                httpResponse = stockService.changeStock(stockChangeRequest);
            } catch (Exception e) {
                e.printStackTrace();
                throw  new GroundRuntimeException("库存操作失败");
            }
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){

            }else{
                log.error(httpResponse.getMessage());
                throw  new GroundRuntimeException("库存操作失败");
            }
            return "success";
            }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
            //审批中
            return "success";
        }else {
            return "false";
        }
    }

    /**
     *  调拨单生成出库单
     * @param id  调拨单id
     * @return   出库单编码
     */
    public String createOutbound(Long  id ){
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

    private List<StockVoRequest> allocationProductTransStock(Allocation allocation, List<AllocationProductBatch> products) {
        List<StockVoRequest> stockVoRequests = Lists.newArrayList();
        if(CollectionUtils.isNotEmptyCollection(products)){
            StockVoRequest stockVoRequest = null;
            for (AllocationProductBatch allocationProduct : products) {
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
}
