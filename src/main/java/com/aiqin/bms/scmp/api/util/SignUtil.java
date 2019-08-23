package com.aiqin.bms.scmp.api.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by boyd on 2018/9/3.
 */
@Slf4j
public class SignUtil {
    private final static String SECRET_KEY = "SECRET_KEY";

    /**
     * 根据map获取签名
     *
     * @param map
     * @return
     */
    public static String mapTosign(TreeMap<String, String> map) {
        Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序
        if(null == resultMap){
            return null;
        }
        String unsign = SECRET_KEY;
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            unsign += entry.getKey() + entry.getValue();
        }
        unsign = unsign + SECRET_KEY;
        String sign = MD5Utils.getMD5(unsign).toUpperCase();
        return sign;
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

    /**
     * 一个简单的map转json字符串
     *
     * @param map
     * @return
     */
    public static String mapToJsonString(Map<String, String> map) {
        if (map.size() < 1) {
            return null;
        }
        String json = "{";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            json += "\"" + key + "\"" + ":\"" + value + "\",";
        }
        json = json.substring(0, json.length() - 1);
        return json + "}";
    }

    public static String getURLEncoderString(String str) {//url编码
        String result = "";
        if (StringUtils.isBlank(str)) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("error", e);
        }
        return result;
    }

    public static String URLDecoderString(String str) {//url解码
        String result = "";
        if (StringUtils.isBlank(str)) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("error", e);
        }
        return result;
    }
}
