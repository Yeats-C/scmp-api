package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.mapper.EncodingRuleMapper;
import com.aiqin.bms.scmp.api.supplier.service.EncodingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EncodingRuleServiceImpl implements EncodingRuleService {
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private EncodingRuleMapper encodingRuleMapper;
    @Override
    public Long getNumberValue(Long id) {
        return encodingRuleDao.getNumberValue(id);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int updateNumberValue(Long versionValue, Long id) {
        int k=encodingRuleDao.updateNumberValue(versionValue,id);
        if(k>0){
            return k;
        }else {
            throw new GroundRuntimeException("修改编码失败");
        }
    }

    @Override
    public EncodingRule getNumberingType(String numberingType) {
        return encodingRuleDao.getNumberingType(numberingType);
    }
}
