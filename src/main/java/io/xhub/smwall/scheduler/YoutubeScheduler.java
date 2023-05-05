package io.xhub.smwall.scheduler;

import io.xhub.smwall.client.YoutubeClient;
import io.xhub.smwall.client.YoutubeSearchParams;
import io.xhub.smwall.config.YoutubeProperties;
import io.xhub.smwall.dto.youtube.YoutubeMediaDTO;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.holders.ApiClientErrorCodes;
import io.xhub.smwall.holders.CacheNames;
import io.xhub.smwall.holders.RegexPatterns;
import io.xhub.smwall.service.WebSocketService;
import io.xhub.smwall.utlis.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Slf4j
@EnableAsync
public class YoutubeScheduler {
    private static String CHANNEL_PROFILE_KEY = "channelProfileUrl";
    private final WebSocketService webSocketService;
    private final YoutubeClient youtubeClient;
    private final YoutubeProperties youtubeProperties;
    private final Cache processedMediaCache;
    private final Cache channelCache;
    private WebClient webClient;

    public YoutubeScheduler(YoutubeProperties youtubeProperties, WebSocketService webSocketService, YoutubeClient youtubeClient, CacheManager cacheManager) {
        this.youtubeProperties = youtubeProperties;
        this.webSocketService = webSocketService;
        this.youtubeClient = youtubeClient;
        this.processedMediaCache = cacheManager.getCache(CacheNames.PROCESSED_YOUTUBE_MEDIA);
        this.channelCache = cacheManager.getCache(CacheNames.YOUTUBE_CHANNEL_PROFILE_URL);
        this.webClient = WebClient.builder().build();
    }

    public String getChannelProfilePictureById(String channelId) {
        return Optional.ofNullable(channelCache.get(CHANNEL_PROFILE_KEY, String.class))
                .orElseGet(() -> {
                    String url = StringUtils.generateStringUrl(channelId);
                    log.info("getting channel info");
                    String html = webClient.mutate()
                            .codecs(
                                    configure -> configure
                                            .defaultCodecs()
                                            .maxInMemorySize(16 * 1024 * 1024)
                            )
                            .build()
                            .get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();
                    Pattern thumbnailPattern = Pattern.compile(RegexPatterns.YOUTUBE_CHANNEL_PROFILE);
                    Matcher thumbnailMatcher = thumbnailPattern.matcher(html);
                    String profilePicUrl = thumbnailMatcher.find() ? thumbnailMatcher.group(1) : null;
                    if (profilePicUrl == null) {
                        throw new BusinessException(ApiClientErrorCodes.PROFILE_PICTURE_EXTRACTION_FAILED.getErrorMessage());
                    }
                    channelCache.put(CHANNEL_PROFILE_KEY, profilePicUrl);
                    return profilePicUrl;
                });
    }

    @Async
    @Scheduled(
            fixedDelayString = "${application.webhooks.youtube.scheduling.shorts-delay}",
            timeUnit = TimeUnit.SECONDS)
    public void getYoutubeChannelShorts() {
        log.info("Getting youtube shorts");

        String profilePictureUrl = getChannelProfilePictureById(youtubeProperties.getChannelId());
        List<YoutubeMediaDTO> newMedia = youtubeClient.getRecentChannelShorts(
                        YoutubeSearchParams.getDefaultSearchParams(),
                        youtubeProperties.getChannelId(),
                        youtubeProperties.getApiKey())
                .getItems()
                .stream()
                .filter(this::isNewYoutubeMedia)
                .map(youtubeShort -> {
                    youtubeShort.getSnippet().setAvatar(profilePictureUrl);
                    return youtubeShort;
                })
                .collect(Collectors.toList());

        if (!newMedia.isEmpty()) {
            log.info("Broadcasting {} new fetched youtube shorts to WebSocket", newMedia.size());
            webSocketService.sendYoutubeMedia(newMedia);
        }


    }

    private boolean isNewYoutubeMedia(YoutubeMediaDTO media) {
        return processedMediaCache != null && processedMediaCache.putIfAbsent(media.getId().getVideoId(), true) == null;
    }
}


