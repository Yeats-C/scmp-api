/*
 * 文件名：MD5Util.java
 * 版权：Copyright (c) 2014, doit.com All Rights Reserved. 
 * 修改时间：2014-3-1 下午3:41:10
 */
package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.constant.Global;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @author Estn
 * @description MD5加密工具
 * @date 2014-3-1
 */
@Slf4j
public class MD5Utils {

    /**
     * MD5普通加密
     *
     * @param rawPass 明文
     * @return
     */
    public static String encrypt(String rawPass) {
        return encrypt(rawPass, null);
    }

    /**
     * * MD5盐值加密
     *
     * @param rawPass 明文
     * @param salt    盐值
     * @return
     */
    public static String encrypt(String rawPass, Object salt) {
        String saltedPassword = mergePasswordAndSalt(rawPass, salt);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(saltedPassword.getBytes("UTF-8"));
            return new String(encode(digest));
        } catch (Exception e) {
            return rawPass;
        }
    }

    /**
     * 拼接密码与盐值
     *
     * @param password
     * @param salt
     * @return 密码{盐值}
     */
    private static String mergePasswordAndSalt(String password, Object salt) {
        if (salt == null || "".equals(salt.toString().trim())) {
            return password;
        }
        return password + "{" + salt.toString() + "}";
    }

    /**
     * encrypt
     *
     * @param bytes
     * @return
     */
    private static char[] encode(byte[] bytes) {
        char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int nBytes = bytes.length;
        char[] result = new char[2 * nBytes];
        int j = 0;
        for (byte aByte : bytes) {
            result[j++] = HEX[(0xF0 & aByte) >>> 4];
            result[j++] = HEX[(0x0F & aByte)];
        }
        return result;
    }

    /**
     * 校验明文加验证MD5后的值是否等于密文
     *
     * @param plainText  明文
     * @param salt       盐值
     * @param cipherText 密文
     * @return
     */
    public static boolean verify(String plainText, String salt, String cipherText) {
        return (MD5Utils.encrypt(plainText, salt)).equals(cipherText);
    }
    public static String getMD5(String message) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2 将消息变成byte数组
            byte[] input = message.getBytes();

            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);

        } catch (Exception e) {
            log.error(Global.ERROR, e);
        }
        return md5str;
    }
    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
    /**
     * 生成含有随机盐的密码
     */
    public static String generate(String password,String salt) {
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }
    /**
     *
     */
    public static String salt(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        return salt;
    }

    /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }
    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String salt = salt();
        System.out.printf(salt);
        String password1 = generate("admin",salt);
        System.out.println("结果：" + password1 + "   长度："+ password1.length());
        // 解码
        System.out.println(verify("admin", password1));
        // 加密+加盐
        salt = salt();
        System.out.printf(salt);
        String password2= generate("admin",salt);
        System.out.println("结果：" + password2 + "   长度："+ password2.length());
        // 解码
        System.out.println(verify("admin", password2));
    }
}