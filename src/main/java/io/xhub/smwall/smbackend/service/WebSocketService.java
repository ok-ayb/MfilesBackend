package io.xhub.smwall.smbackend.service;

import io.xhub.smwall.smbackend.dto.InstagramMediaDTO;
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

    public void sendIgMedia(List<InstagramMediaDTO> instagramMediaDTO) {
        log.info("Sending instagram media posts {} to all connected clients", instagramMediaDTO);
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS, instagramMediaDTO);
    }
}
