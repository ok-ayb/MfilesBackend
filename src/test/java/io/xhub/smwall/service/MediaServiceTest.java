package io.xhub.smwall.service;

import io.xhub.smwall.domains.Media;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.filter.ContentTextFiltering;
import io.xhub.smwall.filter.filterMedia.FilterMedia;
import io.xhub.smwall.repositories.MediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private MediaRepository mediaRepository;
    @Mock
    private WebSocketService webSocketService;
    @Mock
    private ContentTextFiltering contentTextFiltering;
    @Mock
    private FilterMedia filterMedia;


    @InjectMocks
    private MediaService mediaService;


    @BeforeEach
    public void setup() {
        mediaService = new MediaService(
                applicationEventPublisher,
                mediaRepository,
                webSocketService,
                contentTextFiltering,
                filterMedia
        );
    }

    @Test
    public void should_throwBusinessException_when_mediaIsHidden() {
        Media media = new Media();
        media.setId("test");
        media.setText("text");
        media.setHidden(true);

        doReturn(Optional.of(media))
                .when(mediaRepository).findById(anyString());

        assertThrows(BusinessException.class, () -> {
            mediaService.updateMediaPinning(media.getId());
        });
    }

    @Test
    public void should_updateMediaPinning() {
        Media media = new Media();
        media.setId("mediaId");
        media.setText("text");
        media.setHidden(false);

        when(mediaRepository.findById(media.getId())).thenReturn(Optional.of(media));
        mediaService.updateMediaPinning(media.getId());

        assertEquals(true, media.getPinned());

        verify(mediaRepository).save(media);
        verify(webSocketService).sendPinnedMedia(media);
    }

}
