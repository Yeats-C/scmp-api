package com.aiqin.bms.scmp.api.supplier.service;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;

public interface EncodingRuleService {
    Long getNumberValue(Long id);

    int updateNumberValue(Long versionValue, Long id);

    EncodingRule getNumberingType(String numberingType);
}
