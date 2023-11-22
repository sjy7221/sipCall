package com.net.sipcall.sipcalling.service;

import com.net.sipcall.sipcalling.dto.SIPDto;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;

import java.net.SocketException;

public interface SipService {

//    void clickToDial(String number) throws SocketException, SipUriSyntaxException;

    void clickToDial(SIPDto sipDto) throws SocketException;

    void hangUp(String name) throws SipUriSyntaxException;

    String getStatus(String name);


}
