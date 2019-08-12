package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.*;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.SkuStatusRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.DetailConfigSupplierRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigDetailRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:42
 */
public interface ProductSkuConfigService {

    /**
     * 批量保存临时配置信息
     * @param configReqVos
     * @return
     */
    Integer insertDraftList(List<SaveSkuConfigReqVo> configReqVos);
    /**
     * 批量保存导入的临时配置信息
     * @param configReqVos
     * @return
     */
    Integer importSaveDraft(List<SaveSkuConfigReqVo> configReqVos);

    void saveDraftBatch(List<ProductSkuConfigDraft> draftList);

    void deleteByIds(List<Long> ids);

    /**
     * 批量修改临时配置信息
     * @param reqVo
     * @return
     */
    Integer updateDraftList(UpdateSkuConfigSupplierReqVo reqVo);

    /**
     * 批量插入临时配置信息(数据库)
     * @param drafts
     * @return
     */
    Integer insertDraftBatch(List<ProductSkuConfigDraft> drafts);

    /**
     * 保存临时配置信息
     * @param draft
     * @return
     */
    Integer insertDraft(ProductSkuConfigDraft draft);


    /**
     * 查询临时表配置信息,排除掉不显示(从SKU新增来的数据)
     * @param companyCode
     * @return
     */
    DetailConfigSupplierRespVo findDraftList(String companyCode);

    /**
     * 删除临时表配置信息
     * @param id
     * @return
     */
    Integer deleteDraftById(Long id);


    /**
     * 根据SkuCodes批量删除
     * @param skuCodes
     * @return
     */
    Integer deleteDraftBySkuCodes(List<String> skuCodes);

    /**
     * 保存申请信息
     * @param reqVo
     * @return
     */
    Integer insertApplyList(ApplySkuConfigReqVo reqVo);

    /**
     * 外部调用保存到申请列表,不进入审批流
     * @param applyProductSkus
     * @return
     */
    Integer outInsertApplyList( List<ApplyProductSku> applyProductSkus);
    /**
     * 审批流调入
     * @param formNo
     * @param applyCode
     * @param userName
     */
    void workFlow(String formNo, String applyCode, String userName,String directSupervisorCode);



    /**
     * 更新申请表审批状态
     * @param newVO
     */
    void updateApplyInfoByVO(WorkFlowCallbackVO newVO,String applyCode);

    /**
     * 审批流-审批通过
     * @param newVO
     * @param list
     */
    void saveOfficial(WorkFlowCallbackVO newVO, List<ApplyProductSkuConfig> list);


    /**
     * 批量新增正式表数据
     * @param configs
     * @return
     */
    Integer insertBatch(List<ProductSkuConfig> configs);

    /**
     * 批量修改正式表数据
     * @param configs
     * @return
     */
    Integer updateBatch(List<ProductSkuConfig> configs);

    /**
     * 根据FormNo根据申请数据状态
     * @param req
     * @return
     */
    Integer updateApplyInfoByVO(ApplyProductSkuConfigReqVo req);

    /**
     *
     * 功能描述: 根据申请编码更新状态
     *
     * @param req
     * @return
     * @auther knight.xie
     * @date 2019/7/4 22:42
     */
    Integer updateInfoByVo(ApplyProductSkuConfigReqVo req);

    /**
     * 查询正式表信息
     * @param reqVo
     * @return
     */
    BasePage<SkuConfigsRepsVo> findList(QuerySkuConfigReqVo reqVo);


    /**
     * 获取正式表配置详情根据SKU编码
     * @param skuCode
     * @return
     */
    SkuConfigDetailRepsVo detail(String skuCode);

    /**
     * 查询申请审批列表信息
     * @param reqVo
     * @return
     */
    List<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo);

    /**
     * 根据申请编码查询申请详情
     * @param code
     * @return
     */
    ProductApplyInfoRespVO<SkuConfigDetailRepsVo> applyView(String code);

    /**
     * 申请取消
     * @param applyCode
     * @return
     */
    Integer cancelApply(String applyCode);


    /**
     * 批量保存临时备用仓库信息
     * @param spareWarehouseReqVos
     * @return
     */
    Integer insertSpareWarehouseDraftList(List<ProductSkuConfigSpareWarehouseDraft> spareWarehouseReqVos);

    /**
     * 批量保存备用仓库信息
     * @param skuConfigSpareWarehouses
     * @return
     */
    Integer insertSpareWarehouseList(List<ProductSkuConfigSpareWarehouse> skuConfigSpareWarehouses);


   /**
    *
    * 功能描述: 获取临时表数据根据SkuCode
    *
    * @param skuCode
    * @return
    * @auther knight.xie
    * @date 2019/7/5 20:10
    */
    List<SkuConfigsRepsVo> draftDetail(String skuCode);

    /**
     *
     * 功能描述: 根据配置信息计算SKU状态和销售状态
     *
     * @param skuConfigsRepsVos
     * @return
     * @auther knight.xie
     * @date 2019/7/6 19:44
     */
    SkuStatusRespVo calculationSkuStatus(List<SkuConfigsRepsVo> skuConfigsRepsVos);

    /**
     *
     * 功能描述: 获取申请表数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:33
     */
    List<SkuConfigsRepsVo> getApply(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 获取正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:39
     */
    List<SkuConfigsRepsVo> getList(String skuCode);


    /**
     *
     * 功能描述: 保存到正式表
     *
     * @param skuCode
     * @param applyCode
     * @param workFlowCallbackVO
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:00
     */
    Integer saveList(WorkFlowCallbackVO workFlowCallbackVO, String skuCode, String applyCode);
    /**
     * 配置导入
     * @author NullPointException
     * @date 2019/7/18
     * @param file
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo>
     */
    List<SaveSkuConfigReqVo> importData(MultipartFile file,String purchaseGroupCode);
    /**
     * 导入供应商配置
     * @author NullPointException
     * @date 2019/7/19
     * @param file
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft>
     */
    List<ProductSkuSupplyUnitDraft> importSupplyData(MultipartFile file,String purchaseGroupCode);
    /**
     * 供应商配置导入保存
     * @author NullPointException
     * @date 2019/7/19
     * @param reqVo
     * @return java.lang.Boolean
     */
    Boolean saveImportSupply(List<ProductSkuSupplyUnitDraft> reqVo);

    DetailRequestRespVo getInfoByForm(String formNo);
}
