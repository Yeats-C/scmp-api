package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.AllocationTypeEnum;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.ObjectTypeCode;
import com.aiqin.bms.scmp.api.product.domain.pojo.Allocation;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.movement.QueryMovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.QueryMovementResVo;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductMapper;
import com.aiqin.bms.scmp.api.product.service.AllocationService;
import com.aiqin.bms.scmp.api.product.service.MovementService;
import com.aiqin.bms.scmp.api.product.service.ProductOperationLogService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@WorkFlowAnnotation(WorkFlow.MOVEMENT_ODER)
public class MovementServiceImpl extends BaseServiceImpl implements MovementService, WorkFlowHelper {

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private AllocationMapper allocationMapper;

    @Autowired
    private AllocationProductMapper allocationProductMapper;

    @Autowired
    private AllocationProductBatchMapper allocationProductBatchMapper;

    @Autowired
    private ProductOperationLogService productOperationLogService;
    /**
     * 移库列表搜索
     * @param vo 列表搜索实体
     * @return  列表返回实体
     */
    @Override
    public BasePage<QueryMovementResVo> getList(QueryMovementReqVo vo) {
        vo.setAllocationType(AllocationTypeEnum.MOVE.getType());
        AuthToken authToken = getUser();
        vo.setCompanyCode(authToken.getCompanyCode());
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<QueryMovementResVo> list = allocationMapper.getMoveList(vo);
        BasePage<QueryMovementResVo> basePage = PageUtil.getPageList(vo.getPageNo(),list);
        return basePage;
    }


    /**
     * 新增移库单转化实体
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(MovementReqVo vo) {
        AllocationReqVo reqVo = new AllocationReqVo();
        BeanCopyUtils.copy(vo,reqVo);
        reqVo.setCallOutLogisticsCenterCode(vo.getLogisticsCenterCode());
        reqVo.setCallOutLogisticsCenterName(vo.getLogisticsCenterName());
        reqVo.setCallInLogisticsCenterCode(vo.getLogisticsCenterCode());
        reqVo.setCallInLogisticsCenterName(vo.getLogisticsCenterName());
        reqVo.setAllocationType(AllocationTypeEnum.MOVE.getType());
        reqVo.setAllocationTypeName(AllocationTypeEnum.MOVE.getTypeName());
        return allocationService.save(reqVo);
    }

    /**
     * 查询移库单详情
     *
     * @param id
     * @return
     */
    @Override
    public MovementResVo view(Long id) {
        MovementResVo movementResVo = new MovementResVo();
        Allocation allocation = allocationMapper.selectByPrimaryKey(id);
        if(null == allocation){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        BeanCopyUtils.copy(allocation,movementResVo);
        movementResVo.setMovementCode(allocation.getAllocationCode());
        movementResVo.setMovementStatusCode(allocation.getAllocationStatusCode());
        movementResVo.setMovementStatusName(allocation.getAllocationStatusName());
        movementResVo.setLogisticsCenterCode(allocation.getCallInLogisticsCenterCode());
        movementResVo.setLogisticsCenterName(allocation.getCallInLogisticsCenterName());
        movementResVo.setSkuList(allocationProductMapper.selectByAllocationCode(movementResVo.getMovementCode()));
        movementResVo.setBatchSkuList(allocationProductBatchMapper.selectByAllocationCode(movementResVo.getMovementCode()));
        // 获取日志
        if (null != movementResVo) {
            //获取操作日志
            OperationLogVo operationLogVo = new OperationLogVo();
            operationLogVo.setPageNo(1);
            operationLogVo.setPageSize(100);
            operationLogVo.setObjectType(ObjectTypeCode.ALLOCATION.getStatus());
            operationLogVo.setObjectId(movementResVo.getMovementCode());
            List<LogData> pageList = productOperationLogService.getLogType(operationLogVo);
            movementResVo.setLogDataList(pageList);
        }
        return  movementResVo;
    }

    /**
     * 撤销移库单
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int revocation(Long id) {
        return allocationService.revocation(id);
    }

    /**
     * 审核回调接口
     *
     * @param vo
     * @return
     * @author zth
     * @date 2019/1/15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo) {
        return allocationService.nativeWorkFlowCallback(vo);
    }
}
