package com.snailmann.seckill.util;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtils {

    /**
     * 生成Token
     * @return
     */
    public static String createToken(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
