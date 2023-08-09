package io.xhub.smwall.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xhub.smwall.commands.UserUpdateCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.constants.RoleName;
import io.xhub.smwall.domains.Authority;
import io.xhub.smwall.domains.User;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "testUser")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    UserUpdateCommand userUpdateCommand;

    @Test
    public void should_updateUser_when_userExists() throws Exception {

        User user = new User();

        user.setId("123");
        user.setEmail("email@gmail.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");

        Authority adminAuthority = new Authority(
                "6466001f342fd96df0b30fae",
                RoleName.ROLE_ADMIN
        );
        Set<Authority> authorities = new HashSet<>();
        authorities.add(adminAuthority);
        userUpdateCommand = new UserUpdateCommand(
                "NewFirstName",
                "NewLastName",
                "new-email@gmail.com",
                authorities
        );

        when(userService.updateUser(user.getId(), userUpdateCommand)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateCommand)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(userService, times(1))
                .updateUser(anyString(), any(UserUpdateCommand.class));

    }

    @Test
    public void should_throwBusinessException_when_userDoesNotExist() throws Exception {

        String invalidId = "invalidId";

        when(userService.updateUser(anyString(), any(UserUpdateCommand.class)))
                .thenThrow(new BusinessException(ApiClientErrorCodes.USER_NOT_FOUND.getErrorMessage()));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        verify(userService, times(0))
                .updateUser(anyString(), any(UserUpdateCommand.class));
    }

}
