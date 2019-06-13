package com.aiqin.bms.scmp.api.workflow.helper;


import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-19
 * @time: 15:29
 */
public interface WorkFlowHelper {
    /**
     * 审核回调接口
     * @author zth
     * @date 2019/1/15
     * @param vo
     * @return
     */
    String workFlowCallback(WorkFlowCallbackVO vo);
}
