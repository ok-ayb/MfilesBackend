package io.xhub.smwall.service;

import io.xhub.smwall.commands.AddWallSettingCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.BinaryImage;
import io.xhub.smwall.domains.WallSetting;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.WallSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class WallService {
    private final WallSettingRepository wallSettingRepository;

    public WallSetting addWallSetting(AddWallSettingCommand command) {
        try {
            return wallSettingRepository.save(WallSetting.create(command));
        } catch (IOException e) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }
}
