package io.xhub.smwall.events.announcement;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NoCurrentAnnouncementFoundEvent extends ApplicationEvent {
    /**
     * TODO: This is just for demo purpose and needs to be changed ASAP!
     */
    public NoCurrentAnnouncementFoundEvent(Object source) {
        super(source);
    }
}