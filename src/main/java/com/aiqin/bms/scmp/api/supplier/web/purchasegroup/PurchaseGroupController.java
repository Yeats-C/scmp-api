package com.aiqin.bms.scmp.api.supplier.web.purchasegroup;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.request.dictionary.EnabledSave;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.PurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.QueryPurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.UpdatePurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.UserPositionsRequest;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.QueryPurchaseGroupResVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 描述:采购管理组管理控制器
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@RestController
@Api(description = "基础数据采购管理组管理")
@RequestMapping("/purchaseGroup")
public class PurchaseGroupController {

    @Autowired
    private PurchaseGroupService purchaseGroupService;

    /**
     * 分页获取采购管理组列表
     * @param vo
     * @return
     */
    @ApiOperation("查询采购管理组List")
    @PostMapping("/getPurchaseGroupList")
    public HttpResponse<BasePage<QueryPurchaseGroupResVo>> getPurchaseGroupList(@RequestBody @Valid QueryPurchaseGroupReqVo vo) {
        return HttpResponse.success(purchaseGroupService.findPurchaseGroupList(vo));
    }


    /**
     * 新增采购组
     * @param purchaseGroupReqVo
     * @return
     */
    @ApiOperation("新增采购组")
    @PostMapping("/savePurchaseGroup")
    public HttpResponse<Integer> savePurchaseGroup(@RequestBody  @Valid PurchaseGroupReqVo purchaseGroupReqVo){
        return purchaseGroupService.savePurchaseGroup(purchaseGroupReqVo);
    }

    /**
     * 通过id获取采购组详情
     * @param id
     * @return
     */
    @ApiOperation("通过id获取采购组详情")
    @GetMapping("/getPurchaseGroup")
    public HttpResponse<PurchaseGroupResVo> findPurchaseGroupDetail(@RequestParam  @ApiParam( value = "传入主键id" ,required = true) Long id){
        try {
            return HttpResponse.success(purchaseGroupService.findPurchaseGroupDetail(id));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    /**
     * 修改采购组
     * @param updatePurchaseGroupReqVo
     * @return
     */
    @ApiOperation("修改采购管理组")
    @PutMapping("/updatePurchaseGroup")
    public HttpResponse<Integer> updatePurchaseGroup(@RequestBody @Valid UpdatePurchaseGroupReqVo updatePurchaseGroupReqVo){
            return purchaseGroupService.updatePurchaseGroup(updatePurchaseGroupReqVo);
    }


    /**
     * 提供未禁用的采购组
     * @return
     */
    @ApiOperation("采购组外部提供接口")
    @PostMapping("/getPurchaseGroupApi")
    public HttpResponse<List<PurchaseGroupVo>> getPurchaseGroup(String name){
        try{
            return HttpResponse.success(purchaseGroupService.getPurchaseGroup(name));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
    }

    /**
     * 获取外购专员
     * @param
     * @return
     */
    @ApiOperation("获取外购专员")
    @GetMapping("/getPurchaseGroupBuyerList")
    public HttpResponse getPurchaseGroupBuyerList( UserPositionsRequest  userPositionsRequest) {
        return purchaseGroupService.getPurchaseGroupBuyerList(userPositionsRequest);
    }

    @PutMapping("/enabled")
    @ApiOperation("启用或禁止")
    public HttpResponse<Integer> enabledSupplierDictionary(@RequestBody @Validated EnabledSave enabledSave) {
        try {
            return HttpResponse.success(purchaseGroupService.enabled(enabledSave));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
