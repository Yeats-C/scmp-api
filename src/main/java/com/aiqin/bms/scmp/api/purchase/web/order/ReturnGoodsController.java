package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnOrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.QueryReturnOrderManagementRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderDetailRespVO;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:24
 */
@RestController
@Slf4j
@Api("退货相关")
@RequestMapping("/returnGoods")
public class ReturnGoodsController {
    @Autowired
    private ReturnGoodsService returnGoodsService;

    @ApiOperation("退货单同步")
    @PostMapping("/save")
    public HttpResponse<Boolean> saveReturnOrder(@RequestBody @Valid List<ReturnOrderInfoReqVO> reqVO){
        log.info("ReturnGoodsController---saveReturnOrder---param：[{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(returnGoodsService.save(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @ApiOperation("退货单管理")
    @PostMapping("/returnOrderManagement")
    public HttpResponse<BasePage<QueryReturnOrderManagementRespVO>> returnOrderManagement(@RequestBody QueryReturnOrderManagementReqVO reqVO){
        log.info("ReturnGoodsController---returnOrderManagement---param：[{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(returnGoodsService.returnOrderManagement(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @ApiOperation("退货详情")
    @PostMapping("/returnOrderDetail")
    public HttpResponse<ReturnOrderDetailRespVO> returnOrderDetail(@RequestParam String code){
        log.info("ReturnGoodsController---returnOrderDetail---param：[{}]", code);
        try {
            return HttpResponse.success(returnGoodsService.returnOrderDetail(code));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
