package com.aiqin.bms.scmp.api.account.domain.Util;

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
public class CodeUtil {

    public static String SUPPLIER_PREFIX = "sp";

    /**
     *  生成规则编号: 前缀 + 四位编号（从1开始，不够前补0）
     * @param prefix 前缀
     * @param code  最新编号
     * @return
     */
    public static String getCode(String prefix, String code){
        String newCode = prefix+"0001";
        if(code != null && !code.isEmpty()){
            int flowCode = Integer.valueOf(code.substring(2,6))+1;
            newCode = String.format(prefix + "%04d", flowCode);
        }
        return newCode;
    }

}
