package com.net.sipcall.sipcalling.controller;

import com.net.sipcall.sipcalling.dto.SIPDto;
import com.net.sipcall.sipcalling.http.HttpResult;
import com.net.sipcall.sipcalling.service.SipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.SocketException;

@RequestMapping("sip")
@RestController
@Api(tags = "拨打电话")
public class SipController {

    @Autowired
    private SipService sipService;

    @PostMapping("/clickToDial")
    @ApiOperation("拨打电话")
    public HttpResult<Void> clickToDial(@RequestBody SIPDto sipDto) throws SocketException, SipUriSyntaxException {
        sipService.clickToDial(sipDto);
        return new HttpResult<>().ok();
    }

    @GetMapping("/hangUp")
    @ApiOperation("挂断电话")
    public HttpResult<Void> hangUp(String name) throws SocketException, SipUriSyntaxException {
        sipService.hangUp(name);
        return new HttpResult<>().ok();
    }

    @GetMapping("/get_status")
    @ApiOperation("查询状态")
    public HttpResult<String> getStatus(String name) {
        String status = sipService.getStatus(name);
        return new HttpResult<>().ok(status);
    }

}
