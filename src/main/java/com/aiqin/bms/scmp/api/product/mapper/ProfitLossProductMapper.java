package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLoss;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLossProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossDetailRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProfitLossProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProfitLossProduct record);

    int insertSelective(ProfitLossProduct record);

    ProfitLossProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProfitLossProduct record);

    int updateByPrimaryKey(ProfitLossProduct record);

    List<ProfitLossProduct> getListByOrderCode(String orderCode);

    void insertList(@Param(value = "list") List<ProfitLossDetailRequest> profitLossProductList);

    List<ProfitLossProduct> listForSap(SapOrderRequest sapOrderRequest);
}