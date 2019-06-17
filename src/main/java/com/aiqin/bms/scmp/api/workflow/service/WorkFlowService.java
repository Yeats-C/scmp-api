package com.aiqin.bms.scmp.api.workflow.service;

import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 17:11
 */
public interface WorkFlowService {
    /**
     * 审批回调
     * @author NullPointException
     * @date 2019/6/12
     * @param workFlow 回调的类型
     * @param vo 审批传入的值
     * @return java.lang.String
     */
    String WorkFlowCallBack(WorkFlow workFlow, WorkFlowCallbackVO vo);
}
