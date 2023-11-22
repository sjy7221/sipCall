package com.net.sipcall.sipcalling.exception;

import lombok.Data;

@Data
public class BaseException extends RuntimeException{

    public Integer code;

    public String message;

    public BaseException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
