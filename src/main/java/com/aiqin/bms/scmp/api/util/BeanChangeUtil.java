package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.base.PropertyMsg;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @date 2019/11/7
 */
public class BeanChangeUtil<T> {
    public String contrastObj(Object oldBean, Object newBean) {
        // 创建字符串拼接对象
        StringBuilder str = new StringBuilder();
        // 转换为传入的泛型T
        T pojo1 = (T) oldBean;
        T pojo2 = (T) newBean;
        // 通过反射获取类的Class对象
        Class clazz = pojo1.getClass();
        // 获取类型及字段属性
        Field[] fields = clazz.getDeclaredFields();
        return jdk8Before(fields, pojo1, pojo2, str, clazz);
    }

    public String jdk8Before(Field[] fields, T pojo1, T pojo2, StringBuilder str, Class clazz) {
        int i = 1;
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(PropertyMsg.class)) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                    // 获取对应属性值
                    Method getMethod = pd.getReadMethod();
                    Object o1 = getMethod.invoke(pojo1);
                    Object o2 = getMethod.invoke(pojo2);
                    if (o1 == null || o2 == null) {
                        continue;
                    }
                    if (!o1.toString().equals(o2.toString())) {
                        String replace = field.getAnnotation(PropertyMsg.class).replace();
                        if (StringUtils.isNotBlank(replace)) {
                            String oldStr = getReplaceString(replace, o1);
                            String newStr = getReplaceString(replace, o2);
                            str.append(field.getAnnotation(PropertyMsg.class).value() + ":" + "从\"" + oldStr + "\"改为\"" + newStr + "\";");
                        } else {
                            str.append(field.getAnnotation(PropertyMsg.class).value() + ":" + "从\"" + o1 + "\"改为\"" + o2 + "\";");
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    /**
     * 通过replace替换字段值
     *
     * @param replace
     * @param obj
     * @return
     */
    private String getReplaceString(String replace, Object obj) {
        if (StringUtils.isEmpty(replace) || obj == null) {
            return null;
        }
        // 格式"1_男,2_女"
        String[] split = replace.split(",");
        for (String s :
                split) {
            String oldStr = StringUtils.substringBefore(s, "_");
            String newStr = StringUtils.substringAfter(s, "_");
            if (obj.toString().equals(oldStr)) {
                return newStr;
            }
        }
        return obj.toString();
    }

    public static void main(String[] args) {
        String s = "1_男,2_女";
        System.out.println(new BeanChangeUtil<>().getReplaceString(s, "1"));
    }
}
