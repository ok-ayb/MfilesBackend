package io.xhub.smwall.api;


import io.xhub.smwall.commands.AnnouncementAddCommand;
import io.xhub.smwall.commands.AnnouncementUpdateCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.exceptions.BusinessException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WithMockUser(username = "testuser")
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;

    AnnouncementAddCommand command;


    @Test
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

    @Test
    public void should_Update_Announcement_When_Valid_Id() throws Exception {
        AnnouncementUpdateCommand updateCommand = new AnnouncementUpdateCommand(
                "Updated Title",
                "Updated announcement description ",
                Instant.parse("2024-11-20T12:00:00Z"),
                Instant.parse("2024-10-21T12:00:00Z")
        );
        Announcement updatedAnnouncement = new Announcement(
                "announcementId",
                "Updated Title",
                "Updated announcement description",
                Instant.parse("2024-09-20T12:00:00Z"),
                Instant.parse("2024-08-21T12:00:00Z")
        );
        when(announcementService.updateAnnouncement(anyString(), any(AnnouncementUpdateCommand.class)))
                .thenReturn(updatedAnnouncement);

        mockMvc.perform(patch(ApiPaths.V1 + ApiPaths.ANNOUNCEMENTS + "/announcementId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJsonString(updateCommand)))
                .andExpect(status().isOk());
        verify(announcementService, times(1))
                .updateAnnouncement(anyString(), any(AnnouncementUpdateCommand.class));
    }

    @Test
    public void should_throwBusinessException_when_update_announcement_with_invalidId() throws Exception {
        String nonExistingAnnouncementId = "nonExistingId";

        when(announcementService.updateAnnouncement(anyString(), any(AnnouncementUpdateCommand.class))).thenReturn(null);

        mockMvc.perform(patch(ApiPaths.V1 + ApiPaths.ANNOUNCEMENTS + nonExistingAnnouncementId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(announcementService, times(0)).updateAnnouncement(anyString(), any(AnnouncementUpdateCommand.class));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void should_deleteAnnouncement_when_validId() throws Exception {
        Announcement announcement = new Announcement();
        announcement.setId("announcementId");

        when(announcementService.getAnnouncementById(announcement.getId())).thenReturn(announcement);

        mockMvc.perform(delete("/api/v1/announcements/{id}", announcement.getId()))
                .andExpect(status().isNoContent());

        verify(announcementService, times(1)).deleteAnnouncementById(announcement.getId());

    }

    @Test
    @WithMockUser(username = "testUser")
    public void should_throwBusinessException_when_delete_Announcement_with_invalidId() throws Exception {
        doThrow(new BusinessException(ApiClientErrorCodes.ANNOUNCEMENT_NOT_FOUND.getErrorMessage()))
                .when(announcementService).deleteAnnouncementById("invalidId");

        mockMvc.perform(delete("/api/v1/announcements/{id}", "invalidId"))
                .andExpect(status().isBadRequest());

        verify(announcementService).deleteAnnouncementById("invalidId");
    }

}
