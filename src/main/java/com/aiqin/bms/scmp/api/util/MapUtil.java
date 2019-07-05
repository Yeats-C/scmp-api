package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.purchase.service.impl.GoodsRejectServiceImpl;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

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
public class MapUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapUtil.class);


    public static Map<String, Object> objectToMap(Object obj) {
        try {
            Map<String, Object> map = new TreeMap<>(
                    new Comparator<String>() {
                        @Override
                        public int compare(String obj1, String obj2) {
                            // 降序排序
                            return obj2.compareTo(obj1);
                        }
                    });
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (!"sign".equals(fieldName) && !"title".equals(fieldName)) {
                    Object value = field.get(obj);
                    map.put(fieldName, value);
                }
            }
            return map;
        } catch (IllegalAccessException e) {
            LOGGER.error("转化失败异常:{}",e.getMessage());
            throw new GroundRuntimeException("转换失败！");
        }
    }
}
