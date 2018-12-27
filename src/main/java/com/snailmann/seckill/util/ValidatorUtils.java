package com.snailmann.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检验工具类
 * @author liwenjie
 */
public class ValidatorUtils {

    //1开头，后面跟了10个数字，我们就认为其为手机号
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src) {

        if (StringUtils.isBlank(src)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();

    }

    public static void main(String[] args) {
        System.out.println(isMobile("19780023127"));
    }
}