package io.xhub.smwall.api;

import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.service.MediaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WithMockUser(username = "testUser")
public class MediaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MediaService mediaService;

    @Test
    public void should_get_all_media() throws Exception {
        Media media1 = new Media();
        media1.setId("id1");
        media1.setText("text1");

        Media media2 = new Media();
        media2.setId("id2");
        media2.setText("text2");

        List<Media> mediaList = Arrays.asList(media1, media2);

        Page<Media> mediaPage = new PageImpl<>(mediaList);

        when(mediaService.getAllMedia(any(), any())).thenReturn(mediaPage);

        mockMvc.perform(get(ApiPaths.V1 + ApiPaths.MEDIA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("id1"))
                .andExpect(jsonPath("$.content[0].text").value("text1"))
                .andExpect(jsonPath("$.content[1].id").value("id2"))
                .andExpect(jsonPath("$.content[1].text").value("text2"));
    }

    @Test
    public void should_get_empty_media_list() throws Exception {
        List<Media> emptyMediaList = Collections.emptyList();
        Page<Media> emptyMediaPage = new PageImpl<>(emptyMediaList);

        when(mediaService.getAllMedia(any(), any())).thenReturn(emptyMediaPage);

        mockMvc.perform(get(ApiPaths.V1 + ApiPaths.MEDIA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    public void should_updateMediaPinningStatus_when_mediaExist() throws Exception {
        String mediaId = "sampleMediaId";

        mockMvc.perform(MockMvcRequestBuilders.put(ApiPaths.V1 + ApiPaths.MEDIA + "/" + mediaId + ApiPaths.MEDIA_PINNING_STATUS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mediaService, times(1)).updateMediaPinning(mediaId);
    }

    @Test
    public void should_updateMediaVisibilityStatus_when_mediaExist() throws Exception {
        String mediaId = "otherSampleMediaId";

        mockMvc.perform(MockMvcRequestBuilders.put(ApiPaths.V1 + ApiPaths.MEDIA + "/" + mediaId + ApiPaths.MEDIA_VISIBILITY_STATUS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mediaService, times(1)).updateMediaVisibility(mediaId);
    }
}
