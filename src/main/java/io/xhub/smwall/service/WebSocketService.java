package io.xhub.smwall.service;

import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.constants.WebSocketPaths;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.mappers.MediaMapper;
import io.xhub.smwall.websocket.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MediaMapper mediaMapper;

    public void sendPinnedMedia(Media pinnedMedia) {
        log.info("Send the pinned Media to all connected clients");
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS + ApiPaths.PINNED_POST, mediaMapper.toDTO(pinnedMedia));
    }

    public void sendNewMediaVisibilityStatus(Media hiddenMedia) {
        log.info("Send new Media visibility status");
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS + ApiPaths.HIDDEN_POSTS, mediaMapper.toDTO(hiddenMedia));
    }

    public void broadcastClosestAnnouncement(Payload payload) {
        log.info("Broadcasting closest announcement: {}", payload);
        simpMessagingTemplate.convertAndSend(WebSocketPaths.TOPIC + WebSocketPaths.CLOSEST_ANNOUNCEMENT, payload);
    }

    public void broadcastMedia(Payload payload) {
        log.info("Broadcasting media: {}", payload);
        simpMessagingTemplate.convertAndSend(WebSocketPaths.TOPIC + WebSocketPaths.MEDIA, payload);
    }
}
