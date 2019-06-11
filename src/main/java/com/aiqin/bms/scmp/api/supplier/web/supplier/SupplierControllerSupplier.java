package com.aiqin.bms.scmp.api.supplier.web.supplier;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.DownSupplierFileReq;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierUpdateReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplierDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplierListRespVO;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplierService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierService;
import com.aiqin.bms.scmp.api.supplier.web.SupplierBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 11:02
 */
@RestController
@RequestMapping("/supplier")
@Api(description = "供应商集团管理")
public class SupplierControllerSupplier extends SupplierBaseController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ApplySupplierService applySupplierService;

    @PostMapping("/list")
    @ApiOperation("查询供应商集团列表")
    public HttpResponse<BasePage<SupplierListRespVO>> listSupplier(@RequestBody @Validated  QuerySupplierReqVO querySupplierReqVO){
        BasePage<SupplierListRespVO> result = supplierService.getSupplierList(querySupplierReqVO);
        return HttpResponse.success(result);
    }

    @PostMapping("/add")
    @ApiOperation("新增供应商集团")
    public HttpResponse<Integer> addApplySupplier(@RequestBody @Validated ApplySupplierReqVO supplierReq){
            return HttpResponse.success(applySupplierService.saveApply(supplierReq));
    }

    @PutMapping("/update")
    @ApiOperation("修改供应商集团")
    public HttpResponse<Integer> updateSupplierInfo(@RequestBody @Validated SupplierUpdateReqVO supplierUpdateReqVO){
        try {
            return HttpResponse.success(applySupplierService.updateApply(supplierUpdateReqVO));
        } catch (Exception e) {
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查看详情")
    public HttpResponse<SupplierDetailRespVO> getSupplierDetail(@RequestParam @ApiParam("供应商主键id，必传") Long id){
        try {
            SupplierDetailRespVO detailRespVO= supplierService.getSupplierDetail(id);
            return HttpResponse.success(detailRespVO);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @PostMapping("/down/log")
    @ApiOperation("添加下载记录")
    public HttpResponse<Integer> addSupplierFileDownLog(@RequestBody DownSupplierFileReq downSupplierFileReq){
        return HttpResponse.success();
    }

}
