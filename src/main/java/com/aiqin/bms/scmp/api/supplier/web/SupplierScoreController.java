package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.bms.scmp.api.supplier.domain.request.score.SavePurchaseScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SaveRejectScoreReqVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.QueryScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SaveScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.score.DetailScoreRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.score.ScoreListRespVo;
import com.aiqin.bms.scmp.api.supplier.service.SupplierScoreService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupplierScoreController
 * @date 2019/5/23 16:40
 * @description TODO
 */
@RestController
@Api(description = "供应商-评分管理")
@RequestMapping("/supplier/score")
@Slf4j
public class SupplierScoreController {
    @Autowired
    private SupplierScoreService scoreService;

    @PostMapping("/list")
    @ApiOperation("列表查询")
    public HttpResponse<BasePage<ScoreListRespVo>> list(@RequestBody QueryScoreReqVo reqVo){
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/supplier/score/list", JSON.toJSON(reqVo));
        try {
            return HttpResponse.success(scoreService.list(reqVo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save")
    @ApiOperation("保存")
    public HttpResponse<String> save(@RequestBody SaveScoreReqVo reqVo){
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/supplier/score/save", JSON.toJSON(reqVo));
        try {
            return HttpResponse.success(scoreService.save(reqVo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save/reject")
    @ApiOperation("退供评分保存")
    public HttpResponse<String> saveByReject(@RequestBody SaveRejectScoreReqVo reqVo){
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/supplier/score/save/reject", JSON.toJSON(reqVo));
        try {
            return HttpResponse.success(scoreService.saveByReject(reqVo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save/purchase")
    @ApiOperation("采购评分保存")
    public HttpResponse<String> saveByPurchase(@RequestBody SavePurchaseScoreReqVo reqVo){
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/supplier/score/save/purchase", JSON.toJSON(reqVo));
        try {
            return HttpResponse.success(scoreService.saveByPurchase(reqVo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/view")
    @ApiOperation("详情")
    public HttpResponse<DetailScoreRespVo> view(@RequestParam @NotNull(message = "主键ID不能为空") Long id) {
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/supplier/score/view",id);
        try {
            return HttpResponse.success(scoreService.detail(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @GetMapping("/view/code")
    @ApiOperation("根据code查看详情")
    public HttpResponse<DetailScoreRespVo> getViewByCode(@RequestParam @NotNull(message = "编码不能为空") String code) {
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/supplier/score/view/code",code);
        try {
            return HttpResponse.success(scoreService.detailByCode(code));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
