package io.xhub.smwall.api;

import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.service.AnnouncementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnnouncementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;

    @Test
    public void should_get_AllAnnouncements() throws Exception {
        List<Announcement> announcementList = new ArrayList<>();
        Announcement announcement1 = new Announcement("1", "Announcement 1", "Description 1", Instant.parse("2023-07-20T00:00:00Z"), Instant.parse("2023-07-20T01:00:00Z"));
        Announcement announcement2 = new Announcement("2", "Announcement 2", "Description 2", Instant.parse("2023-07-20T02:00:00Z"), Instant.parse("2023-07-20T02:15:00Z"));

        announcementList.add(announcement1);
        announcementList.add(announcement2);

        when(announcementService.getAllAnnouncement(any(), any())).thenReturn(new PageImpl<>(announcementList));

        mockMvc.perform(get(ApiPaths.V1 + ApiPaths.ANNOUNCEMENTS).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(announcementService, times(1)).getAllAnnouncement(any(), any());

    }
}