package io.xhub.smwall.service;

import io.xhub.smwall.domains.Sponsor;
import io.xhub.smwall.domains.WallFooter;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.WallFooterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WallFooterServiceTest {
    @Mock
    private WallFooterRepository wallFooterRepository;
    @InjectMocks
    private WallFooterService wallFooterService;
    private WallFooter wallFooter;


    @BeforeEach
    void setUp() {
        wallFooterService = new WallFooterService(wallFooterRepository);
        wallFooter = new WallFooter();
        wallFooter.setId("1");
        wallFooter.setCoOrganizer("Organizer");
        wallFooter.setInstitutionalPartners(Arrays.asList("Partner 1", "Partner 2"));
        wallFooter.setSponsors(Arrays.asList(
                new Sponsor("Sponsor 1", "Type 1", "http://sponsor1.com/logo"),
                new Sponsor("Sponsor 2", "Type 2", "http://sponsor2.com/logo"))
        );
        wallFooter.setLogoUrl("http://example.com/logo");
        wallFooter.setTimestamp(Instant.now());

    }

    @Test
    void should_getWallFooter() throws BusinessException {
        when(wallFooterRepository.findFirstByIdIsNotNullOrderByTimestampDesc())
                .thenReturn(Optional.of(wallFooter));

        WallFooter result = wallFooterService.getWallFooterInfo();

        assertEquals(wallFooter, result);

        verify(wallFooterRepository, times(1)).findFirstByIdIsNotNullOrderByTimestampDesc();
    }

    @Test
    void should_throwBusinessException_When_itDoesNotExist() {
        when(wallFooterRepository.findFirstByIdIsNotNullOrderByTimestampDesc()).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> wallFooterService.getWallFooterInfo());


        verify(wallFooterRepository, times(1)).findFirstByIdIsNotNullOrderByTimestampDesc();
    }
}
