package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuManufacturerImportMain;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturer;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ProductSkuManufacturerListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ProductSkuManufacturerReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuManufacturerDetailRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuManufacturerRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuManufacturerService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @date 2019/11/19
 */

@RestController
@Api(tags="sku商品制造商管理")
@RequestMapping("/sku/manufacturer")
@Slf4j
public class SkuManufacturerController {
    @Resource
    private ProductSkuManufacturerService productSkuManufacturerService;

    @PostMapping("/list")
    @ApiOperation("商品制造商管理列表")
    public HttpResponse<BasePage<ProductSkuManufacturerRespVo>> getList(@RequestBody ProductSkuManufacturerReqVO productSkuManufacturerReqVO) {
        return HttpResponse.successGenerics( productSkuManufacturerService.getList(productSkuManufacturerReqVO));
    }

    @GetMapping("/detail")
    @ApiOperation("商品制造商详情")
    public HttpResponse<ProductSkuManufacturerDetailRespVo> getDetail(@RequestParam String skuCode) {
        return HttpResponse.successGenerics( productSkuManufacturerService.getDetail(skuCode));
    }

    @DeleteMapping("/deleteById")
    @ApiOperation("通过id删除")
    public HttpResponse deleteById(@RequestParam String id) {
        try {
            return HttpResponse.successGenerics(productSkuManufacturerService.deleteById(id));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save")
    @ApiOperation("保存商品制造商信息")
    public HttpResponse save(@RequestBody ProductSkuManufacturerListReqVO productSkuManufacturerListReqVO) {
        List<ProductSkuManufacturer> manufacturerList = productSkuManufacturerListReqVO.getManufacturerList();
        log.info("SkuInfoController---save---入参：[{}]", JSON.toJSONString(manufacturerList));
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (currentAuthToken == null) {
            throw new BizException(ResultCode.USER_NOT_FOUND);
        }
        if (CollectionUtils.isNotEmptyCollection(manufacturerList)) {
            Date now = new Date();
            manufacturerList.forEach(item -> {
                if(Objects.isNull(item.getCreateTime())) {
                    //新增保存
                    item.setCreateBy(currentAuthToken.getPersonName());
                    item.setCreateTime(now);
                    item.setDelFlag((byte) 0);
                }
                item.setUpdateTime(now);
                item.setUpdateBy(currentAuthToken.getPersonName());
            });
        }
        try {
            return HttpResponse.successGenerics(productSkuManufacturerService.save(manufacturerList));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/import")
    @ApiOperation("导入商品制造商信息")
    public HttpResponse importManufacturer(MultipartFile file) {
        log.info("SkuInfoController---import---入参：[{}]", JSON.toJSONString(file.getOriginalFilename()));
        try {
            return HttpResponse.successGenerics(productSkuManufacturerService.importManufacturer(file));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/import/save")
    @ApiOperation("导入保存商品制造商信息")
    public HttpResponse importManufacturerSave(@RequestBody SkuManufacturerImportMain skuManufacturerImportMain) {
        List<ProductSkuManufacturer> addList = skuManufacturerImportMain.getAddList();
        log.info("SkuInfoController---import/save---入参：[{}]", JSON.toJSONString(addList));
        if (CollectionUtils.isNotEmptyCollection(addList)) {
            Date now = new Date();
            AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
            if (Objects.nonNull(currentAuthToken)) {
                addList.forEach(item -> {
                    item.setCreateTime(now);
                    item.setUpdateTime(now);
                    item.setCreateBy(currentAuthToken.getPersonName());
                    item.setUpdateBy(currentAuthToken.getPersonName());
                    item.setDelFlag((byte) 0);
                });
            }
        }
        try {
            return HttpResponse.successGenerics(productSkuManufacturerService.importManufacturerSave(skuManufacturerImportMain));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.IMPORT_DATA_SAVE_FAILED);
        }
    }

}