package io.xhub.smwall.domains;

import io.xhub.smwall.commands.AnnouncementCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.function.BiPredicate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "announcements")
public class Announcement {
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

    public void delete() {
        setDeleted(true);
    }


    public static Announcement create(final BiPredicate<Instant, Instant> ThereAnyAnnouncement, final AnnouncementCommand command) {

        if (ThereAnyAnnouncement.test(command.getStartDate(), command.getEndDate()))
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());

        Announcement announcement = new Announcement();
        announcement.setTitle(command.getTitle());
        announcement.setDescription(command.getDescription());
        announcement.setStartDate(command.getStartDate());
        announcement.setEndDate(command.getEndDate());

        return announcement;
    }
}
