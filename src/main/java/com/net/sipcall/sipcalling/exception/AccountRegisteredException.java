package com.net.sipcall.sipcalling.exception;

public class AccountRegisteredException extends BaseException{
    public AccountRegisteredException(Integer code, String message) {
        super(code, message);
    }

    public AccountRegisteredException(String message) {
        super(message);
    }

    public AccountRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountRegisteredException(Throwable cause) {
        super(cause);
    }

    protected AccountRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
