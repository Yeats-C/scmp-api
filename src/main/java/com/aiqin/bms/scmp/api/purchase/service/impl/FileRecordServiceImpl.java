package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.purchase.dao.FileRecordDao;
import com.aiqin.bms.scmp.api.purchase.dao.OperationLogDao;
import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.service.FileRecordService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-26
 **/
@Service
public class FileRecordServiceImpl implements FileRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRecordServiceImpl.class);

    @Resource
    private FileRecordDao fileRecordDao;
    @Resource
    private OperationLogDao operationLogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse deleteFile(FileRecord fileRecord) {
        if (fileRecord == null || StringUtils.isBlank(fileRecord.getFileId())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Integer count = fileRecordDao.update(fileRecord);
        if (count == 0) {
            LOGGER.info("删除采购文件失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse fileList(String fileId) {
        if (StringUtils.isBlank(fileId)) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<FileRecord> files = fileRecordDao.fileList(fileId);
        return HttpResponse.success(files);
    }

    @Override
    public HttpResponse downloadFile(String id, String fileId, String createById, String createByName, String fileName) {
        //增加操作记录 操作状态  : 0 新增 1 修改 2 下载
        operationLogDao.insert(new OperationLog(id, 2, String.format("下载文件,文件名:{%s},文件编码:{%s}", fileName, fileId), "", createById, createByName));
        return HttpResponse.success();
    }
}
