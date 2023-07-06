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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // Test methods and other code here

    @BeforeEach
    public void setup() {
        Announcement announcement = new Announcement();
        announcement.setId("123");
        announcement.setStartDate(Instant.now().plusSeconds(3600));
        announcement.setEndDate(Instant.now().plusSeconds(7200));
        announcementRepository.save(announcement);
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


}