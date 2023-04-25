package io.xhub.smwall.smbackend.task;

import io.xhub.smwall.smbackend.client.meta.MetaClient;
import io.xhub.smwall.smbackend.client.meta.response.InstagramMediaResponse;
import io.xhub.smwall.smbackend.config.MetaProperties;
import io.xhub.smwall.smbackend.dto.InstagramMediaDTO;
import io.xhub.smwall.smbackend.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor
public class MetaTask {
    private final MetaProperties metaProperties;
    private final WebSocketService webSocketService;
    private final MetaClient metaClient;
    private Instant lastHashtaggedMediaTimestamp = Instant.now();
    private Instant lastTaggedMediaTimestamp = Instant.now();
    private Instant lastUserMediaTimestamp = Instant.now();
    private Instant lastStoriesMediaTimestamp = Instant.now();


    /**
     * Retrieves recent media for each hashtag and sends new media to the WebSocket service.
     */
    @Scheduled(fixedDelayString = "${application.webhooks.meta.schedule.hashtag-delay}")
    public void getHashtaggedRecentMedia() {
        log.info("Getting recent media for each hashtag");

        String requestFields = String.join(",", InstagramMediaResponse.FIELDS);
        List<InstagramMediaDTO> newMedia = metaProperties.getHashtags()
                .values()
                .stream()
                .map(hashtagId -> metaClient.getRecentHashtaggedMedia(hashtagId,
                        requestFields,
                        metaProperties.getUserId()))
                .flatMap(res -> res.getData().stream())
                .filter(media -> {
                    if (media.getTimestamp().isAfter(lastHashtaggedMediaTimestamp)) {
                        lastHashtaggedMediaTimestamp = media.getTimestamp();
                        return true;
                    }
                    return false;
                })
                .distinct()
                .collect(Collectors.toList());

        if (!newMedia.isEmpty()) {
            log.info("Broadcasting {} new fetched hashtagged media to WebSocket", newMedia.size());
            webSocketService.sendIgMedia(newMedia);
        }
    }

    @Scheduled(fixedDelayString = "${application.webhooks.meta.schedule.user-media-delay}")
    public void getUserRecentMedia() {
        log.info("Getting user recent media");

        List<InstagramMediaDTO> newUserMedia = metaClient.getRecentUserMedia(metaProperties.getUserId(),
                        String.join(",", InstagramMediaResponse.FIELDS))
                .getData()
                .stream()
                .filter(media -> {
                    if (media.getTimestamp().isAfter(lastUserMediaTimestamp)) {
                        lastUserMediaTimestamp = media.getTimestamp();
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        if (!newUserMedia.isEmpty()) {
            log.info("Broadcasting {} new fetched user media to WebSocket", newUserMedia.size());
            webSocketService.sendIgMedia(newUserMedia);
        }
    }

    /**
     * Retrieves media in which an the IG user has been tagged by another Instagram user
     * and sends new media to the WebSocket service.
     */
    @Scheduled(fixedDelayString = "${application.webhooks.meta.schedule.tag-delay}")
    public void getTaggedMedia() {
        log.info("Getting tagged media");
        List<InstagramMediaDTO> newMedia = metaClient.getTaggedMedia(
                        String.join(",", InstagramMediaResponse.FIELDS),
                        metaProperties.getUserId())
                .getData()
                .stream()
                .filter(media -> {
                    if (media.getTimestamp().isAfter(lastTaggedMediaTimestamp)) {
                        lastTaggedMediaTimestamp = media.getTimestamp();
                        return true;
                    }

                    return false;
                })
                .collect(Collectors.toList());

        if (!newMedia.isEmpty()) {
            log.info("Broadcasting {} new fetched tagged media to WebSocket", newMedia.size());
            webSocketService.sendIgMedia(newMedia);
        }
    }
    @Scheduled(fixedDelayString = "${application.webhooks.meta.schedule.story-delay}")
    public void getStoriesRecentMedia() {
        log.info("Start getting recent media for each story");
        List<InstagramMediaDTO> newMedia = metaClient.getStories(
                        String.join(",", InstagramMediaResponse.FIELDS),
                        metaProperties.getUserId())
                .getData()
                .stream()
                .filter(media -> {
                    if (media.getTimestamp().isAfter(lastStoriesMediaTimestamp)) {
                        lastStoriesMediaTimestamp = media.getTimestamp();
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        if (!newMedia.isEmpty()) {
            log.info("Broadcasting {} new fetched media to WebSocket", newMedia.size());
            webSocketService.sendIgMedia(newMedia);
        }
    }
}



