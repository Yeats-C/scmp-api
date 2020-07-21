package com.aiqin.bms.scmp.api.abutment.dao;

import com.aiqin.bms.scmp.api.abutment.domain.DlStoreInfo;

public interface DlStoreInfoDao {

    Integer insert(DlStoreInfo record);

    Integer update(DlStoreInfo record);

    DlStoreInfo selectStoreInfo(String storeCode);

}