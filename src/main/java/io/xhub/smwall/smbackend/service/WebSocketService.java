package io.xhub.smwall.smbackend.service;

import io.xhub.smwall.smbackend.dto.IGMedia;
import io.xhub.smwall.smbackend.holders.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendIgMedia(List<IGMedia> igMedia) {
        log.info("Sending media posts to all connected clients");
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.POSTS, igMedia);
    }
}
