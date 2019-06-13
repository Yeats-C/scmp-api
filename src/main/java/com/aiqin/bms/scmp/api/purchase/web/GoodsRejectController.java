package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
@Api("退供controller")
@RequestMapping("/reject")
@RestController
@SuppressWarnings("unchecked")
public class GoodsRejectController {

    @Resource
    private GoodsRejectService goodsRejectService;

    @GetMapping("/apply/list")
    @ApiOperation(value = "退供单列表")
    public HttpResponse<List<RejectApplyQueryRequest>> rejectApplyList(@RequestBody RejectApplyQueryRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectApplyList(rejectApplyQueryRequest);
    }

    @PostMapping("/apply")
    @ApiOperation(value = "退供单增加/修改")
    public HttpResponse<List<RejectApplyRequest>> rejectApply(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectApply(rejectApplyQueryRequest);
    }

    @PostMapping("/apply/import")
    @ApiOperation(value = "批量导入退供申请单")
    public HttpResponse<List<RejectApplyRequest>> rejectApplyImport(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectApplyImport(rejectApplyQueryRequest);
    }

    @GetMapping("/apply/info")
    @ApiOperation(value = "查询退供申请单信息去生成退供单")
    public HttpResponse<List<RejectApplyRequest>> rejectApplyInfo(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectApplyInfo(rejectApplyQueryRequest);
    }

    @PostMapping("/record")
    @ApiOperation(value = "新增退供单记录")
    public HttpResponse<List<RejectApplyRequest>> addReject(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.addReject(rejectApplyQueryRequest);
    }

    @PutMapping("/record")
    @ApiOperation(value = "修改退供单记录")
    public HttpResponse<List<RejectApplyRequest>> updateReject(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.updateReject(rejectApplyQueryRequest);
    }

    @GetMapping("/record/list")
    @ApiOperation(value = "查询退供单列表")
    public HttpResponse<List<RejectApplyRequest>> rejectList(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectList(rejectApplyQueryRequest);
    }

    @GetMapping("/record/info")
    @ApiOperation(value = "查询退供单详情")
    public HttpResponse<List<RejectApplyRequest>> rejectInfo(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectInfo(rejectApplyQueryRequest);
    }


    @PutMapping("/record/supplier")
    @ApiOperation(value = "供应商确认")
    public HttpResponse<List<RejectApplyRequest>> rejectSupplier(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectSupplier(rejectApplyQueryRequest);
    }

}
