package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.dto.salearea.ProductSkuSaleAreaMainDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaMain;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaMainReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.ProductSaleAreaForOfficialMainRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaMainRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductSkuSaleAreaMainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSaleAreaMain record);

    int insertSelective(ProductSkuSaleAreaMain record);

    ProductSkuSaleAreaMain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSaleAreaMain record);

    int updateByPrimaryKey(ProductSkuSaleAreaMain record);
    /**
     * 批量插入
     * @author NullPointException
     * @date 2019/6/5
     * @param main
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuSaleAreaMain> main);
    /**
     * 通过编码集合查询数据
     * @author NullPointException
     * @date 2019/6/5
     * @param codes
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ProductSkuSaleAreaMain>
     */
    List<ProductSkuSaleAreaMainDTO> selectListByCodes(@Param("items") Set<String> codes);
    /**
     * 批量更新数据
     * @author NullPointException
     * @date 2019/6/5
     * @param main
     * @return int
     */
    int updateByCode(@Param("items") List<ProductSkuSaleAreaMain> main);
    /**
     * 销售区域列表查询
     * @author NullPointException
     * @date 2019/6/5
     * @param request
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.salearea.QueryProductSaleAreaMainRespVO>
     */
    List<QueryProductSaleAreaMainRespVO> selectListByQueryVo(List<Long> ids);
    /**
     * 销售区域列表查询
     * @author NullPointException
     * @date 2019/6/5
     * @param request
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.salearea.QueryProductSaleAreaMainRespVO>
     */
    List<Long> selectListByQueryVoCount(QueryProductSaleAreaMainReqVO request);
    /**
     * 编码查询详情
     * @author NullPointException
     * @date 2019/6/5
     * @param code
     * @return com.aiqin.mgs.product.api.domain.response.salearea.ProductSaleAreaForOfficialMainRespVO
     */
    ProductSaleAreaForOfficialMainRespVO selectDetailByCode(String code);
    /**
     * 根据生效状态日志查询未生效的数据
     * @author NullPointException
     * @date 2019/6/6
     * @param
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ProductSkuSaleAreaMain>
     */
    List<ProductSkuSaleAreaMain> selectListByStatusAndDate();
}