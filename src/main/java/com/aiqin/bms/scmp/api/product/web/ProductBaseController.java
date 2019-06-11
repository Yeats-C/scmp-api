package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.common.workflow.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.product.service.impl.ProductSaleAreaServiceImplProduct;
import com.aiqin.bms.scmp.api.product.service.impl.ProductSkuChangePriceServiceImplProduct;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@Slf4j
public class ProductBaseController {

    @Autowired
    private VariablePriceService variablePriceService;

    @Autowired
    private AllocationService allocationService;
    @Autowired
    private ApplyProductService applyProductService;
    @Autowired
    private ProductSkuConfigService productSkuConfigService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private ProductSaleAreaServiceImplProduct saleAreaService;
    @Autowired
    private MovementService movementService;

    @Autowired
    private ProductSkuChangePriceServiceImplProduct productSkuChangePrice;
    @PostMapping("/product/workFlowCallBack/{type}")
    @ApiOperation("审批回调")
    @ResponseBody
    public String workFlowCallBack(@RequestBody WorkFlowCallbackVO vo,
                                   @PathVariable("type") Integer type){
        log.info("BaseController-workFlowCallBack-参数是：[{}],类型是：[{}]", JSONObject.toJSONString(vo),type);
        try {
            switch (type){
                case 1:
                    //return variablePriceService.workFlowCallback(vo);
                case 2:
                    //供应商审批
                case 3:
                    //供货单位审批
                case 4:
                    //供货单位账户的审批
                case 5:
                    //申请制造商的审批
                    //return applyManufacturerService.workFlowCallBack(vo);
                case 6:
                case 7:
                case 8:
                    //调拨单的审批
                    log.info("调拨单的审批");
                    return allocationService.workFlowCallback(vo);
                case 9:
                    log.info("商品申请，sku申请审批");
                    return applyProductService.workFlowCallback(vo);
                case 10:
                    log.info("sku配置申请审批");
                    return productSkuConfigService.workFlowCallback(vo);
                case 11:
                case 12:
                    // 商品变价的审批
                    log.info("商品变价的审批");
                    return productSkuChangePrice.workFlowCallback(vo);
                case 13:
                    // 商品变价的审批
                    log.info("移库申请审批回调接口");
                    return movementService.workFlowCallback(vo);
                case 14:
                    return saleAreaService.workFlowCallback(vo);
                default: return "false";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
