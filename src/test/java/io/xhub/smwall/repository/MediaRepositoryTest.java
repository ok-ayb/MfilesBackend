package io.xhub.smwall.repository;


import io.xhub.smwall.domains.Media;
import io.xhub.smwall.repositories.MediaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MediaRepositoryTest {

    @Mock
    private MediaRepository mediaRepository;

    @Test
    void should_returnTrue_when_pinnedMediaExists() {

        Media media = new Media();
        media.setId("mediaId");
        media.setText("text");
        media.setHidden(false);
        media.setPinned(true);

        when(mediaRepository.findByPinned(true)).thenReturn(Optional.of(media));

        Optional<Media> optionalMedia = mediaRepository.findByPinned(true);

        assertTrue(optionalMedia.isPresent());

        Media mediaToVerify = optionalMedia.get();
        assertEquals(media.getId(), mediaToVerify.getId());
        assertEquals(media.getText(), mediaToVerify.getText());
        assertEquals(media.getPinned(), mediaToVerify.getPinned());
        assertEquals(media.getHidden(), mediaToVerify.getHidden());


    }

    @Test
    void should_returnFalse_when_pinnedMediaDoesNotExist() {

        when(mediaRepository.findByPinned(anyBoolean())).thenReturn(Optional.empty());

        Optional<Media> optionalMedia = mediaRepository.findByPinned(true);

        assertFalse(optionalMedia.isPresent());

    }


}
