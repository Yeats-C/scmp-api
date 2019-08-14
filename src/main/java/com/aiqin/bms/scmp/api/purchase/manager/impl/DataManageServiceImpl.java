package com.aiqin.bms.scmp.api.purchase.manager.impl;

import com.aiqin.bms.scmp.api.product.dao.ProductCategoryDao;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryResponse;
import com.aiqin.bms.scmp.api.purchase.manager.DataManageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Service
public class DataManageServiceImpl implements DataManageService {
    /**
     * 品类code递增长度
     */
    private static final int categoryAddLength = 2;
    private static Logger LOGGER = LoggerFactory.getLogger(DataManageServiceImpl.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ProductCategoryDao productCategoryDao;

    @Cacheable(value = "category_manage", key = "#categoryCode", unless = "#result eq null")
    public String selectCategoryName(String categoryCode) {
        LOGGER.info("缓存信息,category_manage:{}",categoryCode);
        StringBuilder stringBuilder = new StringBuilder();
        ProductCategoryResponse productCategoryResponse;
        if (StringUtils.isNotBlank(categoryCode)) {
            int s = categoryCode.length() / categoryAddLength;
            for (int i = 0; i < s; i++) {
                productCategoryResponse = productCategoryDao.selectCategoryLevelByCategoryId(categoryCode.substring(0, (i + 1) * 2));
                if (productCategoryResponse != null) {
                    stringBuilder.append(productCategoryResponse.getCategoryName());
                    if (i < s - 1) {
                        stringBuilder.append("/");
                    }
                }
            }
        }
        LOGGER.info("缓存信息,category_name:{}",stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public void deleteCategoryName() {
        String value = "category_manage:*";
        LOGGER.info("delete category_manage cache " + value);
        Set<String> keys = stringRedisTemplate.keys(value);
        for (String key : keys) {
            stringRedisTemplate.delete(key);
            LOGGER.debug("delete category_manage cache, {}", key);
        }
    }

    public boolean expire(final String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.MINUTES);
    }

}
