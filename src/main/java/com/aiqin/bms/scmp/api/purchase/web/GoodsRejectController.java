package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyBatch;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyAndTransportResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
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
    public HttpResponse<PageResData<RejectApplyRecordDetail>>  selectRejectApplyProduct(@RequestParam("reject_apply_record_code") String rejectApplyRecordCode){
        LOGGER.info("退供申请单商品查询:{}", rejectApplyRecordCode);
        return goodsRejectService.selectRejectApplyProduct(rejectApplyRecordCode);
    }

    @GetMapping("/apply/batch")
    @ApiOperation(value = "退供申请单批次查询")
    public HttpResponse<PageResData<RejectApplyBatch>> selectRejectApplyBatch(@RequestParam("reject_apply_record_code") String rejectApplyRecordCode) {
        LOGGER.info("退供申请单批次查询:{}", rejectApplyRecordCode);
        return goodsRejectService.selectRejectApplyBatch(rejectApplyRecordCode);
    }



    @PostMapping("/apply")
    @ApiOperation(value = "退供申请单增加")
    public HttpResponse<RejectApplyRequest> rejectApply(@RequestBody RejectApplyHandleRequest rejectApplyQueryRequest) {
        LOGGER.info("退供申请单增加请求:{}", rejectApplyQueryRequest.toString());
        return goodsRejectService.rejectApply(rejectApplyQueryRequest);
    }

    @PutMapping("/apply/finish/{reject_apply_record_code}")
    @ApiOperation(value = "更改退供申请单为完成状态")
    @ApiImplicitParam(name = "reject_apply_record_code", value = "退货申请单号", type = "String")
    public HttpResponse<RejectApplyRequest> updateApplyReject(@PathVariable String reject_apply_record_code) {
        LOGGER.info("更改退供申请单为完成状态,rejectRecord:{}", reject_apply_record_code);
        return goodsRejectService.updateReject(reject_apply_record_code);
    }

    @PutMapping("/apply/{reject_apply_record_code}")
    @ApiOperation(value = "退供申请单修改")
    @ApiImplicitParam(name = "reject_apply_record_code", value = "退货申请单号", type = "String")
    public HttpResponse<RejectApplyRequest> updateRejectApply(@PathVariable String reject_apply_record_code, @RequestBody RejectApplyHandleRequest rejectApplyQueryRequest) {
        rejectApplyQueryRequest.setRejectApplyRecordCode(reject_apply_record_code);
        LOGGER.info("退供申请单修改请求:{}", rejectApplyQueryRequest.toString());
        return goodsRejectService.updateRejectApply(rejectApplyQueryRequest);
    }

    @PostMapping("/apply/import")
    @ApiOperation(value = "批量导入退供申请单")
    public HttpResponse<List<RejectImportResponse>> rejectApplyImport(MultipartFile file, @RequestParam(name = "purchase_group_code") String purchaseGroupCode) {
        LOGGER.info("批量导入退供申请单请求,purchaseGroupCode:{}", purchaseGroupCode);
        return goodsRejectService.rejectApplyImport(file, purchaseGroupCode);
    }

    @PostMapping("/apply/info")
    @ApiOperation(value = "查询退供申请单信息去生成退供单")
    public HttpResponse<List<RejectApplyResponse>> rejectApplyInfo( @RequestBody RejectApplyRequest rejectApplyRequest) {
        LOGGER.info("查询退供申请单信息去生成退供单请求,rejectRecord:{}", rejectApplyRequest.toString());
        return goodsRejectService.rejectApplyInfo(rejectApplyRequest);
    }

    @PostMapping("/apply/info/detail")
    @ApiOperation(value = "查询退供申请单详情信息去生成退供单")
    public HttpResponse<PageResData<RejectApplyRecordDetail>> rejectApplyDetailInfo(@RequestBody RejectApplyRequest rejectApplyRequest) {
        LOGGER.info("查询退供申请单详情信息去生成退供单,rejectApplyRequest:{}", rejectApplyRequest.toString());
        return goodsRejectService.rejectApplyDetailInfo(rejectApplyRequest);
    }

    @PostMapping("/apply/record/lists")
    @ApiOperation(value = "查询退供申请单详情信息去生成退供单-显示退供申请单的编号列表")
    public HttpResponse<List<RejectApplyListResponse>> rejectApplyListInfo(@RequestBody RejectApplyRequest rejectApplyRequest) {
        LOGGER.info("查询退供申请单详情信息去生成退供单-显示退供申请单的编号列表,rejectApplyRequest:{}", rejectApplyRequest.toString());
        return goodsRejectService.rejectApplyListInfo(rejectApplyRequest);
    }

    @PostMapping("/record")
    @ApiOperation(value = "新增退供单记录")
    public HttpResponse addReject(@RequestBody RejectRequest request) {
        LOGGER.info("新增退供单记录请求,rejectRecord:{}", request.toString());
        return goodsRejectService.addReject(request);
    }

    @GetMapping("/record/list")
    @ApiOperation(value = "查询退供单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reject_record_code", value = "退供单号", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房", type = "String"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组 code", type = "String"),
            @ApiImplicitParam(name = "reject_status", value = "退供单状态: 0 待审核 1 审核中  2 待供应商确认 3 待出库  4 出库开始 5 已出库 6 已发运 7 完成 8 取消 9 审核不通过", type = "Integer"),
            @ApiImplicitParam(name = "begin_time", value = "开始时间", type = "String"),
            @ApiImplicitParam(name = "finish_time", value = "结束时间", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<RejectRecord>> rejectList(@RequestParam(value = "page_no", required = false) Integer page_no, @RequestParam(value = "page_size", required = false) Integer page_size,
                                                              @RequestParam(value = "begin_time", required = false) String beginTime, @RequestParam(value = "purchase_group_code", required = false) String purchase_group_code,
                                                              @RequestParam(value = "reject_record_code", required = false) String reject_record_code, @RequestParam(value = "reject_status", required = false) Integer reject_status,
                                                              @RequestParam(value = "transport_center_code", required = false) String transport_center_code, @RequestParam(value = "supplier_code", required = false) String supplier_code,
                                                              @RequestParam(value = "warehouse_code", required = false) String warehouse_code, @RequestParam(value = "finish_time", required = false) String finishTime) {
        RejectQueryRequest rejectQueryRequest = new RejectQueryRequest(beginTime, finishTime, reject_record_code, purchase_group_code, supplier_code, transport_center_code, warehouse_code, reject_status);
        rejectQueryRequest.setPageNo(page_no);
        rejectQueryRequest.setPageSize(page_size);
        LOGGER.info("查询退供单列表请求,rejectRecord:{}", rejectQueryRequest.toString());
        return goodsRejectService.rejectList(rejectQueryRequest);
    }

    @GetMapping("/record/{reject_record_code}")
    @ApiOperation(value = "查询退供单详情")
    @ApiImplicitParam(name = "reject_record_code", value = "退供单code", type = "String")
    public HttpResponse<RejectResponse> rejectInfo(@PathVariable String reject_record_code) {
        LOGGER.info("查询退供单详情请求,reject_record_code:{}", reject_record_code);
        return goodsRejectService.rejectInfo(reject_record_code);
    }


    @PutMapping("/record/supplier/{reject_record_id}")
    @ApiOperation(value = "供应商确认")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province_id ", value = "省", type = "String"),
            @ApiImplicitParam(name = "province_name ", value = "省", type = "String"),
            @ApiImplicitParam(name = "city_id ", value = "市", type = "String"),
            @ApiImplicitParam(name = "city_name ", value = "市", type = "String"),
            @ApiImplicitParam(name = "district_id", value = "县", type = "String"),
            @ApiImplicitParam(name = "district_name", value = "县", type = "String"),
            @ApiImplicitParam(name = "address", value = "地址", type = "String"),
            @ApiImplicitParam(name = "contacts_person_phone", value = "联系人电话", type = "String"),
            @ApiImplicitParam(name = "contacts_person", value = "联系人名称", type = "String"),
            @ApiImplicitParam(name = "create_by_company_code", value = "创建人公司code", type = "String"),
    })
    public HttpResponse rejectSupplier(@PathVariable String reject_record_id, @RequestParam(value = "province_id", required = false) String province_id, @RequestParam(value = "province_name", required = false) String province_name,
                                       @RequestParam(value = "city_id", required = false) String city_id, @RequestParam(value = "district_id", required = false) String district_id,
                                       @RequestParam(value = "city_name", required = false) String city_name, @RequestParam(value = "district_name", required = false) String district_name,
                                       @RequestParam(value = "address", required = false) String address, @RequestParam(value = "contacts_person_phone", required = false) String contacts_person_phone,
                                       @RequestParam(value = "contacts_person", required = false) String contacts_person,@RequestParam(value = "create_by_company_code", required = false) String create_by_company_code) {
        LOGGER.info("供应商确认请求,reject_record_id:{}", reject_record_id);
        RejectRecord rejectRecord = new RejectRecord(reject_record_id, contacts_person, contacts_person_phone, province_id, province_name, city_id, city_name, district_id, district_name, address);
        return goodsRejectService.rejectSupplier(rejectRecord,create_by_company_code);
    }

    @PutMapping("/record/transport/{reject_record_id}")
    @ApiOperation(value = "退供发运")
    @ApiImplicitParam(name = "reject_record_id", value = "退供单id", type = "String")
    public HttpResponse rejectTransport(@PathVariable String reject_record_id,  @RequestBody RejectRecord rejectRecord) {
        rejectRecord.setRejectRecordId(reject_record_id);
        LOGGER.info("退供发运请求,rejectRecord:{}", rejectRecord.toString());
        return goodsRejectService.rejectTransport(rejectRecord);
    }

    @PutMapping("/record/transport/finish/{reject_record_id}")
    @ApiOperation(value = "退供完成")
    @ApiImplicitParam(name = "reject_record_id", value = "退供单id", type = "String")
    public HttpResponse rejectTransportFinish(@PathVariable String reject_record_id) {
        LOGGER.info("提供完成请求,reject_record_id:{}", reject_record_id);
        return goodsRejectService.rejectTransportFinish(reject_record_id);
    }

    @PutMapping("/record/cancel/{reject_record_id}")
    @ApiOperation(value = "取消退供单")
    @ApiImplicitParam(name = "reject_record_id", value = "退供单id", type = "String")
    public HttpResponse rejectCancel(@PathVariable String reject_record_id) {
        LOGGER.info("取消退供单,reject_record_id:{}", reject_record_id);
        return goodsRejectService.rejectCancel(reject_record_id);
    }



    @GetMapping("/record/approval/{approval_code}")
    @ApiOperation(value = "通过审批关联查询退供单详情")
    @ApiImplicitParam(name = "approval_code", value = "审批单code", type = "String")
    public HttpResponse<RejectResponse> applyRejectInfo(@PathVariable String approval_code) {
        LOGGER.info("查询退供单详情请求,reject_record_code:{}", approval_code);
        return goodsRejectService.applyRejectInfo(approval_code);
    }

    @GetMapping("/delete")
    @ApiOperation("删除退供申请单")
    public HttpResponse rejectDelete(@RequestParam("reject_apply_record_code") String rejectApplyRecordCode) {
        return goodsRejectService.rejectDelete(rejectApplyRecordCode);
    }
}
