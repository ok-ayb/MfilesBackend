package io.xhub.smwall.service;

import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService {
    private final MediaRepository mediaRepository;

    public Page<Media> getAllMedia(Pageable pageable) {
        log.info("Start getting all media");
        return mediaRepository.findAll(pageable);
    }

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

        if (mediaToToggle.isPinned()) {
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
    }

    public void changeMediaVisibility(String mediaId) {
        log.info("Start updating media visibility");
        Media postToToggle = getMediaById(mediaId);

        postToToggle.setHidden(!postToToggle.isHidden());
        mediaRepository.save(postToToggle);
    }
}