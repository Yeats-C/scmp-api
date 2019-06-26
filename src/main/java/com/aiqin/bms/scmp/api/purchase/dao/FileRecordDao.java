package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;

import java.util.List;

public interface FileRecordDao {

    Integer insert(FileRecord record);

    Integer update(FileRecord record);

    List<FileRecord> fileList(String purchaseId);

}