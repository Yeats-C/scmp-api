package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.ProductSkuChangePriceRespVO;
import com.aiqin.bms.scmp.api.product.service.ProductApplyService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChangePriceService;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyTransportCenter;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseNewContrastRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseOrderProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.domain.request.DownPicReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.DetailApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.RequsetParamReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.service.ApplyService;
import com.aiqin.bms.scmp.api.supplier.service.FileInfoService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.domain.*;
import com.aiqin.platform.flows.client.domain.vo.FileVo;
import com.aiqin.platform.flows.client.service.FormDetailService;
import com.aiqin.platform.flows.client.service.FormFileService;
import com.aiqin.platform.flows.client.service.FormMsgService;
import com.aiqin.platform.flows.client.service.FormOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
    private FileInfoService fileInfoService;
    @Resource
    private FormDetailService formDetailService;
    @Resource
    private FormMsgService formMsgService;
    @Resource
    private FormFileService formFileService;
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

    @GetMapping("/purchase/details/apply")
    @ApiOperation("查询采购申请单-采购通用信息")
    public HttpResponse<PurchaseApplyCurrencyResponse> purchaseCurrency(@RequestParam("purchase_apply_code") String purchaseApplyCode) {
        return purchaseApplyService.purchaseCurrency(purchaseApplyCode);
    }

    @GetMapping("/purchase/product/apply")
    @ApiOperation("查询申请采购单，商品详情列表")
    public HttpResponse<List<PurchaseApplyDetailResponse>> searchApplyProduct(@RequestParam("product_apply_code") String purchaseApplyCode,
                                                                              @RequestParam(value = "transport_center_code",required = false) String transportCenterCode) {
        return purchaseApplyService.searchApplyProduct(purchaseApplyCode, transportCenterCode);
    }

    @GetMapping("/transport/center/info")
    @ApiOperation("查询申请采购单，分仓采购信息")
    public HttpResponse<List<PurchaseApplyTransportCenter>> transportCenterPurchase(
            @RequestParam("purchase_apply_code") String purchaseApplyCode,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode) {
        return purchaseApplyService.transportCenterPurchase(purchaseApplyCode, transportCenterCode);
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

    @PostMapping("/form/operate/cancel")
    @ApiOperation("撤销")
    HttpResponse saveToCancel(@RequestBody FormCancelRequest request) {
        return formOperateService.saveToCancel(request);
    }

    @PostMapping("/form/msg/detail")
    @ApiOperation("查询历史消息")
    HttpResponse findMsgProcessList(@RequestParam("person_id") String personId, @RequestParam(value = "form_no", required = false) String formNo, @RequestParam(value = "process_instance_id", required = false) String processInstanceId, @RequestParam(value = "is_history", required = false) String isHistory) {
        log.info("查询历史消息,personId={},form_no={},process_instance_id={},isHistory={}", personId, formNo, processInstanceId, isHistory);
        if (StringUtils.isBlank(processInstanceId)) {
            if (StringUtils.isBlank(formNo)) {
                log.error("查询历史消息-没有传表单编号");
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            Object data = formDetailService.getProcessInstanceIdByProcessCode(formNo).getData();
            if (data == null) {
                log.error("查询历史消息-查询不到流程实例-id");
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
            processInstanceId = String.valueOf(data);
        }
        return formMsgService.findMsgProcessList(processInstanceId, personId, isHistory);
    }

    @PostMapping("/form/msg")
    @ApiOperation("保存回复/追加的消息")
    HttpResponse saveMsgProcess(@RequestBody SaveMsgRequest request) {
        return formMsgService.saveMsgProcess(request);
    }

    @PostMapping("/form/file/activiti")
    @ApiOperation("历史消息中上传附件")
    HttpResponse saveFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            List<FileVo> fileList = new ArrayList<>();
            if (files != null && files.length > 0) {
                FileVo fileVo;
                for (MultipartFile file : files) {
                    String encode = Base64.getEncoder().encodeToString(file.getBytes());
                    String fileName = file.getOriginalFilename();
                    String fileType = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
                    fileVo = new FileVo(fileType, fileName, encode, "msg", file.getSize());
                    fileList.add(fileVo);
                }
            }
            log.info("历史消息上传附件,fileList={} ", fileList);
            return formFileService.saveFiles(fileList);
        } catch (Exception e) {
            log.info("历史消息上传附件异常,{}", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }

    }

    @DeleteMapping("/form/file/activiti")
    @ApiOperation("历史消息中删除附件")
    HttpResponse saveFiles(@RequestParam() String fileKey) {
        return formFileService.deleteFile(fileKey);
    }

    @GetMapping("/product/changePrice/getCodeByFormNo")
    @ApiOperation("根据FormNo获取申请编码")
    public HttpResponse<String> getApplyCodeByFormNo(@RequestParam String formNo) {
        log.info("ProductSkuChangePriceController---getCodeByFormNo---入参：[{}]", formNo);
        try {
            return HttpResponse.success(productSkuChangePriceService.getApplyCodeByFormNo(formNo));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/product/apply/getInfoByFormNo")
    @ApiOperation("根据formNo获取情接口请求 审批类型 1:商品 2.仓库 3.区域 4 供应商")
    public HttpResponse<DetailRequestRespVo> getInfoByFormNo(@RequestParam String formNo,
                                                             @RequestParam Integer approvalType){
        log.info("ProductApplyController---getInfoByFormNo---类型:[{}],编码:[{}] ", approvalType,formNo);
        return HttpResponse.successGenerics(productApplyService.getRequsetParam(formNo,approvalType));
    }

    @PostMapping("/file/down/pic")
    @ApiModelProperty(value = "图片下载")
    public HttpResponse<String> downFile(@RequestBody DownPicReqVo reqVo){
        try {
            return HttpResponse.success(fileInfoService.down(reqVo.getFilePath()));
        } catch (BizException ex) {
            return HttpResponse.failure(ex.getMessageId());
        }catch (Exception e) {
            return HttpResponse.failure(ResultCode.FILE_DOWN_ERROR);
        }
    }
}
