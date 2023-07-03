package io.xhub.smwall.events.media;

import io.xhub.smwall.service.WebSocketService;
import io.xhub.smwall.websocket.Action;
import io.xhub.smwall.websocket.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MediaCreatedEventListener implements ApplicationListener<MediaCreatedEvent> {
    private final WebSocketService webSocketService;

    @Override
    public void onApplicationEvent(MediaCreatedEvent event) {
        webSocketService.broadcastMedia(new Payload(Action.CREATE, event.getMedia()));
    }
}
