package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.QuerySkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.QueryProductSkuSupplyUnitsRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuSupplyUnitDraftMapper {

    Integer delete(List<String> skuCodes);

    List<ProductSkuSupplyUnitDraft> selectByIds(List<Long> ids);

    int deleteDraftByIds(List<Long> ids);

    int deleteDraftById(Long id);
    /**
     * 通过vo查找数据
     * @author NullPointException
     * @date 2019/7/19
     * @param reqVo
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft>
     */
    List<ProductSkuSupplyUnitDraft> selectByVo(List<ProductSkuSupplyUnitDraft> reqVo);

    List<ProductSkuSupplyUnitDraft> selectDraftBySkuCode(String skuCode);

    int deleteBySkuCodeAndSupplierCodes(@Param("skuCode") String skuCode, @Param("deleteCodes")List<String> deleteSupplyUnitCodes);

    List<QueryProductSkuSupplyUnitsRespVo> getListPage(QuerySkuSupplyUnitReqVo reqVo);
}