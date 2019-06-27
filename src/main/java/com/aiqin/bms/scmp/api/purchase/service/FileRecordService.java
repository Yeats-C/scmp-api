package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface FileRecordService {

    HttpResponse deleteFile(FileRecord fileRecord);

    HttpResponse fileList(String fileId);
}
