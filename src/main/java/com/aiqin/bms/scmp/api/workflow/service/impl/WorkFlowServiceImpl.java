package com.aiqin.bms.scmp.api.workflow.service.impl;

import com.aiqin.bms.scmp.api.common.WorkFlowReturn;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.service.WorkFlowService;
import com.aiqin.bms.scmp.api.workflow.utils.WorkFlowCallBackFactory;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 17:13
 */
@Service
@Slf4j
public class WorkFlowServiceImpl implements WorkFlowService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallBack(WorkFlow workFlow, WorkFlowCallbackVO vo) {
        try {
            return WorkFlowCallBackFactory.createWorkFlow(workFlow).workFlowCallback(vo);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return WorkFlowReturn.FALSE;
        }
    }
}
