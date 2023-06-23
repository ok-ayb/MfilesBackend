package io.xhub.smwall.events.announcement;

import io.xhub.smwall.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoCurrentAnnouncementFoundEventListener implements ApplicationListener<NoCurrentAnnouncementFoundEvent> {
    /**
     * TODO: This is just for demo purpose and needs to be changed ASAP!
     */
    private final WebSocketService webSocketService;

    @Override
    public void onApplicationEvent(NoCurrentAnnouncementFoundEvent event) {
        webSocketService.sendLastAnnouncementDeletedIntent();
    }
}