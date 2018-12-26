package com.snailmann.seckill.execption;

import com.snailmann.seckill.entity.template.CodeMsg;
import lombok.Getter;
import lombok.Setter;

/**
 * 全局异常
 */
public class GlobalException extends RuntimeException {


    @Getter@Setter
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;

    }
}
