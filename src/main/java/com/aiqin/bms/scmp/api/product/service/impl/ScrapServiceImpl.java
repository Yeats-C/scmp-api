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
import com.aiqin.bms.scmp.api.product.domain.request.scrap.QueryScrapReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.scrap.ScrapReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.scrap.QueryScrapResVo;
import com.aiqin.bms.scmp.api.product.domain.response.scrap.ScrapResVo;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductMapper;
import com.aiqin.bms.scmp.api.product.service.AllocationService;
import com.aiqin.bms.scmp.api.product.service.ProductOperationLogService;
import com.aiqin.bms.scmp.api.product.service.ScrapService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname: ScrapServiceImpl
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Slf4j
@Service
@WorkFlowAnnotation(WorkFlow.SCRAP)
public class ScrapServiceImpl extends BaseServiceImpl implements ScrapService, WorkFlowHelper {

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
    public BasePage<QueryScrapResVo> getList(QueryScrapReqVo vo) {
        vo.setAllocationType(AllocationTypeEnum.SCRAP.getType());
        AuthToken authToken = getUser();
        vo.setCompanyCode(authToken.getCompanyCode());
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<QueryScrapResVo> list = allocationMapper.getScrapList(vo);
        BasePage<QueryScrapResVo> basePage = PageUtil.getPageList(vo.getPageNo(),list);
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
    public Long save(ScrapReqVo vo) {
        AllocationReqVo reqVo = new AllocationReqVo();
        BeanCopyUtils.copy(vo,reqVo);
        reqVo.setCallOutLogisticsCenterCode(vo.getLogisticsCenterCode());
        reqVo.setCallOutLogisticsCenterName(vo.getLogisticsCenterName());
        reqVo.setCallInLogisticsCenterCode(vo.getLogisticsCenterCode());
        reqVo.setCallInLogisticsCenterName(vo.getLogisticsCenterName());
        reqVo.setCallOutWarehouseCode(vo.getWarehouseCode());
        reqVo.setCallOutWarehouseName(vo.getWarehouseName());
        reqVo.setCallInWarehouseCode(vo.getWarehouseCode());
        reqVo.setCallInWarehouseName(vo.getWarehouseName());
        reqVo.setAllocationType(AllocationTypeEnum.SCRAP.getType());
        reqVo.setAllocationTypeName(AllocationTypeEnum.SCRAP.getTypeName());
        return allocationService.save(reqVo);
    }

    /**
     * 查询移库单详情
     *
     * @param id
     * @return
     */
    @Override
    public ScrapResVo view(Long id) {
        ScrapResVo scrapResVo = new ScrapResVo();
        Allocation allocation = allocationMapper.selectByPrimaryKey(id);
        if(null == allocation){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        BeanCopyUtils.copy(allocation,scrapResVo);
        scrapResVo.setLogisticsCenterCode(allocation.getCallOutLogisticsCenterCode());
        scrapResVo.setLogisticsCenterName(allocation.getCallOutLogisticsCenterName());
        scrapResVo.setWarehouseCode(allocation.getCallOutWarehouseCode());
        scrapResVo.setWarehouseName(allocation.getCallOutWarehouseName());
        scrapResVo.setScrapCode(allocation.getAllocationCode());
        scrapResVo.setScrapStatusCode(allocation.getAllocationStatusCode());
        scrapResVo.setScrapStatusName(allocation.getAllocationStatusName());
        scrapResVo.setSkuList(allocationProductMapper.selectByAllocationCode(scrapResVo.getScrapCode()));
        scrapResVo.setBatchSkuList(allocationProductBatchMapper.selectByAllocationCode(scrapResVo.getScrapCode()));
        // 获取日志
        if (null != scrapResVo) {
            //获取操作日志
            OperationLogVo operationLogVo = new OperationLogVo();
            operationLogVo.setPageNo(1);
            operationLogVo.setPageSize(100);
            operationLogVo.setObjectType(ObjectTypeCode.ALLOCATION.getStatus());
            operationLogVo.setObjectId(scrapResVo.getScrapCode());
            List<LogData> pageList = productOperationLogService.getLogType(operationLogVo);
            scrapResVo.setLogDataList(pageList);
        }
        return  scrapResVo;
    }

    /**
     * 撤销移库单
     *
     * @param id
     * @return
     */
    @Override
    public int revocation(Long id) {
        return 0;
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
