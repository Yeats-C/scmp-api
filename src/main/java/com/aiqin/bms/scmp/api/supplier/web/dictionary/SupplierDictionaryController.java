package com.aiqin.bms.scmp.api.supplier.web.dictionary;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.request.dictionary.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.DictionaryType;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryCodeResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryDetailResVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryListResVO;
import com.aiqin.bms.scmp.api.supplier.service.SupplierDictionaryService;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author dixin
 * @data 2018/11/30 18点17分
 */
@RestController
@RequestMapping("/dictionary")
@Api(description = "供应商字典")
public class SupplierDictionaryController {


    @Autowired
    private SupplierDictionaryService supplierDictionaryService;

    @PostMapping("/batchInsert")
    @ApiOperation("批量新增")
    public HttpResponse<Integer> batchInsert(@RequestBody  @Validated DictionarySaveReqDTO dictionarySaveReqDTO) {
            return supplierDictionaryService.batchInsert(dictionarySaveReqDTO);
    }

    @PutMapping("/batchUpdate")
    @ApiOperation("批量修改")
    public HttpResponse<Integer> batchUpdate(@RequestBody @Validated DictionaryUpdateReqDTO supplierDictionaryReqDTO) {
            return supplierDictionaryService.batchUpdate(supplierDictionaryReqDTO);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "删除的字典id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "dictionaryType", value = "supplyChain->供应链,logistics->物流，商品，purchase->采购", required = true, paramType = "query")
    })
    public HttpResponse<Integer>  deleteSupplierDictionary(Long id, String dictionaryType) {
            return supplierDictionaryService.offOrOn(id, dictionaryType);
    }

    @PutMapping("/enabled")
    @ApiOperation("启用或禁止")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryType", value = "supplyChain->供应链,logistics->物流，商品，purchase->采购", required = true, paramType = "query")
    })
    public HttpResponse<Integer> enabledSupplierDictionary(@RequestBody @Validated EnabledSave enabledSave, String dictionaryType) {
            return supplierDictionaryService.enabled(enabledSave, dictionaryType);
    }

    @GetMapping("/info")
    @ApiOperation("根据id查询详细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询的字典id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "dictionaryType", value = "supplyChain->供应链,logistics->物流，商品，purchase->采购", required = true, paramType = "query")
    })
    public HttpResponse<DictionaryDetailResVO> getListDictionary(Long id,String dictionaryType) {
            return supplierDictionaryService.getListDictionary(id, dictionaryType);
    }

    @PostMapping("/list")
    @ApiOperation("查询供应商字典")
    public HttpResponse<BasePage<DictionaryListResVO>> listSupplierDicionary(@RequestBody QueryDictionaryReqVO queryDictionaryReqVO) {
        try {
            return supplierDictionaryService.listDictionary(queryDictionaryReqVO, queryDictionaryReqVO.getDictionaryType());
        } catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.SUPPLIER_API, -1, ex.getMessage()));
        }
    }
    @GetMapping("/type")
    @ApiOperation("字典类型")
    public HttpResponse<List<DictionaryType>>typeList(){
           return HttpResponse.success(supplierDictionaryService.getType());
    }

    @PostMapping("/infoList")
//    @ApiOperation("根据dictionaryName查询详细--->前端使用")
    @ApiOperation("根据dictionaryCode查询详细--->前端使用")
    public HttpResponse<List<DictionaryCodeResVo>> getDictionaryName(@RequestBody DictionaryInfoReqVO dictionaryInfoReqVO){
        return  HttpResponse.success(supplierDictionaryService.getCode(dictionaryInfoReqVO,dictionaryInfoReqVO.getDictionaryType()));
    }
    @PostMapping("/infoList/getDictionary")
//    @ApiOperation("根据dictionaryName查询详细--->后端使用")
    @ApiOperation("根据dictionaryCode查询详细--->后端使用")
    public HttpResponse<List<DictionaryCodeResVo>> dictionaryName(@RequestBody DictiReVo dictiReVo){
        String dictionaryType=dictiReVo.getDictionaryType();
        DictionaryInfoReqVO dictionaryInfoReqVO=new DictionaryInfoReqVO();
        dictionaryInfoReqVO.setDictionaryCode(dictiReVo.getDictionaryCode());
        dictionaryInfoReqVO.setDictionaryName(dictiReVo.getDictionaryName());
        dictionaryInfoReqVO.setDictionaryType(dictionaryType);
        return  HttpResponse.success(supplierDictionaryService.getCode(dictionaryInfoReqVO,dictionaryType));
    }

}
