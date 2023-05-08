package io.xhub.smwall.service;

import io.xhub.smwall.domains.Media;
import io.xhub.smwall.dto.meta.InstagramMediaDTO;
import io.xhub.smwall.dto.youtube.YoutubeMediaDTO;
import io.xhub.smwall.holders.ApiPaths;
import io.xhub.smwall.mappers.MediaMapper;
import io.xhub.smwall.mappers.meta.InstagramMediaMapper;
import io.xhub.smwall.mappers.youtube.YoutubeMediaMapper;
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
    private final YoutubeMediaMapper youtubeMediaMapper;

    private final MediaMapper mediaMapper;

    public void sendIgMedia(List<InstagramMediaDTO> instagramMediaDTO) {
        log.info("Send instagram media posts to all connected clients");
        List<Media> mediaList = instagramMediaDTO.stream()
                .map(instagramMediaMapper::toEntity)
                .collect(Collectors.toList());
        mediaRepository.saveAll(mediaList);
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS, mediaList.stream().map(mediaMapper::toDTO));
    }

    public void sendYoutubeMedia(List<YoutubeMediaDTO> youtubeMediaDTO) {
        log.info("Send youtube media to all connected clients");
        List<Media> socialMediaList = youtubeMediaDTO.stream()
                .map(youtubeMediaMapper::toEntity)
                .collect(Collectors.toList());
        mediaRepository.saveAll(socialMediaList);
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS, socialMediaList.stream().map(mediaMapper::toDTO));
    }

}
