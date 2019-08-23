package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.bms.scmp.api.supplier.domain.request.promotesales.QueryPromoteSalesReqVo;
import com.aiqin.bms.scmp.api.supplier.service.ProductSkuPromoteSalesService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuPromoteSalesController
 * @date 2019/7/3 12:52

 */
@RestController
@Api(description = "基础资料-SKU促销管理")
@RequestMapping("/basic/promote/sale")
@Slf4j
public class ProductSkuPromoteSalesController {

    @Autowired
    private ProductSkuPromoteSalesService service;

    @PostMapping("/uploadFile")
    @ApiModelProperty(value = " 文件上传")
    public HttpResponse<Integer> fileUpload(@NotNull MultipartFile file){
        return HttpResponse.success(service.fileUpload(file));
    }

    @PostMapping("/list")
    @ApiModelProperty(value = "列表查询")
    public HttpResponse<Integer> getList(@RequestBody QueryPromoteSalesReqVo reqVo){
        return HttpResponse.success(service.getList(reqVo));
    }

    @DeleteMapping("/delete")
    @ApiModelProperty(value = "删除")
    public HttpResponse<Integer> delete(Long id){
        return HttpResponse.success(service.deleteById(id));
    }

    @DeleteMapping("/delete/all")
    @ApiModelProperty(value = "全部删除")
    public HttpResponse<Integer> deleteAll(){
        return HttpResponse.success(service.deleteAll());
    }

}
