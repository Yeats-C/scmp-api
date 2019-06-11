package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVo;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVoPage;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVoPage;
import com.aiqin.bms.scmp.api.product.service.InventoryService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyongqiang
 * @description 库存管理请求控制器
 * @date 2018/11/20 15:04
 */
@RestController
@Api(value = "库存管理操作接口")
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {
    @Resource
    private InventoryService inventoryService;

    /**
     * 库存列表
     *
     * @param param 请求参数
     * @return 库存列表分页响应结果
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取库存列表")
    public HttpResponse<PageResData<Inventory>> list(@RequestBody InventoryRequest param) {
        log.info("Search inventories:{}", param);
        try {
            return HttpResponse.success(this.inventoryService.searchInventories(param));
        } catch (Exception e) {
            log.error("Get inventories failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    /**
     * 库存列表
     *
     * @param param 请求参数
     * @return 库存列表分页响应结果
     */
    @GetMapping("/getInventoryWarehouse")
    @ApiOperation(value = "获取仓库")
    public HttpResponse<PageResData<InventoryWarehouse>> getInventoryWarehouse(@RequestParam("distributor_id") String distributorId, @RequestParam("distributor_name") String distributorName) {
        log.info("Search getInventoryWarehouse:{}", "distributor_id:" + distributorId + ",distributor_name:" + distributorName);
        try {
            return HttpResponse.success(this.inventoryService.getInventoryWarehouse(distributorId, distributorName));
        } catch (Exception e) {
            log.error("Get getInventoryWarehouse failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    /**
     * 获取库存分布列表
     *
     * @param param 请求参数
     * @return 库存分布列表分页响应结果
     */
    @PostMapping("/distributions")
    @ApiOperation(value = "获取库存分布列表")
    public HttpResponse<PageResData<InventoryDistribution>> distributions(@RequestBody InventoryDistributionRequest param) {
        log.info("Search inventory distributions:{}", param);
        try {
            return HttpResponse.success(this.inventoryService.searchInventoryDistributions(param));
        } catch (Exception e) {
            log.error("Get inventory distributions failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 批量设置库存预警
     *
     * @param param 请求参数
     * @return 执行结果
     */
    @PostMapping("/update/warning")
    @ApiOperation(value = "设置库存预警")
    public HttpResponse updateInventoryWarning(@RequestBody InventoryWarningRequest param) {
        log.info("Batch modify inventory warning:{}", param);
        try {
            this.inventoryService.updateInventoryWarning(param);
            return HttpResponse.success();
        } catch (Exception e) {
            log.error("Batch modify inventory warning failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取出入库记录列表
     *
     * @param param 请求参数
     * @return 库存出入库记录列表响应结果
     */
    @PostMapping("/accounts")
    @ApiOperation(value = "获取库存出入库记录列表")
    public HttpResponse<PageResData<InventoryAccount>> accounts(@RequestBody InventoryAccountRequest param) {
        log.info("Search inventory accounts:{}", param);
        try {
            return HttpResponse.success(this.inventoryService.searchInventoryAccounts(param));
        } catch (Exception e) {
            log.error("Get inventor accounts failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取库存出入库记录明细
     *
     * @param masterNumber 出/入库单号
     * @return 库存出入库记录明细响应结果
     */
    @GetMapping("/account/detail")
    @ApiOperation(value = "获取库存出入库记录明细")
    public HttpResponse<InventoryAccountDetail> accountDetails(@RequestParam("master_number") String masterNumber) {
        log.info("Search inventory account detail:{}", masterNumber);
        try {
            return HttpResponse.success(this.inventoryService.searchInventoryAccountDetails(masterNumber));
        } catch (Exception e) {
            log.error("Get inventor account detail failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取库存出入库记录明细
     */
    @PostMapping("/get/byidslu")
    @ApiOperation(value = "根据门店ID和商品的SKU（批量） 查商品的库存信息")
    public HttpResponse<List<ProductDistributor>> getByidslu(@RequestBody ProductDistributorVo vo) {
        log.info("Search inventory account detail:{}", vo);
        try {
            return HttpResponse.success(this.inventoryService.getByidslu(vo));
        } catch (Exception e) {
            log.error("Get inventor account detail failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    /**
     * 获取库存出入库记录明细(不分页)
     */
//    @PostMapping ("/get/getptdirevo")
//    @ApiOperation(value = "根据所属门店和商品标识和商品分类查询(不分页)")
    public HttpResponse<List<ProductDistributorReVo>> getPtDiReVo(@RequestBody ProductDistributorQuVo vo) {
        log.info("Search inventory account detail:{}", vo);
        try {
            return HttpResponse.success(this.inventoryService.getPtDiReVo(vo));
        } catch (Exception e) {
            log.error("Get inventor account detail failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取库存出入库记录明细(分页)
     */
    @PostMapping("/get/getptdirevo/page")
    @ApiOperation(value = "根据所属门店和商品标识和商品分类查询(分页)")
    public HttpResponse<List<ProductDistributorReVoPage>> getPtDiReVoPage(@RequestBody ProductDistributorQuVoPage vo) {
        log.info("Search inventory account detail:{}", vo);
        try {
            return HttpResponse.success(this.inventoryService.getPtDiReVoPage(vo));
        } catch (Exception e) {
            log.error("Get inventor account detail failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 更新库存记录
     *
     * @param params 更新参数
     * @return 更新结果
     */
    @PostMapping("/update/record")
    @ApiOperation(value = "更新库存记录")
    public HttpResponse updateInventoryRecord(@RequestBody List<InventoryRecordRequest> params) {
        log.info("Modify inventory record:{}", JSONObject.toJSON(params));
        try {
            this.inventoryService.updateInventoryRecord(params);
            return HttpResponse.success();
        } catch (IllegalArgumentException e) {
            log.error("Modify inventory record failed", e);
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, 900, e.getMessage()));
        } catch (Exception e) {
            log.error("Modify inventory record failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 库存添加
     *
     * @param params 更新参数
     * @return 更新结果
     */
    @PostMapping("/add/record")
    @ApiOperation(value = "添加库存")
    public HttpResponse<Boolean> addProductDistributor(@RequestBody List<ProductDistributor> params) {
        log.info("Modify inventory record:{}", JSONObject.toJSON(params));
        try {

            return HttpResponse.success(this.inventoryService.addProductDistributor(params));
        } catch (IllegalArgumentException e) {
            log.error("Modify inventory record failed", e);
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, 900, e.getMessage()));
        } catch (Exception e) {
            log.error("Modify inventory record failed", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    @PostMapping("/sold/out/stock")
    @ApiOperation("畅缺商品-库存低于预警库存的商品信息")
    public HttpResponse<List<ProductDistributorOrder>> getSoldOutOfStock(@RequestBody ProductDistributorOrderRequest vo) {
        log.info("inventory/sold/out/stock [{}]", vo);
        return HttpResponse.success(inventoryService.getProductDistributorOrder(vo));
    }
}
