package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyAndTransportResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyGroupResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectResponse;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Api(tags = "退供相关接口")
@RequestMapping("/reject")
@RestController
@SuppressWarnings("unchecked")
public class GoodsRejectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectController.class);

    @Resource
    private GoodsRejectService goodsRejectService;

    @GetMapping("/apply/list")
    @ApiOperation(value = "退供申请单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "create_begin_time", value = "创建开始时间", type = "String"),
            @ApiImplicitParam(name = "create_finish_time", value = "创建结束时间", type = "String"),
            @ApiImplicitParam(name = "update_begin_time", value = "修改开始时间", type = "String"),
            @ApiImplicitParam(name = "update_finish_time", value = "修改结束时间", type = "String"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "reject_apply_record_code", value = "退货申请单号", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商名称", type = "String"),
            @ApiImplicitParam(name = "apply_type", value = "申请单类型: 0 手动 1自动", type = "Integer"),
            @ApiImplicitParam(name = "apply_record_status", value = "退供申请单状态: " +
                    "0.待提交 1.待审核 2.审核中 3.审核通过 4.审核不通过 5.撤销", type = "Integer"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<RejectApplyRecord>>
    rejectApplyList(@RequestParam(value = "create_begin_time", required = false) String createBeginTime,
                    @RequestParam(value = "create_finish_time", required = false) String createFinishTime,
                    @RequestParam(value = "update_begin_time", required = false) String updateBeginTime,
                    @RequestParam(value = "update_finish_time", required = false) String updateFinishTime,
                    @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                    @RequestParam(value = "reject_apply_record_code", required = false) String rejectApplyRecordCode,
                    @RequestParam(value = "supplier_code", required = false) String supplierCode,
                    @RequestParam(value = "supplier_name", required = false) String supplierName,
                    @RequestParam(value = "apply_type", required = false) Integer applyType,
                    @RequestParam(value = "apply_record_status", required = false) Integer applyRecordStatus,
                    @RequestParam(value = "page_no", required = false) Integer page_no,
                    @RequestParam(value = "page_size", required = false) Integer page_size) {
        RejectApplyQueryRequest rejectApplyQueryRequest = new RejectApplyQueryRequest(createBeginTime, createFinishTime,
                updateBeginTime, updateFinishTime, purchaseGroupCode, rejectApplyRecordCode, supplierCode, supplierName,
                applyType, applyRecordStatus);
        rejectApplyQueryRequest.setPageNo(page_no);
        rejectApplyQueryRequest.setPageSize(page_size);
        LOGGER.info("退供申请单列表请求:{}", rejectApplyQueryRequest.toString());
        return goodsRejectService.rejectApplyList(rejectApplyQueryRequest);
    }

    @GetMapping("/stock/product")
    @ApiOperation(value = "查询退供申请单的商品信息(手动选择商品)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房", type = "String"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组 code", type = "String", required = true),
            @ApiImplicitParam(name = "sku_code", value = "sku编号", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "分类", type = "String"),
            @ApiImplicitParam(name = "category_id", value = "分类", type = "String"),
            @ApiImplicitParam(name = "brand_name", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "brand_id", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "product_property_name", value = "商品属性name", type = "String"),
            @ApiImplicitParam(name = "product_property_code", value = "商品属性code", type = "String"),
            @ApiImplicitParam(name = "spu_code", value = "spu编码", type = "String"),
            @ApiImplicitParam(name = "spu_name", value = "spu名称", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<RejectApplyDetailHandleResponse>>
    rejectStockProduct(@RequestParam(value = "page_no", required = false) Integer pageNo,
                       @RequestParam(value = "page_size", required = false) Integer pageSize,
                       @RequestParam(value = "product_property_name", required = false) String productPropertyName,
                       @RequestParam(value = "purchase_group_code") String purchaseGroupCode,
                       @RequestParam(value = "sku_code", required = false) String skuCode,
                       @RequestParam(value = "sku_name", required = false) String skuName,
                       @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                       @RequestParam(value = "supplier_code", required = false) String supplierCode,
                       @RequestParam(value = "category_name", required = false) String categoryName,
                       @RequestParam(value = "product_property_code", required = false) String productPropertyCode,
                       @RequestParam(value = "category_id", required = false) String categoryId,
                       @RequestParam(value = "brand_id", required = false) String brandId,
                       @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                       @RequestParam(value = "brand_name", required = false) String brandName,
                       @RequestParam(value = "spu_code", required = false) String spuCode,
                       @RequestParam(value = "spu_name", required = false) String spuName) {
        RejectProductRequest rejectQueryRequest = new RejectProductRequest(purchaseGroupCode, supplierCode, transportCenterCode,
                warehouseCode, skuCode, skuName, categoryId, categoryName, brandId, brandName, productPropertyCode, productPropertyName,
                spuCode, spuName);
        rejectQueryRequest.setPageNo(pageNo);
        rejectQueryRequest.setPageSize(pageSize);
        LOGGER.info("查询退供申请单的商品信息,rejectRecord:{}", rejectQueryRequest.toString());
        return goodsRejectService.rejectStockProduct(rejectQueryRequest);
    }

    @GetMapping("/apply/info")
    @ApiOperation(value = "退供申请单查询(退供信息、分仓信息)")
    public HttpResponse<RejectApplyAndTransportResponse> selectRejectApply(@RequestParam("reject_apply_record_code") String rejectApplyRecordCode,
                                                                           @RequestParam(value = "warehouse_code", required = false) String warehouseCode) {
        LOGGER.info("退供申请单查询(退供信息、分仓信息):{}", rejectApplyRecordCode);
        return goodsRejectService.selectRejectApply(rejectApplyRecordCode, warehouseCode);
    }

    @GetMapping("/apply/product")
    @ApiOperation(value = "退供申请单商品查询")
    public HttpResponse<PageResData<RejectApplyRecordDetail>> selectRejectApplyProduct(@RequestParam("reject_apply_record_code") String rejectApplyRecordCode){
        LOGGER.info("退供申请单商品查询:{}", rejectApplyRecordCode);
        return goodsRejectService.selectRejectApplyProduct(rejectApplyRecordCode);
    }

    @GetMapping("/apply/batch")
    @ApiOperation(value = "退供申请单批次查询")
    public HttpResponse<PageResData<RejectApplyRecordDetail>> selectRejectApplyBatch(@RequestParam("reject_apply_record_code") String rejectApplyRecordCode) {
        LOGGER.info("退供申请单批次查询:{}", rejectApplyRecordCode);
        return goodsRejectService.selectRejectApplyBatch(rejectApplyRecordCode);
    }

    @PostMapping("/product/group")
    @ApiOperation(value = "根据商品对退供申请单按供应商、结算方式、采购组进行生成对应信息")
    public HttpResponse<RejectApplyGroupResponse> productGroup(@RequestBody List<RejectApplyDetailRequest> request) {
        return goodsRejectService.productGroup(request);
    }

    @GetMapping("/apply/product/edit")
    @ApiOperation(value = "退供申请单编辑的商品信息查询")
    public HttpResponse<RejectApplyDetailHandleResponse> applyProductEdit(@RequestParam("reject_apply_record_code") String rejectApplyRecordCode) {
        LOGGER.info("退供申请单编辑的商品信息查询,rejectApplyRecordCode:{}", rejectApplyRecordCode);
        return goodsRejectService.applyProductEdit(rejectApplyRecordCode);
    }

    @PostMapping("/apply/import")
    @ApiOperation(value = "批量导入退供申请单")
    public HttpResponse<List<RejectImportResponse>> rejectApplyImport(MultipartFile file, @RequestParam(name = "purchase_group_code") String purchaseGroupCode) {
        LOGGER.info("批量导入退供申请单请求,purchaseGroupCode:{}", purchaseGroupCode);
        return goodsRejectService.rejectApplyImport(file, purchaseGroupCode);
    }

    @PostMapping("/submit/apply/record")
    @ApiOperation(value = "保存、提交审核退供申请单")
    public HttpResponse addApplyReject(@RequestBody RejectApplyGroupRequest request) {
        LOGGER.info("保存、提交审核退供申请单请求,rejectRecord:{}", request.toString());
        return goodsRejectService.addApplyReject(request);
    }

    @GetMapping("/record/list")
    @ApiOperation(value = "查询退供单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "create_begin_time", value = "创建开始时间", type = "String"),
            @ApiImplicitParam(name = "create_finish_time", value = "创建结束时间", type = "String"),
            @ApiImplicitParam(name = "update_begin_time", value = "修改开始时间", type = "String"),
            @ApiImplicitParam(name = "update_finish_time", value = "修改结束时间", type = "String"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "reject_record_code", value = "退供单号", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商名称", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房", type = "String"),
            @ApiImplicitParam(name = "reject_status", value = "退供单状态:0 待确认 1.待出库 2.出库开始 3.已完成 4.已撤销 ", type = "Integer"),
            @ApiImplicitParam(name = "source_type", value = "来源类型 0.退供申请 1.退货单", type = "Integer"),
            @ApiImplicitParam(name = "source_code", value = "来源单号", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<RejectRecord>> rejectList(@RequestParam(value = "create_begin_time", required = false) String createBeginTime,
                                                              @RequestParam(value = "create_finish_time", required = false) String createFinishTime,
                                                              @RequestParam(value = "update_begin_time", required = false) String updateBeginTime,
                                                              @RequestParam(value = "update_finish_time", required = false) String updateFinishTime,
                                                              @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                                                              @RequestParam(value = "reject_record_code", required = false) String rejectRecordCode,
                                                              @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                                              @RequestParam(value = "supplier_name", required = false) String supplierName,
                                                              @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                                              @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                                                              @RequestParam(value = "reject_status", required = false) Integer rejectStatus,
                                                              @RequestParam(value = "source_type", required = false) Integer sourceType,
                                                              @RequestParam(value = "source_code", required = false) String sourceCode,
                                                              @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                              @RequestParam(value = "page_size", required = false) Integer pageSize) {
        RejectQueryRequest rejectQueryRequest = new RejectQueryRequest(createBeginTime, createFinishTime, updateBeginTime, updateFinishTime, purchaseGroupCode,
                rejectRecordCode, supplierCode, supplierName, transportCenterCode, warehouseCode, rejectStatus, sourceType, sourceCode);
        rejectQueryRequest.setPageNo(pageNo);
        rejectQueryRequest.setPageSize(pageSize);
        LOGGER.info("查询退供单列表请求,rejectRecord:{}", rejectQueryRequest.toString());
        return goodsRejectService.rejectList(rejectQueryRequest);
    }

    @GetMapping("/record/info")
    @ApiOperation(value = "查询退供单详情")
    @ApiImplicitParam(name = "reject_record_code", value = "退供单code", type = "String")
    public HttpResponse<RejectResponse> rejectInfo(@RequestParam("reject_record_code") String rejectRecordCode) {
        LOGGER.info("查询退供单详情请求,reject_record_code:{}", rejectRecordCode);
        return goodsRejectService.rejectInfo(rejectRecordCode);
    }

    @GetMapping("/record/product")
    @ApiOperation(value = "查询退供单商品")
    @ApiImplicitParam(name = "reject_record_code", value = "退供单code", type = "String")
    public HttpResponse<PageResData<RejectRecordDetail>> rejectProductInfo(@RequestParam("reject_record_code") String rejectRecordCode) {
        LOGGER.info("查询退供单详情请求,reject_record_code:{}", rejectRecordCode);
        return goodsRejectService.rejectProductInfo(rejectRecordCode);
    }

    @GetMapping("/record/batch")
    @ApiOperation(value = "查询退供单批次")
    @ApiImplicitParam(name = "reject_record_code", value = "退供单code", type = "String")
    public HttpResponse<PageResData<RejectRecordBatch>> rejectBatchInfo(@RequestParam("reject_record_code") String rejectRecordCode) {
        LOGGER.info("查询退供单详情请求,reject_record_code:{}", rejectRecordCode);
        return goodsRejectService.rejectBatchInfo(rejectRecordCode);
    }

}
