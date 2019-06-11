package com.aiqin.bms.scmp.api.product.service;


import com.aiqin.bms.scmp.api.base.WorkFlow;
import com.aiqin.bms.scmp.api.common.workflow.WorkFlowVO;
import com.aiqin.bms.scmp.api.product.domain.response.workflow.WorkFlowRespVO;

/**
 * 刘大爷
 */
public interface ProductBaseService {
    String getSupplierApiUrl(String path);

    /**
     * 获取商品链接
     * @author zth
     * @date 2019/1/3
     * @param vo 传入的vo
     * @param workFlow 模块的枚举值
     * @return java.lang.String
     */
    WorkFlowRespVO callWorkFlowApi(WorkFlowVO vo, WorkFlow workFlow);

    String getCode(String prefix, String Code);
}
