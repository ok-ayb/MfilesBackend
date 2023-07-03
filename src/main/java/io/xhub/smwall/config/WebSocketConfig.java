package io.xhub.smwall.config;

import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.constants.WebSocketPaths;
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
        config.enableSimpleBroker(WebSocketPaths.TOPIC);
        config.setApplicationDestinationPrefixes(WebSocketPaths.APP);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ApiPaths.WS + ApiPaths.PINNED_POST).setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint(ApiPaths.WS + ApiPaths.HIDDEN_POSTS).setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint(WebSocketPaths.WS)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}