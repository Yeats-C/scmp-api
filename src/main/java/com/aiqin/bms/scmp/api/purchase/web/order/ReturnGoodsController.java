package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnDLReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.ReturnReceiptReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
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
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:24
 */
@RestController
@Slf4j
@Api(tags = "退货单API")
@RequestMapping("/returnGoods")
public class ReturnGoodsController {

    @Autowired
    private ReturnGoodsService returnGoodsService;

    @ApiOperation("运营中台推送退货单")
    @PostMapping("/record/return")
    public HttpResponse<String> record(@RequestBody ReturnReq reqVO){
        log.info("运营中台推送退货单参数：[{}]", JsonUtil.toJson(reqVO));
        return returnGoodsService.record(reqVO);
    }

    @ApiOperation("退货列表")
    @PostMapping("/list")
    public HttpResponse<PageResData<ReturnOrderInfo>> returnInspection(@RequestBody ReturnGoodsRequest request){
        log.info("调用退货单列表参数：{}", JsonUtil.toJson(request));
        return returnGoodsService.returnOrderList(request);
    }

    @ApiOperation("退货商品列表")
    @GetMapping("/product/list")
    public HttpResponse<PageResData<ReturnOrderInfoItem>> returnOrderProductList(
            @RequestParam("return_order_code") String returnOrderCode,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        ReturnGoodsRequest request = new ReturnGoodsRequest();
        request.setReturnOrderCode(returnOrderCode);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return returnGoodsService.returnOrderProductList(request);
    }

    @ApiOperation("退货商品列表")
    @GetMapping("/batch/list")
    public HttpResponse<PageResData<ReturnOrderInfoInspectionItem>> returnOrderBatchList(
            @RequestParam("return_order_code") String returnOrderCode,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        ReturnGoodsRequest request = new ReturnGoodsRequest();
        request.setReturnOrderCode(returnOrderCode);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return returnGoodsService.returnOrderBatchList(request);
    }

    @ApiOperation("退货详情(退货信息、客户信息及地址、退货数量及金额)")
    @GetMapping("/detail")
    public HttpResponse<ReturnOrderDetailResponse> returnOrderDetail(@RequestParam("return_order_code") String returnOrderCode) {
        return returnGoodsService.returnOrderDetail(returnOrderCode);
    }

    @ApiOperation("入库单商品批次信息")
    @GetMapping("/inbound/batch")
    public HttpResponse inboundBatch(@RequestParam("inbound_oder_code") String inboundOderCode,
                                     @RequestParam(value = "page_no", required = false) Integer pageNo,
                                     @RequestParam(value = "page_size", required = false) Integer pageSize){
        InboundBatchReqVo request = new InboundBatchReqVo(inboundOderCode, pageSize, pageNo);
        return returnGoodsService.inboundBatch(request);
    }

    @ApiOperation("退货异常终止")
    @GetMapping("/cancel")
    public HttpResponse returnOrderCancel(@RequestParam("return_order_code") String returnOrderCode){
        log.info("退货异常终止参数：{}", returnOrderCode);
        return returnGoodsService.returnOrderCancel(returnOrderCode);
    }

    @ApiOperation("验货保存")
    @PostMapping("/inspection/save")
    public HttpResponse<Boolean> saveReturnInspection(@RequestBody ReturnInspectionRequest request){
        log.info("退货验货提交参数：[{}]", JsonUtil.toJson(request));
        return returnGoodsService.saveReturnInspection(request);
    }

    @ApiOperation("退货收货")
    @PostMapping("/receipt")
    public HttpResponse<Boolean> returnReceipt(@RequestBody List<ReturnOrderInfoItem> itemList) {
        return returnGoodsService.returnReceipt(itemList);
    }

//    @ApiOperation("退货单同步")
//    @PostMapping("/save")
//    public HttpResponse<Boolean> saveReturnOrder(@RequestBody @Valid List<ReturnOrderInfoReqVO> reqVO){
//        log.info("ReturnGoodsController---saveReturnOrder---param：[{}]", JSONObject.toJSONString(reqVO));
//        try {
//            return HttpResponse.success(returnGoodsService.save(reqVO));
//        } catch (BizException e){
//            return HttpResponse.failure(e.getMessageId());
//        }catch (Exception e) {
//            log.error(e.getMessage(),e);
//            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
//        }
//    }
//
//    @ApiOperation("DL退货单调用接口")
//    @PostMapping("recordDL/return")
//    public HttpResponse recordDL(@RequestBody ReturnDLReq reqVO){
//        log.info("ReturnGoodsController---saveReturnOrder---param：[{}]", JSONObject.toJSONString(reqVO));
//        try {
//            return HttpResponse.success(returnGoodsService.recordDL(reqVO));
//        } catch (BizException e){
//            return HttpResponse.failure(MessageId.create(Project.SCMP_API, -1, e.getMessage()));
//        }catch (Exception e) {
//            log.error(e.getMessage(),e);
//            return HttpResponse.failure(MessageId.create(Project.SCMP_API, -1, e.getMessage()));
//        }
//    }

}
