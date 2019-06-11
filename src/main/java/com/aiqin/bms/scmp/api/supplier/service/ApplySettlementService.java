package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySettlementInformation;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySettlementDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySettlementInfoReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySettlementInfoReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySettlementVO;
import com.aiqin.bms.scmp.api.common.workflow.WorkFlowCallbackVO;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/4 0004 10:06
 */
public interface ApplySettlementService {
    /**
     * 编码，数据设置，日志添加
     * @param applySettlementInfoReqVO
     * @return
     */
    Long saveApply(ApplySettlementInfoReqVO applySettlementInfoReqVO);

    /**
     * 复制对象
     * @param applySettlementVO
     * @return
     */
    int updateApply(ApplySettlementVO applySettlementVO);

    /**
     * 撤销方法赋值等
     * @param id
     * @return
     */
    int cancelApply(Long id);
    /**
     * 调用DAO层修改数据
     * @param applySettlementInformation
     * @return
     */
    int update(ApplySettlementInformation applySettlementInformation);
    /**
     * 调用DAO层插入数据
     * @param applySettlementInfoReqDTO
     * @return
     */
    Long insert(ApplySettlementInfoReqDTO applySettlementInfoReqDTO);

    /**
     * 非前端请求新增
     * @param applySettlementDTO
     * @return
     */
    Long insideSaveApply(ApplySettlementDTO applySettlementDTO);

    /**
     * 结算信息审批流
     * @param applySettlementInformation
     * @return
     */
    String insideWorkFlowCallback(ApplySettlementInformation applySettlementInformation, WorkFlowCallbackVO workFlowCallbackVO);

}
