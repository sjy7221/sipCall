package com.net.sipcall.sipcalling.exception;

public class UserNotInACallException extends BaseException{
    public UserNotInACallException(Integer code, String message) {
        super(code, message);
    }

    public UserNotInACallException(String message) {
        super(message);
    }

    public UserNotInACallException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotInACallException(Throwable cause) {
        super(cause);
    }

    protected UserNotInACallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
