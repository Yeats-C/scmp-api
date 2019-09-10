package com.aiqin.bms.scmp.api.util;


import com.aiqin.bms.scmp.api.constant.Global;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by boyd on 2018/9/3.
 */
@Slf4j
public class SignUtil {

    private SignUtil(){

    }
    public static String getURLEncoderString(String str) {//url编码
        String result = "";
        if (StringUtils.isBlank(str)) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(Global.ERROR, e);
        }
        return result;
    }
}
