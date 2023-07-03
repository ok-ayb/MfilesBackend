package io.xhub.smwall.events.media;

import io.xhub.smwall.domains.Media;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class MediaCreatedEvent extends ApplicationEvent {
    private final List<Media> media;

    public MediaCreatedEvent(Object source, List<Media> media) {
        super(source);
        this.media = media;
    }
}
