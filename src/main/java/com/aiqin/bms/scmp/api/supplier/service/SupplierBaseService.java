package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-03
 * @time: 11:09
 */
public interface SupplierBaseService {
    /**
     * 获取采购链接
     * @author zth
     * @date 2019/1/3
     * @param path
     * @return java.lang.String
     */
    String getPurChaseApiUrl(String path);
    /**
     * 获取商品链接
     * @author zth
     * @date 2019/1/3
     * @param path
     * @return java.lang.String
     */
    String getProductApiUrl(String path);

    String getSkuApiUrl(String path);

    /**
     * 门店api链接
     * @author NullPointException
     * @date 2019/5/16
     * @param path
     * @return java.lang.String
     */
    String getStoreApiUrl(String path);

    /**
     * 获取商品链接
     * @author zth
     * @date 2019/1/3
     * @param vo 传入的vo
     * @param workFlow 模块的枚举值
     * @return java.lang.String
     */
    WorkFlowRespVO callWorkFlowApi(WorkFlowVO vo, WorkFlow workFlow);
}
