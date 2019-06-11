package com.aiqin.bms.scmp.api.supplier.web;

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
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/scmp/score/list", JSON.toJSON(reqVo));
        try {
            return HttpResponse.success(scoreService.list(reqVo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save")
    @ApiOperation("保存")
    public HttpResponse<Integer> save(@RequestBody SaveScoreReqVo reqVo){
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/scmp/score/save", JSON.toJSON(reqVo));
        try {
            return HttpResponse.success(scoreService.save(reqVo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/view")
    @ApiOperation("详情")
    public HttpResponse<DetailScoreRespVo> view(@RequestParam @NotNull(message = "主键ID不能为空") Long id) {
        log.info("供应商-评分管理 request uri:{},参数信息:{}","/scmp/score/view",id);
        try {
            return HttpResponse.success(scoreService.detail(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
