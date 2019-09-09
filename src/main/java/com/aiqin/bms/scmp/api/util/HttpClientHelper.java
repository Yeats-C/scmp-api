package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.ground.util.http.AbstractHttpClient;
import com.aiqin.ground.util.http.HttpClient;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * Createed by sunx on 2018/11/22.<br/>
 */
public class HttpClientHelper {
    public static AbstractHttpClient getCurrentClientEx(AbstractHttpClient client){
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(Objects.nonNull(authToken)){
            if(StringUtils.isNotBlank(authToken.getTicket())){
                client.addParameter(Global.TICKET,authToken.getTicket());
            }
            if(StringUtils.isNotBlank(authToken.getPersonId())){
                client.addParameter(Global.TICKET_PERSON_ID,authToken.getPersonId());
            }
            if(StringUtils.isNotBlank(authToken.getAccountId())){
                client.addParameter("account_id",authToken.getAccountId());
            }
            if(StringUtils.isNotBlank(authToken.getCompanyCode())){
                client.addParameter("company_code",authToken.getCompanyCode());
            }
            if(StringUtils.isNotBlank(authToken.getCompanyName())){
                client.addParameter("company_name", authToken.getCompanyName());
            }
            if(StringUtils.isNotBlank(authToken.getPersonName())){
                client.addParameter("person_name", authToken.getPersonName());
            }
            if(StringUtils.isNotBlank(authToken.getPositionCode())){
                client.addParameter("position_code",authToken.getPositionCode());
            }
        }
        client.timeout(15000);
        return client;
    }

    public static HttpClient getCurrentClient(HttpClient client){
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(Objects.nonNull(authToken)){
            if(StringUtils.isNotBlank(authToken.getPersonId())){
                client.addParameter(Global.TICKET_PERSON_ID,authToken.getPersonId());
            }
            if(StringUtils.isNotBlank(authToken.getTicket())){
                client.addParameter(Global.TICKET,authToken.getTicket());
            }
            if(StringUtils.isNotBlank(authToken.getAccountId())){
                client.addParameter("account_id",authToken.getAccountId());
            }
            if(StringUtils.isNotBlank(authToken.getCompanyCode())){
                client.addParameter("company_code",authToken.getCompanyCode());
            }
            if(StringUtils.isNotBlank(authToken.getCompanyName())){
                client.addParameter("company_name", authToken.getCompanyName());
            }
            if(StringUtils.isNotBlank(authToken.getPersonName())){
                client.addParameter("person_name", authToken.getPersonName());
            }
            if(StringUtils.isNotBlank(authToken.getPositionCode())){
                client.addParameter("position_code",authToken.getPositionCode());
            }
        }
        client.timeout(15000);
        return client;
    }
}
