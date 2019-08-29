package com.aiqin.bms.scmp.api.supplier.web.warehouse;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.QueryWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.UpdateWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseListReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterApiResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 描述:库房管理控制器
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@RestController
@Api(description = "基础数据库房管理")
@RequestMapping("/warehouse")
public class WarehouseController {


    @Autowired
    private WarehouseService warehouseService;

    /**
     * 列表展示库房
     * @param vo
     * @return
     */
    @ApiOperation("查询库房List")
    @PostMapping("/getWarehouseList")
    public HttpResponse<BasePage<QueryWarehouseResVo>> getWarehouseList(@RequestBody  QueryWarehouseReqVo vo) {
        try{
        return HttpResponse.success(warehouseService.findWarehouseList(vo));
        }catch (Exception e){
            return  HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    /**
     * 新增库房
     * @param warehouseReqVo
     * @return
     */
    @ApiOperation("新增库房")
    @PostMapping("/saveWarehouse")
    public HttpResponse<Integer> saveWarehouse(@RequestBody  @Valid WarehouseReqVo warehouseReqVo){
            return warehouseService.saveWarehouse(warehouseReqVo);
    }
    /**
     * 通过id获取库房详情
     * @param id
     * @return
     */
    @ApiOperation("通过id获取库房详情")
    @GetMapping("/getWarehouse")
    public HttpResponse<WarehouseResVo> getWarehouse(@RequestParam  @ApiParam( value = "传入主键id" ,required = true) Long id){
        try {
            return HttpResponse.success(warehouseService.selectByPrimaryKey(id));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }
    /**
     * 修改库房
     * @param updateWarehouseReqVo
     * @return
     */
    @ApiOperation("修改库房")
    @PutMapping("/updateWarehouse")
    public HttpResponse<Integer> updateWarehouse(@RequestBody @Valid UpdateWarehouseReqVo updateWarehouseReqVo){
            return warehouseService.updateWarehouse(updateWarehouseReqVo);

    }

    /**
     * 通过code获取库房详情
     * @param code
     * @return
     */
    @ApiOperation("通过code获取库房详情")
    @GetMapping("/getWarehouseByCode")
    public HttpResponse<WarehouseResVo> getWarehouseByCode(@RequestParam  @ApiParam( value = "传入库房编码" ,required = true) String code){
        try {
            return HttpResponse.success(warehouseService.getWarehouseByCode(code));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    /**
     * 通过物流中心编码查询库房列表
     * @param logisticsCenterCode
     * @return
     */
    @ApiOperation("通过物流中心编码查询库房列表")
    @GetMapping("/getWarehouseByLogisticsCenterCode")
    public HttpResponse<List<WarehouseResVo>> getWarehouseByLogisticsCenterCode(@RequestParam  @ApiParam( value = "传入物流中心编码" ,required = true) String logisticsCenterCode){
        try {
            return HttpResponse.success(warehouseService.getWarehouseByLogisticsCenterCode(logisticsCenterCode));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    @ApiOperation("根据地区编码查询库房信息")
    @PostMapping("/getWarehouseApi")
    public  HttpResponse<List<LogisticsCenterApiResVo>>  getWarehouseApi(@RequestBody WarehouseListReqVo warehouseListReqVo){
        try {
            return HttpResponse.success(warehouseService.getWarehouseApi(warehouseListReqVo));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }
    /**
     * 通过物流中心编码以及库房类型查询库房
     * @param logisticsCenterCode
     * @return
     */
    @ApiOperation("通过物流中心编码以及库房类型查询库房")
    @GetMapping("/getWarehouseByLogisticsCenterCodeAndType")
    public HttpResponse<List<WarehouseResVo>> getWarehouseTypeByLogisticsCenterCode(@RequestParam  @ApiParam( value = "传入物流中心编码" ,required = true) String logisticsCenterCode,@RequestParam  @ApiParam( value = "仓库类型编码  1销售库 2特卖库 3残品库 4监管库" ,required = true) Byte warehouseTypeCode){
        try {
            return HttpResponse.success(warehouseService.getWarehouseTypeByLogisticsCenterCode(logisticsCenterCode,warehouseTypeCode));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    @ApiOperation("通过物流中心编码以及库房类型查询不在库房类型外所有库房")
    @GetMapping("/getWarehouseByLogisticsCenterCodeAndNotExistsType")
    public HttpResponse<List<WarehouseResVo>> getWarehouseByLogisticsCenterCodeAndNotExistsType(@RequestParam  @ApiParam( value = "传入物流中心编码" ,required = true) String logisticsCenterCode,@RequestParam  @ApiParam( value = "仓库类型编码  1销售库 2特卖库 3残品库 4监管库" ,required = true) Byte warehouseTypeCode){
        try {
            return HttpResponse.success(warehouseService.getWarehouseByLogisticsCenterCodeAndNotExistsType(logisticsCenterCode,warehouseTypeCode));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }
}
