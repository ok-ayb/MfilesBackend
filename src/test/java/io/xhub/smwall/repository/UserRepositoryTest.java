package io.xhub.smwall.repository;

import io.xhub.smwall.domains.User;
import io.xhub.smwall.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void should_findUserByEmailIgnoreCase_When_UserExists() {

        User user = new User();
        user.setEmail("email@gmail.com");

        when(userRepository.findFirstByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findFirstByEmailIgnoreCase(user.getEmail());

        assertTrue(result.isPresent());

    }

    @Test
    public void should_notFindUserByEmailIgnoreCase() {

        String invalidEmail = "nonexistent@gmail.com";

        when(userRepository.findFirstByEmailIgnoreCase(invalidEmail)).thenReturn(Optional.empty());

        Optional<User> result = userRepository.findFirstByEmailIgnoreCase(invalidEmail);

        assertEquals(Optional.empty(), result);
    }

}
