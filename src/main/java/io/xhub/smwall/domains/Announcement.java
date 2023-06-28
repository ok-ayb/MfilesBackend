package io.xhub.smwall.domains;

import io.xhub.smwall.commands.AnnouncementAddCommand;
import io.xhub.smwall.commands.AnnouncementUpdateCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.function.BiPredicate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Document(collection = "announcements")
public class Announcement extends AbstractAuditingDocument {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("startDate")
    private Instant startDate;

    @Field("endDate")
    private Instant endDate;

    @Field("deleted")
    private boolean deleted = false;

    public static Announcement create(final BiPredicate<Instant, Instant> thereAnyAnnouncement, final AnnouncementAddCommand command) {

        if (thereAnyAnnouncement.test(command.getStartDate(), command.getEndDate()))
            throw new BusinessException(ApiClientErrorCodes.ANNOUNCEMENT_ALREADY_EXISTS.getErrorMessage());

        Announcement announcement = new Announcement();
        announcement.setTitle(command.getTitle());
        announcement.setDescription(command.getDescription());
        announcement.setStartDate(command.getStartDate());
        announcement.setEndDate(command.getEndDate());

        return announcement;
    }

    public void delete() {
        setDeleted(true);
    }

    public void update(final TriConsumer<Instant, Instant, String> alreadyExist, final AnnouncementUpdateCommand command) {
        alreadyExist.accept(command.getEndDate(), this.startDate, this.id);

        if (command.getTitle() != null) {
            this.setTitle(command.getTitle());
        }
        if (command.getDescription() != null) {
            this.setDescription(command.getDescription());
        }
        if (command.getEndDate() != null) {
            this.setEndDate(command.getEndDate());
        }
        if (command.getStartDate() != null) {
            this.setStartDate(command.getStartDate());
        }
    }
}
