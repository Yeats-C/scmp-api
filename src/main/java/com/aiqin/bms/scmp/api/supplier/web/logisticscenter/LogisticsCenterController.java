package com.aiqin.bms.scmp.api.supplier.web.logisticscenter;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.AreaBasic;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.LogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.QueryLogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.UpdateLogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.QueryLogisticsCenterResVo;
import com.aiqin.bms.scmp.api.supplier.service.LogisticsCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 描述:物流中心管理控制器
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@RestController
@Api(description = "基础数据物流中心管理")
@RequestMapping("/LogisticsCenter")
public class LogisticsCenterController {

   @Autowired
     private LogisticsCenterService logisticsCenterService;
    /**
     * 物流中心列表
     * @param vo
     * @return
     */
    @ApiOperation("物流中心列表")
    @PostMapping("/getLogisticsCenterList")
    public HttpResponse<BasePage<QueryLogisticsCenterResVo>> getLogisticsCenterList(@RequestBody QueryLogisticsCenterReqVo vo) {
        try{
            return HttpResponse.success(logisticsCenterService.getLogisticsCenterList(vo));
        }catch (Exception e){
            return  HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    /**
     * 新增物流中心
     * @param logisticsCenterReqVo
     * @return
     */
    @ApiOperation("新增物流中心")
    @PostMapping("/saveLogisticsCenter")
    public HttpResponse<Integer> saveLogisticsCenter(@RequestBody  @Valid LogisticsCenterReqVo logisticsCenterReqVo){

            return  logisticsCenterService.saveLogisticsCenter(logisticsCenterReqVo);

    }

    /**
     * 通过id获取物流中心详情
     * @param id
     * @return
     */
    @ApiOperation("通过id获取物流中心详情")
    @GetMapping("/getLogisticsCenter")
    public HttpResponse<LogisticsCenterResVo> getLogisticsCenter(@RequestParam  @ApiParam( value = "传入主键id" ,required = true) Long id){
        try {
            return HttpResponse.success(logisticsCenterService.getLogisticsCenter(id));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    /**
     * 修改物流中心
     * @param updateLogisticsCenterReqVo
     * @return
     */
    @ApiOperation("修改物流中心")
    @PutMapping("/updateLogisticsCenter")
    public HttpResponse<Integer> updateLogisticsCenter(@RequestBody @Valid UpdateLogisticsCenterReqVo updateLogisticsCenterReqVo){
            return  logisticsCenterService.updateLogisticsCenter(updateLogisticsCenterReqVo);
    }
    /**
     * 物流中心外部API
     * @return
     */
    @ApiOperation("物流中心外部API")
    @GetMapping("/getLogisticsCenterAPI")
    public HttpResponse<List<LogisticsCenterResVo>> getLogisticsCenterAPI(String companyCode){
        try {
            return HttpResponse.success(logisticsCenterService.getLogisticsCenterAPI(companyCode));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    /**
     * 查询所有省
     * @return
     */
    @GetMapping("/province")
    @ApiOperation("查询所有省")
    public HttpResponse<List<AreaBasic>> getProvinceList(){
        return logisticsCenterService.getProvinceList();
    }


    /**
     * 查询市
     * @param parentId
     * @return
     */
    @GetMapping("/province/city")
    @ApiOperation("根据ID查询市区")
    public HttpResponse<List<AreaBasic>> getCityList(@RequestParam @ApiParam(value = "上级区域ID,必传")String parentId){
        return logisticsCenterService.getCityList(parentId);
    }
}
