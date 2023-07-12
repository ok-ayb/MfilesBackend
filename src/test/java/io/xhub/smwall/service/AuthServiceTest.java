package io.xhub.smwall.service;

import io.xhub.smwall.commands.LoginCommand;
import io.xhub.smwall.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;
    private LoginCommand loginCommand;

    @BeforeEach
    void setup() {
        loginCommand = new LoginCommand();
        loginCommand.setEmail("email@test.com");
        loginCommand.setPassword("password");
    }

    @Test
    void should_returnValidToken_when_AuthenticationSucceeds() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(jwtTokenProvider.generateToken(any(Authentication.class)))
                .thenReturn("validToken");

        // Act
        String token = authService.authenticate(loginCommand);

        // Assert
        assertEquals("validToken", token);
    }

    @Test
    public void should_throwException_when_AuthenticationFails() {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("test@example.com");
        loginCommand.setPassword("InvalidPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(AuthenticationServiceException.class);

        assertThrows(AuthenticationServiceException.class, () -> authService.authenticate(loginCommand));
    }
}
