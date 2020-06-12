package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("验货查询对应sku的销售批次信息")
    @GetMapping("/order/batch")
    public HttpResponse<List<OrderInfoItemProductBatch>> orderBatch(@RequestParam("order_code") String orderCode,
                                                              @RequestParam(value = "sku_code") String skuCode,
                                                              @RequestParam(value = "line_code") Integer lineCode){
        return returnGoodsService.orderBatch(orderCode, skuCode, lineCode);
    }

}
