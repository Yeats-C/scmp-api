package com.aiqin.bms.scmp.api.supplier.web.applycontract;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.supplier.domain.excel.ContractImportResp;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.QueryApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.UpdateApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.ApplyContractUpdateResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.ApplyContractViewResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.QueryApplyContractResVo;
import com.aiqin.bms.scmp.api.supplier.service.ApplyContractService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @description:   申请合同管理控制器
 * @author:曾兴旺
 * @date: 2018/11/30
 */
@RestController
@Api(description = "合同申请管理")
@RequestMapping("/applyContract")
public class ApplyContractController {

    @Autowired
    private ApplyContractService applyContractService;

    /**
     * 分页获取申请合同详情
     * @return
     */
    @ApiOperation("待审请列表")
    @PostMapping("/getApplyContractList")
    public HttpResponse<BasePage<QueryApplyContractResVo>> getApplyContractList(@RequestBody @Valid QueryApplyContractReqVo vo) {
        return HttpResponse.success(applyContractService.findApplyContractList(vo));
    }

    /**
     * 新增申请合同
     * @param applyContractReqVo
     * @return
     */
    @ApiOperation("新增合同申请")
    @PostMapping("/saveApplyContract")
    public HttpResponse<Integer> saveApplyContract(@RequestBody @Valid ApplyContractReqVo applyContractReqVo){
        try {
            int kp = applyContractService.saveApplyContract(applyContractReqVo);
            return HttpResponse.success(kp);
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.ADD_ERROR);
        }
    }

    /**
     * 查看申请合同详情
     * @param id
     * @return
     */
    @ApiOperation("查看合同申请详情")
    @GetMapping("/getApplyContract")
    public HttpResponse<ApplyContractViewResVo> findApplyContractDetail(@RequestParam("id")  @ApiParam(value = "传入主键id",required = true) Long id) {
        try {
            return HttpResponse.success(applyContractService.findApplyContractDetail(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (GroundRuntimeException e) {
            return HttpResponse.failure(MessageId.create(Project.SCMP_API,999,e.getMessage()));
        }catch (Exception e) {
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 通过合同管理申请合同编码，去查找要修改申请合同的详情
     * @param applyContractCode
     * @return
     */
    @ApiOperation("获取修改合同工详情")
    @PutMapping("/update/getUpdateContract")
    public HttpResponse<Integer> findUpdateAContractDetail(@RequestParam("applyContractCode") @ApiParam(value = "申请合同编码" ,required = true)String applyContractCode){
        try {
            ApplyContractUpdateResVo detail = applyContractService.findUpdateContractDetail(applyContractCode);
            return HttpResponse.success(detail);
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }


    /**
     * 修改申请合同
     * @param updateApplyContractReqVo
     * @return
     */
    @ApiOperation("修改申请合同")
    @PutMapping("/updateApplyContract")
    public HttpResponse<Integer> updateApplyContract(@RequestBody @Valid UpdateApplyContractReqVo updateApplyContractReqVo){
        try{
            int k =applyContractService.updateApplyContract(updateApplyContractReqVo);
            return HttpResponse.success(k);
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, ex.getMessage()));
        }
    }

    /**
     * 待申请修改合同保存
     * @param updateApplyContractReqVo
     * @return
     */
    @ApiOperation("待申请修改合同保存")
    @PutMapping("/saveUpdateApply")
    public HttpResponse<Boolean> saveUpdateApply(@RequestBody @Valid UpdateApplyContractReqVo updateApplyContractReqVo){
        try{
            return HttpResponse.success(applyContractService.saveUpdateApply(updateApplyContractReqVo));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, ex.getMessage()));
        }
    }

    /**
     * 撤销申请合同
     * @param id
     * @return
     */
    @ApiOperation("撤销")
    @PutMapping("/updateStatusApplyContract")
    public HttpResponse<Integer> updateStatusApplyContract(@RequestParam  @ApiParam(value = " 传入主键id" ,required = true) Long id){
        try {
            return HttpResponse.success(applyContractService.updateStatusApplyContract(id));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.MARKET_API, -1, ex.getMessage()));
        }
    }

    @PostMapping("/import/add")
    @ApiOperation(value = "新增批量导入")
    public HttpResponse<ContractImportResp> importData(MultipartFile file, String purchaseGroupCode, String purchaseGroupName){
        try {
            return HttpResponse.success(applyContractService.dealImport(file));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/import/save")
    @ApiOperation(value = "新增导入保存")
    public HttpResponse<Boolean> saveImportData(@RequestBody ContractImportResp req){
        try {
            applyContractService.saveImportData(req);
            return HttpResponse.success(true);
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (GroundRuntimeException e) {
            return HttpResponse.failure(MessageId.create(Project.SCMP_API,999,e.getMessage()));
        }catch (Exception e) {
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
