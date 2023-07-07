package io.xhub.smwall.repository;

import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.repositories.AnnouncementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@AutoConfigureTestEntityManager
@TestPropertySource(properties = {
        "spring.data.mongodb.database=test"
})
public class AnnouncementRepositoryTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    private Announcement announcement;

    // Test methods and other code here
    @BeforeEach
    public void setup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneId.of("UTC"));

        Announcement announcement = new Announcement();
        announcement.setId("123");
        announcement.setTitle("this is the title");
        announcement.setDescription("This is a description that contains some text, thank you!");
        announcement.setStartDate(Instant.parse(formatter.format(Instant.now().plusSeconds(3600))));
        announcement.setEndDate(Instant.parse(formatter.format(Instant.now().plusSeconds(7200))));
        this.announcement = announcementRepository.save(announcement);
    }

    @AfterEach
    public void cleanup() {
        mongoTemplate.getCollection("announcements").drop();
    }

    @Test
    public void should_returnFalse_when_announcement_notExistsByStartDateLessThanEqualAndEndDateGreaterThanEqual() {

        boolean result = announcementRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(Instant.now(), Instant.now().plusSeconds(3600));

        assertFalse(result);
    }

    @Test
    public void should_returnTrue_when_announcementExistsByStartDateLessThanEqualAndEndDateGreaterThanEqual() {

        boolean result = announcementRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(Instant.now().plusSeconds(5400), Instant.now().plusSeconds(3600));

        assertTrue(result);
    }

    @Test
    public void should_deleteAnnouncement_when_validId() {
        announcementRepository.deleteById("announcementId");
        boolean result = announcementRepository.existsById("announcementId");
        assertFalse(result);
    }

    @Test
    public void should_returnAnnouncementById_when_Exists() {
        // given
        String id = announcement.getId();

        // when
        Optional<Announcement> announcementOptional = announcementRepository.findById(id);

        // then
        assertTrue(announcementOptional.isPresent());
        Announcement announcement = announcementOptional.get();
        assertEquals(this.announcement.getDescription(), announcement.getDescription());
        assertEquals(this.announcement.getStartDate(), announcement.getStartDate());
        assertEquals(this.announcement.getEndDate(), announcement.getEndDate());
        assertEquals(this.announcement.getTitle(), announcement.getTitle());
    }

    @Test
    public void should_returnFalse_when_announcementDoesNotExist() {
        String nonExistingId = "nonExistingId";

        Optional<Announcement> announcementOptional = announcementRepository.findById(nonExistingId);

        assertFalse(announcementOptional.isPresent());
    }

}