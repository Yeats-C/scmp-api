package com.aiqin.bms.scmp.api.workflow.service.impl;

import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.service.WorkFlowService;
import com.aiqin.bms.scmp.api.workflow.utils.WorkFlowCallBackFactory;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 17:13
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {
    @Override
    public String WorkFlowCallBack(WorkFlow workFlow, WorkFlowCallbackVO vo) {
        return WorkFlowCallBackFactory.createBehavior(workFlow).workFlowCallback(vo);
    }
}
