package io.xhub.smwall.service;

import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.mappers.AnnouncementMapper;
import io.xhub.smwall.mappers.MediaMapper;
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
    private final MediaMapper mediaMapper;
    private final AnnouncementMapper announcementMapper;

    public void sendMedia(List<Media> media) {
        log.info("Sending media to all connected clients");
        simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS, media.stream().map(mediaMapper::toDTO));
    }

    public void sendPinnedMedia(Media pinnedMedia) {
        log.info("Send the pinned Media to all connected clients");
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS + ApiPaths.PINNED_POST, mediaMapper.toDTO(pinnedMedia));
    }

    public void sendNewMediaVisibilityStatus(Media hiddenMedia) {
        log.info("Send new Media visibility status");
        this.simpMessagingTemplate.convertAndSend(ApiPaths.MEDIA + ApiPaths.WS + ApiPaths.HIDDEN_POSTS, mediaMapper.toDTO(hiddenMedia));
    }

    public void sendAnnouncement(Announcement announcement) {
        log.info("Sending announcement to all connected clients");
        this.simpMessagingTemplate.convertAndSend(ApiPaths.ANNOUNCEMENTS + ApiPaths.WS, announcementMapper.toDTO(announcement));
    }
}
