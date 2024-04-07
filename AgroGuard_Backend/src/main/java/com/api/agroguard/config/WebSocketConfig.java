package com.api.agroguard.config;

import com.api.agroguard.interceptor.TokenHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private TokenHandshakeInterceptor tokenHandshakeInterceptor;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //定义一个前缀为“/ws/ep”的 endPoint，并开启 sockjs 支持，允许跨域
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS(); // 添加握手拦截器
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
        消息代理的前缀，即如果消息代理的前缀为指定的字符，就会将消息转发给消息代理broker
        在由消息代理将消息广播给当前的连接的客户端。
        */
        registry.enableSimpleBroker("/topic");
//                .setHeartbeatValue(new long[]{10000, 20000});
        /*
       前缀为“/app”的 destination 可以通过＠MessageMapping 注解的方法处理，
       而其他 destination （例如“/topic”“/queue”）将被直接交给 broker 处理。
        */
        registry.setApplicationDestinationPrefixes("/app");
    }
}
