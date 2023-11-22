package com.net.sipcall.sipcalling.exception;

public class JudgeBusyException extends BaseException{


    public JudgeBusyException(Integer code, String message) {
        super(code, message);
    }

    public JudgeBusyException(String message) {
        super(message);
    }

    public JudgeBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JudgeBusyException(Throwable cause) {
        super(cause);
    }

    protected JudgeBusyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
