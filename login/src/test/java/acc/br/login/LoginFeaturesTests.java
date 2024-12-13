package acc.br.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class LoginFeaturesTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Test
    void shouldReturnMessageWhenLoginWithInvalidCredentials() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        String requestBody = "{\"username\":\"invalid_user\",\"password\":\"wrong_pass\"}";

        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid username or password"));
    }

    @Test
    void shouldRegisterAndLoginSuccessfully() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        String requestBody = "{\"username\":\"test_user\",\"password\":\"secure_pass\"}";

        // Register user
        mockMvc.perform(post("/register")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registration successful"));

        // Login with registered user
        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"));
    }

    @Test
    void shouldRedirectToCourseRegistrationPageAfterLogin() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        String requestBody = "{\"username\":\"test_user\",\"password\":\"secure_pass\"}";

        // Login with valid credentials
        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.redirectUrl").value("/courses"));
    }
}
