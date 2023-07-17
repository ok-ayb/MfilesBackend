package io.xhub.smwall.repository;


import io.xhub.smwall.domains.WallHeader;
import io.xhub.smwall.repositories.WallHeaderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WallHeaderRepositoryTest {
    @Mock
    private WallHeaderRepository wallHeaderRepository;


    @Test
    public void should_returnWallHeader_when_itExists() {
        WallHeader wallHeader = new WallHeader();

        when(wallHeaderRepository.findFirstByIdIsNotNullOrderByTimestampDesc())
                .thenReturn(Optional.of(wallHeader));

        Optional<WallHeader> optionalWallHeader = wallHeaderRepository.findFirstByIdIsNotNullOrderByTimestampDesc();
        assertTrue(optionalWallHeader.isPresent());
        assertEquals(wallHeader, optionalWallHeader.get());
        verify(wallHeaderRepository, times(1)).findFirstByIdIsNotNullOrderByTimestampDesc();
    }

    @Test
    public void should_returnFalse_when_itDoesNotExist() {

        when(wallHeaderRepository.findFirstByIdIsNotNullOrderByTimestampDesc())
                .thenReturn(Optional.empty());

        Optional<WallHeader> result = wallHeaderRepository.findFirstByIdIsNotNullOrderByTimestampDesc();

        assertFalse(result.isPresent());
    }
}
