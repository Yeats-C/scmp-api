package com.aiqin.bms.scmp.api.product.service.Stocktaking;


import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.SelectStocktakingRequest;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.SelectStocktakingWholeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.StocktakingWhole;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVoPage;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import javax.validation.Valid;

/**
 * Createed by hzy on 2019/03/18.<br/>
 * 盘点
 */
public interface StocktakingService {

	//添加盘点数据
	HttpResponse addStocktakingWhole(StocktakingWhole param);

	//查询库存
	HttpResponse searchInventories(ProductDistributorQuVoPage param);

	//查询盘点
	HttpResponse selectStocktaking(SelectStocktakingRequest param);

	//查询盘点详情
	HttpResponse selectStocktakingWhole(SelectStocktakingWholeRequest param);

	//更新商品信息删除标识
	HttpResponse updateDelFlag(@Valid String stocktakingProductId);
//
//	//查询盘点商品列表
//	HttpResponse<StocktakingProductInfo> selectProduct(SelectProductRequest param);

}
