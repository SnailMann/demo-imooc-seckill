package com.snailmann.seckill.execption;

import com.snailmann.seckill.entity.ouput.CodeMsg;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Select;

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
