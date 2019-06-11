package com.aiqin.bms.scmp.api.config;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class CommonInterceptor {

    /**
     * 获取登陆人
     *
     * @return
     */
    public Map<String,String> getRequest() {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        Map<String,String> map = new HashMap<>();
        if(null != authToken) {
            if(StringUtils.isNotBlank(authToken.getPersonName())) {
                map.put("userName", authToken.getPersonName());
            }else {
                map.put("userName", "爱亲张昀童");
            }
            if(StringUtils.isNotBlank(authToken.getTicket())){
                map.put("ticket",authToken.getTicket());
            }
            if(StringUtils.isNotBlank(authToken.getPersonId())){
                map.put("ticketPersonId",authToken.getPersonId());
            }
            if(StringUtils.isNotBlank(authToken.getAccountId())){
                map.put("accountId",authToken.getAccountId());
            }
            if(StringUtils.isNotBlank(authToken.getCompanyCode())){
                map.put("companyCode",authToken.getCompanyCode());
            }
            if(StringUtils.isNotBlank(authToken.getCompanyName())){
                map.put("companyName",authToken.getCompanyName());
            }
            if(StringUtils.isNotBlank(authToken.getPositionCode())){
                map.put("positionCode",authToken.getPositionCode());
            }
        } else {
            map.put("userName", "爱亲张昀童");
        }

        return map;
    }

    @Before("@annotation(com.aiqin.bms.scmp.api.common.Save)")
    public void source(JoinPoint joinPoint) throws Exception {
        Date date = new Date();
        Map<String,String> map = getRequest();
        String userName = null;
        if(map != null){
            userName = getRequest().get("userName");
        }
        int len = joinPoint.getArgs().length;
        Object[] pl = joinPoint.getArgs();
        if (len != 1) {
            return;
        }
        if (pl[0] instanceof CommonBean) {
            CommonBean base = (CommonBean) pl[0];
            base.setDelFlag((byte) 0);
            base.setCreateTime(date);
            base.setCreateBy(userName);
            base.setUpdateTime(date);
            base.setUpdateBy(userName);
        }
    }
    @Before("@annotation(com.aiqin.bms.scmp.api.common.Update)")
    public void sourceList(JoinPoint joinPoint) {
        Date date = new Date();
        Map<String,String> map = getRequest();
        String userName = null;
        if(map != null){
            userName = getRequest().get("userName");
        }
        if (joinPoint.getArgs()[0] instanceof CommonBean) {
            CommonBean base = (CommonBean) joinPoint.getArgs()[0];
            base.setUpdateTime(date);
            base.setUpdateBy(userName);
            BeanCopyUtils.copyIgnoreNullValue(base, joinPoint.getArgs()[0]);
        }

    }

    @Before("@annotation(com.aiqin.bms.scmp.api.common.SaveList)")
    public void InsertList(JoinPoint joinPoint) {
        Map<String,String> map = getRequest();
        String userName = null;
        if(map != null){
            userName = getRequest().get("userName");
        }
        int len = joinPoint.getArgs().length;
        List<CommonBean> baseCommonEntities = new LinkedList<>();
        Object[] pl = joinPoint.getArgs();
        if (len != 1) {
            return;
        }
        if (pl[0] instanceof Collection) {
            Collection list = (Collection) pl[0];
            for (Object item : list) {
                CommonBean base = (CommonBean) item;
                base.setCreateTime(new Date());
                base.setCreateBy(userName);
                base.setDelFlag((byte) 0);
                base.setUpdateTime(new Date());
                base.setUpdateBy(userName);
                baseCommonEntities.add(base);
            }
        }
    }

    @Before("@annotation(com.aiqin.bms.scmp.api.common.UpdateList)")
    public void UpdateList(JoinPoint joinPoint) {
        Map<String,String> map = getRequest();
        String userName = null;
        if(map != null){
            userName = getRequest().get("userName");
        }

        List<CommonBean> updateList = new ArrayList<>();
        int len = joinPoint.getArgs().length;
        Object[] pl = joinPoint.getArgs();
        if (len != 1) {
            return;
        }
        if (pl[0] instanceof Collection) {
            Collection list = (Collection) pl[0];
            for (Object item : list) {
                CommonBean base = (CommonBean) item;
                base.setUpdateTime(new Date());
                base.setUpdateBy(userName);
                BeanCopyUtils.copyIgnoreNullValue(base, joinPoint.getArgs()[0]);
                updateList.add(base);
            }
        }
    }
}
