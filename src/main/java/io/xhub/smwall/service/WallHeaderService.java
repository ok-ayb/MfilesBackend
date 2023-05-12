package io.xhub.smwall.service;

import io.xhub.smwall.domains.WallHeader;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.repositories.WallHeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WallHeaderService {

    private final WallHeaderRepository wallHeaderRepository;

    public WallHeader getWallHeaderInfo() throws BusinessException {
        log.info("Begin fetching wall header information");
        return wallHeaderRepository.findFirstByIdIsNotNullOrderByTimestampDesc().orElseThrow(() -> new BusinessException(ApiClientErrorCodes.WALL_HEADER_NOT_FOUND.getErrorMessage()));
    }
}
