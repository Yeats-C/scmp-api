package com.aiqin.bms.scmp.api.product.web.product;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.ApplyDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryApplyProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductDetailsResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductResponseVO;
import com.aiqin.bms.scmp.api.product.service.ApplyProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(description = "商品申请接口")
@RequestMapping("/apply/product")
@Slf4j
public class ApplyProductController {
    @Autowired
    private ApplyProductService applyProductService;

    //TODO 这个接口没有使用
    @PostMapping("/saveList")
    @ApiOperation("批量新增申请商品")
    public HttpResponse<String> saveDraftProduct(@RequestBody @Valid ApplyDraftReqVO applyDraftReqVO) {
        try {
            return HttpResponse.success(applyProductService.saveList(applyDraftReqVO,20L));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }

    @PostMapping("/getList")
    @ApiOperation("商品申请管理")
    public HttpResponse<BasePage<ApplyProductResponseVO>> getList(@RequestBody QueryApplyProductReqVO queryApplyProductReqVO) {
        try {
            return HttpResponse.success(applyProductService.getList(queryApplyProductReqVO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @GetMapping("/details")
    @ApiOperation("显示申请")
    public HttpResponse<List<ApplyProductDetailsResponseVO>>getApplyProduct(String applyCode){
        try {
            return HttpResponse.success(applyProductService.getApplyProduct(applyCode));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @PostMapping("/priceRevoke/{formNo}")
    @ApiOperation("商品撤销")
    public HttpResponse<Integer>priceRevoke(@PathVariable("formNo") String formNo){
        try {
            return HttpResponse.success(applyProductService.revoke(formNo));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }

}
