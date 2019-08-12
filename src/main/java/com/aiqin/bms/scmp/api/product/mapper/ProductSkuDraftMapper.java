package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.excel.ExportSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductSkuDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuDraft record);

    int insertSelective(ProductSkuDraft record);

    ProductSkuDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuDraft record);

    int updateByPrimaryKey(ProductSkuDraft record);

    List<ProductSkuDraftRespVo> getProductSkuDraftByCompanyCode(@Param("companyCode") String companyCode, @Param("personId") String personId);

    List<ProductSkuDraft> getProductSkuDraftByProductCode(String productCode);

    ProductSkuDraft getOfficialBySkuCode(String skuCode);

    /**
     *
     * 功能描述: 检测品牌在SKU中是否存在
     *
     * @param brandCode
     * @return
     * @auther knight.xie
     * @date 2019/7/19 18:52
     */
    int checkBrand(String brandCode);

    /**
     *
     * 功能描述: 检测品类在SKU中是否存在
     *
     * @param categoryCode
     * @return
     * @auther knight.xie
     * @date 2019/7/19 18:52
     */
    int checkCategory(String categoryCode);

    int checkName(@Param("skuCode") String skuCode,@Param("skuName") String skuName);

    ProductSkuDraft selectProductByFolderCode(@Param(value = "picFolderCode") String folderName);

    /**
     * 查看
     * @param skuCodes
     * @return
     */
    List<ExportSkuInfo> exportSku();
    @MapKey("skuCode")
    Map<String, ProductSkuDraft> selectBySkuCode(@Param("list") Set<String> skuNameList, @Param("companyCode") String companyCode);
}
