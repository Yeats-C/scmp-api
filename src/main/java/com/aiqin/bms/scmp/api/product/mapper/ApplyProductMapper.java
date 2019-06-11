package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProduct;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryApplyProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductDetailsResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductResponseVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ApplyProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProduct record);

    int insertSelective(ApplyProduct record);

    ApplyProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProduct record);

    int updateByPrimaryKey(ApplyProduct record);

    Integer getName(@Param("proName") String proName);

    Integer insertList(@Param("list") Collection<ApplyProduct> applyProducts);

    List<ApplyProductResponseVO>getList(QueryApplyProductReqVO queryApplyProductReqVO);


    List<ApplyProductDetailsResponseVO> getApplyProduct(@Param("applyCode") String applyCode);


    List<ApplyProduct>getApplyCode(@Param("applyCode") String applyCode);

    String getFromNo(@Param("applyCode") String applyCode);


    int updateList(@Param("list") Collection<ApplyProduct> list);

    List<ApplyProduct>selectByFormNO(@Param("formNo") String formNo);

    /**
     * 修改申请中的状态
     * @param applyStatus
     * @param formNo
     * @return
     */
  int updateStatusByFormNo(@Param("applyStatus") byte applyStatus, @Param("formNo") String formNo);

    List<ApplyProduct> getProductApplyList(@Param("productCodes") List<String> productCodes, @Param("number") Byte number);
}