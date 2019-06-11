package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.supplier.domain.request.rule.SaveReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.rule.DetailRespVo;
import com.aiqin.bms.scmp.api.supplier.service.SupplierRuleService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupplierRuleController
 * @date 2019/5/23 14:27
 * @description TODO
 */
@RestController
@Api(description = "基础资料-规则管理")
@RequestMapping("/basic/rule")
@Slf4j
public class SupplierRuleController {
    @Autowired
    private SupplierRuleService ruleService;

    @PostMapping("/save")
    @ApiOperation(value = "保存规则管理")
    public HttpResponse<Integer> save(@RequestBody SaveReqVo reqVo){
        try {
            log.info("保存规则管理 request uri:{},参数信息:{}","/basic/rule/save", JSON.toJSON(reqVo));
            return HttpResponse.success(ruleService.save(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @GetMapping("/get")
    @ApiOperation(value = "获取规则管理")
    public HttpResponse<DetailRespVo> get(){
        try {
            log.info("获取规则管理 request uri:{}","/basic/rule/get");
            return HttpResponse.success(ruleService.findRule());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
