package com.aiqin.bms.scmp.api.product.web.inbound;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundCallBackRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.QueryInboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderMainReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundResVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundResponse;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.QueryInboundResVo;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "库房入库管理")
@RequestMapping("/product/inbound")
public class InboundController {

    @Autowired
    private InboundService inboundService;

    @ApiOperation("查询入库单列表详情")
    @PostMapping("/getInboundList")
    public HttpResponse<BasePage<QueryInboundResVo>> getInboundList(@RequestBody QueryInboundReqVo vo) {
        return HttpResponse.success(inboundService.getInboundList(vo));
    }

    @ApiOperation("查询入库信息")
    @PostMapping("/info/by/search")
    public HttpResponse<InboundResponse> selectInBoundInfoByBoundSearch(@RequestBody BoundRequest boundRequest) {
        return HttpResponse.success(inboundService.selectInBoundInfoByBoundSearch(boundRequest));
    }

    @ApiOperation("新建入库单")
    @PostMapping("/save")
    public HttpResponse<Integer>save(@RequestBody @Valid InboundReqSave reqVo){
       return HttpResponse.success(inboundService.saveInbound(reqVo));
    }

    @ApiOperation("通过id获取入库单")
    @GetMapping("/view")
    public HttpResponse<InboundResVo> view(@RequestParam @ApiParam(value = "主键id",required = true) Long id){
        return HttpResponse.success(inboundService.view(id));
    }

    @ApiOperation("查询入库类型")
    @GetMapping("/getInboundType")
    public HttpResponse<List<EnumReqVo>> getInboundType(){
        return HttpResponse.success(inboundService.getInboundType());
    }

    @ApiOperation("查询出入库状态")
    @GetMapping("/getInboundOutboundStatus")
    public HttpResponse<List<EnumReqVo>> getInboundOutboundStatus(){
        return HttpResponse.success(inboundService.getInboundOutboundStatus());
    }

    @ApiOperation("退货新建入库单")
    @PostMapping("/returnGoods/save")
    public HttpResponse<Integer>returnGoodSave(@RequestBody SupplyReturnOrderMainReqVO reqVo){
        return HttpResponse.success(inboundService.saveReturnGoodsToInbound(reqVo));
    }

    @ApiOperation("入库单回调接口")
    @PostMapping("/workFlowCallBack")
    public HttpResponse<Integer> workFlowCallBack(@RequestBody InboundCallBackRequest request) {
        inboundService.workFlowCallBack(request);
        return HttpResponse.success();
    }

//    @ApiOperation("根据入库单号查询入库商品批次详情")
//    @GetMapping("/getInfoByOderCode")
//    public HttpResponse<InboundBatch> selectInboundBatchInfoByInboundOderCode(@RequestParam(value = "inbound_oder_code")String inboundOderCode,
//                                                                              @RequestParam(value = "page_size", required = false)Integer pageSize,
//                                                                              @RequestParam(value = "page_no", required = false)Integer pageNo){
//        return inboundService.selectInboundBatchInfoByInboundOderCode(new InboundBatch(inboundOderCode, pageSize, pageNo));
//        return HttpResponse.success();
//    }

//    @ApiOperation("测试wms")
//    @GetMapping("/test")
//    public HttpResponse<InboundBatch> inboundOderCode(@RequestParam(value = "inbound_oder_code")String inboundOderCode){
//        inboundService.pushWms(inboundOderCode);
//        return HttpResponse.success();
//    }
//
//    @ApiOperation("测试wms调拨入库回传")
//    @GetMapping("/test/allocation")
//    public HttpResponse allocationTest(@RequestParam(value = "allocation_code")String allocationCode){
//        inboundService.inBoundReturn(allocationCode);
//        return HttpResponse.success();
//    }
//
//    @ApiOperation("入库回调根据类型回传给来源单号状态测试")
//    @PostMapping("/returnSource/test")
//    public HttpResponse returnSource() {
//        inboundService.returnSource(3304L);
//        return HttpResponse.success();
//    }
}
