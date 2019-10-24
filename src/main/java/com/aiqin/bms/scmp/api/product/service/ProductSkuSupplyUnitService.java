package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.BaseService;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ConfigSearchVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.QuerySkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.UpdateSkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitCapacityRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.QueryProductSkuSupplyUnitsRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.SkuSupplierDetailRepsVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:32
 */
public interface ProductSkuSupplyUnitService extends BaseService {
    int insertDraftList(List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts);

    int insertList(List<ProductSkuSupplyUnit> productSkuSupplyUnits);

    int saveList(String skuCode,String applyCode);

    int insertApplyList(List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    /**
     * 供应商信息-临时表
     * @param skuCode
     * @return
     */
    List<ProductSkuSupplyUnitRespVo> getDraftList(String skuCode);

    List<ProductSkuSupplyUnitRespVo> getDraftList(List<String> skuCodes);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);
    /**
     * 查询供应商
     * @author NullPointException
     * @date 2019/7/2
     * @param skuCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo>
     */
    List<ProductSkuSupplyUnitRespVo> selectBySkuCode(String skuCode);
    
    /**
     *
     * 功能描述: 根据Id批量查询临时表信息
     *
     * @param ids
     * @return 
     * @auther knight.xie
     * @date 2019/7/4 16:09
     */
    List<ProductSkuSupplyUnitDraft> getDraftByIds(List<Long> ids);

    /**
     *
     * 功能描述: 根据Id批量删除临时表信息
     *
     * @param ids
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:19
     */
    int deleteDraftByIds(List<Long> ids);

    Integer deleteDraftById(Long id);

    /**
     *
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return 
     * @auther knight.xie
     * @date 2019/7/6 22:59
     */
    List<ProductSkuSupplyUnitRespVo> getApply(String skuCode, String applyCode);

    List<ProductSkuSupplyUnitRespVo> getList(String skuCode);

    Integer saveListForChange(List<ApplyProductSkuSupplyUnit> unitList);

    void tobeEffective(List<ApplyProductSkuSupplyUnit> list);

    List<ProductSkuSupplyUnitRespVo> getSupplyList(ConfigSearchVo vo);

    List<ProductSkuSupplyUnitRespVo> getApplyCode(String code);

    List<ProductSkuSupplyUnitRespVo> selectApplyBySkuCode(String skuCode);

    List<ProductSkuSupplyUnitRespVo> selectApplyBySkuCodes(List<String> collect);

    /**
     * SKU供应商管理-前端列表查询接口
     * @param reqVo
     * @return
     */
    BasePage<QueryProductSkuSupplyUnitsRespVo> getListPage(QuerySkuSupplyUnitReqVo reqVo);

    /**
     * SKU供应商管理-前端列表详情接口
     * @param skuCode
     * @return
     */
    SkuSupplierDetailRepsVo detail(String skuCode);

    /**
     * SKU供应商管理-产能
     * @param supplyUnitCode
     * @param productSkuCode
     * @return
     */
    List<ProductSkuSupplyUnitCapacityRespVo> getCapacityInfoBySupplyUnitCodeAndProductSkuCode(String supplyUnitCode,String productSkuCode);

    /**
     * SKU供应商修改
     * @param reqVo
     * @return
     */
    Integer update(UpdateSkuSupplyUnitReqVo reqVo);

    /**
     * SKU供应商管理-前端待审请列表查询接口
     * @param reqVo
     * @return
     */
    BasePage<QueryProductSkuSupplyUnitsRespVo> getDraftListPage(QuerySkuSupplyUnitReqVo reqVo);
}
