package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileRecordService {

    HttpResponse deleteFile(FileRecord fileRecord);

    HttpResponse fileList(String fileId);

    HttpResponse downloadFile(String id, String fileId, String createById, String createByName,String fileName);

    HttpResponse<String> uploadImageFolder(MultipartFile[] folder);
}
