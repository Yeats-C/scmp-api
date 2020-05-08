package com.aiqin.bms.scmp.api.supplier.web.supplier;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyAcctReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComAcctInfo2RespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.QueryApplySupplierComAcctRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.QuerySupplierComAcctRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComAcctMainRespVO;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplyComAcctService;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 15:50
 */
@RestController
@RequestMapping("/supplier/company/account")
@Api(description = "供应商账户管理")
public class SupplyComAcctController {
    @Autowired
    private ApplySupplyComAcctService applySupplyComAcctService;

    @PostMapping("/apply/add")
    @ApiOperation("新增供应商账户申请")
    public HttpResponse addApplySupplyComAcct(@RequestBody @Valid ApplySupplyCompanyAcctReqVO applySupplyCompanyAcctReqVO){
        try {

            return HttpResponse.success( applySupplyComAcctService.saveApply(applySupplyCompanyAcctReqVO));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()+"，保存失败"));
        }
    }
    /**
     * 修改供应商账户
     * @author zth
     * @date 2018/12/4
     * @param applySupplyCompanyAcctReq
     * @return HttpResponse
     */
    @PutMapping("/updateAccount")
    @ApiOperation("修改供应商账户")
    public HttpResponse updateSupplyCompany(@RequestBody @Valid ApplySupplyCompanyAcctReqVO applySupplyCompanyAcctReq){
        try {

            return HttpResponse.success(applySupplyComAcctService.update(applySupplyCompanyAcctReq));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()));
        }
    }
    /**
     * 通过id查询供应商账户
     * @author zth
     * @date 2018/12/4
     * @param id
     * @return HttpResponse
     */
    @GetMapping("/getApplyAccount")
    @ApiOperation("通过id查询申请供应商账户详情")
    public HttpResponse<ApplySupplyComAcctInfo2RespVO> getApplySupplyCompanyAccountById(@RequestParam Long id){
        try {
            ApplySupplyComAcctInfo2RespVO resp = applySupplyComAcctService.getApplySupplyCompanyAccountById(id);
            return HttpResponse.success(resp);
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()));
        }
    }
    /**
     * 通过id查询供应商账户
     * @author zth
     * @date 2018/12/4
     * @param id
     * @return HttpResponse
     */
    @GetMapping("/getSupplyAccount")
    @ApiOperation("通过id查询供应商账户详情")
    public HttpResponse<SupplyComAcctMainRespVO> getSupplyCompanyAccount(@RequestParam  Long id){
        try {
            SupplyComAcctMainRespVO resp = applySupplyComAcctService.getSupplyCompanyAccountById(id);
            return HttpResponse.success(resp);
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()));
        }
    }
    /**
     * 申请供应商账户列表
     * @author zth
     * @date 2018/12/4
     * @param vo 查询vo
     * @return HttpResponse
     */
    @PostMapping("/applyList")
    @ApiOperation("申请供应商账户列表")
    public HttpResponse<QueryApplySupplierComAcctRespVo> applyList(@RequestBody QueryApplySupplierComAcctReqVo vo){
        try {
            BasePage<QueryApplySupplierComAcctRespVo> s = applySupplyComAcctService.selectApplyListByQueryVO(vo);
            return HttpResponse.success(s);
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()));
        }
    }
    /**
     * 供应商账户列表
     * @author zth
     * @date 2018/12/4
     * @param vo 查询vo
     * @return HttpResponse
     */
    @PostMapping("/supplyList")
    @ApiOperation("供应商账户列表")
    public HttpResponse<BasePage<QuerySupplierComAcctRespVo>> supplyList(@RequestBody QuerySupplierComAcctReqVo vo){
        try {
            BasePage<QuerySupplierComAcctRespVo> s = applySupplyComAcctService.selectSupplyListByQueryVO(vo);
            return HttpResponse.success(s);
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()));
        }
    }

    @GetMapping("/list/supplierCode")
    @ApiOperation("根据供应商编码查询")
    public HttpResponse<List<QuerySupplierComAcctRespVo>> selectSupplierComAcctBySupplierCode(String supplierCode){
        try {
            return HttpResponse.success(applySupplyComAcctService.selectSupplierComAcctBySupplierCode(supplierCode));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()));
        }
    }

    @GetMapping("/cancelApply")
    @ApiOperation("申请供应商账户撤销")
    public HttpResponse cancel(@RequestParam Long id){
        try {
            applySupplyComAcctService.cancelById(id);
            return HttpResponse.success();
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()));
        }
    }

    @PostMapping("/getSupplyAccountByCode")
    @ApiOperation("通过供应商编码查询供应商账户详情")
    public HttpResponse<List<SupplyCompanyAccount>> getSupplyAccountByCompanyCode(@RequestParam List<String> codes){
        try {
            List<SupplyCompanyAccount> resp = applySupplyComAcctService.getSupplyCompanyAccountByCompanyCode(codes);
            return HttpResponse.success(resp);
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, e.getMessage()));
        }
    }

}
