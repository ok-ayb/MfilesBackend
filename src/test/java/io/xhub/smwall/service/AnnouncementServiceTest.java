package io.xhub.smwall.service;

import io.xhub.smwall.commands.AnnouncementAddCommand;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.events.announcement.AnnouncementCreatedEvent;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private AnnouncementRepository announcementRepository;
    @InjectMocks
    private AnnouncementService announcementService;
    private AnnouncementAddCommand addCommand;

    @BeforeEach
    void setUp() {
        addCommand = new AnnouncementAddCommand(
                "Test Announcement title",
                "This is a test announcement description",
                Instant.now().plusSeconds(3600),
                Instant.now().plusSeconds(3700));
    }

    @Test
    void should_addAnnouncement_when_AnnouncementIsValid() {
        Announcement savedAnnouncement = new Announcement();
        savedAnnouncement.setId("announcementId");
        savedAnnouncement.setTitle(addCommand.getTitle());
        savedAnnouncement.setDescription(addCommand.getDescription());
        savedAnnouncement.setStartDate(addCommand.getStartDate());
        savedAnnouncement.setEndDate(addCommand.getEndDate());

        when(announcementRepository.save(any(Announcement.class))).thenReturn(savedAnnouncement);
        when(announcementRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(any(Instant.class), any(Instant.class)))
                .thenReturn(false);
        Announcement createdAnnouncement = announcementService.addAnnouncement(addCommand);

        assertNotNull(createdAnnouncement);
        assertEquals(savedAnnouncement.getId(), createdAnnouncement.getId());
        assertEquals(savedAnnouncement.getTitle(), createdAnnouncement.getTitle());
        assertEquals(savedAnnouncement.getDescription(), createdAnnouncement.getDescription());
        assertEquals(savedAnnouncement.getStartDate(), createdAnnouncement.getStartDate());
        assertEquals(savedAnnouncement.getEndDate(), createdAnnouncement.getEndDate());

        verify(announcementRepository, times(1)).save(any(Announcement.class));
        verify(eventPublisher, times(1)).publishEvent(any(AnnouncementCreatedEvent.class));
    }

    @Test
    void should_throwsBusinessException_when_AnnouncementAlreadyExist() {
        when(announcementRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(any(Instant.class), any(Instant.class)))
                .thenReturn(true);

        assertThrows(BusinessException.class, () -> {
            announcementService.addAnnouncement(addCommand);
        });
    }


}
