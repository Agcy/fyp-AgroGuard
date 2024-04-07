package com.api.agroguard.ws;

import com.api.agroguard.config.GetHttpSessionConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {

    private static final Map<String, Session> onlineFriends = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session){
        this.httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        onlineFriends.put("username", session);
    }

    @OnMessage
    public void onMessage(Session session){

    }

    @OnClose
    public void onClose(Session session){

    }
}
