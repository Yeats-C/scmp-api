package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.QueryTagRespVo;
import com.aiqin.bms.scmp.api.supplier.service.TagInfoService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
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
 * @className TagInfoController
 * @date 2019/4/29 16:09

 */

@RestController
@Api(description = "基础资料-标签管理API")
@RequestMapping("/basic/tag")
@Slf4j
public class TagInfoController {

    @Autowired
    private TagInfoService tagInfoService;

    @PostMapping("/list")
    @ApiOperation(value = "获取标签列表信息")
    public HttpResponse<BasePage<QueryTagRespVo>> queryTagInfoList(@RequestBody QueryTagReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/tag/list", JSON.toJSON(reqVo));
            return HttpResponse.success(tagInfoService.getList(reqVo));
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/all")
    @ApiOperation(value = "获取所有标签列表信息")
    public HttpResponse<List<QueryTagRespVo>> queryAllTagInfoList(@RequestParam @NotNull(message = "标签类型编码不能为空") String tagTypeCode) {
        try {
            log.info("request uri:{},参数信息:{}","/basic/tag/all", "无参数");
            return HttpResponse.success(tagInfoService.getAll(tagTypeCode));
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增标签信息")
    public HttpResponse<List<QueryTagRespVo>> addTagInfo(@RequestBody @Validated AddTagReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/tag/add", JSON.toJSON(reqVo));
            return HttpResponse.success(tagInfoService.add(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改标签信息")
    public HttpResponse<Integer> updateTagInfo(@RequestBody @Validated UpdateTagReqVo reqVo){
        try {
            log.info("request uri:{},参数信息:{}","/basic/tag/update", JSON.toJSON(reqVo));
            return HttpResponse.success(tagInfoService.update(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PutMapping("/update/tagUseNum")
    @ApiOperation(value = "修改标签使用数量信息")
    public HttpResponse<Integer> updateTagUserNum(@RequestBody List<UpdateUseNumReqVo> reqVos){
        try {
            log.info("request uri:{},参数信息:{}","/basic/tag/update/tagUseNum", JSON.toJSON(reqVos));
            return HttpResponse.success(tagInfoService.updateUseNum(reqVos));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取标签内信息")
    public HttpResponse<QueryTagRespVo> getTagInfo(@RequestParam @NotNull(message = "主键ID不能为空") Long id){
        try {
            log.info("request uri:{},参数信息:[{}]","/basic/tag/get",id);
            return HttpResponse.success(tagInfoService.view(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save/record")
    @ApiOperation(value = "保存标签使用记录,同一个使用对象只能打一次")
    public HttpResponse<Integer> saveRecordList(@RequestBody List<SaveUseTagRecordReqVo> records){
        try {
            log.info("request uri:{},参数信息:{}","/basic/tag/save/record", JSON.toJSON(records));
            return HttpResponse.success(tagInfoService.saveRecordList(records));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save/record/repeat")
    @ApiOperation(value = "保存标签使用记录,同一个使用对象可以重复打标签")
    public HttpResponse<Integer> saveRecordListRepeat(@RequestBody List<UseTagRecordReqVo> records){
        try {
            log.info("request uri:{},参数信息:{}","/basic/tag/save/record", JSON.toJSON(records));
            return HttpResponse.success(tagInfoService.saveRecordListRepeat(records));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/get/record")
    @ApiOperation(value = "获取标签内信息")
    public HttpResponse<List<DetailTagUseRespVo>> getRecordInfo(@RequestParam @NotNull(message = "使用对象编码不能为空")String useObjectCode,
                                                                @RequestParam @NotNull(message = "标签类型编码不能为空")String tagTypeCode){
        try {
            log.info("request uri:{},参数信息:[{},{}]","/basic/tag/get/record",useObjectCode,tagTypeCode);
            return HttpResponse.success(tagInfoService.getUseTagRecordByUseObjectCode(useObjectCode,tagTypeCode));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error("error", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
