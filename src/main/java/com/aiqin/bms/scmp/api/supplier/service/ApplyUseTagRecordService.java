package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyUseTagRecord;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApplyUseTagRecordService
 * @date 2019/5/5 14:21
 * @description TODO
 */
public interface ApplyUseTagRecordService {

    /**
     * 批量新增
     * @param applyUseTagRecords
     * @return
     */
    int saveBatch(List<ApplyUseTagRecord> applyUseTagRecords);

    /**
     * 删除
     * @param appUseObjectCode
     * @return
     */
    int delete(String appUseObjectCode);

    /**
     * 批量删除
     * @param appUseObjectCodes
     * @return
     */
    int deletes(List<String> appUseObjectCodes);

    /**
     * 批量修改
     * @param applyUseTagRecords
     * @return
     */
    int updateBatch(List<ApplyUseTagRecord> applyUseTagRecords);

    /**
     * 根据申请使用者编号查询
     * @param appUseObjectCode
     * @return
     */
    List<ApplyUseTagRecord> getApplyUseTagRecordByAppUseObjectCode(String appUseObjectCode);
}
