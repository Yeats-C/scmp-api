package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.*;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.*;
import com.aiqin.bms.scmp.api.product.service.PriceChannelService;
import com.aiqin.bms.scmp.api.product.service.PriceProjectService;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryInfoResponseVO;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className PriceProjectController
 * @date 2019/4/19 11:42
 * @description 基础资料-价格项目/渠道管理API
 */
@RestController
@Api(description = "基础资料-价格项目/渠道管理API")
@RequestMapping("/basic/price")
@Slf4j
public class PriceProjectController {

    @Autowired
    private PriceProjectService priceProjectService;
    @Autowired
    private PriceChannelService priceChannelService;

    @PostMapping("/project/list")
    @ApiOperation(value = "获取价格项目列表信息")
    public HttpResponse<BasePage<QueryPriceProjectRespVo>> queryProjectList(@RequestBody QueryPriceProjectReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/project/list", JSON.toJSON(reqVo));
            return HttpResponse.success(priceProjectService.getList(reqVo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/project/all")
    @ApiOperation(value = "获取所有价格项目列表信息")
    public  HttpResponse<List<QueryPriceProjectRespVo>> queryAllProjectList() {
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/project/all", "无参数");
            return HttpResponse.success(priceProjectService.getAll());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/project/byType")
    @ApiOperation(value = "获取对应类型的价格项目列表信息")
    public  HttpResponse<List<QueryPriceProjectRespVo>> queryProjectListByType(@RequestParam String type) {
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/project/all", "{}",type);
            return HttpResponse.success(priceProjectService.getByTypeCode(type));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/project")
    @ApiOperation(value = "获取价格项目信息,按照价格类型分组")
    public  HttpResponse<PriceProjetGroupType>  queryProject() {
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/project", "无参数");
            return HttpResponse.success(priceProjectService.queryProjectGroupPriceType());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    @PostMapping("/project/add")
    @ApiOperation(value = "新增价格项目信息")
    public HttpResponse<Integer> addProject(@RequestBody @Validated AddPriceProjectReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/project/add", JSON.toJSON(reqVo));
            return HttpResponse.success(priceProjectService.add(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PutMapping("/project/update")
    @ApiOperation(value = "修改价格项目信息")
    public HttpResponse<Integer> updateProject(@RequestBody @Validated UpdatePriceProjectReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/project/update", JSON.toJSON(reqVo));
            return HttpResponse.success(priceProjectService.update(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/project/get")
    @ApiOperation(value = "获取价格项目信息")
    public HttpResponse<PriceProjectRespVo> getProject(@RequestParam @NotNull(message = "主键ID不能为空") Long id){
        try {
            log.info("request uri:{},参数信息:[{}]","/basic/price/project/get",id);
            return HttpResponse.success(priceProjectService.view(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/channel/all")
    @ApiOperation(value = "获取所有渠道列表信息")
    public HttpResponse<List<QueryPriceChannelRespVo>> queryAllChannelList(){
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/channel/all","无参数");
            return HttpResponse.success(priceChannelService.getAll());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/channel/add")
    @ApiOperation(value = "新增渠道信息")
    public HttpResponse<Integer> addChannel(@RequestBody @Validated AddPriceChannelReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/channel/add", JSON.toJSON(reqVo));
            return HttpResponse.success(priceChannelService.add(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PutMapping("/channel/update")
    @ApiOperation(value = "修改渠道信息")
    public HttpResponse<Integer> updateChannel(@RequestBody @Validated UpdatePriceChannelReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/channel/update", JSON.toJSON(reqVo));
            return HttpResponse.success(priceChannelService.update(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/get/type/category")
    @ApiOperation(value = "获取价格类型/大类（属性）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态（1.类型，2.大类（属性））", type = "Integer"),})
    public HttpResponse<List<DictionaryInfoResponseVO>> selectPriceTypeAndCategory(@RequestParam(value = "status", required = false) Integer status){
        return priceProjectService.selectPriceTypeAndCategory(status);
    }

    @PostMapping("/channel/list")
    @ApiOperation(value = "获取渠道列表信息")
    public HttpResponse<BasePage<QueryPriceChannelRespVo>> queryChannelList(@RequestBody QueryPriceChannelReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/price/channel/list", JSON.toJSON(reqVo));
            return HttpResponse.success(priceChannelService.getList(reqVo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @GetMapping("/channel/get")
    @ApiOperation(value = "获取渠道信息(详情信息)")
    public HttpResponse<PriceChannelRespVo> getChannel(@RequestParam @NotNull(message = "主键ID不能为空") Long id){
        try {
            log.info("request uri:{},参数信息:[{}]","/basic/price/channel/get",id);
            return HttpResponse.success(priceChannelService.view(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/channel/selectByChannelCodes")
    @ApiOperation(value = "通过渠道编码集合查询价格项目")
    public HttpResponse<List<PriceChannelItem>> selectByChannelCodes(@RequestBody List<String> codes){
        log.info("PriceProjectController----selectByChannelCodes uri:{},参数信息:[{}]","/basic/price/channel/selectByChannelCodes",codes.toString());
        try {
            return HttpResponse.success(priceChannelService.selectByChannelCodes(codes));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
