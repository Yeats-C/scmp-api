package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePriceInfo;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QueryProductSkuChangePriceReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QueryProductSkuChangePriceRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuChangePriceInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuChangePriceInfo record);

    int insertSelective(ProductSkuChangePriceInfo record);

    ProductSkuChangePriceInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuChangePriceInfo record);

    int updateByPrimaryKey(ProductSkuChangePriceInfo record);
    /**
     * 批量保存数据
     * @author NullPointException
     * @date 2019/5/22
     * @param infoList
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuChangePriceInfo> infoList);
    /**
     * 通过编码删除数据
     * @author NullPointException
     * @date 2019/5/23
     * @param code
     * @return int
     */
    int deleteByCode(String code);
    /**
     * 列表查询
     * @author NullPointException
     * @date 2019/5/23
     * @param reqVO
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QueryProductSkuChangePriceRespVO>
     */
    List<QueryProductSkuChangePriceRespVO> selectListByQueryVO(QueryProductSkuChangePriceReqVO reqVO);
    /**
     * 批量更新数据
     * @author NullPointException
     * @date 2019/5/24
     * @param infoList
     * @return int
     */
    int updateBatch(@Param("items") List<ProductSkuChangePriceInfo> infoList);
}