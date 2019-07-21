package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryProductBrandRespVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductBrandTypeDao {
    List<ProductBrandType> selectAllBrand();

    List<ProductBrandType> selectAllTopBrand();

    ProductBrandType selectBrandInfoBy(String brandId);
    /**
     * 插入操作
     * @author zth
     * @date 2018/12/13
     * @param type
     * @return int
     */
    Integer insertBrand(ProductBrandType type);
    /**
     * 列表数据
     * @author zth
     * @date 2018/12/13
     * @param vo
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.QueryProductBrandRespVO>
     */
    List<QueryProductBrandRespVO> selectListByQueryVO(QueryProductBrandReqVO vo);
    /**
     * 通过id查找
     * @author zth
     * @date 2018/12/13
     * @param id
     * @return com.aiqin.mgs.product.api.domain.ProductBrandType
     */
    ProductBrandType selectByPrimaryKey(Long id);
    /**
     * 非空更新
     * @author zth
     * @date 2018/12/13
     * @param productBrandType
     * @return int
     */
    Integer updateByPrimaryKeySelective(ProductBrandType productBrandType);
    /**
     * 通过品牌名称查询
     * @author zth
     * @date 2018/12/26
     * @param brandName
     * @param companyCode
     * @return com.aiqin.mgs.product.api.domain.ProductBrandType
     */
    ProductBrandType selectByBrandName(@Param("brandName") String brandName, @Param("companyCode") String companyCode);
    /**
     * 通过品牌编码集合查询品牌集合
     * @author zth
     * @date 2018/12/29
     * @param codes
     * @return java.util.List<com.aiqin.mgs.product.api.domain.ProductBrandType>
     */
    List<ProductBrandType> selectByBrandCodes(@Param("codes") List<String> codes);
    @MapKey("brandName")
    Map<String, ProductBrandType> selectByBrandNames(@Param("list") Set<String> brandNameList, @Param("companyCode") String companyCode);
}