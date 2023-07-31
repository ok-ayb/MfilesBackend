package io.xhub.smwall.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xhub.smwall.commands.WallSettingAddCommand;
import io.xhub.smwall.commands.WallSettingUpdateCommand;
import io.xhub.smwall.domains.BinaryImage;
import io.xhub.smwall.domains.WallSetting;
import io.xhub.smwall.dto.WallSettingDTO;
import io.xhub.smwall.mappers.WallSettingMapper;
import io.xhub.smwall.service.WallService;
import io.xhub.smwall.utlis.JsonUtils;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WithMockUser(username = "testUser")
class WallControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WallService mockWallService;

    @MockBean
    private WallSettingMapper mockWallSettingMapper;

    @Test
    void should_getLatestWallSetting() throws Exception {

        BinaryImage logo = new BinaryImage();
        logo.setValue(new Binary(BsonBinarySubType.BINARY, "content".getBytes()));
        logo.setContentType("contentType");

        WallSetting wallSetting = new WallSetting(
                "id",
                "title",
                logo
        );

        when(mockWallService.getLatestWallSetting()).thenReturn(wallSetting);

        WallSettingDTO wallSettingDTO = new WallSettingDTO(
                "123",
                "This is the title",
                "This is the filename",
                "This is the logoBase64"
        );

        when(mockWallSettingMapper.toDTO(any(WallSetting.class))).thenReturn(wallSettingDTO);

        mockMvc.perform(get("/api/v1/wall/settings/latest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect((status().isOk()))
                .andExpect(content().json(JsonUtils.toJsonString(wallSettingDTO)))
                .andReturn().getResponse();
    }

}
