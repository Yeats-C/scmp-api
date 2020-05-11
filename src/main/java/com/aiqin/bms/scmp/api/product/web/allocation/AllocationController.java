package com.aiqin.bms.scmp.api.product.web.allocation;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.AllocationTypeEnum;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.ManualChoseProductReq;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationImportSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.QueryAllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.ManualChoseProductRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.QueryAllocationResVo;
import com.aiqin.bms.scmp.api.product.service.AllocationService;
import com.aiqin.bms.scmp.api.supplier.domain.response.allocation.AllocationItemRespVo;
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
 * 描述:调拨管理
 *
 * @Author: Kt.w
 * @Date: 2019/1/9`
 * @Version 1.0
 * @since 1.0
 */
@RestController
@Api(description = "调拨管理")
@RequestMapping("/product/allocation")
@Slf4j
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
        vo.setAllocationType(AllocationTypeEnum.ALLOCATION.getType());
        try {
            return HttpResponse.success(allocationService.getList(vo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    /**
     * 新增调拨单
     * @param vo
     * @return
     */
    @ApiOperation("新增调拨单")
    @PostMapping("/save")
    public HttpResponse<Long> save(@RequestBody @Validated AllocationReqVo vo){
        try {
            vo.setAllocationType(AllocationTypeEnum.ALLOCATION.getType());
            vo.setAllocationTypeName(AllocationTypeEnum.ALLOCATION.getTypeName());
            return HttpResponse.success(allocationService.save(vo));
        }catch (BizException ex){
            return HttpResponse.failure(ex.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
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
        } catch (GroundRuntimeException e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API,52,e.getMessage()));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
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
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
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
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.ALLOCATION_RETURN_REVOCATION_ERROR);
        }
    }

    @ApiOperation("wms返回id")
    @GetMapping("/updateWmsStatus")
    public HttpResponse<Integer> updateWmsStatus( @RequestParam @ApiParam(value = "状态编码",required = true) Byte status ,
                                               @RequestParam @ApiParam(value = "调拨单code",required = true) String allocationCode){
        try {
            return HttpResponse.success(allocationService. updateWmsStatus(status,allocationCode));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.ALLOCATION_RETURN_REVOCATION_ERROR);
        }
    }

    @ApiOperation("导入商品信息")
    @PostMapping("/importAllocationSku")
    public HttpResponse<List<AllocationItemRespVo>> importAllocationSku(MultipartFile file,String transportCenterCode, String warehouseCode, String purchaseGroupCode){
        AllocationImportSkuReqVo allocationImportSkuReqVo = new AllocationImportSkuReqVo(file,transportCenterCode,warehouseCode,purchaseGroupCode);
        return HttpResponse.success(allocationService.importAllocationSku(allocationImportSkuReqVo));
    }

    @GetMapping("/getIdByFormNo")
    @ApiOperation("根据formNo获取主键ID")
    public HttpResponse<Long> getIdByFormNo(@RequestParam String formNo){
        return HttpResponse.success(allocationService.getIdByFormNo(formNo));
    }

    @GetMapping("/getNumberByBatchAndSkuCode")
    @ApiOperation("根据批次号sku获取该批次sku的库存数量")
    public HttpResponse<StockBatch> getNumberByBatchAndSkuCode(@RequestParam @ApiParam(value = "sku编码",required = true) String skuCode,
                                                               @RequestParam @ApiParam(value = "批次号",required = true) String batchCode){
        return HttpResponse.success(allocationService.getNumberByBatchAndSkuCode(skuCode,batchCode));
    }

    @GetMapping("/getManualChoseProduct")
    @ApiOperation("手动选择商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transport_center_code", value = "仓编码(物流中心编码)", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌code", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类code", type = "String"),
            @ApiImplicitParam(name = "product_property_code", value = "商品属性", type = "String"),
            @ApiImplicitParam(name = "spu_code", value = "spu编码", type = "String"),
            @ApiImplicitParam(name = "spu_name", value = "spu名称", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "String"),
            @ApiImplicitParam(name = "page_size", value = "当前条数", type = "String"),
    })
    public HttpResponse<BasePage<ManualChoseProductRespVo>> getManualChoseProduct(@RequestParam(value = "transport_center_code",required = false) String transportCenterCode,
                                                                                  @RequestParam(value = "warehouse_code",required = false) String warehouseCode,
                                                                                  @RequestParam(value = "sku_code",required = false) String skuCode,
                                                                                  @RequestParam(value = "sku_name",required = false) String skuName,
                                                                                  @RequestParam(value = "product_brand_code",required = false) String productBrandCode,
                                                                                  @RequestParam(value = "product_category_code",required = false) String productCategoryCode,
                                                                                  @RequestParam(value = "product_property_code",required = false) String productPropertyCode,
                                                                                  @RequestParam(value = "spu_code",required = false) String spuCode,
                                                                                  @RequestParam(value = "spu_name",required = false) String spuName,
                                                                                  @RequestParam(value = "page_no",required = false) Integer pageNo,
                                                                                  @RequestParam(value = "page_size",required = false) Integer pageSize){
        ManualChoseProductReq m = new ManualChoseProductReq();
        m.setTransportCenterCode(transportCenterCode);
        m.setWarehouseCode(warehouseCode);
        m.setSkuCode(skuCode);
        m.setSkuName(skuName);
        m.setProductBrandCode(productBrandCode);
        m.setProductCategoryCode(productCategoryCode);
        m.setProductPropertyCode(productPropertyCode);
        m.setSpuCode(spuCode);
        m.setSpuName(spuName);
        m.setPageNo(pageNo);
        m.setPageSize(pageSize);
        return HttpResponse.success(allocationService.getManualChoseProduct(m));
    }

}
