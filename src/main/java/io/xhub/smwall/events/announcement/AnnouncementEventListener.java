package io.xhub.smwall.events.announcement;

import io.xhub.smwall.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnouncementEventListener implements ApplicationListener<AnnouncementEvent> {
    private final WebSocketService webSocketService;

    @Override
    public void onApplicationEvent(AnnouncementEvent event) {
        webSocketService.sendAnnouncement(event.getAnnouncement());
    }
}