package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaForSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.AddSkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.SaveSkuApplyInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaForSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/28 0028 10:46
 */
public interface SkuInfoService{
    /**
     * 新增sku所有信息
     * @param addSkuInfoReqVO
     * @return
     */
    int saveDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO);

    /**
     * 新增sku草稿信息
     * @param productSkuDraft
     * @return
     */
    int insertDraft(ProductSkuDraft productSkuDraft);

    /**
     * 新增sku申请信息
     * @param applyProductSku
     * @return
     */
    int insertApply(ApplyProductSku applyProductSku);

    /**
     * 根据传入的商品编码集合或者sku编码集合新增申请
     * @param saveSkuApplyInfoReqVO
     * @return
     */
    int saveSkuApplyInfo(SaveSkuApplyInfoReqVO saveSkuApplyInfoReqVO);
    /**
     * 根据skucode获取sku相关所有草稿信息
     * @param skuCode
     * @return
     */
    ProductSkuDetailResp getSkuDraftInfo(String skuCode);

    /**
     * 根据skuCode获取sku详情信息
     * @param skuCode
     * @return
     */
    ProductSkuDetailResp getSkuDetail(String skuCode);

    /**
     * 根据分页条件查询sku管理
     * @param querySkuListReqVO
     * @return
     */
    BasePage<QueryProductSkuListResp> querySkuList(QuerySkuListReqVO querySkuListReqVO);

    /**
     * 通过供应商编号查询
     * @param supplyUnitCode
     * @return
     */
    List<QueryProductSkuListResp> querySkuListBySupplyUnitCode(String supplyUnitCode);
    /**
     * 查询待提交商品列表
     * @return
     */
    List<ProductDraftListResp> getProductDraftList();

    /**
     * 查询待提交sku列表
     * @return
     */
    List<ProductSkuDraft> getProductSkuDraftList();

    /**
     * 根据申请编码获取申请单下得商品列表
     * @param applyCode
     * @return
     */
    ApplySkuDetailResp getSkuApplyDetail(String applyCode);

    /**
     * 单个sku申请详情
     * @param skuCode
     * @return
     */
    ApplyProductSkuDetailResp getProductSkuApplyDetail(String skuCode, String applyCode);

    int cancelSkuApply(String applyCode);

    int cancelApply(ApplyProductSku applyProductSku);


    void workFlow(String applyCode, String form, List<ApplyProductSku> applyProductSkus,String directSupervisorCode);

    String skuWorkFlowCallback(WorkFlowCallbackVO vo1);

    /**
     * 根据公司编码获取SKU临时数据
     * @param companyCode
     * @return
     */
    List<ProductSkuDraftRespVo> getProductSkuDraftsByCompanyCode(String companyCode);


    /**
     * 根据条件查询正式SKU信息
     * @param reqVO
     * @return
     */
    List<ProductSkuRespVo> getProductSkuInfos(QuerySkuReqVO reqVO);

    /**
     * 批量删除草稿信息
     * @param skuCodes
     * @return
     */
    Integer deleteProductSkuDraft(List<String> skuCodes);

    /**
     * 根据商品编码获取SKU临时数据
     * @param productCode
     * @return
     */
    List<ProductSkuDraft> getProductSkuDraftsByProductCode(String productCode);
    /**
     * 通过vo查询sku列表
     * @author NullPointException
     * @date 2019/5/28
     * @param vo
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QuerySkuInfoRespVO>
     */
    BasePage<QuerySkuInfoRespVO> getSkuListByQueryVO(QuerySkuInfoReqVO vo);
    /**
     * 不分页
     * @author NullPointException
     * @date 2019/6/6
     * @param vo
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QuerySkuInfoRespVO>
     */
    List<QuerySkuInfoRespVO> getSkuListByQueryNoPage(QuerySkuInfoReqVO vo);
    /**
     * 查询sku直送的供应商
     * @author NullPointException
     * @date 2019/6/5
     * @param collect
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.salearea.QueryProductSaleAreaRespVO>
     */
    List<QueryProductSaleAreaRespVO> selectDirectSupplierBySkuCodes(List<String> collect);
    /**
     * 销售区域查询sku信息
     * @author NullPointException
     * @date 2019/6/6
     * @param reqVO
     * @return com.aiqin.mgs.product.api.base.BasePage<com.aiqin.mgs.product.api.domain.response.salearea.QueryProductSaleAreaForSkuRespVO>
     */
    BasePage<QueryProductSaleAreaForSkuRespVO> selectSkuListForSaleArea(QueryProductSaleAreaForSkuReqVO reqVO);
}
