package com.aiqin.bms.scmp.api.workflow.utils;

import com.aiqin.bms.scmp.api.util.SpringContextUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 10:52
 */
@Component
public class WorkFlowCallBackFactory {

    @Autowired
    private SpringContextUtil springContextUtil;

    public static Map<WorkFlow, WorkFlowHelper> beanContent = Maps.newConcurrentMap();

    //工厂将 Spring 装配的相关的 Bean 用 Map 保存起来
    @PostConstruct
    public void init() {
        Map<String, Object> beanMap = springContextUtil.getContext().getBeansWithAnnotation(WorkFlowAnnotation.class);
        for (Object workFlow : beanMap.values()) {
            WorkFlowAnnotation annotation = workFlow.getClass().getAnnotation(WorkFlowAnnotation.class);
            beanContent.put(annotation.value(), (WorkFlowHelper)workFlow);
        }
    }
    public static WorkFlowHelper createWorkFlow(WorkFlow workFlow) {
        return beanContent.get(workFlow);
    }
}
