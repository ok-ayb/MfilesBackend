package io.xhub.smwall.service;

import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.WallFooter;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.WallFooterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WallFooterService {
    private final WallFooterRepository wallFooterRepository;

    public WallFooter getWallFooterInfo() throws BusinessException {
        log.info("Begin fetching wall footer information");
        return wallFooterRepository.findFirstByIdIsNotNullOrderByTimestampDesc().orElseThrow(() -> new BusinessException(ApiClientErrorCodes.WALL_FOOTER_NOT_FOUND.getErrorMessage()));
    }
}
