package com.aiqin.bms.scmp.api.config;

import com.aiqin.bms.scmp.api.supplier.domain.response.account.UserDataVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.account.UserPosition;
import com.aiqin.bms.scmp.api.supplier.service.AccountService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.SignUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.mgs.control.client.service.TicketService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 拦截器
 */
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static ThreadLocal<AuthToken> filterThreadLocal = new ThreadLocal<AuthToken>();

    public static AuthToken getCurrentAuthToken() {
        AuthToken authToken = filterThreadLocal.get();
        return authToken;
    }
    @Autowired
    private TicketService ticketService;
    @Autowired
    private AccountService accountService;

    @Value("${center.main.host}")
    private String centerMainHost;
    @Value("${mgs.control.system-code}")
    private String systemCode;

    @Value("${web.host-url}")
    private String webHostUrl;

//    @Value("${evn}")
//    private String evn;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        String referer = httpServletRequest.getHeader("referer");
//        boolean fromSwagger = false;
//        if(null != referer){
//            fromSwagger = referer.indexOf("swagger-ui.html") > 0;
//        }
//        if (fromSwagger || (StringUtils.isNotBlank(evn) && "dev".equals(evn))) {
//            AuthToken current = new AuthToken();
//            current.setPersonId("12211");
//            current.setPositionCode("GW0137");
//            current.setPersonName("张昀童");
//            current.setCompanyCode("04");
//            current.setCompanyName("北京爱亲技术股份有限公司");
//            current.setAccountId("b05d34ae4cf442458e141affcdf54532");
//            filterThreadLocal.set(current);
//            return true;
//        }
        String ticket = httpServletRequest.getParameter("ticket");
        String accountId = httpServletRequest.getParameter("account_id");
        log.info("ticket{}",ticket);
        String personId = httpServletRequest.getParameter("ticket_person_id");
        log.info("ticket_person_id{}",ticket);
        if(httpServletRequest.getRequestURL().indexOf(".jpg")!=-1||
                httpServletRequest.getRequestURL().indexOf(".bmp")!=-1||
                httpServletRequest.getRequestURL().indexOf(".png")!=-1||
                httpServletRequest.getRequestURL().indexOf("no_controller")!=-1||StringUtils.isNotBlank(httpServletRequest.getParameter("authority"))){
            return true;
        }
        JSONObject jsonObject = new JSONObject();
        //返回重定向地址到中控登录
        if (StringUtils.isBlank(ticket) || StringUtils.isBlank(personId)) {
            returnJson(httpServletResponse, "没有登录");
            return false;
        }
        //中台校验ticket是否有效
        HttpResponse response = ticketService.verifyTicket(ticket, personId);
        if (!"0".equals(response.getCode())) {
            returnJson(httpServletResponse, "登录失效");
            return false;
        }
        AuthToken current = AuthenticationInterceptor.getCurrentAuthToken();
        if(null == current) {
            UserDataVo userDataVo = accountService.getAccountInfoByAccountId(ticket, personId, accountId);
            if(null == userDataVo) {
                returnJson(httpServletResponse, "获取人员信息失败");
                return false;
            }
            if(CollectionUtils.isEmptyCollection(userDataVo.getUserPositions())) {
                returnJson(httpServletResponse, "获取人员岗位信息失败");
                return false;
            }
            List<UserPosition> userPositions = userDataVo.getUserPositions();
            UserPosition position = null;
            for (UserPosition userPosition : userPositions) {
                if(accountId.equals(userPosition.getAccountId())) {
                    position = userPosition;
                    break;
                }
            }
            if(null == position) {
                returnJson(httpServletResponse, "获取人员岗位信息失败");
                return false;
            }
            if(StringUtils.isBlank(position.getCompanyCode())) {
                returnJson(httpServletResponse, "获取人员公司信息失败");
                return false;
            }
            if(StringUtils.isBlank(position.getCompanyName())) {
                returnJson(httpServletResponse, "获取人员公司信息失败");
                return false;
            }
            current = new AuthToken();
            current.setPersonId(personId);
            current.setTicket(ticket);
            current.setPersonName(position.getPersonName());
            current.setCompanyCode(position.getCompanyCode());
            current.setCompanyName(position.getCompanyName());
            current.setPositionCode(position.getPositionCode());
            current.setAccountId(accountId);
            filterThreadLocal.set(current);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletResponse.setContentType("text/html;charset=utf-8");
    }

    public void returnJson(HttpServletResponse response, String message) throws Exception {
        PrintWriter writer = null;
        JSONObject jsonObject = new JSONObject();
        try {
            response.setHeader("Access-Control-Allow-Origin", webHostUrl);
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,token,X-Requested-With");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json; charset=utf-8");

            jsonObject.put("code", "-10");
            jsonObject.put("redirect", centerMainHost + "/#/?redirect="+ SignUtil.getURLEncoderString(webHostUrl)+ "&systemCode=" + systemCode);
            jsonObject.put("message", message);
            writer = response.getWriter();
            writer.print(jsonObject.toJSONString());
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        filterThreadLocal.remove();
    }

}
