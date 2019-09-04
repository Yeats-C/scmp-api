package com.aiqin.bms.scmp.api.abutment.domain.conts;

import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

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

public class StringConvertUtil {

    /**
     * 处理字符串 颜色-规格 / 规格-型号
     *
     * @param color
     * @param spec
     * @param model
     * @return
     */
    public static String productDesc(String color, String spec, String model) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(color)) {
            return stringBuilder.append(color).append("-").append(spec).toString();
        } else if (StringUtils.isNotBlank(model)) {
            return stringBuilder.append(spec).append("-").append(model).toString();
        } else {
            return spec;
        }
    }

    /**
     * @param type
     * @return
     */
    public static InnerValue outboundTypeConvert(byte type) {
        //OutboundTypeEnum 对应类型
        InnerValue innerValue = new InnerValue();
        switch (type) {
            case 1:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("5")).getDesc());
                innerValue.setValue("5");
                break;
            case 2:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("40")).getDesc());
                innerValue.setValue("40");
                break;
            case 3:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("10")).getDesc());
                innerValue.setValue("10");
                break;
            case 4:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("31")).getDesc());
                innerValue.setValue("31");
                break;
            default:
                throw new GroundRuntimeException("未查询到对应的类型");
        }
        return innerValue;
    }

    /**
     * @param type
     * @return
     */
    public static InnerValue outboundSourceTypeConvert(byte type) {
        //OutboundTypeEnum 对应类型
        InnerValue innerValue = new InnerValue();
        switch (type) {
            case 1:
                innerValue.setName(Objects.requireNonNull(ScmpOrderEnum.getByCode("5")).getDesc());
                innerValue.setValue("5");
                break;
            case 3:
                innerValue.setName(Objects.requireNonNull(ScmpOrderEnum.getByCode("10")).getDesc());
                innerValue.setValue("10");
                break;
            default:
                throw new GroundRuntimeException("未查询到对应的类型");
        }
        return innerValue;
    }

    /**
     * @param type
     * @return
     */
    public static InnerValue inboundSourceTypeConvert(byte type) {
        //OutboundTypeEnum 对应类型
        InnerValue innerValue = new InnerValue();
        switch (type) {
            case 1:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("0")).getDesc());
                innerValue.setValue("0");
                break;
            case 3:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("25")).getDesc());
                innerValue.setValue("25");
                break;
            default:
                throw new GroundRuntimeException("未查询到对应的类型");
        }
        return innerValue;
    }

    /**
     * @param type
     * @return
     */
    public static InnerValue inboundTypeConvert(byte type) {
        //OutboundTypeEnum 对应类型
        InnerValue innerValue = new InnerValue();
        switch (type) {
            case 1:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("0")).getDesc());
                innerValue.setValue("0");
                break;
            case 2:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("35")).getDesc());
                innerValue.setValue("35");
                break;
            case 3:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("25")).getDesc());
                innerValue.setValue("25");
                break;
            case 4:
                innerValue.setName(Objects.requireNonNull(ScmpStorageChangeEnum.getByCode("30")).getDesc());
                innerValue.setValue("30");
                break;
            default:
                throw new GroundRuntimeException("未查询到对应的类型");
        }
        return innerValue;
    }
}
