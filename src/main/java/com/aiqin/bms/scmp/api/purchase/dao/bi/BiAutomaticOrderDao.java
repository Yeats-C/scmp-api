package com.aiqin.bms.scmp.api.purchase.dao.bi;

import com.aiqin.bms.scmp.api.purchase.domain.bi.BiAutomaticOrder;

import java.util.List;

public interface BiAutomaticOrderDao {

    List<BiAutomaticOrder> automaticOrderList(String orderYearMonth);

}