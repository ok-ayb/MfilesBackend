package io.xhub.smwall.service;

import io.xhub.smwall.domains.Media;
import io.xhub.smwall.dto.meta.InstagramMediaDTO;
import io.xhub.smwall.holders.ApiPaths;
import io.xhub.smwall.mappers.meta.InstagramMediaMapper;
import io.xhub.smwall.repositories.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MediaRepository mediaRepository;
    private final InstagramMediaMapper instagramMediaMapper;

    public void sendIgMedia(List<InstagramMediaDTO> instagramMediaDTO) {
        log.info("Sending meta media posts {} to all connected clients", instagramMediaDTO);
        List<Media> mediaList = instagramMediaDTO.stream()
                .map(instagramMediaMapper::toEntity)
                .collect(Collectors.toList());
        mediaRepository.saveAll(mediaList);
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS, instagramMediaDTO);
    }
}
