package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.dao.MovementDao;
import com.aiqin.bms.scmp.api.product.dao.MovementProductDao;
import com.aiqin.bms.scmp.api.product.domain.converter.MovementResVo2OutboundReqVoConverter;
import com.aiqin.bms.scmp.api.product.domain.pojo.Movement;
import com.aiqin.bms.scmp.api.product.domain.pojo.MovementProduct;
import com.aiqin.bms.scmp.api.product.domain.request.ApplyStatus;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.product.domain.request.StockChangeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.movement.QueryMovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementProductResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.QueryMovementResVo;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.product.service.api.SupplierApiService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname: MovementServiceImplProduct
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Slf4j
@Service
public class MovementServiceImplProduct extends ProductBaseServiceImpl implements MovementService {

    @Autowired
    private MovementDao movementDao;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private MovementProductDao movementProductDao;

    @Autowired
    private ProductCommonService productCommonService;

    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;

    @Autowired
    private ProductOperationLogService productOperationLogService;

    @Autowired
    private StockService stockService;

    @Autowired
    private OutboundService outboundService;

    @Autowired
    private SupplierApiService supplierApiService;

    /**
     * 移库列表搜索
     * @param vo 列表搜索实体
     * @return  列表返回实体
     */
    @Override
    public BasePage<QueryMovementResVo> getList(QueryMovementReqVo vo) {
        try {
            PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
            List<QueryMovementResVo> list = movementDao.getList(vo);
            BasePage<QueryMovementResVo> basePage = PageUtil.getPageList(vo.getPageNo(),list);
            basePage.setDataList(list);
            return basePage;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("分页查询失败");
            throw new GroundRuntimeException(ex.getMessage());
        }
    }



    /**
     * 新增移库单转化实体
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int save(MovementReqVo vo) {
        //转化为移库主体保存实体
        Movement movement = new Movement();
        BeanCopyUtils.copy(vo,movement);

        // 获取编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.MOVEMENT_CODE);
        movement.setMovementCode("YK"+encodingRule.getNumberingValue());

        //设置状态
        movement.setMovementStatusCode(AllocationEnum.ALLOCATION_TYPE_TOCHECK.getStatus());
        movement.setMovementStatusName(AllocationEnum.ALLOCATION_TYPE_TOCHECK.getName());

        //设置流水号
        movement.setFormNo(movement.getMovementCode());
        //更新编码尺度
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
         //新增移库单主体
         int k = ((MovementService) AopContext.currentProxy()).insertSelective(movement);
         //保存日志
        productCommonService.getInstance(movement.getMovementCode()+"", HandleTypeCoce.ADD_MOVEMENT.getStatus(), ObjectTypeCode.MOVEMENT_ODER.getStatus(), vo,HandleTypeCoce.ADD_MOVEMENT.getName());

        //转化为sku列表数据库访问实体
        try {
            List<MovementProduct> list = BeanCopyUtils.copyList(vo.getList(),MovementProduct.class);
            //设置关联编码
            list.stream().forEach(movementProduct->{movementProduct.setMovementCode(movement.getMovementCode());} );
            int j  = ((MovementService) AopContext.currentProxy()).saveList(list);
            // 锁定库存

            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOperationType(1);
            stockChangeRequest.setOrderCode(movement.getMovementCode());
            List<StockVoRequest> list1 = new ArrayList<>();
            for (MovementProduct allocationProduct : list) {
                StockVoRequest  stockVoRequest = new StockVoRequest();
                // 设置公司名称编码
                stockVoRequest.setCompanyCode(movement.getCompanyCode());
                stockVoRequest.setCompanyName(movement.getCompanyName());
                // 设置物流中心名称编码
                stockVoRequest.setTransportCenterCode(movement.getLogisticsCenterCode());
                stockVoRequest.setTransportCenterName(movement.getLogisticsCenterName());
                //设置库房名称编码
                stockVoRequest.setWarehouseCode(movement.getCalloutWarehouseCode());
                stockVoRequest.setWarehouseName(movement.getCalloutWarehouseName());
                //设置采购组编码名称
                stockVoRequest.setPurchaseGroupCode(movement.getPurchaseGroupCode());
                stockVoRequest.setPurchaseGroupName(movement.getPurchaseGroupName());
                //设置sku编号名称
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
            //推送审核
           workFlow(movement.getMovementCode());
            return j;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GroundRuntimeException("保存移库sku失败");
        }
    }

    /**
     * 有选择的添加移库单主体
     * @param record
     * @return
     */
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Override
    @Save
    public int insertSelective(Movement record) {
        try{

            int k = movementDao.insertSelective(record);
            return k;
        }catch (Exception e){
            e.printStackTrace();
            throw  new GroundRuntimeException("添加入库单主体失败");
        }

    }
    /**
     * 批量新增移库sku
     * @param list
     * @return
     */
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Override
    @SaveList
    public int saveList(List<MovementProduct> list) {
        try{

            int k = movementProductDao.saveList(list);
            return k;
        }catch (Exception e){
            e.printStackTrace();
            throw  new GroundRuntimeException("添加入库单主体失败");
        }

    }

