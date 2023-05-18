package io.xhub.smwall.service;

import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.User;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.UserRepository;
import io.xhub.smwall.utlis.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User getUser() {
        return SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findFirstByEmailIgnoreCase)
                .orElseThrow(() -> new BusinessException(ApiClientErrorCodes.USER_NOT_FOUND.getErrorMessage()));
    }

    public User findUserByLogin(String login) {
        return userRepository.findFirstByEmailIgnoreCase(login)
                .orElseThrow(() -> new BusinessException(ApiClientErrorCodes.USER_NOT_FOUND.getErrorMessage()));
    }
}
