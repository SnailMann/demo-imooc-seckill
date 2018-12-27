package com.snailmann.seckill.common;

import com.snailmann.seckill.constant.CodeMsg;
import lombok.Data;

@Data
public class Result<T> {
	
	private int code;
	private String msg;
	private T data;
	
	/**
	 *  成功时候的调用
	 * */
	public static  <T> Result<T> success(T data){
		return new Result<>(data);
	}
	
	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> error(CodeMsg codeMsg){
		return new Result<>(codeMsg);
	}

    /**
     *  失败带数据时的调用
     * */
    public static  <T> Result<T> errorWithDate(CodeMsg codeMsg,T t){
        return new Result<>(codeMsg,t);
    }
	
	private Result(T data) {
		this.data = data;
	}
	
	private Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}


    private Result(CodeMsg codeMsg,T t) {
        if(codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
            if (t !=null) {
                this.data = t;
            }
        } else {
            if (t != null) {
                this.data = t;
            }
        }
    }

}
