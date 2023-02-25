package com.gx.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

//MD5加密的工具类（工具类基本上都是静态方法）

public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }  //返回MD5加密结果

    private static final String salt="1a2b3c4d";   //加盐防止MD5被撞库破解。此处的salt与前端统一，不是数据库存储的salt

    //第一次加密
    public static String inputPassToFromPass(String inputPass){
        //String str = salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        String str =String.valueOf(salt.charAt(0))+String.valueOf(salt.charAt(2))+inputPass+String.valueOf(salt.charAt(5))+String.valueOf(salt.charAt(4));
        return md5(str);
    }
    //第二次加密
    public static String fromPassToDBPass(String fromPass,String salt){
        //String str = "" +salt.charAt(0)+salt.charAt(2)+fromPass+salt.charAt(5)+salt.charAt(4);
        String str =String.valueOf(salt.charAt(0))+String.valueOf(salt.charAt(2))+fromPass+String.valueOf(salt.charAt(5))+String.valueOf(salt.charAt(4));
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass,salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(fromPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9","1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
