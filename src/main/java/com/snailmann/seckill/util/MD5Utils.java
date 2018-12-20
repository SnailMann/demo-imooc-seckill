package com.snailmann.seckill.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MD5Utils {
    private static final String salt = "1a2b3c4d";

    /**
     * 将src进行md5加密
     * @param src
     * @return
     */
    public static String md5(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 第一层
     * 将password进行固定盐拼接再交给MD5加密
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }


    /**
     * 第二层加密
     * 将password进行随机盐拼接再交给MD5加密
     * @param formPass
     * @return
     */
    public static String formPassToDBPass(String formPass,String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }


    /**
     * 一，二层加密一起
     * @param input
     * @param dbSalt
     * @return
     */
    public static String inputPassToDBPass(String input,String dbSalt){
        String formPass = inputPassToFormPass(input);
        return formPassToDBPass(formPass,dbSalt);

    }

    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123456",salt));
    }
}
