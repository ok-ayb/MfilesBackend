package io.xhub.smwall.service;

import io.xhub.smwall.commands.WallSettingAddCommand;
import io.xhub.smwall.domains.WallSetting;
import io.xhub.smwall.repositories.WallSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WallSettingServiceTest {
    @Mock
    private WallSettingRepository wallSettingRepository;
    @InjectMocks
    private WallService wallService;
    private WallSettingAddCommand addCommand;

    @BeforeEach
    void setUp() throws IOException {
        wallService = new WallService(wallSettingRepository);
        addCommand = new WallSettingAddCommand(
                "Title 1",
                new MockMultipartFile(
                        "logo 1",
                        "logo1.png",
                        "image/png",
                        new byte[]{1, 2, 3, 4}
                )
        );
    }

    @Test
    void should_Get_Latest_Wall_Setting() throws IOException {
        WallSetting wallSetting = new WallSetting();
        wallSetting.setId("wallSettingId");
        wallSetting.setTitle(addCommand.getTitle());
        wallSetting.initLogo(addCommand.getLogo());

        when(wallSettingRepository.findFirstByOrderByCreatedAtDesc())
                .thenReturn(Optional.of(wallSetting));

        WallSetting result = wallService.getLatestWallSetting();

        assertEquals(wallSetting, result);
    }

}
