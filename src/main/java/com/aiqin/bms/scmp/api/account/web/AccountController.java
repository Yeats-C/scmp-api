package com.aiqin.bms.scmp.api.account.web;

import com.aiqin.bms.scmp.api.account.domain.Account;
import com.aiqin.bms.scmp.api.account.domain.request.AccountRequest;
import com.aiqin.bms.scmp.api.account.domain.response.AccountResponse;
import com.aiqin.bms.scmp.api.account.service.AccountInfoService;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.response.account.Role;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Api(tags = "账号相关接口")
@RestController
@RequestMapping("/account")
public class AccountController {
    private static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountInfoService accountInfoService;

    @GetMapping("/list")
    @ApiOperation(value = "查询供应商账号列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
            @ApiImplicitParam(name = "account_name", value = "姓名", type = "String"),
            @ApiImplicitParam(name = "role_id", value = "角色id", type = "String"),
            @ApiImplicitParam(name = "account_status", value = "状态 0 启用 1禁用", type = "Integer"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<Account>> accountList(@RequestParam(value = "page_no", required = false) Integer page_no, @RequestParam(value = "page_size", required = false) Integer page_size,
                                                          @RequestParam(value = "supplier_code", required = false) String supplier_code, @RequestParam(value = "role_id", required = false) String role_id,
                                                          @RequestParam(value = "account_status", required = false) Integer account_status, @RequestParam(value = "account_name", required = false) String accountName) {
        AccountRequest request = new AccountRequest(accountName, role_id, supplier_code, account_status);
        request.setPageNo(page_no);
        request.setPageSize(page_size);
        LOGGER.info("查询供应商账号列表参数:{}", request.toString());
        return accountInfoService.accountList(request);
    }

    @PostMapping("/")
    @ApiOperation(value = "新增供应商账号")
    public HttpResponse addAccount(@RequestBody AccountRequest request,HttpServletRequest httpServletRequest) {
        LOGGER.info("新增供应商账号参数:{}", request.toString());
        String ticket = httpServletRequest.getParameter("ticket");
        String personId = httpServletRequest.getParameter("ticket_person_id");
        return accountInfoService.addAccount(request,ticket,personId);
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "查询供应详情")
    @ApiImplicitParam(name = "username", value = "用户名(账号)", type = "String")
    public HttpResponse<AccountResponse> accountInfo(@PathVariable String username) {
        LOGGER.info("查询供应详情username:{}", username);
        return accountInfoService.accountInfo(username);
    }

    @PutMapping("/{username}")
    @ApiOperation(value = "修改供应商账号")
    @ApiImplicitParam(name = "username", value = "用户名(账号)", type = "String")
    public HttpResponse updateAccount(@PathVariable String username, @RequestBody AccountRequest request,HttpServletRequest httpServletRequest) {
        request.setUsername(username);
        LOGGER.info("修改供应商账号:{}", request.toString());
        String ticket = httpServletRequest.getParameter("ticket");
        String personId = httpServletRequest.getParameter("ticket_person_id");
        return accountInfoService.updateAccount(request,ticket,personId);
    }

    @GetMapping("/role")
    @ApiOperation(value = "查询角色")
    public HttpResponse<List<Role>> selectRole(HttpServletRequest request) {
        String personId = request.getParameter("ticket_person_id");
        String ticket = request.getParameter("ticket");
        LOGGER.info("查询角色,ticket:{},personId:{}", ticket, personId);
        if(StringUtils.isBlank(ticket)||StringUtils.isBlank(personId)){
            return HttpResponse.failureGenerics(ResultCode.REQUIRED_PARAMETER,null);
        }
        return accountInfoService.selectRole(personId, ticket);
    }


}
