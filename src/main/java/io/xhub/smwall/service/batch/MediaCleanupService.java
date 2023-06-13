package io.xhub.smwall.service.batch;

import io.xhub.smwall.domains.Media;
import io.xhub.smwall.repositories.MediaRepository;
import io.xhub.smwall.utlis.URLUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaCleanupService {
    private final MediaRepository mediaRepository;
    private int deletedMediaCount = 0;

    @Scheduled(cron = "0 0 0,12 * * *")
    public void deleteInvalidMedia() {
        log.info("Start media cleanup");

        Iterable<Media> allMedia = mediaRepository.findAll();

        for (Media media : allMedia) {
            if (!URLUtils.isURLValid(media.getUrl())) {
                mediaRepository.delete(media);
                deletedMediaCount++;
            }
        }

        log.info("Media cleanup completed. Total media deleted: {}", deletedMediaCount);
    }
}
