package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.dto.salearea.ProductSkuSaleAreaMainDraftDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaMain;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaMainDraft;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaMainRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuSaleAreaMainDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSaleAreaMainDraft record);

    int insertSelective(ProductSkuSaleAreaMainDraft record);

    ProductSkuSaleAreaMainDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSaleAreaMainDraft record);

    int updateByPrimaryKey(ProductSkuSaleAreaMainDraft record);

    /**
     * 查询所以有的临时表的数据
     * @author NullPointException
     * @date 2019/6/4
     * @param companyCode
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.salearea.QueryProductSaleAreaRespVO>
     */
    List<QueryProductSaleAreaMainRespVO> selectAllDraftData(String companyCode);
    /**
     * 通过编码查询数据
     * @author NullPointException
     * @date 2019/6/4
     * @param areaCodes
     * @return java.util.List<com.aiqin.mgs.product.api.domain.dto.salearea.ProductSkuSaleAreaMainDraftDTO>
     */
    List<ProductSkuSaleAreaMainDraftDTO> selectDataByCodes(@Param("items") List<String> areaCodes);
    /**
     * 根据编码批量删除
     * @author NullPointException
     * @date 2019/6/4
     * @param codes
     * @return int
     */
    int deleteDraftBatchByCodes(@Param("items") List<String> codes);

    /**
     * 通过名称判重
     * @param name
     * @return
     */
    List<ProductSkuSaleAreaMain> selectByName(String name);
}