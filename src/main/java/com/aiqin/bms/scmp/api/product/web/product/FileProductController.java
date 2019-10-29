package com.aiqin.bms.scmp.api.product.web.product;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
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
    public HttpResponse<ProductSkuFileRespVo> loadFileProduct(@RequestParam("skuCode") String skuCode) {
        return HttpResponse.success(productFileService.loadFileProduct(skuCode));
    }

    @PostMapping("/updateoradd")
    @ApiOperation("商品文件编辑或者修改")
    public HttpResponse updateoradd(@RequestBody ProductSkuFile ProductSkuFile) {
        try {
            return HttpResponse.success(productFileService.updateoradd(ProductSkuFile));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/delete/file")
    @ApiOperation("商品文件删除")
    public HttpResponse deleteFile(@RequestParam("id") Long id) {
        try {
            return HttpResponse.success(productFileService.deleteFile(id));
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
            log.info("问题输出:"+e.getLocalizedMessage());
            if (ResultCode.FILE_UPLOAD_ERROR2.getMessage().equals(e.getLocalizedMessage())){
                return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR2);
            }
            if (ResultCode.FILE_UPLOAD_ERROR3.getMessage().equals(e.getLocalizedMessage())){
                return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR3);
            }
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR);
        }

    }
}
