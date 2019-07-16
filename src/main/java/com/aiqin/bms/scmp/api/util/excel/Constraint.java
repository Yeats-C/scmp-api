package com.aiqin.bms.scmp.api.util.excel;


import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *   静态封装类
 */
public class Constraint {
    //注意：反斜线属于转义字符，故这里的反斜线是一对的
    private static final String IS_NUM = "^[0-9]*$";     //001.验证只能是0到9
    private static final String IS_JUST_NUMBER = "^\\+?[1-9][0-9]*$";     //002.非0正整数
    private static final String IS_COUNT_NUMBER = "^\\d{n}$";     //003.只能输入n位数字
    private static final String IS_MIN_NUMBER = "^\\d{n,}$";      //004.最少n长度的字符串
    private static final String IS_ONLY_CHN = "^[\\u4e00-\\u9fa5]{0,}$";   //005.只能输入汉字
    private static final String IS_ONLY_CHN_AND_NUM = "^[\\u4e00-\\u9fa50-9]+$";   //006.只能是汉字和数字
    private static final String IS_ONLY_LETTER_NUM = "^[A-Za-z0-9]+$";   //006.只能是字母和数字
    private static final String IS_ONLY_CHN_LETTER_NUM = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$";  //007.只能是字母和数字下换线汉字
    //000.总方法  in:约束名，被约束字符串
    private static boolean ck(String ckName,String inputStr){
        Pattern pat = Pattern.compile(ckName);
        Matcher mat = pat.matcher(inputStr);
        return mat.matches();
    }

    public static void main(String[] args) {
        System.out.println(Constraint.ckChnLetterAndNum("aazzz"));
        System.out.println(Constraint.ckChnLetterAndNumAndChar("呵呵123zzz"));
        System.out.println(Constraint.ckChnCharAndNum("呵呵123"));
    }

    //001.只能是0到9
    public static boolean ckOnlyNum(String InputNumber){
        return ck(IS_NUM,InputNumber);
    }
    //002.只能是大于0的正整数
    public static boolean ckJustNum(String InputNumber){
        return ck(IS_JUST_NUMBER,InputNumber);
    }
    //003.只能输入固定长度的数字串  in:长度，数字串
    public static boolean ckCountNum(int count,String inputNumber){
        String cks = IS_COUNT_NUMBER.replace("n",count+"");   //替换
        return ck(cks,inputNumber);
    }
    //004.只能输入大于等于n位的数字串
    public static boolean ckMinCount(int minCount,String inputNumber){
        String cks = IS_MIN_NUMBER.replace("n",minCount+"");  //替换
        return ck(cks,inputNumber);
    }
    //005.只能是汉字
    public static boolean ckChnChar(String inputStr){
        return ck(IS_ONLY_CHN,inputStr);
    }
    //006.只能是汉字和数字
    public static boolean ckChnCharAndNum(String inputStr){
        return ck(IS_ONLY_CHN_AND_NUM,inputStr);
    }
     //006.只能是字母和数字
    public static boolean ckChnLetterAndNum(String inputStr){
        return ck(IS_ONLY_LETTER_NUM,inputStr);
    }
      //007.只能是字母和数字
    public static boolean ckChnLetterAndNumAndChar(String inputStr){
        return ck(IS_ONLY_CHN_LETTER_NUM,inputStr);
    }
    //a.去全部空格
    public static String delSpaceAll(String str){
        return StringUtils.deleteWhitespace(str);
    }
    //b.去首尾空格
    public static String delSpace(String str){
        return str.trim();
    }
}