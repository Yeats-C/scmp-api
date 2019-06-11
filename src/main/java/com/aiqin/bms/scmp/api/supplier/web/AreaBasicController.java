package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.AreaBasic;
import com.aiqin.bms.scmp.api.supplier.service.AreaBasicInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @功能说明: 省市区
 * @author: wangxu
 * @date: 2018/12/24 0024 14:29
 */
@RestController
@RequestMapping("/area")
@Api(description = "省市区")
public class AreaBasicController {
    @Autowired
    private AreaBasicInfoService areaBasicInfoService;
    @GetMapping("/province")
    @ApiOperation("查询所有省")
    public HttpResponse<List<AreaBasic>> getProvinceList(){
            return areaBasicInfoService.getProvinceList();
    }

    @GetMapping("/province/city")
    @ApiOperation("根据ID查询市区")
    public HttpResponse<List<AreaBasic>> getCityList(@RequestParam @ApiParam(value = "上级区域ID,必传")String parentId){
            return areaBasicInfoService.getCityList(parentId);
    }

    @GetMapping("/list")
    @ApiOperation("省市县树")
    public HttpResponse<List<AreaBasic>> getList(){
            return areaBasicInfoService.getTreeList();
    }

}
