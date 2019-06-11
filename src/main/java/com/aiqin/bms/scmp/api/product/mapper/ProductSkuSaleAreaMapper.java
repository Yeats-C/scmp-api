package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleArea;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaSkuRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductSkuSaleAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSaleArea record);

    int insertSelective(ProductSkuSaleArea record);

    ProductSkuSaleArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSaleArea record);

    int updateByPrimaryKey(ProductSkuSaleArea record);
    /**
     * 批量插入
     * @author NullPointException
     * @date 2019/6/5
     * @param skuList
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuSaleArea> skuList);
    /**
     * 批量删除
     * @author NullPointException
     * @date 2019/6/5
     * @param codes
     * @return int
     */
    int deleteByCodes(@Param("items") Set<String> codes);
    /**
     * 查询销售区域sku的列表
     * @author NullPointException
     * @date 2019/6/6
     * @param reqVO
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.salearea.QueryProductSaleAreaSkuRespVO>
     */
    List<QueryProductSaleAreaSkuRespVO> officialSkuList(QueryProductSaleAreaReqVO reqVO);
}