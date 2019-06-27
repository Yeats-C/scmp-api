package com.aiqin.bms.scmp.api.product.web.product;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.request.draft.DetailReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.draft.SaveReqVo;
import com.aiqin.bms.scmp.api.product.service.DraftService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author knight.xie
 * @version 1.0
 * @className DraftController
 * @date 2019/5/14 11:22
 * @description TODO
 */
@RestController
@Api(description = "商品申请单接口")
@RequestMapping("/product/draft")
@Slf4j
public class DraftController {
    @Autowired
    private DraftService draftService;

    @GetMapping("/list")
    @ApiOperation("根据审批类型获取审批单数据")
    public HttpResponse getApplyList(
            @ApiParam(value = "审批类型,1:商品,2:商品配置,3:销售区域",
                    required = true,allowableValues="1,2,3",defaultValue="1") @RequestParam Integer approvalType){
        log.info("请求获取商品申请清单数据接口,接口参数{}",approvalType);
        try {
            return draftService.list(approvalType);
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,ResultCode.SYSTEM_ERROR.getMessage());
        }
    }


    @PostMapping("/detail")
    @ApiOperation("获取详情")
    public HttpResponse detail(@RequestBody DetailReqVo reqVo){
        log.info("获取商品申请单详情接口,接口参数{}", JSON.toJSON(reqVo));
        try {
            return draftService.detail(reqVo);
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,ResultCode.SYSTEM_ERROR.getMessage());
        }
    }

    @PutMapping("/delete")
    @ApiOperation("删除")
    public HttpResponse delete(@RequestBody DetailReqVo reqVo){
        log.info("删除商品申请单详情接口,接口参数{}", JSON.toJSON(reqVo));
        try {
            return draftService.delete(reqVo);
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,ResultCode.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("/save")
    @ApiOperation("保存")
    public HttpResponse save(@RequestBody SaveReqVo reqVo){
        log.info("保存商品申请单详情接口,接口参数{}", JSON.toJSON(reqVo));
        try {
            return draftService.save(reqVo);
        }  catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,ResultCode.SYSTEM_ERROR.getMessage());
        }
    }

}
