package com.miniware.blog.api.config;

import com.miniware.blog.api.auth.jwt.JwtHandshakeInterceptor;
import com.miniware.blog.api.auth.security.WebSocketAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("*");
//                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketAuthInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // 클라이언트 → 서버로 메시지 보낼 때 사용되는 prefix
        config.setApplicationDestinationPrefixes("/pub");

        // 서버 → 클라이언트로 메시지 전달할 때 사용하는 prefix
        config.enableSimpleBroker("/sub");

        // Redis 기반 브로커를 사용하는 경우엔 다른 설정 필요 (단, 단일 서버에서는 SimpleBroker로 충분)

    }


}
