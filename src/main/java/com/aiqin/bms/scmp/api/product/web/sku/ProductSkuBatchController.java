package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSkuBatchReq;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSkuBatchReq2;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ocenter.QueryCenterSkuListReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.OmsProductSkuListReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.QuerySkuListPageReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOmsSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOrderReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.CheckPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.QueryPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBatchRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuResponse;
import com.aiqin.bms.scmp.api.product.domain.response.sku.QueryProductSkuListResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.merchant.MerchantSkuItemRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ocenter.QueryCenterSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.OmsProductListItemResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.OmsProductSkuItemResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.QuerySkuListResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.SupervisoryWarehouseSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.*;
import com.aiqin.bms.scmp.api.product.service.InspectionReportService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuBatchService;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo2;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 15:59
 */
@RestController
@Api(description="商品批次管理api")
@RequestMapping("/product/batch")
@Slf4j
public class ProductSkuBatchController {
    @Resource
    private SkuService skuService;
    @Resource
    private ProductSkuBatchService productSkuBatchService;

    @PostMapping("/list")
    @ApiOperation("查询商品批次列表")
    public HttpResponse<BasePage<ProductSkuBatchRespVo>> getList(@RequestBody  QueryProductSkuBatchReqVO queryProductSkuBatchReqVO){

        try {
            return HttpResponse.success(productSkuBatchService.getList(queryProductSkuBatchReqVO));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @PostMapping("/getWarehousetList")
    @ApiOperation("查询商品仓库名称")
    public HttpResponse<List<WarehouseDTO>> getWarehousetList(){

        try {
            return HttpResponse.success(productSkuBatchService.getWarehousetList());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @GetMapping("/banById")
    @ApiOperation("根据id来禁用")
    public HttpResponse<Boolean> banById(Long id){
        try {
            return HttpResponse.success(productSkuBatchService.banById(id));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    @PostMapping("/add")
    @ApiOperation("增加")
    public HttpResponse<Boolean> add(@RequestBody ProductSkuBatchReq2 productSkuBatchReq2){

        try {
            return HttpResponse.success(productSkuBatchService.add(productSkuBatchReq2));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
