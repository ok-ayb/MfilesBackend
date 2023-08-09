package io.xhub.smwall.service;

import io.xhub.smwall.constants.RoleName;
import io.xhub.smwall.domains.Authority;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.AuthorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceTest {
    @Mock
    private AuthorityRepository authorityRepository;
    @InjectMocks
    private AuthorityService authorityService;

    @BeforeEach
    void setup() {
        Authority adminAuthority = new Authority(
                "123",
                RoleName.ROLE_ADMIN
        );
        when(authorityRepository.findAll()).thenReturn(List.of(adminAuthority));
    }

    @Test
    void should_getAllAuthorities() throws BusinessException {
        List<Authority> result = authorityService.getAllAuthorities();

        assertEquals(1, result.size());
        assertEquals(RoleName.ROLE_ADMIN, result.get(0).getName());

        verify(authorityRepository, times(1)).findAll();
    }

}
