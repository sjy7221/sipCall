package com.net.sipcall.sipcalling.handle;

import com.bzfar.exception.DataException;
import com.net.sipcall.sipcalling.exception.AccountRegisteredException;
import com.net.sipcall.sipcalling.exception.JudgeBusyException;
import com.net.sipcall.sipcalling.exception.UserNotInACallException;
import com.net.sipcall.sipcalling.http.HttpResult;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = AccountRegisteredException.class)
    @ResponseBody
    public HttpResult AccountRegisteredException(AccountRegisteredException e){
        if (ObjectUtils.isEmpty(e.getCode())) {
            return HttpResult.error(e.message);
        } else {
            return HttpResult.error(e.getCode(), e.message);
        }
    }

    @ExceptionHandler(value = JudgeBusyException.class)
    @ResponseBody
    public HttpResult JudgeBusyException(JudgeBusyException e){
        if (ObjectUtils.isEmpty(e.getCode())) {
            return HttpResult.ok(e.message);
        } else {
            return HttpResult.error(e.getCode(), e.message);
        }
    }

    @ExceptionHandler(value = UserNotInACallException.class)
    @ResponseBody
    public HttpResult UserNotPickUpException(UserNotInACallException e){
        if (ObjectUtils.isEmpty(e.getCode())) {
            return HttpResult.error(e.message);
        } else {
            return HttpResult.error(e.getCode(), e.message);
        }
    }

    @ExceptionHandler(value = DataException.class)
    @ResponseBody
    public HttpResult DataException(DataException e){
        if (ObjectUtils.isEmpty(e.getCode())) {
            return HttpResult.error(e.getMessage());
        } else {
            return HttpResult.error(e.getCode(), e.getMessage());
        }
    }


}
