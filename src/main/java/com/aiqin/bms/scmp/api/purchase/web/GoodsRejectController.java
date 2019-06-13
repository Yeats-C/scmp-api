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
@SuppressWarnings("checkout")
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
    public HttpResponse<List<RejectApplyRequest>> rejectApplys(@RequestBody RejectApplyRequest rejectApplyQueryRequest) {
        return goodsRejectService.rejectApply(rejectApplyQueryRequest);
    }



}
