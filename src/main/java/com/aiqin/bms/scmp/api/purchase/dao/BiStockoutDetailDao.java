package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.BiStockoutDetail;

import java.util.List;

public interface BiStockoutDetailDao {

    List<BiStockoutDetail> stockOutList(String beginTime, String finishTime);

    List<String> stockOutBySkuCount();
}