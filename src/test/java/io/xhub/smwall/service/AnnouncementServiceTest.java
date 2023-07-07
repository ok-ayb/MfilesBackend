package io.xhub.smwall.service;

import com.querydsl.core.types.Predicate;
import io.xhub.smwall.commands.AnnouncementAddCommand;
import io.xhub.smwall.commands.AnnouncementUpdateCommand;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
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
    private Announcement announcement;
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
                Instant.now().plus(2, ChronoUnit.DAYS),
                Instant.now().plus(1, ChronoUnit.DAYS));

        announcement = new Announcement();
        announcement.setId("1234");
        announcement.setTitle("This is a title that contains some text");
        announcement.setDescription("This is a description that contains some text");
        announcement.setStartDate(Instant.now().plus(1825  , ChronoUnit.DAYS));
        announcement.setEndDate(Instant.now().plus(2190, ChronoUnit.DAYS));
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
    void should_throwBusinessException_when_AnnouncementAlreadyExist() {
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
        announcement.setEndDate(Instant.now().plusSeconds(3800));
        announcement.setStartDate(Instant.now());


        when(announcementRepository.findById(announcement.getId())).thenReturn(Optional.of(announcement));

        Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcement.getId(), updateCommand);

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
        announcement.setId("announcementId");

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

    @Test
    public void should_returnAllAnnouncements_WithoutPredicate() {
        // given
        Pageable pageable = mock(Pageable.class);
        Announcement announcement1 = new Announcement();
        Announcement announcement2 = new Announcement();
        List<Announcement> announcements = Arrays.asList(announcement1, announcement2);
        Page<Announcement> expectedPage = new PageImpl<>(announcements);
        when(announcementRepository.findAll(pageable)).thenReturn(expectedPage);

        // when
        Page<Announcement> resultPage = announcementService.getAllAnnouncement(null, pageable);

        // then
        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
        assertTrue(resultPage.getContent().containsAll(announcements));
        verify(announcementRepository, times(1)).findAll(pageable);
    }

    @Test
    public void should_returnFilteredAnnouncements_WithPredicate() {
        Predicate predicate = mock(Predicate.class);
        Pageable pageable = mock(Pageable.class);
        Announcement announcement1 = new Announcement();
        Announcement announcement2 = new Announcement();
        List<Announcement> announcements = Arrays.asList(announcement1, announcement2);
        Page<Announcement> expectedPage = new PageImpl<>(announcements);
        when(announcementRepository.findAll(predicate, pageable)).thenReturn(expectedPage);

        Page<Announcement> resultPage = announcementService.getAllAnnouncement(predicate, pageable);

        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
        assertTrue(resultPage.getContent().containsAll(announcements));
        verify(announcementRepository, times(1)).findAll(predicate, pageable);
    }

    @Test
    public void should_returnAnnouncementById_when_Exists() {
        when(announcementRepository.findById(this.announcement.getId())).thenReturn(Optional.ofNullable(this.announcement));
        Announcement resultAnnouncement = announcementService.getAnnouncementById(this.announcement.getId());
        assertNotNull(resultAnnouncement);
        assertSame(this.announcement, resultAnnouncement);
        verify(announcementRepository, times(1)).findById(this.announcement.getId());
    }

    @Test
    public void should_throwBusinessException_when_IdDoesNotExist() {
        when(announcementRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> announcementService.getAnnouncementById(anyString()));
        verify(announcementRepository, times(1)).findById(anyString());
    }
}
