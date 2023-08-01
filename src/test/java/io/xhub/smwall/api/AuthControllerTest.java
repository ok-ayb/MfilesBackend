package io.xhub.smwall.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xhub.smwall.commands.LoginCommand;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.constants.CookiesNames;
import io.xhub.smwall.service.AuthService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "testUser")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    public void should_logoutSuccessfully_and_removeJwtCookie() throws Exception {

        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("test@gmail.com");
        loginCommand.setPassword("Pa$$d1234");

        mockMvc.perform(post(ApiPaths.V1 + ApiPaths.AUTH + ApiPaths.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginCommand)))
                .andExpect(cookie().exists((CookiesNames.JWT_KEY)))
                .andExpect(status().isOk());


        // Perform logout
        mockMvc.perform(post(ApiPaths.V1 + ApiPaths.AUTH + ApiPaths.LOGOUT))
                .andExpect(status().isOk())
                .andExpect(cookie().maxAge(CookiesNames.JWT_KEY, 0));
    }

}
