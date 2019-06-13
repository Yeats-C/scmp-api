package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplier;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplierReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.CancelApplySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierUpdateReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplierDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplierListRespVO;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;


/**
 * @功能说明: 申请供应商
 * @author: wangxu
 * @date: 2018/12/3 0003 16:49
 */
public interface ApplySupplierService extends WorkFlowHelper {
    /**
     * 生成编码，复制对象
     * @param supplierReq
     * @return
     */
    Long saveApply(ApplySupplierReqVO supplierReq);

    /**
     * 复制对象注入
     * @param supplierUpdateReqVO
     * @return
     */
    int  updateApply(SupplierUpdateReqVO supplierUpdateReqVO);

    /**
     * 调用DAO层新增数据
     * @param supplierReq
     * @return
     */
    Long insert(ApplySupplierReqDTO supplierReq);

    /**
     * 调用DAO层修改数据
     * @param applySupplier
     * @return
     */
    int update(ApplySupplier applySupplier);

    /**
     * 获取申请列表
     * @param queryApplySupplierReqVO
     * @return
     */
    BasePage<ApplySupplierListRespVO> getApplyList(QueryApplySupplierReqVO queryApplySupplierReqVO);

    /**
     * 获取申请详情
     * @param applyCode
     * @return
     */
    ApplySupplierDetailRespVO getApplySupplierDetail(String applyCode);


    /**
     * 撤销申请表
     * @param cancelApplySupplierReqVO
     * @return
     */
    int cancelApply(CancelApplySupplierReqVO cancelApplySupplierReqVO);

    /**
     * 供应商审批流
     * @param applySupplierReqDTO
     */
    void workFlow(ApplySupplierReqDTO applySupplierReqDTO);

}
