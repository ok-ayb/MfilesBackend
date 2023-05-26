package io.xhub.smwall.scheduler;

import io.xhub.smwall.client.YoutubeClient;
import io.xhub.smwall.client.YoutubeSearchParams;
import io.xhub.smwall.config.YoutubeProperties;
import io.xhub.smwall.constants.CacheNames;
import io.xhub.smwall.constants.ProfileNames;
import io.xhub.smwall.dto.youtube.YoutubeMediaDTO;
import io.xhub.smwall.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
@Profile(ProfileNames.YT)
public class YoutubeScheduler {
    private final WebSocketService webSocketService;
    private final YoutubeClient youtubeClient;
    private final YoutubeProperties youtubeProperties;
    private final Cache processedMediaCache;

    public YoutubeScheduler(YoutubeProperties youtubeProperties, WebSocketService webSocketService, YoutubeClient youtubeClient, CacheManager cacheManager) {
        this.youtubeProperties = youtubeProperties;
        this.webSocketService = webSocketService;
        this.youtubeClient = youtubeClient;
        this.processedMediaCache = cacheManager.getCache(CacheNames.PROCESSED_YOUTUBE_MEDIA);
    }

    @Scheduled(
            fixedDelayString = "${application.webhooks.youtube.scheduling.shorts-delay}",
            timeUnit = TimeUnit.SECONDS)
    public void getYoutubeChannelShorts() {
        try {
            log.info("Start getting youtube shorts by channel id");
            List<YoutubeMediaDTO> newMedia = youtubeClient.getRecentChannelShorts(
                            YoutubeSearchParams.getDefaultSearchParams(),
                            youtubeProperties.getChannelId(),
                            youtubeProperties.getApiKey())
                    .getItems()
                    .stream()
                    .filter(this::isNewYoutubeMedia)
                    .collect(Collectors.toList());

            if (!newMedia.isEmpty()) {
                log.info("Broadcasting {} new fetched youtube shorts to WebSocket", newMedia.size());
                webSocketService.sendYoutubeMedia(newMedia);
            }
        } catch (Exception e) {
            log.error("Error while fetching youtube shorts: {}", e.getMessage());
        }
    }

    @Scheduled(
            fixedDelayString = "${application.webhooks.youtube.scheduling.video-delay}",
            timeUnit = TimeUnit.SECONDS)
    public void getYoutubeChannelVideosByKeyword() {
        try {
            log.info("Start getting youtube videos by keyword");

            List<YoutubeMediaDTO> newMedia = youtubeClient.getRecentChannelVideosByKeyword(
                            YoutubeSearchParams.getVideoSearchParams(),
                            youtubeProperties.getApiKey(),
                            youtubeProperties.getKeyword())
                    .getItems()
                    .stream()
                    .filter(this::isNewYoutubeMedia)
                    .map(youtubeMediaDTO -> {
                        youtubeMediaDTO.getSourceTypes()
                                .addAll(Collections.singletonList(youtubeProperties.getKeyword()));
                        return youtubeMediaDTO;
                    })
                    .collect(Collectors.toList());

            if (!newMedia.isEmpty()) {
                log.info("Broadcasting {} new fetched youtube videos to WebSocket", newMedia.size());
                webSocketService.sendYoutubeMedia(newMedia);
            }
        } catch (Exception e) {
            log.error("Error while fetching youtube videos by keyword: {}", e.getMessage());
        }
    }

    @Scheduled(
            fixedDelayString = "${application.webhooks.youtube.scheduling.channel-video-delay}",
            timeUnit = TimeUnit.SECONDS)
    public void getYoutubeChannelVideosByChannelId() {
        try {
            log.info("Start getting youtube videos by channel id");

            List<YoutubeMediaDTO> newMedia = youtubeClient.getRecentChannelVideosByChannelId(
                            YoutubeSearchParams.getVideoSearchParams(),
                            youtubeProperties.getChannelId(),
                            youtubeProperties.getApiKey())
                    .getItems()
                    .stream()
                    .filter(this::isNewYoutubeMedia)
                    .collect(Collectors.toList());

            if (!newMedia.isEmpty()) {
                log.info("Broadcasting {} new fetched youtube videos to WebSocket", newMedia.size());
                webSocketService.sendYoutubeMedia(newMedia);
            }
        } catch (Exception e) {
            log.error("Error while fetching youtube videos by channel id: {}", e.getMessage());
        }
    }

    private boolean isNewYoutubeMedia(YoutubeMediaDTO media) {
        return processedMediaCache != null && processedMediaCache.putIfAbsent(media.getId().getVideoId(), true) == null;
    }
}


