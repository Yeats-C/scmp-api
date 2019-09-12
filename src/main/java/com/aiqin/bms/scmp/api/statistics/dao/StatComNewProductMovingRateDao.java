package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.ProductRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.MovableResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.NewProductMovingRateResponse;

import java.util.List;

public interface StatComNewProductMovingRateDao {

    List<NewProductMovingRateResponse> productMovingSum(ProductRequest request);

    List<MovableResponse> deptList(ProductRequest request);
}