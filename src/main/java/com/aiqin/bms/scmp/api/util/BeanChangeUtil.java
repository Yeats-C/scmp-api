package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.base.PropertyMsg;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @date 2019/11/7
 */
public class BeanChangeUtil<T> {
    /**
     * 比较两个对象的不同，字符串会被去掉空格，再比较
     * 如果其中一个为null,返回空串
     *
     * @param oldBean
     * @param newBean
     * @return
     */
    public String contrastObj(Object oldBean, Object newBean) {
        if (Objects.isNull(oldBean) || Objects.isNull(newBean)) {
            return "";
        }
        // 创建字符串拼接对象
        StringBuilder str = new StringBuilder();
        // 转换为传入的泛型T
        T pojo1 = (T) oldBean;
        T pojo2 = (T) newBean;
        // 通过反射获取类的Class对象
        Class clazz = pojo1.getClass();
        // 获取类型及字段属性
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(PropertyMsg.class)) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                    // 获取对应属性值
                    Method getMethod = pd.getReadMethod();
                    Object o1 = getMethod.invoke(pojo1);
                    Object o2 = getMethod.invoke(pojo2);
                    if (o1 == null && o2 == null) {
                        continue;
                    }
                    if (o1 == null) {
                        str.append("从\"空\"改为\"").append(o2).append("\";");
                    } else if (o2 == null) {
                        str.append("从\"").append(o1).append("\"改为\"\";");
                    } else if (!o1.toString().trim().equals(o2.toString().trim())) {
                        String replace = field.getAnnotation(PropertyMsg.class).replace();
                        if (StringUtils.isNotBlank(replace)) {
                            String oldStr = getReplaceString(replace, o1);
                            String newStr = getReplaceString(replace, o2);
                            str.append(field.getAnnotation(PropertyMsg.class).value()).append(":").append("从\"").append(oldStr).append("\"改为\"").append(newStr).append("\";");
                        } else {
                            str.append(field.getAnnotation(PropertyMsg.class).value()).append(":").append("从\"").append(o1).append("\"改为\"").append(o2).append("\";");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    /**
     * 获取添加或删除对象的字符串描述
     *
     * @param bean   添加或删除对象
     * @param index  第几个
     * @param isAdd  是否是新增
     * @param prefix 字符串前缀，bean的信息描述
     * @return
     */
    public String addOrDeleteObj(T bean, String index, boolean isAdd, String prefix) {
        StringBuilder str = new StringBuilder();
        str = isAdd ? str.append("新增") : str.append("删除");
        str.append(prefix).append(index).append(":");
        Class clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PropertyMsg.class)) {
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                    // 获取对应属性值
                    Method getMethod = pd.getReadMethod();
                    Object object = getMethod.invoke(bean);
                    if (object == null) {
                        continue;
                    }
                    String replace = field.getAnnotation(PropertyMsg.class).replace();
                    String appendStr;
                    // 替换replace字段
                    if (StringUtils.isNotBlank(replace)) {
                        appendStr = getReplaceString(replace, object);
                    } else {
                        appendStr = object.toString();
                    }
                    // 添加
                    if (isAdd) {
                        str.append(field.getAnnotation(PropertyMsg.class).value()).append(":\"").append(appendStr).append("\";");
                    } else {
                        // 判断是否是删除字段
                        if (field.getAnnotation(PropertyMsg.class).delete()) {
                            str.append(field.getAnnotation(PropertyMsg.class).value()).append(":\"").append(appendStr).append("\";");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return str.toString();
    }

    /**
     * 获取两个list差异信息，修改删除增加
     *
     * @param oldList 老数据
     * @param newList 新数据
     * @param <E>
     * @return
     */
    public <E> String getChangeInfo(List<E> oldList, List<E> newList, String prefix) {
        if (CollectionUtils.isEmpty(oldList) || CollectionUtils.isEmpty(newList)) {
            return "";
        }
        BeanChangeUtil<E> changeUtil = new BeanChangeUtil<>();
        StringBuilder stringBuilder = new StringBuilder();
        // 如果大小相等
        if (oldList.size() == newList.size()) {
            for (int i = 0; i < oldList.size(); i++) {
                // 修改
                String compareResult = changeUtil.contrastObj(oldList.get(i), newList.get(i));
                if (StringUtils.isNotBlank(compareResult)) {
                    stringBuilder.append(prefix).append(i + 1).append(":").append(compareResult);
                }
            }
        } else if (oldList.size() - newList.size() > 0) {
            // a比b大,多的删除
            for (int i = 0; i < newList.size(); i++) {
                // 修改
                String compareResult = changeUtil.contrastObj(oldList.get(i), newList.get(i));
                if (StringUtils.isNotBlank(compareResult)) {
                    stringBuilder.append(prefix).append(i + 1).append(":").append(compareResult);
                }
            }
            for (int i = newList.size(); i < oldList.size(); i++) {
                // 删除
                stringBuilder.append(changeUtil.addOrDeleteObj(oldList.get(i), (i + 1) + "", false, prefix));
            }

        } else if (oldList.size() - newList.size() < 0) {
            // b比a大,多的新增
            for (int i = 0; i < oldList.size(); i++) {
                // 修改
                String compareResult = changeUtil.contrastObj(oldList.get(i), newList.get(i));
                if (StringUtils.isNotBlank(compareResult)) {
                    stringBuilder.append(prefix).append(i + 1).append(":").append(compareResult);
                }
            }
            for (int i = oldList.size(); i < newList.size(); i++) {
                // 新增
                stringBuilder.append(changeUtil.addOrDeleteObj(newList.get(i), (i + 1) + "", true, prefix));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 处理replace替换字段值
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
