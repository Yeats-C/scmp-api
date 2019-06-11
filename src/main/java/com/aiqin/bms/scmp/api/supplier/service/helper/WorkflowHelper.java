package com.aiqin.bms.scmp.api.supplier.service.helper;

import com.aiqin.bms.scmp.api.common.workflow.WorkFlowCallbackVO;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-19
 * @time: 15:29
 */
public interface WorkflowHelper {
    /**
     * 审核回调接口
     * @author zth
     * @date 2019/1/15
     * @param vo
     * @return
     */
    String workFlowCallback(WorkFlowCallbackVO vo);
}
