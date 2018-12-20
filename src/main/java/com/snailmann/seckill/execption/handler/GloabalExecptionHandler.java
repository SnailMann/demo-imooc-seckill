package com.snailmann.seckill.execption.handler;


import com.snailmann.seckill.entity.ouput.CodeMsg;
import com.snailmann.seckill.entity.ouput.Result;
import com.snailmann.seckill.execption.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 自定义全局异常处理器
 * 统一异常返回格式
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GloabalExecptionHandler {

    private Map<String, Object> map = new LinkedHashMap<>();

    @ExceptionHandler(value = Exception.class)
    public Result<Object> execptionHandler(HttpServletRequest request, Exception e) {

        //参数校验异常
        if (e instanceof BindException) {
            log.error("BindException: {}", e.getMessage());
            BindException exception = (BindException) e;
            List<ObjectError> errors = exception.getAllErrors();
            AtomicInteger i = new AtomicInteger(0);
            errors.forEach(error ->
                    map.put(String.valueOf(i.getAndIncrement()), error.getDefaultMessage()));
            return Result.errorWithDate(CodeMsg.BIND_ERROR.fillArgs(e.getClass().getSimpleName() + "-" + errors.size()), map);

        } else if (e instanceof GlobalException) {
            //全局异常
            GlobalException exception = (GlobalException)e;
           return Result.error(exception.getCodeMsg());
        } else {
            //其他异常
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            map.put("class", e.getClass().getSimpleName());
            map.put("cause", e.getMessage());
            return Result.errorWithDate(CodeMsg.SERVER_ERROR, map);
        }
    }

}