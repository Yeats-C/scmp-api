package com.aiqin.bms.scmp.api.base.service;


import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;

/**
 * 刘大爷
 */
public interface BaseService {

    AuthToken getUser();

    /**
     * 获取门店api前缀
     *
     * @param path
     * @return java.lang.String
     * @author NullPointException
     * @date 2019/6/15
     */
    String getStoreApiUrl(String path);

    /**
     * 调用审批接口
     *
     * @param vo       传入的vo
     * @param workFlow 模块的枚举值
     * @return java.lang.String
     * @author zth
     * @date 2019/1/3
     */
    WorkFlowRespVO callWorkFlowApi(WorkFlowVO vo, WorkFlow workFlow);

    /**
     * 调用审批取消接口
     *
     * @param vo 只需要formNo
     * @return com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO
     * @author NullPointException
     * @date 2019/6/14
     */
    WorkFlowRespVO cancelWorkFlow(WorkFlowVO vo);

    /**
     * 获取编码
     *
     * @param prefix 前缀
     * @param Code   需要获取的编码
     * @return java.lang.String
     * @author NullPointException
     * @date 2019/6/14
     */
    String getCode(String prefix, String Code);


}
