package com.aiqin.bms.scmp.api.product.web.product;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuFileReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.QueryProductSkuListResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.file.ProductSkuFileRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductFileService;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.supplier.service.FileInfoService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@Api(description = "商品文件管理接口")
@RequestMapping("/file/product")
@Slf4j
public class FileProductController {
    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private ProductFileService productFileService;


    @PostMapping("/getList")
    @ApiOperation("商品文件管理列表")
    public HttpResponse<BasePage<QueryProductSkuListResp>> getSkuList(@RequestBody QuerySkuListReqVO querySkuListReqVO) {
        return HttpResponse.success(skuInfoService.querySkuList(querySkuListReqVO));
    }


    @PostMapping("/load")
    @ApiOperation("商品下属文件详情")
    public HttpResponse<ProductSkuRespVo> loadFileProduct(@RequestParam("skuCode") String skuCode) {
        return productFileService.loadFileProduct(skuCode);
    }

    @PostMapping("/updateoradd")
    @ApiOperation("商品文件编辑或者修改")
    public HttpResponse updateOrAdd(@RequestBody ProductSkuFileReqVo ProductSkuFile) {
        try {
            return HttpResponse.success(productFileService.updateOrAdd(ProductSkuFile));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/delete/file")
    @ApiOperation("商品文件删除")
    public HttpResponse deleteFile(@RequestParam("id") Long id,@RequestParam("skuCode")String skuCode ) {
        try {
            return HttpResponse.success(productFileService.deleteFile(id,skuCode));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    @PostMapping("/upload/files")
    @ApiOperation("文件批量上传")
    public HttpResponse<String> uploadFiles(@RequestParam(value="files",required=true) MultipartFile[] multipartFiles,@RequestParam("skuCode")String skuCode) {
        try {
            return productFileService.uploadFiles(multipartFiles,skuCode);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR);
        }

    }
}
