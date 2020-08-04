package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CodeUtils {


    private final String encoding_rule = "encoding_rule:%s";


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EncodingRuleDao encodingRuleDao;


    public String getRedisCode(String key) {
        String redisKey = String.format(encoding_rule, key);
        if (redisTemplate.hasKey(redisKey)) {
            String code = redisTemplate.opsForValue().increment(redisKey, 1).toString();
            EncodingRule numberingType = encodingRuleDao.getNumberingType(key);
            int i = encodingRuleDao.updateNumberValueById(Long.parseLong(code), numberingType.getId());
            return code;
        } else {
            synchronized (CodeUtils.class) {
                if (redisTemplate.hasKey(redisKey)) {
                    return redisTemplate.opsForValue().increment(redisKey, 1).toString();
                } else {
                    EncodingRule numberingType = encodingRuleDao.getNumberingType(key);
                    Long numberingValue = numberingType.getNumberingValue() + 2L;
                    redisTemplate.opsForValue().set(redisKey, numberingValue + "");
                    encodingRuleDao.updateNumberValueById(numberingValue, numberingType.getId());
                    return numberingValue + "";
                }

            }

        }

    }
}
