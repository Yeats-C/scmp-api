package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyQueryResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectResponse;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
@Api(tags = "退供相关接口")
@RequestMapping("/reject")
@RestController
@SuppressWarnings("unchecked")
public class GoodsRejectController {

    @Resource
    private GoodsRejectService goodsRejectService;

    @GetMapping("/apply/list")
    @ApiOperation(value = "退供申请单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reject_apply_record_code", value = "退货申请单号", type = "String"),
            @ApiImplicitParam(name = "apply_type", value = "申请单类型: 0 手动 1自动", type = "Integer"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组 code", type = "String"),
            @ApiImplicitParam(name = "apply_record_status", value = "退供申请单状态: 0  已完成 1 待提交", type = "Integer"),
            @ApiImplicitParam(name = "begin_time", value = "开始时间", type = "String"),
            @ApiImplicitParam(name = "finish_time", value = "结束时间", type = "String"),
    })
    public HttpResponse<List<RejectApplyQueryResponse>> rejectApplyList(@RequestParam(value = "reject_apply_record_code", required = false) String rejectApplyRecordCode,
                                                                        @RequestParam(value = "apply_type", required = false) Integer applyType,
                                                                        @RequestParam(value = "apply_record_status", required = false) Integer applyRecordStatus,
                                                                        @RequestParam(value = "begin_time", required = false) String beginTime,
                                                                        @RequestParam(value = "finish_time", required = false) String finishTime,
                                                                        @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode) {
        RejectApplyQueryRequest rejectApplyQueryRequest = new RejectApplyQueryRequest(rejectApplyRecordCode, applyType, purchaseGroupCode, applyRecordStatus, beginTime, finishTime);
        return goodsRejectService.rejectApplyList(rejectApplyQueryRequest);
    }

    @PostMapping("/apply")
    @ApiOperation(value = "退供申请单增加")
    public HttpResponse<List<RejectApplyRequest>> rejectApply(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectApply(rejectApplyQueryRequest);
    }

    @PutMapping("/apply/{reject_apply_record_code}")
    @ApiOperation(value = "退供申请单修改")
    @ApiImplicitParam(name = "reject_apply_record_code", value = "退货申请单号", type = "String")
    public HttpResponse<RejectApplyRequest> updateRejectApply(@PathVariable String reject_apply_record_code,@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        rejectApplyQueryRequest.setRejectApplyRecordCode(reject_apply_record_code);
        return goodsRejectService.updateRejectApply(rejectApplyQueryRequest);
    }

    @GetMapping("/apply/{reject_apply_record_code}")
    @ApiOperation(value = "退供申请单查询")
    public HttpResponse<RejectApplyResponse> selectRejectApply(@PathVariable String reject_apply_record_code) {
        return goodsRejectService.selectRejectApply(reject_apply_record_code);
    }

    @PostMapping("/apply/import")
    @ApiOperation(value = "批量导入退供申请单")
    public HttpResponse rejectApplyImport(MultipartFile file, @RequestParam(name = "purchase_group_code") String purchaseGroupCode) {
        return goodsRejectService.rejectApplyImport(file, purchaseGroupCode);
    }

    @PostMapping("/apply/info")
    @ApiOperation(value = "查询退供申请单信息去生成退供单")
    public HttpResponse<List<RejectApplyResponse>> rejectApplyInfo(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectApplyInfo(rejectApplyQueryRequest);
    }

    @PostMapping("/record")
    @ApiOperation(value = "新增退供单记录")
    public HttpResponse addReject(@RequestBody RejectRequest request) {
        return goodsRejectService.addReject(request);
    }

    @PutMapping("/record")
    @ApiOperation(value = "修改退供单记录")
    public HttpResponse<List<RejectApplyRequest>> updateReject(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.updateReject(rejectApplyQueryRequest);
    }

    @GetMapping("/record/list")
    @ApiOperation(value = "查询退供单列表")
    public HttpResponse<RejectRecord> rejectList(@RequestBody RejectQueryRequest rejectQueryRequest) {
        return goodsRejectService.rejectList(rejectQueryRequest);
    }

    @GetMapping("/record/{reject_record_id}")
    @ApiOperation(value = "查询退供单详情")
    @ApiImplicitParam(name = "reject_record_id", value = "退货单id", type = "String")
    public HttpResponse<RejectResponse> rejectInfo(@PathVariable String reject_record_id ) {
        return goodsRejectService.rejectInfo(reject_record_id);
    }


    @PutMapping("/record/supplier/{reject_record_id}")
    @ApiOperation(value = "供应商确认")
    @ApiImplicitParam(name = "reject_record_id", value = "退货单id", type = "String")
    public HttpResponse rejectSupplier(@PathVariable String reject_record_id) {
        return goodsRejectService.rejectSupplier(reject_record_id);
    }

    @PutMapping("/record/transport/{reject_record_id}")
    @ApiOperation(value = "退供发运")
    @ApiImplicitParam(name = "reject_record_id", value = "退货单id", type = "String")
    public HttpResponse rejectTransport(@PathVariable String reject_record_id,@RequestBody RejectRecord rejectRecord) {
        rejectRecord.setRejectRecordId(reject_record_id);
        return goodsRejectService.rejectTransport(rejectRecord);
    }

    @PutMapping("/record/transport/finish/{reject_record_id}")
    @ApiOperation(value = "退供完成")
    @ApiImplicitParam(name = "reject_record_id", value = "退货单id", type = "String")
    public HttpResponse rejectTransportFinish(@PathVariable String reject_record_id) {
        return goodsRejectService.rejectTransportFinish(reject_record_id);
    }


}
