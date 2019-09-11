package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.supervisory.SaveSupervisoryWarehouseOrderReqVo;
import com.aiqin.bms.scmp.api.product.service.SupervisoryWarehouseOrderService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupervisoryWarehouseOrderController
 * @date 2019/6/29 15:26

 */

@RestController
@Api(tags = "监管仓开单接口")
@RequestMapping("/supervisory/warehouse")
@Slf4j
public class SupervisoryWarehouseOrderController {
    @Autowired
    private SupervisoryWarehouseOrderService supervisoryWarehouseOrderService;

    @GetMapping("/getCurrentUserName")
    @ApiOperation(value = "获取当前用户")
    public HttpResponse<String> getCurrentUserName(){
        try {
            return HttpResponse.success(supervisoryWarehouseOrderService.getUser().getPersonName());
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public HttpResponse<Integer> save(@RequestBody SaveSupervisoryWarehouseOrderReqVo reqVo){
        try {
            return HttpResponse.success(supervisoryWarehouseOrderService.save(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
