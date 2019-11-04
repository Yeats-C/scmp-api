package com.aiqin.bms.scmp.api.product.web.product.apply;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.CancelReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.service.ProductApplyService;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-14
 * @time: 16:10
 */
@RestController
@RequestMapping("/product/apply")
@Api(description = "商品申请")
@Slf4j
public class ProductApplyController {

    @Autowired
    private ProductApplyService productApplyService;

    @PostMapping("/list")
    @ApiOperation("商品申请列表")
    public HttpResponse<BasePage<QueryProductApplyRespVO>> queryApplyList(@RequestBody QueryProductApplyReqVO reqVo){
        log.info("ProductApplyController---queryApplyList---入参：[{}]", JSON.toJSONString(reqVo));
        try {
            return HttpResponse.success(productApplyService.queryApplyList(reqVo));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @GetMapping("/info")
    @ApiOperation("商品申请详情")
    public HttpResponse<ProductApplyInfoRespVO<T>> view(@RequestParam String code,
                                                         @RequestParam Integer approvalType){
        log.info("ProductApplyController---queryApplyList---类型:[{}],编码:[{}] ", code,approvalType);
        try {
            return HttpResponse.success(productApplyService.view(code,approvalType));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/cancel")
    @ApiOperation("商品申请撤销")
    public HttpResponse<Integer> cancel(@RequestBody CancelReqVO reqVO ){
        log.info("ProductApplyController---queryApplyList---类型:[{}],编码:[{}] ", reqVO.getCode(),reqVO.getApprovalType());
        try {
            return HttpResponse.success(productApplyService.cancel(reqVO));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/getInfoByFormNo")
    @ApiOperation("根据formNo获取情接口请求 审批类型 1:商品 2.仓库 3.区域 4供应商")
    public HttpResponse<DetailRequestRespVo> getInfoByFormNo(@RequestParam String formNo,
                                                             @RequestParam Integer approvalType){
        log.info("ProductApplyController---getInfoByFormNo---类型:[{}],编码:[{}] ", approvalType,formNo);
        return HttpResponse.successGenerics(productApplyService.getRequsetParam(formNo,approvalType));
    }
}
