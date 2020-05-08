package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuImportMain;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuImportReq;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaForSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaForSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.file.ProductSkuFileRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 新增sku所有信息
     * @param addSkuInfoReqVO
     * @return
     */
    int importSaveDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO);

    void saveProductSkuChannelDraft(AddSkuInfoReqVO addSkuInfoReqVO, ProductSkuDraft productSkuDraft);

    void saveProductSkuDraft(ProductSkuDraft productSkuDraft, String skuCode);

    /**
     * 更新sku所有信息
     * @param addSkuInfoReqVO
     * @return
     */
    int updateDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO);

    @Transactional(rollbackFor = Exception.class)
    int updateDraftSkuInfoForImport(AddSkuInfoReqVO addSkuInfoReqVO);

    /**
     * 新增sku草稿信息
     * @param productSkuDraft
     * @return
     */
    int insertDraft(ProductSkuDraft productSkuDraft);

    /**
     * 新增sku草稿信息
     * @param productSkuDrafts
     * @return
     */
    int batchInsertDraft(List<ProductSkuDraft> productSkuDrafts);

    /**
     * 新增sku申请信息
     * @param applyProductSku
     * @return
     */
    int insertApply(ApplyProductSku applyProductSku);

    /**
     * 根据传入的商品编码集合或者sku编码集合新增申请
     * @param saveSkuApplyInfoReqVO
     * @param approvalName
     * @param approvalRemark
     * @return
     */
    String saveSkuApplyInfo(SaveSkuApplyInfoReqVO saveSkuApplyInfoReqVO, String approvalName, String approvalRemark);
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
     * 根据条件查询skuCode
     * @param querySkuListReqVO
     * @return
     */
    List<String> querySkuCodeList(QuerySkuListReqVO querySkuListReqVO);

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
    ProductApplyInfoRespVO<ProductSkuApplyVo2> getSkuApplyDetail(String applyCode);

    /**
     * 单个sku申请详情
     * @param skuCode
     * @return
     */
    ProductSkuDetailResp getProductSkuApplyDetail(String skuCode, String applyCode);

    int cancelSkuApply(String applyCode);

    int cancelApply(ApplyProductSku applyProductSku);


    void workFlow(String applyCode, String form, List<ApplyProductSku> applyProductSkus, String directSupervisorCode, String approvalName, String positionCode);

    String skuWorkFlowCallback(WorkFlowCallbackVO vo1);

    /**
     * 保存数据
     * @param applyProductSkus
     */
    void toBeEffective(List<ApplyProductSku> applyProductSkus);

    /**
     * 根据公司编码获取SKU临时数据
     * @param companyCode
     * @return
     */
    List<ProductSkuDraftRespVo> getProductSkuDraftsByCompanyCode(String companyCode,String personId);


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

    Integer deleteProductSkuDraftForPlatform(List<String> skuCodes);

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

    List<Long> getSkuListByQueryNoPageCount(QuerySkuInfoReqVO vo);

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


    /**
     * 查询申请审批列表信息
     * @param reqVo
     * @return
     */
    List<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo);

    /**
     *
     * 功能描述: 更新SKU状态
     *
     * @param respVos
     * @return
     * @auther knight.xie
     * @date 2019/7/18 0:39
     */
    int updateStatus(List<SkuStatusRespVo> respVos);
    /**
     * 通过sku编码查询数据
     * @author NullPointException
     * @date 2019/7/18
     * @param skuList
     * @param companyCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.ProductSku>
     */
    Map<String,ProductSkuInfo> selectBySkuCodes(Set<String> skuList, String companyCode);
    /**
     * 商品导入
     * @author NullPointException
     * @date 2019/7/21
     * @param file
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.request.sku.AddSkuInfoReqVO>
     */
    SkuImportMain importSkuNew(MultipartFile file);

    SkuImportMain importSkuForSupplyPlatform(MultipartFile file);

    /**
     * 通过名称查询
     * @author NullPointException
     * @date 2019/7/21
     * @param skuNameList
     * @param companyCode
     * @return java.util.Map<java.lang.String,com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo>
     */
    Map<String, ProductSkuInfo> selectBySkuNames(Set<String> skuNameList, String companyCode);

    /**
     * 修改导入sku
     * @param file
     * @return
     */
    SkuImportMain importSkuUpdate(MultipartFile file);

    /**
     * 新增保存
     * @return
     */
    Boolean importSkuNewSave(SkuImportReq reqVO);

    Boolean importSkuNewUpdate(SkuImportReq reqVO);

    Boolean exportSku(HttpServletResponse resp);

    /**
     * 通过skuCode导出sku正式数据
     * @param resp
     * @param skuCodeList
     * @return
     */
    Boolean exportFormalSkuBySkuCode(HttpServletResponse resp, List<String> skuCodeList);

    Boolean importSkuUpdateForSupplyPlatform(SkuImportReq reqVO);

    int saveDraftSkuInfoForPlatform(AddSkuInfoReqVO reqVO);

    DetailRequestRespVo getInfoByForm(String formNo);

    BasePage<ProductSkuDraftRespVo> getProductSkuDraftList(QuerySkuDraftListReqVO reqVO);

    /**
     * 带条件获取商品临时表，不分页
     * @param reqVO
     * @return
     */
    List<ProductSkuDraftRespVo> getProductSkuDraftListNoPage(QuerySkuDraftListReqVO reqVO);

    /**
     * 查未同步的
     * @return
     */
    List<ApplyProductSku> selectUnSynData();

    /**
     * 导出新增的sku
     */
    Boolean exportAddSku(HttpServletResponse resp, String applyCode);

    Boolean exportEditSku(HttpServletResponse resp, String applyCode);

    Integer reUpdateApply(String formNo);

    Integer saveDraftSkuInfo2(AddSkuInfoReqVO addSkuInfoReqVO);
}
