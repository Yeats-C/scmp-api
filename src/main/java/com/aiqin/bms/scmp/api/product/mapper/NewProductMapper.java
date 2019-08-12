package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.NewProduct;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryNewProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewSkuDetailsResponseVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NewProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NewProduct record);

    int insertSelective(NewProduct record);

    int insertList(@Param("list") Collection<NewProduct> list);

    int deleteList(@Param("list") Collection<NewProduct> list);

    NewProduct selectByPrimaryKey(Long id);
    @MapKey("productName")
    Map<String,NewProduct> selectBySpuName(@Param("list") Set<String> list, @Param("companyCode") String companyCode);

    NewProduct getProductCode(@Param("productCode") String productCode);

    int updateByPrimaryKeySelective(NewProduct record);

    int updateByPrimaryKey(NewProduct record);

    List<NewProductResponseVO> getList(QueryNewProductReqVO queryNewProductReqVO);

    List<NewSkuDetailsResponseVO> productSku(@Param("productCode") String productCode, @Param("productName") String productName);

    int checkName(@Param("name")String name, @Param("companyCode")String companyCode, @Param("code") String code);
}