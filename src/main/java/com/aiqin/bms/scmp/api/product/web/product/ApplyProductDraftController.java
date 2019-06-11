package com.aiqin.bms.scmp.api.product.web.product;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.service.ApplyProductDraftService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(description = "商品草稿接口")
@RequestMapping("/draft/product")
@Slf4j
public class ApplyProductDraftController {
    @Autowired
    private ApplyProductDraftService applyProductDraftService;

    @PostMapping("/save")
    @ApiOperation("新增商品草稿")
    public HttpResponse<String> saveDraftProduct(@RequestBody @Valid NewProductSaveReqVO newProductSaveReqVO) {
        try {
            return HttpResponse.success(applyProductDraftService.insertProduct(newProductSaveReqVO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }

    @PutMapping("/update")
    @ApiOperation("商品草稿修改")
    public HttpResponse<Integer> updateDraftProductt(@RequestBody @Valid NewProductUpdateReqVO newProductUpdateReqVO) {
        try {
            return HttpResponse.success(applyProductDraftService.updateProduct(newProductUpdateReqVO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }

}
