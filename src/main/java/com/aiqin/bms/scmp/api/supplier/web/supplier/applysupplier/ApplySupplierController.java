package com.aiqin.bms.scmp.api.supplier.web.supplier.applysupplier;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.CancelApplySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplierDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplierListRespVO;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @功能说明:申请供应商信息
 * @author: wangxu
 * @date: 2018/11/30 0030 11:02
 */
@RestController
@RequestMapping("/applySupplier")
@Api(description = "供应商集团申请管理")
public class ApplySupplierController {
    @Autowired
    private ApplySupplierService applySupplierService;

    @PostMapping("/list")
    @ApiOperation("供应商申请管理")
    public HttpResponse<BasePage<ApplySupplierListRespVO>> listApplySupplier(@RequestBody @Validated QueryApplySupplierReqVO queryApplySupplierReqVO){
        try {
            BasePage<ApplySupplierListRespVO> listApply= applySupplierService.getApplyList(queryApplySupplierReqVO);
            return HttpResponse.success(listApply);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查看详情")
    public HttpResponse<ApplySupplierDetailRespVO> getApplySupplierDetail( @RequestParam @ApiParam(value = "申请编码,必传") String applyCode){
        try {
            ApplySupplierDetailRespVO detailRespVO= applySupplierService.getApplySupplierDetail(applyCode);
            return HttpResponse.success(detailRespVO);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @PutMapping("/cancel")
    @ApiOperation("撤销")
    public HttpResponse cancelSupplierInfo(@RequestBody @Validated CancelApplySupplierReqVO cancelApplySupplierReqVO){
        try {
            applySupplierService.cancelApply(cancelApplySupplierReqVO);
            return HttpResponse.success();
        } catch (GroundRuntimeException e) {
            return HttpResponse.failure(ResultCode.CANCEL_ERROR);
        }
    }



}
