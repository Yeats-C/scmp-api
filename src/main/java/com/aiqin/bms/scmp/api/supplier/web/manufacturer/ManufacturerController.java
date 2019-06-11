package com.aiqin.bms.scmp.api.supplier.web.manufacturer;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.ManufacturerReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.ManufacturerUpdateReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.QueryManufacturerReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer.ManufacturerResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer.QueryManufacturerResVo;
import com.aiqin.bms.scmp.api.supplier.service.ManufacturerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 描述: 制造商管理控制器
 *
 * @Author: Kt.w
 * @Date: 2018/12/10
 * @Version 1.0
 * @since 1.0
 */
@RestController
@Api(description = "制造商管理")
@RequestMapping("/manufacturer")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    /**
     * 制造商列表查看
     * @param vo
     * @return
     */
    @ApiOperation("制造商列表查看")
    @PostMapping("/list")
    public HttpResponse<BasePage<QueryManufacturerResVo>> list(@RequestBody QueryManufacturerReqVo vo) {
        try {
            return HttpResponse.success(manufacturerService.list(vo));
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    @ApiOperation("新增制造商")
    @PostMapping("/save")
    public HttpResponse<Integer> save(@RequestBody @Valid ManufacturerReqVo vo){
            return manufacturerService.save(vo);
    }

    @ApiOperation("查看制造商")
    @GetMapping("/view")
    public HttpResponse<ManufacturerResVo>view(@RequestParam(required = true)Long id ){
        try {
            return HttpResponse.success(manufacturerService.view(id));
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }

    }
    @ApiOperation("制造商修改")
    @PostMapping("/update")
    public HttpResponse<Integer> update(@RequestBody @Valid ManufacturerUpdateReqVo vo){
            return manufacturerService.update(vo);

    }
    @ApiOperation("制造商启用禁用")
    @GetMapping("/enable")
    public HttpResponse<Integer>  enable(@RequestParam("manufacturerCode")String manufacturerCode,@RequestParam("enable") byte enable){
        try {
            return HttpResponse.success(manufacturerService.enable(manufacturerCode,enable));
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
    }
}
