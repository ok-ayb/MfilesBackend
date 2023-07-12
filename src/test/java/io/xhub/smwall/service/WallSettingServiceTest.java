package io.xhub.smwall.service;

import io.xhub.smwall.commands.WallSettingAddCommand;
import io.xhub.smwall.commands.WallSettingUpdateCommand;
import io.xhub.smwall.domains.BinaryImage;
import io.xhub.smwall.domains.WallSetting;
import io.xhub.smwall.repositories.WallSettingRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static io.xhub.smwall.utlis.AssertUtils.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WallSettingServiceTest {
    @Mock
    private WallSettingRepository wallSettingRepository;
    @InjectMocks
    private WallService wallService;
    private WallSettingAddCommand addCommand;
    private WallSettingUpdateCommand updateCommand;
    private Validator validator;

    @BeforeEach
    void setUp() {
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
        updateCommand = new WallSettingUpdateCommand(
                "Example Title",
                new MockMultipartFile(
                        "logo",
                        "logo.png",
                        "image/png",
                        new byte[]{1, 2, 3, 4}
                )
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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

    @Test
    void should_updateWallSetting_and_perform_Validation_when_WallSettingIsValid() {
        WallSetting wallSetting = new WallSetting();
        wallSetting.setId("wallSettingId");
        wallSetting.setTitle("Old Title");

        Mockito.when(wallSettingRepository.findById(wallSetting.getId())).thenReturn(Optional.of(wallSetting));
        Mockito.when(wallSettingRepository.save(Mockito.any(WallSetting.class))).thenReturn(wallSetting);

        WallSetting updateWallSetting = wallService.updateWallSetting(wallSetting.getId(), updateCommand);
        Set<ConstraintViolation<WallSettingUpdateCommand>> violations = validator.validate(updateCommand);

        assertNotNull(updateWallSetting);
        assertEquals(wallSetting.getId(), updateWallSetting.getId());
        assertEquals(wallSetting.getTitle(), updateWallSetting.getTitle());

        BinaryImage expectedLogo = wallSetting.getLogo();
        BinaryImage actualLogo = updateWallSetting.getLogo();
        assertEquals(expectedLogo.getFilename(), actualLogo.getFilename());
        assertEquals(expectedLogo.getContentType(), actualLogo.getContentType());

        assertTrue(violations.isEmpty());

        Mockito.verify(wallSettingRepository, times(1)).findById(wallSetting.getId());
    }

}