    /**
     * 查询移库单详情
     * @param id
     * @return
     */
    @Override
    public MovementResVo view(Long id) {
        MovementResVo movementResVo = new MovementResVo();
        Movement movement =  movementDao.selectByPrimaryKey(id);
        BeanCopyUtils.copy(movement,movementResVo);
        //查询行商品
         List<MovementProduct> list = movementProductDao.selectByCode(movementResVo.getMovementCode());
        try {
              List<MovementProductResVo> list1 =BeanCopyUtils.copyList(list,MovementProductResVo.class);
            movementResVo.setList(list1);


            if (null != movementResVo) {
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.MOVEMENT_ODER.getStatus());
                operationLogVo.setObjectId(movementResVo.getMovementCode());
                List<LogData> pageList = productOperationLogService.getLogType(operationLogVo);
                movementResVo.setLogDataList(pageList);
            }
            return movementResVo;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new GroundRuntimeException("查询移库单失败");
        }

    }


    /**
     * 撤销移库单
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int revocation(Long id) {
            Movement allocation = movementDao.selectByPrimaryKey(id);
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormNo(allocation.getFormNo());
            // 调用审批流的撤销接口
            WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
            if(workFlowRespVO.getSuccess().equals(true)){
                return 1;
            }else {
                log.error("移库单id为"+id+"撤销失败");
                throw  new GroundRuntimeException("撤销失败");
            }
    }

    /**
     * 异步提交移库单
     * @param movementCode
     *
     */
    @Override
    public void workFlow(String movementCode ) {

        Movement movement = movementDao.selectByCode(movementCode);
        log.info("MovementServiceImplProduct-workFlow-传入参数是：[{}]", JSON.toJSONString(movementCode));
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO();
            productCommonService.getInstance(movement.getMovementCode(), HandleTypeCoce.SUBMIT_MOVEMENT.getStatus(), ObjectTypeCode.MOVEMENT_ODER.getStatus(), movementCode,HandleTypeCoce.SUBMIT_MOVEMENT.getName());

            workFlowVO.setFormUrl(workFlowBaseUrl.movement +"?id="+movement.getId()+"&"+workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setTitle("申请从"+movement.getCalloutWarehouseName()+"到"+movement.getCallinWarehouseName()+"移库");
            workFlowVO.setFormNo(movement.getFormNo());
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+ WorkFlow.MOVEMENT_ODER.getNum());
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.MOVEMENT_ODER);
            if(workFlowRespVO.getSuccess()){
                // 更新移库申请状态
                movement.setMovementStatusName(AllocationEnum.ALLOCATION_TYPE_CHECK.getName());
                movement.setMovementStatusCode(AllocationEnum.ALLOCATION_TYPE_CHECK.getStatus());
                int  k = movementDao.updateByPrimaryKeySelective(movement);
                if (k > 0) {

                }else {
                    log.error("移库单提交审核失败");
                    throw new GroundRuntimeException("移库单提交审核失败");
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

    @Override
    public String workFlowCallback(WorkFlowCallbackVO workFlowCallbackVO) {
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(workFlowCallbackVO);
        // 通过流水编码查询移库单实体
        Movement movement  = movementDao.selectByFormNo(vo.getFormNo());
        List<MovementProduct> list = movementProductDao.selectByCode(movement.getMovementCode());
        if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())) {

            productCommonService.instanceThreeParty(movement.getMovementCode()+"", HandleTypeCoce.PASS_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), workFlowCallbackVO,HandleTypeCoce.PASS_MOVEMENT.getName(),new Date(),vo.getApprovalUserName());

            //审批成功
            //生成出库单并且返回出库单编码


            MovementResVo movementResVo = new MovementResVo();
            BeanCopyUtils.copy(movement,movementResVo);
            List<MovementProduct> list1 = movementProductDao.selectDetailByCode(movement.getMovementCode());

            //设置sku
            try {
                movementResVo.setList(BeanCopyUtils.copyList(list1,MovementProductResVo.class));
            } catch (Exception e) {
                e.printStackTrace();
                return "false";
            }
            String outboundOderCode = create(movementResVo);
            movement.setOutboundOderCode(outboundOderCode);
            movement.setMovementStatusCode(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getStatus());
            movement.setMovementStatusName(AllocationEnum.ALLOCATION_TYPE_TO_OUTBOUND.getName());
            //更新审核状态
            int  k = movementDao.updateByPrimaryKeySelective(movement);

            if (k > 0) {
                return "success";
            }else {
                log.error("调拨单撤销失败");
                throw new GroundRuntimeException("调拨单撤销失败");
            }

        }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){
            productCommonService.instanceThreeParty(movement.getMovementCode()+"", HandleTypeCoce.NOPASS_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), workFlowCallbackVO,HandleTypeCoce.NOPASS_MOVEMENT.getName(),new Date(),vo.getApprovalUserName());



            //解锁库存
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOperationType(3);
            stockChangeRequest.setOrderCode(movement.getMovementCode());
