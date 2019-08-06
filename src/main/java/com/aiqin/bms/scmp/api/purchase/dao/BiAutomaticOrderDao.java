package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.BiAutomaticOrder;

import java.util.List;

public interface BiAutomaticOrderDao {

    List<BiAutomaticOrder> automaticOrderList(String orderYearMonth);

}