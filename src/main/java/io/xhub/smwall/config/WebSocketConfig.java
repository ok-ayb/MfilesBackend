package io.xhub.smwall.config;

import io.xhub.smwall.constants.ApiPaths;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(ApiPaths.MEDIA);
        config.setApplicationDestinationPrefixes(ApiPaths.APP);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ApiPaths.WS).setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint(ApiPaths.WS + ApiPaths.PINNED_POST).setAllowedOriginPatterns("*").withSockJS();
    }
}