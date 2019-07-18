package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.Supplier;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author knight.xie
 * @version 1.0
 * @className GetChangeValueUtil
 * @date 2019/7/17 18:55
 * @description TODO
 */
public class GetChangeValueUtil<T> {
    public String compareObject(Object oldBean, Object newBean) {
        String str = "";
        //if (oldBean instanceof SysConfServer && newBean instanceof SysConfServer) {
        T pojo1 = (T) oldBean;
        T pojo2 = (T) newBean;
        try {
            Class clazz = pojo1.getClass();
            Field[] fields = pojo1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(pojo1);
                Object o2 = getMethod.invoke(pojo2);
                if (o1 == null || o2 == null) {
                    continue;
                }
                if (!o1.toString().equals(o2.toString())) {
                    if (i != 1) {
                        str += ";";
                    }
                    str += i + "." + field.getName() + ",旧值:" + o1 + ",新值:" + o2;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args) {
        Supplier old = new Supplier();
        old.setId(1L);
        old.setSupplierName("张三");
        old.setSupplierAbbreviation("张三2");


        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setSupplierName("张三2");
        supplier.setSupplierAbbreviation("张三");
        System.out.println(new GetChangeValueUtil<Supplier>().compareObject(old,supplier));

    }


}
