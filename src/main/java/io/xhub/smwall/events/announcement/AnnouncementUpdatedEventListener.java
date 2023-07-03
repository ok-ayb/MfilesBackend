package io.xhub.smwall.events.announcement;

import io.xhub.smwall.service.AnnouncementService;
import io.xhub.smwall.service.WebSocketService;
import io.xhub.smwall.websocket.Action;
import io.xhub.smwall.websocket.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnouncementUpdatedEventListener implements ApplicationListener<AnnouncementUpdatedEvent> {
    private final WebSocketService webSocketService;
    private final AnnouncementService announcementService;

    @Override
    public void onApplicationEvent(AnnouncementUpdatedEvent event) {
        webSocketService.broadcastClosestAnnouncement(new Payload(Action.READ, announcementService.getClosestAnnouncement()));
    }
}