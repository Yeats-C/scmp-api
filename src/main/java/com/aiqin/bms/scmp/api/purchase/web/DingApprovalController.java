package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.ProductSkuChangePriceRespVO;
import com.aiqin.bms.scmp.api.product.service.ProductApplyService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChangePriceService;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseNewContrastRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseOrderProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyProductInfoResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseNewContrastResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectResponse;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.DetailApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.RequsetParamReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.service.ApplyService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.domain.FormBackRequest;
import com.aiqin.platform.flows.client.domain.FormCompleteRequest;
import com.aiqin.platform.flows.client.domain.FormRejectRequest;
import com.aiqin.platform.flows.client.domain.FormSaveInRequest;
import com.aiqin.platform.flows.client.service.FormDetailService;
import com.aiqin.platform.flows.client.service.FormOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: zhao shuai
 * @create: 2019-09-20
 **/
@Api(tags = "钉钉审批流详情页调用")
@RequestMapping("/ding")
@RestController
@Slf4j
public class DingApprovalController {

    @Resource
    private ApplyService applyService;
    @Resource
    private FormDetailService formDetailService;
    @Resource
    private FormOperateService formOperateService;
    @Resource
    private ProductApplyService productApplyService;
    @Resource
    private GoodsRejectService goodsRejectService;
    @Resource
    private PurchaseApplyService purchaseApplyService;
    @Resource
    private PurchaseManageService purchaseManageService;
    @Resource
    private ProductSkuChangePriceService productSkuChangePriceService;

    @PostMapping("/apply/detail")
    @ApiOperation("查看供应商申请详情")
    public HttpResponse detail(@RequestBody @Validated DetailApplyReqVo detailApplyReqVo){
        return applyService.detail(detailApplyReqVo);
    }

    @PostMapping("/apply/getInfoByFormNo")
    @ApiOperation("根据formNo获取情接口请求")
    public HttpResponse<DetailRequestRespVo> getInfoByFormNo(@RequestBody @Validated RequsetParamReqVo requsetParamReqVo){
        return HttpResponse.successGenerics(applyService.getRequsetParam(requsetParamReqVo));
    }

    @GetMapping("/product/apply/info")
    @ApiOperation("商品申请详情")
    public HttpResponse<ProductApplyInfoRespVO<T>> view(@RequestParam String code,
                                                        @RequestParam Integer approvalType){
        return HttpResponse.success(productApplyService.view(code,approvalType));
    }

