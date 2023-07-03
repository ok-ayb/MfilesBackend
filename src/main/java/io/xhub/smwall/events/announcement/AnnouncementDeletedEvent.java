package io.xhub.smwall.events.announcement;

import io.xhub.smwall.domains.Announcement;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AnnouncementDeletedEvent extends ApplicationEvent {
    private final Announcement announcement;

    public AnnouncementDeletedEvent(Object source, Announcement announcement) {
        super(source);
        this.announcement = announcement;
    }
}