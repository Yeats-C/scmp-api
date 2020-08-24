package com.aiqin.bms.scmp.api.abutment.dao;

import com.aiqin.bms.scmp.api.abutment.domain.DlOrderBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DlOrderBillDao {

    Integer insert(DlOrderBill record);

    Integer update(DlOrderBill record);

    DlOrderBill selectByCode(DlOrderBill info);

    List<DlOrderBill> selectByCodes(@Param("list")List<String> list);

    List<DlOrderBill> pullOrderDl(@Param("day") String day);
}