package com.aiqin.bms.scmp.api.supplier.web.supplier.applysupplycom;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.CancelApplySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComListRespVO;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplyComServcie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 15:20
 */
@RestController
@RequestMapping("/supplyCom/apply")
@Api(description = "供货单位申请管理")
public class ApplySupplyComController {
    @Autowired
    private ApplySupplyComServcie applySupplyComServcie;

    @PostMapping("/list")
    @ApiOperation("供货单位申请管理")
    public HttpResponse<BasePage<ApplySupplyComListRespVO>> listApplySupplyCompany(@RequestBody @Validated QueryApplySupplyComReqVO queryApplySupplyComReqVO){
        try {
            BasePage<ApplySupplyComListRespVO> result = applySupplyComServcie.getApplyList(queryApplySupplyComReqVO);
            return HttpResponse.success(result);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查看详情")
    public HttpResponse<ApplySupplyComDetailRespVO> getApplySupplyComDetail(@RequestParam @ApiParam("供货单位申请编码,必传") String applyCode){
        try {
            ApplySupplyComDetailRespVO applySupplyComDetailRespVO = applySupplyComServcie.getApplySupplyComDetail(applyCode);
            return HttpResponse.success(applySupplyComDetailRespVO);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @PutMapping("/cancel")
    @ApiOperation("撤销")
    public HttpResponse cancelApplySupplyCom(@RequestBody @Validated CancelApplySupplyComReqVO cancelApplySupplyComReqVO){
        try {
            applySupplyComServcie.cancelApply(cancelApplySupplyComReqVO);
            return HttpResponse.success();
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.CANCEL_ERROR);
        }
    }

}