    @GetMapping("/product/changePrice/view")
    @ApiOperation("查看")
    public HttpResponse<ProductSkuChangePriceRespVO> view(@RequestParam String code) {
        log.info("ProductSkuChangePriceController---view---入参：[{}]", code);
        try {
            return HttpResponse.success(productSkuChangePriceService.view(code));
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/record/approval/{approval_code}")
    @ApiOperation(value = "通过审批关联查询退供单详情")
    @ApiImplicitParam(name = "approval_code", value = "审批单code", type = "String")
    public HttpResponse<RejectResponse> applyRejectInfo(@PathVariable String approval_code) {
        return goodsRejectService.applyRejectInfo(approval_code);
    }

    @GetMapping("/manage/order/details/apply")
    @ApiOperation("查询采购单审批详情")
    public HttpResponse<PurchaseApplyDetailResponse> applyDetails(@RequestParam("purchase_order_code") String purchaseOrderCode) {
        return purchaseManageService.applyDetails(purchaseOrderCode);
    }

    @GetMapping("/manage/order/amount")
    @ApiOperation("查询采购单-采购数量金额/实际数量金额")
    public HttpResponse<PurchaseApplyProductInfoResponse> purchaseOrderAmount(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.purchaseOrderAmount(purchaseOrderId);
    }

    @GetMapping("/manage/order/product/apply")
    @ApiOperation("查询采购单商品审批信息")
    public HttpResponse applyOrderProduct(@RequestParam("purchase_order_id") String purchaseOrderId,
                                          @RequestParam(value = "is_page", required = false) Integer isPage,
                                          @RequestParam(value = "page_no", required = false) Integer pageNo,
                                          @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest(purchaseOrderId, isPage);
        request.setPageSize(pageSize);
        request.setPageNo(pageNo);
        return purchaseManageService.applyOrderProduct(request);
    }

    @GetMapping("/manage/order/file")
    @ApiOperation("查询采购单文件-文件信息")
    public HttpResponse purchaseOrderFile(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.purchaseOrderFile(purchaseOrderId);
    }

    @PostMapping("/apply/product/contrast/new")
    @ApiOperation("查询采购对比信息")
    public HttpResponse<PurchaseNewContrastResponse> purchaseContrast(@RequestBody PurchaseNewContrastRequest contrastRequest) {
        return purchaseApplyService.purchaseContrast(contrastRequest);
    }

    @GetMapping("/form/detail/key/{form_no}")
    @ApiOperation("根据表单编号获取流程定义key")
    HttpResponse getProcessDefinitionKeyByProcessCode(@PathVariable(value = "form_no") String formNo) {
        return formDetailService.getProcessDefinitionKeyByProcessCode(formNo);
    }

    @GetMapping("/form/detail/task")
    @ApiOperation("获取任务相关信息")
    HttpResponse goTaskOperateForm(@RequestParam("form_no") String formNo,
                                   @RequestParam("person_id") String personId) {
        return formDetailService.goTaskOperateFormScmp(formNo, personId);
    }

    @PostMapping("/log")
    @ApiOperation("查询流程日志")
    HttpResponse goTaskOpLogList(@RequestParam(value = "process_instance_id", required = false) String processInstanceId,
                                 @RequestParam(value = "form_no", required = false) String formNo,
                                 @RequestParam(value = "is_intervene", required = false) String isIntervene,
                                 @RequestParam("person_id") String personId) {
        log.info("查询流程日志,personId={},form_no={},process_instance_id={},isIntervene={}", personId, formNo, processInstanceId, isIntervene);
        if (StringUtils.isBlank(processInstanceId)) {
            if (StringUtils.isBlank(formNo)) {
                log.error("查询流程日志-没有传表单编号");
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            Object data = formDetailService.getProcessInstanceIdByProcessCode(formNo).getData();
            if (data == null) {
                log.error("查询流程日志-查询不到流程实例-id");
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
            processInstanceId = String.valueOf(data);
        }
        return formDetailService.goTaskOpLogList(processInstanceId, isIntervene, personId);
    }

    @PostMapping("/form/operate/complete")
    @ApiOperation("同意")
    HttpResponse complete(@RequestBody FormCompleteRequest request) {
        return formOperateService.complete(request);
    }

    @PostMapping("/form/operate/save/in")
    @ApiOperation("知会")
    HttpResponse saveInform(@RequestBody FormSaveInRequest request) {
        return formOperateService.saveInform(request);
    }

    @PostMapping("/reject")
    @ApiOperation("驳回")
    HttpResponse saveRejectToApply(@RequestBody FormRejectRequest request) {
        return formOperateService.saveRejectToApply(request);
    }

    @PostMapping("/form/operate/back")
    @ApiOperation("回退")
    HttpResponse saveTurnBack(@RequestBody FormBackRequest request) {
        return formOperateService.saveTurnBack(request);
    }

    @GetMapping("/form/operate/back/process")
    @ApiOperation("获得回退的审批节点")
    HttpResponse saveToCancel(@RequestParam("process_instance_id") String processInstanceId, @RequestParam("task_id") String taskId) {
        return formOperateService.getBackHisProcessOptLog(processInstanceId, taskId);
    }

    @PostMapping("/form/operate/read")
    @ApiOperation("已阅")
    HttpResponse read(@RequestBody FormCompleteRequest request) {
        return formOperateService.read(request);
    }
}
