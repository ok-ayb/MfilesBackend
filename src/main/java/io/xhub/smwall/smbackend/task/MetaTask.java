package io.xhub.smwall.smbackend.task;

import io.xhub.smwall.smbackend.client.meta.MetaClient;
import io.xhub.smwall.smbackend.client.meta.response.IGHashtaggedMediaResponse;
import io.xhub.smwall.smbackend.config.MetaProperties;
import io.xhub.smwall.smbackend.dto.IGMedia;
import io.xhub.smwall.smbackend.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MetaTask {
    private final MetaProperties metaProperties;
    private final WebSocketService webSocketService;
    private final MetaClient metaClient;
    private Instant lastHashtaggedMediaTimestamp  = Instant.now();

    /**
     * Retrieves recent media for each hashtag and sends new media to the WebSocket service.
     */
    @Scheduled(fixedDelayString = "${application.webhooks.meta.schedule.hashtag-delay}")
    public void getHashtaggedRecentMedia() {
        log.info("Getting recent media for each hashtag");

        List<IGMedia> newMedia = metaProperties.getHashtags()
                .values()
                .stream()
                .map(hashtagId -> metaClient.getRecentHashtaggedMedia(hashtagId,
                        String.join(",", IGHashtaggedMediaResponse.FIELDS),
                        metaProperties.getUserId()))
                .flatMap(res -> res.getData().stream())
                .filter(this::isMediaNewerThanLastFetch)
                .distinct()
                .toList();

        if(!newMedia.isEmpty()) {
            log.info("Broadcasting {} new fetched hashtagged media to WebSocket", newMedia.size());
            webSocketService.sendIgMedia(newMedia);
        }
    }

    /**
     * Returns true if the media's timestamp is more recent than the last fetch timestamp.
     *
     * @param media the media to check
     * @return true if the media is new, false otherwise
     */
    private boolean isMediaNewerThanLastFetch(IGMedia media) {
        if (media.getTimestamp().isAfter(lastHashtaggedMediaTimestamp)) {
            lastHashtaggedMediaTimestamp = media.getTimestamp();
            return true;
        }

        return false;
    }
}
