package io.xhub.smwall.repository;

import io.xhub.smwall.domains.WallFooter;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.WallFooterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WallFooterRepositoryTest {
    @Mock
    private WallFooterRepository wallFooterRepository;

    @Test
    public void should_returnWallFooter_When_itExists() throws BusinessException {
        WallFooter wallFooter = new WallFooter();

        when(wallFooterRepository.findFirstByIdIsNotNullOrderByTimestampDesc()).thenReturn(Optional.of(wallFooter));

        Optional<WallFooter> result = wallFooterRepository.findFirstByIdIsNotNullOrderByTimestampDesc();

        assertTrue(result.isPresent());
        assertEquals(wallFooter, result.get());
        verify(wallFooterRepository, times(1)).findFirstByIdIsNotNullOrderByTimestampDesc();
    }

    @Test
    public void should_returnFalse_When_itDoesNotExist() throws BusinessException {

        when(wallFooterRepository.findFirstByIdIsNotNullOrderByTimestampDesc()).thenReturn(Optional.empty());

        Optional<WallFooter> result = wallFooterRepository.findFirstByIdIsNotNullOrderByTimestampDesc();

        assertFalse(result.isPresent());
    }

}

