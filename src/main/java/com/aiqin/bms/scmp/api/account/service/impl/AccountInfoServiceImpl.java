package com.aiqin.bms.scmp.api.account.service.impl;

import com.aiqin.bms.scmp.api.account.dao.AccountDao;
import com.aiqin.bms.scmp.api.account.domain.Account;
import com.aiqin.bms.scmp.api.account.domain.Util.CodeUtil;
import com.aiqin.bms.scmp.api.account.domain.request.AccountRequest;
import com.aiqin.bms.scmp.api.account.domain.request.ExternalAccountRequest;
import com.aiqin.bms.scmp.api.account.domain.request.RoleRequest;
import com.aiqin.bms.scmp.api.account.domain.response.AccountResponse;
import com.aiqin.bms.scmp.api.account.domain.response.ExternalAccountResponse;
import com.aiqin.bms.scmp.api.account.service.AccountInfoService;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.purchase.dao.OperationLogDao;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.supplier.domain.response.account.Role;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    private static Logger LOGGER = LoggerFactory.getLogger(AccountInfoServiceImpl.class);
    /**
     * 公司编码
     */
    private static String COMPANY_CODE = "15";
    /**
     * 公司名称
     */
    private static String COMPANY_NAME = "熙耘供应链供应商集合";
    @Resource
    private AccountDao accountDao;
    @Resource
    private OperationLogDao operationLogDao;
    @Value("${control.url.account}")
    private String CONTROL_URL_ACCOUNT;
    @Value("${control.url.role-list}")
    private String CONTROL_ROLE_LIST;
    @Value("${control.url.role-add}")
    private String CONTROL_ROLE_ADD;
    private static String SYSTEM_CODE = "smps-system";

    @Override
    public HttpResponse<PageResData<Account>> accountList(AccountRequest request) {
        List<Account> list = accountDao.list(request);
        Integer count = accountDao.listCount(request);
        LOGGER.info("查询供应商账号列表:{}", count);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse addAccount(AccountRequest accountRequest, String ticket, String personId) {
        if (CollectionUtils.isEmpty(accountRequest.getRoleIds())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Account account = new Account();
        account.setSupplierCode(accountRequest.getSupplierCode());
        Account request = accountDao.selectOne(account);
        BeanUtils.copyProperties(accountRequest, account);
        //同一个供应商 使用同一个岗位
        if (request != null) {
            account.setPositionName(request.getPositionName());
            account.setPositionCode(request.getPositionCode());
        }
        Account response = accountDao.selectLastOne();
        String username = "";
        if (response == null) {
            username = CodeUtil.getCode(CodeUtil.SUPPLIER_PREFIX, "");
        } else {
            username = CodeUtil.getCode(CodeUtil.SUPPLIER_PREFIX, response.getUsername());
        }
        account.setUsername(username);
        account.setPersonId(username);
        account.setAccountStatus(0);
        account.setCompanyCode(COMPANY_CODE);
        account.setCompanyName(COMPANY_NAME);
        account.setRoleIds(accountRequest.getRoleIds().stream().collect(Collectors.joining(",")));
        ExternalAccountRequest externalAccountRequest = new ExternalAccountRequest();
        BeanUtils.copyProperties(account, externalAccountRequest);
        //业务类型
        externalAccountRequest.setBusinessName(accountRequest.getSupplierName());
        externalAccountRequest.setBusinessType(1);
        externalAccountRequest.setSystemCode(SYSTEM_CODE);
        //增加人员信息 部门信息  岗位信息 绑定岗位 增加账号信息
        HttpClient client = HttpClient.post(CONTROL_URL_ACCOUNT).json(externalAccountRequest).timeout(10000);
        HttpResponse httpResponse = client.action().result(HttpResponse.class);
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            ExternalAccountResponse externalAccountResponse = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), ExternalAccountResponse.class);
            //绑定角色信息
            RoleRequest roleRequest = new RoleRequest();
            roleRequest.setRoleId(account.getRoleIds());
            HttpClient roleClient = HttpClient.post(String.format("%s/%s?ticket=%s&ticket_person_id=%s", CONTROL_ROLE_ADD, externalAccountResponse.getAccountId(), ticket, personId)).json(roleRequest);
            HttpResponse roleResponse = roleClient.action().result(HttpResponse.class);
            if (roleResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
                account.setAccountId(externalAccountResponse.getAccountId());
                account.setPositionCode(externalAccountResponse.getPositionCode());
                account.setPositionName(externalAccountResponse.getPositionName());
                account.setDepartmentName(externalAccountResponse.getDepartmentName());
                account.setDepartmentCode(externalAccountResponse.getDepartmentCode());
                Integer count = accountDao.insert(account);
                LOGGER.info("添加供应商关联表:{}", count);
                //增加操作记录 操作状态  : 0 新增 1 修改 2 下载
                operationLogDao.insert(new OperationLog(account.getUsername(), 0, "新增账号", "", account.getCreateById(), account.getCreateByName()));
            } else {
                return HttpResponse.failureGenerics(ResultCode.CONTROL_ERROR, roleResponse.getMessage());
            }
        } else {
            return HttpResponse.failureGenerics(ResultCode.CONTROL_ERROR, httpResponse.getMessage());
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<List<Role>> selectRole(String personId, String ticket) {
        try {
            HttpClient client = HttpClient.get(CONTROL_ROLE_LIST + "?system_code=smps-system&page_size=1000")
                    .addParameter("ticket", ticket)
                    .addParameter("ticket_person_id", personId);
            HttpResponse response = client.action().result(HttpResponse.class);
            if (response.getCode().equals(MessageId.SUCCESS_CODE)) {
                PageResData resData = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), PageResData.class);
                if (resData != null && CollectionUtils.isNotEmpty(resData.getDataList())) {
                    List<Role> roleList = JsonUtil.fromJson(JsonUtil.toJson(resData.getDataList()), new TypeReference<List<Role>>() {
                    });
                    return HttpResponse.successGenerics(roleList);
                }
                return HttpResponse.successGenerics(new ArrayList<>());
            } else {
                LOGGER.error("调用主控查询异常", response.getMessage());
                throw new GroundRuntimeException("调用主控查询异常");
            }
        } catch (Exception e) {
            LOGGER.error("查询角色异常:{}", e);
            return HttpResponse.failureGenerics(ResultCode.SELECT_ROLE_ERROR, null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse updateAccount(AccountRequest request, String ticket, String personId) {

        Account account = new Account();
        BeanUtils.copyProperties(request,account);
        Account response = accountDao.selectOne(account);
        if (response == null) {
            return HttpResponse.failureGenerics(ResultCode.NO_HAVE_ACCOUNT, null);
        }
        ExternalAccountRequest externalAccountRequest = new ExternalAccountRequest();
        BeanUtils.copyProperties(request, externalAccountRequest);
        //修改person 修改account 删除原来的角色
        if(CollectionUtils.isNotEmpty(request.getRoleIds())){
            String roleIds = request.getRoleIds().stream().collect(Collectors.joining(","));
            externalAccountRequest.setRoleIds(roleIds);
            account.setRoleIds(roleIds);
        }
        externalAccountRequest.setBusinessName(request.getSupplierName());
        externalAccountRequest.setPersonId(response.getPersonId());
        externalAccountRequest.setBusinessType(1);
        externalAccountRequest.setCompanyCode(COMPANY_CODE);
        externalAccountRequest.setCompanyName(COMPANY_NAME);
        externalAccountRequest.setSystemCode(SYSTEM_CODE);
        HttpClient client = HttpClient.put(CONTROL_URL_ACCOUNT).json(externalAccountRequest).timeout(10000);
        HttpResponse httpResponse = client.action().result(HttpResponse.class);
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            if (CollectionUtils.isNotEmpty(request.getRoleIds())) {
                // 修改角色
                RoleRequest roleRequest = new RoleRequest();
                roleRequest.setRoleId(request.getRoleIds().stream().collect(Collectors.joining(",")));
                HttpClient roleClient = HttpClient.post(String.format("%s/%s?ticket=%s&ticket_person_id=%s", CONTROL_ROLE_ADD, response.getAccountId(), ticket, personId)).json(roleRequest);
                HttpResponse roleResponse = roleClient.action().result(HttpResponse.class);
                if (!roleResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
                    LOGGER.error("调用主控查询异常", httpResponse.getMessage());
                    throw new GroundRuntimeException("调用主控查询异常");
                }
            }

        } else {
            LOGGER.error("调用主控查询异常", httpResponse.getMessage());
            throw new GroundRuntimeException("调用主控查询异常");
        }
        // 修改供应链关联表
        Integer count = accountDao.updateByPrimaryKeySelective(account);
        LOGGER.info("修改供应商关联表:{}", count);
        //增加操作记录 操作状态  : 0 新增 1 修改 2 下载
        operationLogDao.insert(new OperationLog(account.getUsername(), 1, "修改账号", "", account.getUpdateById(), account.getUpdateByName()));
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<AccountResponse> accountInfo(String username) {
        Account request = new Account();
        request.setUsername(username);
        Account account = accountDao.selectOne(request);
        if (account == null) {
            HttpResponse.failureGenerics(ResultCode.NO_HAVE_ACCOUNT, null);
        }
        AccountResponse response = new AccountResponse();
        BeanUtils.copyProperties(account, response);
        if (StringUtils.isNotEmpty(account.getRoleIds())) {
            response.setRoleIds(Arrays.asList(account.getRoleIds().split(",")));
        }
        List<OperationLog> operationLogList = operationLogDao.list(account.getUsername());
        response.setOperationLogList(operationLogList);
        return HttpResponse.successGenerics(response);
    }
}
