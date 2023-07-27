package io.xhub.smwall.api;

import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.domains.Sponsor;
import io.xhub.smwall.domains.WallFooter;
import io.xhub.smwall.service.WallFooterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WallFooterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WallFooterService wallFooterService;

    private WallFooter wallFooter;


    @Test
    public void should_getWallFooterInformation() throws Exception {
        wallFooter = new WallFooter("1", "Organizer",
                Arrays.asList("Partner 1", "Partner 2"),
                Arrays.asList(
                        new Sponsor("Sponsor 1", "Type 1", "http://sponsor1.com/logo"),
                        new Sponsor("Sponsor 2", "Type 2", "http://sponsor2.com/logo")
                ),
                "http://example.com/logo",
                Instant.now()
        );

        when(wallFooterService.getWallFooterInfo()).thenReturn(wallFooter);

        mockMvc.perform(get(ApiPaths.V1 + ApiPaths.FOOTER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
