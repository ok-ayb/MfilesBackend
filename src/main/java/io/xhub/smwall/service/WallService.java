package io.xhub.smwall.service;

import io.xhub.smwall.commands.WallSettingAddCommand;
import io.xhub.smwall.commands.WallSettingUpdateCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.WallSetting;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.WallSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class WallService {
    private final WallSettingRepository wallSettingRepository;

    public WallSetting addWallSetting(WallSettingAddCommand command) {
        log.info("Start creating a wall setting");

        try {
            return wallSettingRepository.save(WallSetting.create(command));
        } catch (IOException e) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    public WallSetting updateWallSetting(String id, WallSettingUpdateCommand command) {
        log.info("Start partially updating a wall setting");

        try {
            WallSetting wallSetting = getWallSettingById(id);
            wallSetting.update(command);

            return wallSettingRepository.save(wallSetting);
        } catch (IOException e) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    public WallSetting getLatestWallSetting() {
        log.info("Start getting latest wall setting");

        return wallSettingRepository.findFirstByOrderByCreatedAtDesc()
                .orElse(null);
    }

    private WallSetting getWallSettingById(String id) {
        log.info("Start getting wall setting by ID");

        return wallSettingRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiClientErrorCodes.WALL_SETTING_NOT_FOUND.getErrorMessage()));
    }
}
