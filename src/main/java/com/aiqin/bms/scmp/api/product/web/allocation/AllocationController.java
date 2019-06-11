package com.aiqin.bms.scmp.api.product.web.allocation;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.QueryAllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.QueryAllocationResVo;
import com.aiqin.bms.scmp.api.product.service.AllocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:调拨管理
 *
 * @Author: Kt.w
 * @Date: 2019/1/9
 * @Version 1.0
 * @since 1.0
 */
@RestController
@Api(description = "调拨管理")
@RequestMapping("/product/allocation")
public class AllocationController {



    @Autowired
    private AllocationService allocationService;

    /**
     * 调拨单列表详情
     * @return
     */
    @ApiOperation("调拨单列表详情")
    @PostMapping("/list")
    public HttpResponse<BasePage<QueryAllocationResVo>> getList(@RequestBody QueryAllocationReqVo vo) {
        return HttpResponse.success(allocationService.getList(vo));
    }


    /**
     * 新增调拨单
     * @param vo
     * @return
     */
    @ApiOperation("新增调拨单")
    @PostMapping("/save")
    public HttpResponse<Long> save(@RequestBody  AllocationReqVo vo){
        try {
            return HttpResponse.success(allocationService.save(vo));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.ALLOCATION_RETURN_ADD_ERROR);
        }
    }


    /**
     * 撤销调拨单
     * @param id
     * @return
     */
    @ApiOperation("撤销调拨单" )
    @GetMapping ("/revocation")
    public HttpResponse<Integer> revocation(@RequestParam @ApiParam(value = " 传入主键id" ,required = true)Long id ){
        try {
            return HttpResponse.success(allocationService.revocation(id));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.ALLOCATION_RETURN_REVOCATION_ERROR);
        }
    }

    /**
     * 通过id返回调拨单详情
     * @param id
     * @return
     */
    @ApiOperation("通过id返回调拨单详情")
    @GetMapping("/view")
    public HttpResponse<AllocationResVo> view(@RequestParam @ApiParam(value = "传入id",required = true)Long id) {
        try {
            return HttpResponse.success(allocationService. view(id));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.ALLOCATION_RETURN_REVOCATION_ERROR);
        }
    }


    @ApiOperation("调拨状态")
    @GetMapping("/getAllocationStatus")
    public HttpResponse<List<EnumReqVo>> getAllocationStatus(){
        return HttpResponse.success(allocationService.getAllocationStatus());
    }
    
    @ApiOperation("审批结果id")
    @GetMapping("/updateStatus")
    public HttpResponse<Integer> updateSubmit( @RequestParam @ApiParam(value = "状态编码",required = true) Byte status ,
                                               @RequestParam @ApiParam(value = "流程id",required = true) String formNo){
        try {
            return HttpResponse.success(allocationService. updateSubmit(status,formNo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.ALLOCATION_RETURN_REVOCATION_ERROR);
        }
    }
}
