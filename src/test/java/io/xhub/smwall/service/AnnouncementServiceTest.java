package io.xhub.smwall.service;

import io.xhub.smwall.commands.AnnouncementAddCommand;
import io.xhub.smwall.commands.AnnouncementUpdateCommand;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.events.announcement.AnnouncementCreatedEvent;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.util.Optional;

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
    private AnnouncementUpdateCommand updateCommand;

    @BeforeEach
    void setUp() {
        addCommand = new AnnouncementAddCommand(
                "Test Announcement title",
                "This is a test announcement description",
                Instant.now().plusSeconds(3600),
                Instant.now().plusSeconds(3700));
        updateCommand = new AnnouncementUpdateCommand(
                "New Title",
                "New Description",
                Instant.now().plusSeconds(3800),
                Instant.now().plusSeconds(3900));
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
    @Test
    void should_updateAnnouncement_when_AnnouncementIsValid() {
        Announcement announcement = new Announcement();
        announcement.setId("announcementId");
        announcement.setTitle("Old Title");
        announcement.setDescription("Old Description");
        announcement.setStartDate(Instant.now().plusSeconds(3600));
        announcement.setEndDate(Instant.now().plusSeconds(3700));

        when(announcementRepository.findById(announcement.getId())).thenReturn(Optional.of(announcement));
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcement.getId(),updateCommand);

        assertNotNull(updatedAnnouncement);
        assertEquals(announcement.getId(), updatedAnnouncement.getId());
        assertEquals(updateCommand.getTitle(), updatedAnnouncement.getTitle());
        assertEquals(updateCommand.getDescription(), updatedAnnouncement.getDescription());
        assertEquals(updateCommand.getStartDate(), updatedAnnouncement.getStartDate());
        assertEquals(updateCommand.getEndDate(), updatedAnnouncement.getEndDate());

        verify(announcementRepository, times(1)).findById(announcement.getId());
    }

    @Test
    void should_deleteAnnouncement_when_validId() {

        Announcement announcement = new Announcement();
        announcement.setId("announcementId" );

        when(announcementRepository.findById(announcement.getId())).thenReturn(Optional.of(announcement));

        announcementService.deleteAnnouncementById(announcement.getId());

        verify(announcementRepository, times(1)).findById(announcement.getId());
    }

    @Test
    void should_throwBusinessException_when_invalidId() {

        when(announcementRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            announcementService.deleteAnnouncementById(anyString());
        });

        verify(announcementRepository, never()).deleteById(anyString());
    }
}
