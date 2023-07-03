package io.xhub.smwall.events.announcement;

import io.xhub.smwall.domains.Announcement;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AnnouncementUpdatedEvent extends ApplicationEvent {
    private final Announcement announcement;

    public AnnouncementUpdatedEvent(Object source, Announcement announcement) {
        super(source);
        this.announcement = announcement;
    }
}