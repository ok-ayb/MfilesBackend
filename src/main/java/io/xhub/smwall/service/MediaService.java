package io.xhub.smwall.service;

import com.querydsl.core.types.Predicate;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.events.media.MediaCreatedEvent;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.filter.ContentTextFiltering;
import io.xhub.smwall.filter.filterMedia.FilterMedia;
import io.xhub.smwall.repositories.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MediaService {
    private final ApplicationEventPublisher eventPublisher;
    private final MediaRepository mediaRepository;
    private final WebSocketService webSocketService;
    private final ContentTextFiltering contentTextFiltering;

    private final FilterMedia filterMedia;

    @Transactional(readOnly = true)
    public Page<Media> getAllMedia(Predicate predicate, Pageable pageable) {
        log.info("Start getting all media");
        if (predicate == null) {
            return mediaRepository.findAll(pageable);
        }
        return mediaRepository.findAll(predicate, pageable);
    }

    @Transactional(readOnly = true)
    public Media getMediaById(String id) {
        log.info("Start getting media of id '{}'", id);
        return mediaRepository.findById(id).orElseThrow(() -> {
            log.error("No media found with id '{}'", id);
            return new BusinessException(ApiClientErrorCodes.MEDIA_NOT_FOUND.getErrorMessage());
        });
    }

    public void updateMediaPinning(String mediaId) {
        log.info("Start updating media pinning ");
        Media mediaToToggle = getMediaById(mediaId);
        if (mediaToToggle.getHidden()) {
            log.info("Cannot pin a media with hidden status");
            throw new BusinessException(ApiClientErrorCodes.MEDIA_HIDDEN.getErrorMessage());
        } else {
            if (mediaToToggle.getPinned()) {
                mediaToToggle.setPinned(false);
            } else {
                Media currentlyPinnedMedia = mediaRepository.findByPinned(true)
                        .orElse(null);

                if (currentlyPinnedMedia != null) {
                    currentlyPinnedMedia.setPinned(false);
                    mediaRepository.save(currentlyPinnedMedia);
                }

                mediaToToggle.setPinned(true);
            }

            mediaRepository.save(mediaToToggle);
            webSocketService.sendMediaPinningStatus(mediaToToggle);
        }
    }

    public void updateMediaVisibility(String mediaId) {
        log.info("Start updating media visibility");
        Media mediaToToggle = getMediaById(mediaId);
        if (!mediaToToggle.getHidden() && mediaToToggle.getPinned()) {
            mediaToToggle.setPinned(false);
        }
        mediaToToggle.setHidden(!mediaToToggle.getHidden());
        mediaRepository.save(mediaToToggle);
        webSocketService.sendNewMediaVisibilityStatus(mediaToToggle);
    }

    public void addAllMedia(List<Media> mediaList) {
        log.info("Start creating all media");
        for (Media media : mediaList) {
            if (Boolean.TRUE.equals(mediaRepository.findByIdAndAnalyzedTrue(media.getId()))) {
                log.info("this media {} already analyzed !", media.getId());
            } else {
                Boolean textFilter = contentTextFiltering.textFiltering(media);
                if (Boolean.TRUE.equals(textFilter))
                    filterMedia.filterContent(media);
                media.setAnalyzed(Boolean.TRUE);
            }
        }
        eventPublisher.publishEvent(new MediaCreatedEvent(this, mediaRepository.saveAll(mediaList)));
    }
}
