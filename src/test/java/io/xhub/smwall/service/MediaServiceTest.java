package io.xhub.smwall.service;

import io.xhub.smwall.domains.Media;
import io.xhub.smwall.events.media.MediaCreatedEvent;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.filter.ContentTextFiltering;
import io.xhub.smwall.filter.filterMedia.FilterMedia;
import io.xhub.smwall.repositories.MediaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
        media.setId("id");
        media.setText("text");
        media.setHidden(true);

        doReturn(Optional.of(media))
                .when(mediaRepository).findById(media.getId());

        assertThrows(BusinessException.class, () -> {
            mediaService.updateMediaPinning(media.getId());
        });
    }

    @Test
    public void should_updateMediaPinning_when_mediaExists() {
        Media media = new Media();
        media.setId("id");
        media.setText("text");

        when(mediaRepository.findById(media.getId())).thenReturn(Optional.of(media));
        mediaService.updateMediaPinning(media.getId());

        assertEquals(true, media.getPinned());

        verify(mediaRepository).save(media);
        verify(webSocketService).sendMediaPinningStatus(media);
    }

    @Test
    void should_updateVisibility_when_mediaExists() {

        Media media = new Media();
        media.setId("id");
        media.setText("text");

        when(mediaRepository.findById(media.getId())).thenReturn(Optional.of(media));

        mediaService.updateMediaVisibility(media.getId());

        Assertions.assertEquals(true, media.getHidden());

        verify(mediaRepository).save(any(Media.class));
        verify(webSocketService).sendNewMediaVisibilityStatus(any(Media.class));
    }

    @Test
    void should_throwBusinessException_when_mediaIsNotFound() {

        when(mediaRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mediaService.updateMediaVisibility(anyString()))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void should_unpinAndHideMedia_when_mediaIsPinnedAndShown() {

        Media media = new Media();
        media.setId("id");
        media.setPinned(true);

        when(mediaRepository.findById("id")).thenReturn(Optional.of(media));

        mediaService.updateMediaVisibility("id");

        Assertions.assertEquals(false, media.getPinned());
        Assertions.assertEquals(true, media.getHidden());

        verify(mediaRepository).save(media);
        verify(webSocketService).sendNewMediaVisibilityStatus(media);
    }

    @Test
    void should_throwBusinessException_when_pinningHiddenMedia() {

        Media hiddenMedia = new Media();
        hiddenMedia.setId("id");
        hiddenMedia.setHidden(true);
        hiddenMedia.setPinned(true);

        when(mediaRepository.findById(hiddenMedia.getId())).thenReturn(Optional.of(hiddenMedia));

        assertThatThrownBy(() -> mediaService.updateMediaPinning(hiddenMedia.getId()))
                .isInstanceOf(BusinessException.class);

    }


    @Test
    void should_addAllMediaSuccessfully() {

        Media firstMedia = new Media();
        firstMedia.setId("firstId");
        firstMedia.setText("someText");

        Media secondMedia = new Media();
        secondMedia.setId("secondId");
        secondMedia.setText("anotherText");

        List<Media> mediaList = Arrays.asList(firstMedia, secondMedia);

        doReturn(false).when(mediaRepository).findByIdAndAnalyzedTrue(anyString());
        doReturn(true).when(contentTextFiltering).textFiltering(any(Media.class));

        when(mediaRepository.saveAll(mediaList)).thenReturn(mediaList);

        mediaService.addAllMedia(mediaList);

        verify(mediaRepository).saveAll(mediaList);
        verify(applicationEventPublisher).publishEvent(any(MediaCreatedEvent.class));
        Assertions.assertEquals(2, mediaList.size());
        Assertions.assertEquals("firstId", mediaList.get(0).getId());
        Assertions.assertEquals("secondId", mediaList.get(1).getId());
    }


}
