package com.aiqin.bms.scmp.api.abutment.domain.conts;

import org.apache.commons.lang.StringUtils;

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

public  class StringConvertUtil {

    /**
     *  处理字符串 颜色-规格 / 规格-型号
     * @param color
     * @param spec
     * @param model
     * @return
     */
    public static String productDesc(String color,String spec,String model){
        StringBuilder stringBuilder = new StringBuilder();
        if(StringUtils.isNotBlank(color)){
            return stringBuilder.append(color).append("-").append(spec).toString();
        }else if(StringUtils.isNotBlank(model)){
            return stringBuilder.append(spec).append("-").append(model).toString();
        }else {
            return spec;
        }
    }

}
