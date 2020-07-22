package com.aiqin.bms.scmp.api.supplier.web.supplier;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.supplier.domain.excel.SupplierImportReq;
import com.aiqin.bms.scmp.api.supplier.domain.excel.SupplierImportResp;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailByCodeRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComListRespVO;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplyComService;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 15:20
 */
@RestController
@RequestMapping("/supplier/company")
@Api(description = "供应商管理")
@Slf4j
public class SupplyCompanyController {
    @Autowired
    private ApplySupplyComService applySupplyComService;
    @Autowired
    private SupplyComService supplyComService;

    @PostMapping("/list")
    @ApiOperation("查询供应商列表")
    public HttpResponse<BasePage<SupplyComListRespVO>> listApplySupplyCompany(@RequestBody @Validated QuerySupplyComReqVO querySupplyComReqVO){
        try {
            BasePage<SupplyComListRespVO> result = supplyComService.getSupplyCompanyInfoList(querySupplyComReqVO);
            return HttpResponse.success(result);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    @GetMapping("/all")
    @ApiOperation("查询所有供应商")
    public HttpResponse<List<SupplyComListRespVO>> getAllSupplyComList(@RequestParam(value = "编码", required = false) String code,
                                                                       @RequestParam(value = "名称", required = false) String name){

            try {
            List<SupplyComListRespVO> respVOList = supplyComService.getAllSupplyComList(code,name);
            return HttpResponse.success(respVOList);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }


    @PostMapping("/add")
    @ApiOperation("新增供应商")
    public HttpResponse addApplySupplyCompany(@RequestBody @Validated ApplySupplyCompanyReqVO applySupplyCompanyReqVO){
        try {
            return  applySupplyComService.saveApply(applySupplyCompanyReqVO);
        } catch (GroundRuntimeException ex) {
            return HttpResponse.failure(MessageId .create(Project.SUPPLIER_API,13,ex.getMessage()));
        } catch (BizException ex) {
            return HttpResponse.failure(ex.getMessageId());
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.ADD_ERROR);
        }
    }

//    @PostMapping("/add2")
//    @ApiOperation("新增供应商")
//    public HttpResponse addApplySupplyCompany2(@RequestBody  ApplySupplyCompanyReqVO applySupplyCompanyReqVO){
//        try {
//            return  applySupplyComService.saveApply2(applySupplyCompanyReqVO);
//        } catch (GroundRuntimeException ex) {
//            return HttpResponse.failure(MessageId .create(Project.SUPPLIER_API,13,ex.getMessage()));
//        } catch (BizException ex) {
//            return HttpResponse.failure(ex.getMessageId());
//        } catch (Exception e) {
//            return HttpResponse.failure(ResultCode.ADD_ERROR);
//        }
//    }

    @PutMapping("/update")
    @ApiOperation("修改供应商")
    public HttpResponse<Long> updateApplySupplyCom(@RequestBody @Validated ApplySupplyCompanyReqVO applySupplyCompanyReqVO){
        if (null == applySupplyCompanyReqVO.getApplySupplyCode() || applySupplyCompanyReqVO.getApplySupplyCode().isEmpty()){
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"供应商编码不能为空"));
        }
        try {
            return HttpResponse.success(applySupplyComService.updateApply(applySupplyCompanyReqVO));
        } catch (GroundRuntimeException ex) {
            return HttpResponse.failure(MessageId .create(Project.SUPPLIER_API,13,ex.getMessage()));
        } catch (BizException ex) {
            return HttpResponse.failure(ex.getMessageId());
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
    }

    @DeleteMapping("/logicdelete")
    @ApiOperation(value = "逻辑删除")
    public HttpResponse logicDelete(@RequestParam @ApiParam("供货单位主键id必传") Long id){
        try {
            int i = supplyComService.logicDelete(id);
            return HttpResponse.success(i);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.DELETE_ERROR);
        }
    }
    @GetMapping("/detail")
    @ApiOperation(value = "修改查看详情")
    public HttpResponse<SupplyComDetailRespVO> getSupplyCompanyDetail(@RequestParam @ApiParam("供货单位主键id必传") Long id){
        try {
            SupplyComDetailRespVO supplyComDetailRespVO = supplyComService.getSupplyComDetail(id, StatusTypeCode.UN_SHOW_ACCOUNT_SKU);
            return HttpResponse.success(supplyComDetailRespVO);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @GetMapping("/detailByCode")
    @ApiOperation(value = "查看详情通过编码")
    public HttpResponse<SupplyComDetailByCodeRespVO> detailByCode(@RequestParam @ApiParam("供货单位主键id必传") String supplyCode){
        try {
            SupplyComDetailByCodeRespVO supplyComDetailRespVO = supplyComService.detailByCode(supplyCode);
            return HttpResponse.success(supplyComDetailRespVO);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @GetMapping("/view")
    @ApiOperation(value = "查看详情")
    public HttpResponse<SupplyComDetailRespVO> view(@RequestParam @ApiParam("供货单位主键id必传") Long id){
        try {
            SupplyComDetailRespVO supplyComDetailRespVO = supplyComService.getSupplyComDetail(id, StatusTypeCode.SHOW_ACCOUNT_SKU);
            return HttpResponse.success(supplyComDetailRespVO);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }
    @PostMapping("/import/add")
    @ApiOperation(value = "新增批量导入")
    public HttpResponse<SupplierImportResp> importData(MultipartFile file, String purchaseGroupCode,String purchaseGroupName){
        try {
            return HttpResponse.success(applySupplyComService.dealImport(file));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/importSupplierNewSave")
    @ApiOperation(value = "新增导入保存")
    public HttpResponse<Boolean> importSupplierNewSave(@RequestBody SupplierImportReq req){
        try {
            return HttpResponse.success(applySupplyComService.importSupplierNewSave(req));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (GroundRuntimeException e) {

            return HttpResponse.failure(MessageId.create(Project.SCMP_API,999,e.getMessage()));
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    @PostMapping("/import/update")
    @ApiOperation(value = "修改批量导入")
    public HttpResponse<SupplierImportResp> importData2(MultipartFile file,String purchaseGroupCode,String purchaseGroupName){
        try {
            return HttpResponse.success(applySupplyComService.dealImport2(file));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/importSupplierNewUpdate")
    @ApiOperation(value = "修改导入保存")
    public HttpResponse<Boolean> importSupplierNewUpdate(@RequestBody SupplierImportReq req){
        try {
            return HttpResponse.success(applySupplyComService.importSupplierNewUpdate(req));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/apply/reUpdate")
    @ApiOperation(value = "审批追踪重新编辑")
    public HttpResponse reUpdateApply(@RequestParam("applyCode") String applyCode){
        try {
            return HttpResponse.success( applySupplyComService.reUpdateApply(applyCode));
        } catch (GroundRuntimeException ex) {
            return HttpResponse.failure(MessageId .create(Project.SUPPLIER_API,13,ex.getMessage()));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/import/wms")
    @ApiOperation(value = "供应商信息信息导入wms")
    public HttpResponse skuImportWms(){
        return supplyComService.supplyImportWms();
    }
}
