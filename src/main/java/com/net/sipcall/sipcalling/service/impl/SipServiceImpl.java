package com.net.sipcall.sipcalling.service.impl;


import com.bzfar.exception.DataException;
import com.net.sipcall.sipcalling.config.CustomConfig;
import com.net.sipcall.sipcalling.dto.SIPDto;
import com.net.sipcall.sipcalling.exception.JudgeBusyException;
import com.net.sipcall.sipcalling.exception.UserNotInACallException;
import com.net.sipcall.sipcalling.service.SipService;
import net.sourceforge.peers.FileLogger;
import net.sourceforge.peers.javaxsound.JavaxSoundManager;
import net.sourceforge.peers.sip.Utils;
import net.sourceforge.peers.sip.core.useragent.SipListener;
import net.sourceforge.peers.sip.core.useragent.UserAgent;
import net.sourceforge.peers.sip.syntaxencoding.SipHeader;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;
import net.sourceforge.peers.sip.transport.SipRequest;
import net.sourceforge.peers.sip.transport.SipResponse;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class SipServiceImpl implements SipService, SipListener {

    public static final String CALLING_ACTION_STATUS = "calling"; //拨号中
    public static final String HANGUP_ACTION_STATUS = "hangup"; //挂断
    public static final String PICKUP_ACTION_STATUS = "pickup"; //通话中
    public static final String BUSY_ACTION_STATUS = "busy"; //当前法官不在线
    public static final String REGISTER_ACTION_STATUS = "registering"; //注册中

    private UserAgent userAgent;
    private SipRequest sipRequest;


//    private HashMap<String, UserAgent> users = new HashMap<>();
//    private HashMap<String, SipRequest> requests = new HashMap<>();
    private HashMap<String, String> statusMap = new HashMap<>();

    @Override
    public void clickToDial(SIPDto sipDto) throws SocketException {
        if (statusMap.containsKey(sipDto.getName()) && (statusMap.get(sipDto.getName()) != HANGUP_ACTION_STATUS &&
                statusMap.get(sipDto.getName()) != BUSY_ACTION_STATUS)) {
            throw new DataException("该账号已被注册，请等待");
        }

        String peersHome = Utils.DEFAULT_PEERS_HOME;
        CustomConfig config = new CustomConfig();
        config.setUserPart(sipDto.getName());
        config.setDomain(sipDto.getIp());
        config.setPassword(sipDto.getPass());

        FileLogger logger = new FileLogger(peersHome);
        JavaxSoundManager javaxSoundManager = new JavaxSoundManager(false, logger, peersHome);
        userAgent = new UserAgent(this, config, logger, javaxSoundManager);

        try {
            userAgent.register();
        } catch (SipUriSyntaxException e) {
            throw new DataException("注册失败，请稍后再试");
        }

        String callee = "sip:" + sipDto.getNumber() + "@" + sipDto.getIp();

        try {
            sipRequest = userAgent.invite(callee,
                    Utils.generateCallID(userAgent.getConfig().getLocalInetAddress()));
        } catch (SipUriSyntaxException e) {
            e.printStackTrace();
        }
        statusMap.put(sipDto.getName(), CALLING_ACTION_STATUS);
    }

    @Override
    public void hangUp(String name) throws SipUriSyntaxException {
        if (!statusMap.containsKey(name)) {
            throw new UserNotInACallException("用户并未通话");
        }
        statusMap.put(userAgent.getConfig().getUserPart(), HANGUP_ACTION_STATUS);
        try {
            userAgent.terminate(sipRequest);
            userAgent.unregister();
        } catch (SipUriSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registering(SipRequest sipRequest) {

    }

    @Override
    public void registerSuccessful(UserAgent userAgent, SipResponse sipResponse) {
    }

    @Override
    public void registerFailed(UserAgent userAgent, SipResponse sipResponse) {
    }

    @Override
    public void incomingCall(SipRequest sipRequest, SipResponse provResponse) {

    }

    @Override
    public void remoteHangup(SipRequest sipRequest) throws SipUriSyntaxException {
        //获取用户name
        ArrayList<SipHeader> headers = sipRequest.getSipHeaders().getHeaders();
        SipHeader sipHeader = headers.get(2);
        String value = sipHeader.getValue().getValue();
        String name = value.substring(value.indexOf(":") + 1, value.indexOf("@"));

        statusMap.put(userAgent.getConfig().getUserPart(), HANGUP_ACTION_STATUS);
        try {
            userAgent.terminate(sipRequest);
            userAgent.unregister();
        } catch (SipUriSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ringing(SipResponse sipResponse) {
        statusMap.put(userAgent.getConfig().getUserPart(), CALLING_ACTION_STATUS);
    }

    @Override
    public void calleePickup(SipResponse sipResponse) {
        statusMap.put(userAgent.getConfig().getUserPart(), PICKUP_ACTION_STATUS);
    }

    @Override
    public void error(SipResponse sipResponse) throws SipUriSyntaxException {
        statusMap.put(userAgent.getConfig().getUserPart(), BUSY_ACTION_STATUS);
        try {
            userAgent.terminate(sipRequest);
            userAgent.unregister();
        } catch (SipUriSyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getStatus(String name) {

        if (statusMap.containsKey(name)) {

        }else {
            throw new UserNotInACallException("用户并未通话");
        }

        String status = statusMap.get(name);

        if (status.equals(BUSY_ACTION_STATUS)) {
            statusMap.remove(name);
            userAgent = null;
            sipRequest = null;
            throw new JudgeBusyException("busy");
        }else if (status.equals(HANGUP_ACTION_STATUS)) {
            statusMap.remove(name);
            userAgent = null;
            sipRequest = null;
        }

        return status;
    }

}
