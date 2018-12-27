package com.snailmann.seckill.execption;

import com.snailmann.seckill.constant.CodeMsg;
import lombok.Getter;
import lombok.Setter;

/**
 * 全局异常
 * @author liwenjie
 */
public class GlobalException extends RuntimeException {


    @Getter@Setter
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;

    }
}
