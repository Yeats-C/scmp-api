package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileRecordDao {

    Integer insert(FileRecord record);

    Integer update(FileRecord record);

    List<FileRecord> fileList(String fileId);

    Integer insertAll(@Param("fileId") String fileId,@Param("list") List<FileRecord> list);



}