package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePrice;
import com.aiqin.bms.scmp.api.product.domain.request.variableprice.PriceManagementReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.variableprice.VariablePriceManagementReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.variableprice.PriceManagement;
import com.aiqin.bms.scmp.api.product.domain.response.variableprice.VariablePriceManagementResVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface VariablePriceMapper {
    int deleteByPrimaryKey(Long id);


    int insertSelective(VariablePrice record);

    VariablePrice selectByPrimaryKey(Long id);

    VariablePrice selectByPrimaryCode(@Param("variablePriceCode") String variablePriceCode);

    int updateByPrimaryKeySelective(VariablePrice record);

    int insertList(Collection<VariablePrice> collection);

    List<PriceManagement> getManagement(PriceManagementReqVo priceManagementReqVo);

    List<VariablePriceManagementResVO>getPriceDataList(VariablePriceManagementReqVO variablePriceManagementReqVO);

    VariablePrice variableDetail(@Param("priceTypeCode") String priceTypeCode, @Param("variablePriceCode") String variablePriceCode, @Param("status") Byte status);

    VariablePrice selectByFormNO(@Param("formNo") String formNo);

    Integer getName(@Param("name") String name);


}