package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.Stocktaking.StocktakingInfo;
import com.aiqin.bms.scmp.api.product.domain.Stocktaking.StocktakingProductInfo;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.SelectStocktakingRequest;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.SelectStocktakingWholeRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StocktakingDao {
	
	void addStocktakingInfo(StocktakingInfo info) throws Exception;

	void addProductInfo(StocktakingProductInfo productInfo)throws Exception;

	List<StocktakingInfo> selectStocktakingList(SelectStocktakingRequest param)throws Exception;

	Integer countStocktaking(SelectStocktakingRequest param)throws Exception;

	StocktakingInfo selectStocktaking(SelectStocktakingWholeRequest param)throws Exception;

	List<StocktakingProductInfo> selectProductList(SelectStocktakingWholeRequest param)throws Exception;
	
	Integer countProductList(SelectStocktakingWholeRequest param)throws Exception;

	void updateDelFlag(@Param("stocktakingProductId") String stocktakingProductId)throws Exception;
}