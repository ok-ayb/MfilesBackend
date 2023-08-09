package io.xhub.smwall.service;


import com.querydsl.core.types.Predicate;
import io.xhub.smwall.commands.UserAddCommand;
import io.xhub.smwall.commands.UserUpdateCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.User;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.UserRepository;
import io.xhub.smwall.utlis.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Predicate predicate, Pageable pageable) {
        log.info("Start getting all users");
        if (predicate == null) {
            return userRepository.findAll(pageable);
        }
        return userRepository.findAll(predicate, pageable);
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

    public void deleteUserById(String id) {
        log.info("Start deleting user with ID: {}", id);
        User user = getUserById(id);
        userRepository.delete(user);
    }

}
