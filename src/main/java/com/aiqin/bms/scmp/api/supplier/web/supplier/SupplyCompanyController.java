package com.aiqin.bms.scmp.api.supplier.web.supplier;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.supplier.domain.excel.im.SupplierImportNew;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailByCodeRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComListRespVO;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplyComServcie;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.bms.scmp.api.supplier.web.SupplierBaseController;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
public class SupplyCompanyController extends SupplierBaseController {
    @Autowired
    private ApplySupplyComServcie applySupplyComServcie;
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
    public HttpResponse<List<SupplyComListRespVO>> getAllSupplyComList(){
        try {
            List<SupplyComListRespVO> respVOList = supplyComService.getAllSupplyComList();
            return HttpResponse.success(respVOList);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }


    @PostMapping("/add")
    @ApiOperation("新增供应商")
    public HttpResponse addApplySupplyCompany(@RequestBody @Validated ApplySupplyCompanyReqVO applySupplyCompanyReqVO){
        try {
            return  applySupplyComServcie.saveApply(applySupplyCompanyReqVO);
        } catch (GroundRuntimeException ex) {
            return HttpResponse.failure(MessageId .create(Project.SUPPLIER_API,13,ex.getMessage()));
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.ADD_ERROR);
        }
    }

    @PutMapping("/update")
    @ApiOperation("修改供应商")
    public HttpResponse updateApplySupplyCom(@RequestBody @Validated ApplySupplyCompanyReqVO applySupplyCompanyReqVO){
        if (null == applySupplyCompanyReqVO.getApplySupplyCode() || applySupplyCompanyReqVO.getApplySupplyCode().isEmpty()){
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"供应商编码不能为空"));
        }
        try {
            int i = applySupplyComServcie.updateApply(applySupplyCompanyReqVO);
            return HttpResponse.success(i);
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
    @PostMapping("/import")
    @ApiOperation(value = "批量导入")
    public List<SupplierImportNew> importData(MultipartFile file){
        try {
            List<SupplierImportNew> supplierImportNews = ExcelUtil.readExcel(file, SupplierImportNew.class, 1, 0);
            return supplierImportNews;
        } catch (ExcelException e) {
            e.printStackTrace();
            return null;
        }
    }
}
