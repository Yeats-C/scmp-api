package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionaryInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionarySaveReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionaryUpdateReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.QueryDictionaryReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryCodeResVo;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryListResVO;
import com.aiqin.bms.scmp.api.product.service.ProductDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product/dictionary")
@Api(description = "商品字典")
public class ProductDictionaryController {

    @Autowired
    private ProductDictionaryService productDictionaryService;

    @PostMapping("/batchInsert")
    @ApiOperation("批量新增")
    public HttpResponse batchInsert(@RequestBody DictionarySaveReqDTO productDictionarySaveReqVO) {
        try {
            return HttpResponse.success(productDictionaryService.batchInsert(productDictionarySaveReqVO));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }

    @PutMapping("/batchUpdate")
    @ApiOperation("批量修改")
    public HttpResponse batchUpdate(@RequestBody DictionaryUpdateReqDTO productDictionaryReqDTO) {
        try {
            return HttpResponse.success(productDictionaryService.batchUpdate(productDictionaryReqDTO));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除")
    public HttpResponse deleteSupplierDictionary(@PathVariable("id") Long id) {
        try {
            return HttpResponse.success(productDictionaryService.offOrOn(id));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }

    @PutMapping("/enabled/{id}")
    @ApiOperation("启用或禁止")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "status", value = "0 启用/1 禁用", required = true, paramType = "query")
    })
    public HttpResponse enabledSupplierDictionary(@PathVariable("id") Long id, String status) {
        try {
            return HttpResponse.success(productDictionaryService.enabled(id, Byte.valueOf(status)));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }

    @GetMapping("/info/{id}")
    @ApiOperation("根据id查询详细")
    public HttpResponse getListDictionary(@PathVariable("id") Long id) {
        try {
            return HttpResponse.success(productDictionaryService.getListDictionary(id));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }

    @PostMapping("/list")
    @ApiOperation("查询供应商字典列表")
    public HttpResponse<BasePage<DictionaryListResVO>> listSupplierDicionary(@RequestBody QueryDictionaryReqVO queryDictionaryReqVO) {
        try {
            return HttpResponse.success(productDictionaryService.listDictionary(queryDictionaryReqVO));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }

    @PostMapping("/infoCode")
    @ApiOperation("根据dictionaryName查询详细")
    public HttpResponse<DictionaryCodeResVo> getDictionaryName(@RequestBody DictionaryInfoReqVO dictionaryInfoReqVO) {
        try {
            return HttpResponse.success(productDictionaryService.getCode(dictionaryInfoReqVO));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }

}