//                 stockChangeRequest.setOrderType();
            List<StockVoRequest> list1 = new ArrayList<>();
            for (MovementProduct allocationProduct : list) {
                StockVoRequest  stockVoRequest = new StockVoRequest();
                // 设置公司名称编码
                stockVoRequest.setCompanyCode(movement.getCompanyCode());
                stockVoRequest.setCompanyName(movement.getCompanyName());
                // 设置物流中心名称编码
                stockVoRequest.setTransportCenterCode(movement.getLogisticsCenterCode());
                stockVoRequest.setTransportCenterName(movement.getLogisticsCenterName());
                //设置库房名称编码
                stockVoRequest.setWarehouseCode(movement.getCalloutWarehouseCode());
                stockVoRequest.setWarehouseName(movement.getCalloutWarehouseName());
                //设置采购组编码名称
                stockVoRequest.setPurchaseGroupCode(movement.getPurchaseGroupCode());
                stockVoRequest.setPurchaseGroupName(movement.getPurchaseGroupName());
                //设置sku编号名称
                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
                stockVoRequest.setSkuName(allocationProduct.getSkuName());
                stockVoRequest.setChangeNum(allocationProduct.getQuantity());
                list1.add(stockVoRequest);
            }
            stockChangeRequest.setStockVoRequests(list1);
            // 调用锁定库存数
            HttpResponse httpResponse= null;
            try {
                httpResponse = stockService.changeStock(stockChangeRequest);

            } catch (Exception e) {
                e.printStackTrace();

                return "false";
            }
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){

            }else{
                log.error(httpResponse.getMessage());
                return "false";
            }
            // 审核不通过
            movement.setMovementStatusCode(AllocationEnum.ALLOCATION_TYPE_REJECTED.getStatus());
            movement.setMovementStatusName(AllocationEnum.ALLOCATION_TYPE_REJECTED.getName());


            //更新状态
            int  k = movementDao.updateByPrimaryKeySelective(movement);
            if (k > 0) {
                return "success";
            }else {
                log.error("调拨单撤销失败");
                return "false";
            }
        } else if(vo.getApplyStatus().intValue()==ApplyStatus.REVOKED.getNumber()){
            movement.setMovementStatusCode(AllocationEnum.ALLOCATION_TYPE_CANCEL.getStatus());
            movement.setMovementStatusName(AllocationEnum.ALLOCATION_TYPE_CANCEL.getName());
            movementDao.updateByPrimaryKeySelective(movement);

            //解锁库存
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            stockChangeRequest.setOperationType(3);
            stockChangeRequest.setOrderCode(movement.getMovementCode());
            //  stockChangeRequest.setOrderType();
            List<StockVoRequest> list1 = new ArrayList<>();
            for (MovementProduct allocationProduct : list) {
                StockVoRequest  stockVoRequest = new StockVoRequest();
                // 设置公司名称编码
                stockVoRequest.setCompanyCode(movement.getCompanyCode());
                stockVoRequest.setCompanyName(movement.getCompanyName());
                // 设置物流中心名称编码
                stockVoRequest.setTransportCenterCode(movement.getLogisticsCenterCode());
                stockVoRequest.setTransportCenterName(movement.getLogisticsCenterName());
                //设置库房名称编码
                stockVoRequest.setWarehouseCode(movement.getCalloutWarehouseCode());
                stockVoRequest.setWarehouseName(movement.getCalloutWarehouseName());
                //设置采购组编码名称
                stockVoRequest.setPurchaseGroupCode(movement.getPurchaseGroupCode());
                stockVoRequest.setPurchaseGroupName(movement.getPurchaseGroupName());
                //设置sku编号名称
                stockVoRequest.setSkuCode(allocationProduct.getSkuCode());
                stockVoRequest.setSkuName(allocationProduct.getSkuName());
                stockVoRequest.setChangeNum(allocationProduct.getQuantity());
                list1.add(stockVoRequest);
            }
            stockChangeRequest.setStockVoRequests(list1);
            // 调用锁定库存数
            HttpResponse httpResponse= null;
            try {
                httpResponse = stockService.changeStock(stockChangeRequest);

            } catch (Exception e) {
                e.printStackTrace();

                return "false";
            }
            if(httpResponse.getCode().equals(MsgStatus.SUCCESS)){

            }else{
                log.error(httpResponse.getMessage());
                return "false";
            }
            // 打印撤销的日志
            productCommonService.getInstance(movement.getMovementCode()+"", HandleTypeCoce.RECOBER_MOVEMENT.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(),workFlowCallbackVO ,HandleTypeCoce.RECOBER_MOVEMENT.getName());

            return "success";
        }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
            //审批中
            return "success";
        }else {
            return "false";
        }
    }

    /**
     * 移库生成出库单
     * @param resVo
     * @return
     */
    @Override
    public String create(MovementResVo resVo) {
        try {
            OutboundReqVo convert =  new MovementResVo2OutboundReqVoConverter(supplierApiService).convert(resVo);
            return  outboundService.save(convert);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new GroundRuntimeException("保存出库单失败");
        }
    }
}
