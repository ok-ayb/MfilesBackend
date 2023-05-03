package io.xhub.smwall.service;

import io.xhub.smwall.domains.Media;
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
}