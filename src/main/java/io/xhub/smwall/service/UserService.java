package io.xhub.smwall.service;


import io.xhub.smwall.commands.UserAddCommand;
import io.xhub.smwall.commands.UserUpdateCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.User;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.UserRepository;
import io.xhub.smwall.utlis.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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

    public User getUserById(String id) {
        log.info("Start getting user by id");
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiClientErrorCodes.USER_NOT_FOUND.getErrorMessage()));
    }

    public User updateUser(String userId, UserUpdateCommand userUpdateCommand) {
        log.info("Start updating user by id {} :", userId);
        User user = getUserById(userId);
        user.update(userUpdateCommand);
        return userRepository.save(user);
    }

    public User createUser(final UserAddCommand userAddCommand) {
        log.info("Start creating a new user");
        return userRepository.save(User.create(userAddCommand));
    }
}
