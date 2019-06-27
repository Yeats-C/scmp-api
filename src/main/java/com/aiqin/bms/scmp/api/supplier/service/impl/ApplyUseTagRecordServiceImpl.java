package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyUseTagRecord;
import com.aiqin.bms.scmp.api.supplier.mapper.ApplyUseTagRecordMapper;
import com.aiqin.bms.scmp.api.supplier.service.ApplyUseTagRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApplyUseTagRecordServiceImpl
 * @date 2019/5/5 14:25
 * @description TODO
 */
@Service
public class ApplyUseTagRecordServiceImpl implements ApplyUseTagRecordService {

    @Autowired
    private ApplyUseTagRecordMapper applyUseTagRecordMapper;
    /**
     * 批量新增
     *
     * @param applyUseTagRecords
     * @return
     */
    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public int saveBatch(List<ApplyUseTagRecord> applyUseTagRecords) {
        return applyUseTagRecordMapper.saveBatch(applyUseTagRecords);
    }

    /**
     * 删除
     *
     * @param appUseObjectCode
     * @return
     */
    @Override
    public int delete(String appUseObjectCode) {
        return applyUseTagRecordMapper.delete(appUseObjectCode);
    }

    /**
     * 批量删除
     *
     * @param appUseObjectCodes
     * @return
     */
    @Override
    public int deletes(List<String> appUseObjectCodes) {
        return applyUseTagRecordMapper.deletes(appUseObjectCodes);
    }

    /**
     * 批量修改
     *
     * @param applyUseTagRecords
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(List<ApplyUseTagRecord> applyUseTagRecords) {
        return applyUseTagRecordMapper.updateBatch(applyUseTagRecords);
    }

    /**
     * 根据申请使用者编号查询
     *
     * @param appUseObjectCode
     * @return
     */
    @Override
    public List<ApplyUseTagRecord> getApplyUseTagRecordByAppUseObjectCode(String appUseObjectCode) {
        return applyUseTagRecordMapper.getApplyUseTagRecordByAppUseObjectCode(appUseObjectCode);
    }
}
