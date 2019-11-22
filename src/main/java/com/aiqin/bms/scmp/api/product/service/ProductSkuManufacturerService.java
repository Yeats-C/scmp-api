package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuManufacturerImportMain;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuManufacturer;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturer;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturerDraft;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ProductSkuManufacturerReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.QueryPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuManufacturerDetailRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuManufacturerRespVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:36
 */
public interface ProductSkuManufacturerService {
    int insertDraftList(List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts);
    int insertDraftList(String applyCode);

    int insertList(List<ProductSkuManufacturer> productSkuManufacturers);

    int saveList(String skuCode, String applyCode);

    int insertApplyList(List<ApplyProductSkuManufacturer> applyProductSkuManufacturers);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    List<ProductSkuManufacturerRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     *
     * 功能描述: 申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:11
     */
    List<ProductSkuManufacturerRespVo> getApply(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 正式数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:13
     */
    List<ProductSkuManufacturerRespVo> getList(String skuCode);

    /**
     * 根据条件获取正式数据,商品供应商管理
     * @param productSkuManufacturerReqVO 筛选条件
     * @return
     */
    BasePage<ProductSkuManufacturerRespVo> getList(ProductSkuManufacturerReqVO productSkuManufacturerReqVO);

    /**
     * 获取商品制造详情
     * @param skuCode
     * @return
     */
    ProductSkuManufacturerDetailRespVo getDetail(String skuCode);

    /**
     * 通过id删除
     * @param id
     */
    Boolean deleteById(String id);

    /**
     * 添加
     * @param productSkuManufacturer
     * @return
     */
    Boolean save(List<ProductSkuManufacturer> productSkuManufacturer);

    /**
     * 导入检查
     * @param file
     * @return 列表展示
     */
    SkuManufacturerImportMain importManufacturer(MultipartFile file);
    /**
     * 导入保存
     * @param skuManufacturerImportMain
     * @return 列表展示
     */
    Boolean importManufacturerSave(SkuManufacturerImportMain skuManufacturerImportMain);
}
