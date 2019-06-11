package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.mgs.control.client.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/index")
@Api(description = "登录信息")
public class WebIndexController  {
    @Value("${mgs.control.system-code}")
    private String systemCode;

    @Resource
    private TicketService ticketService;

    @GetMapping("/menu")
    @ApiOperation(value = "获取菜单资源", notes = "获取菜单资源")
    public HttpResponse index(String account_id, String ticket, String person_id,HttpServletRequest request) {
        if("0".equals(request.getParameter("authority"))){
            return HttpResponse.success(true);
        }
        return ticketService.accountResources(account_id, ticket, systemCode, person_id);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public HttpResponse logout(@RequestParam(value = "ticket_person_id")String ticketPersonId,
                               @RequestParam(value = "ticket") String ticket, HttpServletRequest request) {
        return ticketService.logout(ticket,ticketPersonId, request);
    }

    @GetMapping("/account/info")
    @ApiOperation(value = "根据员工编号获取员工信息", notes = "根据员工编号获取员工信息")
    public HttpResponse accountInfo(@RequestParam(value = "ticket_person_id",required = false)String ticketPersonId,
                                    @RequestParam(value = "ticket",required = false) String ticket,HttpServletRequest request) {
        if("0".equals(request.getParameter("authority"))){
            return HttpResponse.success(true);
        }
        return ticketService.accountInfo(ticketPersonId, ticket);
    }

}
