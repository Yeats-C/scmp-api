package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseFile;

import java.util.List;

public interface PurchaseFileDao {

    Integer insert(PurchaseFile record);

    Integer update(PurchaseFile record);

    List<PurchaseFile> fileList(String purchaseId);

}