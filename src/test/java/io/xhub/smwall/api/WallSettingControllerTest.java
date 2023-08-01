package io.xhub.smwall.api;

import io.xhub.smwall.commands.WallSettingAddCommand;
import io.xhub.smwall.commands.WallSettingUpdateCommand;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.domains.WallSetting;
import io.xhub.smwall.mappers.WallSettingMapper;
import io.xhub.smwall.service.WallService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WithMockUser(username = "testuser")
public class WallSettingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WallService wallService;
    @MockBean
    private WallSettingMapper wallSettingMapper;
    private WallSettingAddCommand wallSettingAddCommand;

    @Test
    @WithMockUser(username = "testuser")
    public void should_Create_WallSetting() throws Exception {
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                "image/png",
                new byte[]{0x50, 0x4E, 0x47}
        );
        wallSettingAddCommand = new WallSettingAddCommand("titleTest", logoFile);
        when(wallService.addWallSetting(wallSettingAddCommand)).thenReturn(any(WallSetting.class));
        this.mockMvc.perform(
                        multipart(ApiPaths.V1 + ApiPaths.WALL + ApiPaths.SETTINGS)
                                .file(logoFile)
                                .param("title", "titleTest")
                                .accept(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
        verify(wallService, times(1)).addWallSetting(any(WallSettingAddCommand.class));

    }

    @Test
    public void should_Update_WallSetting_When_Valid_Id() throws Exception {
        String title = "title";
        MockMultipartFile logoFile = new MockMultipartFile(
                "logo",
                "logo.png",
                "image/png",
                new byte[]{0x50, 0x4E, 0x47}
        );
        WallSetting updatedWallSetting = new WallSetting();
        updatedWallSetting.setTitle("Updated Title");

        when(wallService.updateWallSetting(anyString(), any(WallSettingUpdateCommand.class)))
                .thenReturn(updatedWallSetting);

        mockMvc.perform(patch(ApiPaths.V1 + ApiPaths.WALL + ApiPaths.SETTINGS + "/wallSettingId")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(logoFile.getBytes())
                        .param(title, " logo file title")
                        .accept(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(wallService, times(1))
                .updateWallSetting(eq("wallSettingId"), any(WallSettingUpdateCommand.class));
    }

    @Test
    public void should_throwBusinessException_when_update_wallSetting_with_invalidId() throws Exception {
        String nonExistingAnnouncementId = "nonExistingId";

        when(wallService.updateWallSetting(anyString(), any(WallSettingUpdateCommand.class)))
                .thenReturn(null);

        mockMvc.perform(patch(ApiPaths.V1 + ApiPaths.WALL + ApiPaths.SETTINGS + nonExistingAnnouncementId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
