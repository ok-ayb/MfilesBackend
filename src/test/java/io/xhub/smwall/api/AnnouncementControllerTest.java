package io.xhub.smwall.api;


import io.xhub.smwall.commands.AnnouncementAddCommand;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.service.AnnouncementService;
import io.xhub.smwall.utlis.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AnnouncementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;

    AnnouncementAddCommand command;



    @Test
    @WithMockUser(username = "testuser")
    public void should_Create_NewAnnouncement() throws Exception {
        command = new AnnouncementAddCommand(
                "Valid Title",
                "This is a test announcement description",
                Instant.parse("2043-07-20T12:00:00Z"),
                Instant.parse("2041-08-21T12:00:00Z")
        );
        when(announcementService.addAnnouncement(command)).thenReturn(any(Announcement.class));

        mockMvc.perform(post(ApiPaths.V1 + ApiPaths.ANNOUNCEMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJsonString(command)))
                .andExpect(status().isCreated());

        verify(announcementService, times(1)).addAnnouncement(any(AnnouncementAddCommand.class));
    }

}
