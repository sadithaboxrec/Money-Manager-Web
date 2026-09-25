package com.sadi.FinanceManager.controller;





import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.repo.ProfileRepo;
import com.sadi.FinanceManager.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileRepo profileRepo;

    @MockBean
    private EmailService emailServiceImpl;


    @Test
    void registerProfileSuccessfully() throws Exception {

        String email = "test" + UUID.randomUUID() + "@gmail.com";

        String requestBody = """
                {
                    "fullName": "Test User",
                    "email": "%s",
                    "password": "Password123"
                }
                """.formatted(email);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }


    @Test
    void duplicateEmailShouldBeRejected() throws Exception {

        String email = "duplicate" + UUID.randomUUID() + "@gmail.com";

        String requestBody = """
                {
                    "fullName": "Test User",
                    "email": "%s",
                    "password": "Password123"
                }
                """.formatted(email);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotAcceptable());
    }


    @Test
    void activateProfileSuccessfully() throws Exception {

        Profile profile = new Profile();

        profile.setFullName("Activation Test");
        profile.setEmail("activation" + UUID.randomUUID() + "@gmail.com");
        profile.setPassword("Password123");
        profile.setActivationToken(UUID.randomUUID().toString());
        profile.setIsActive(false);

        profileRepo.save(profile);

        mockMvc.perform(get("/activateprofile")
                        .param("token", profile.getActivationToken()))
                .andExpect(status().isOk());
    }


    @Test
    void inactiveAccountCannotLogin() throws Exception {

        String email = "inactive" + UUID.randomUUID() + "@gmail.com";

        Profile profile = new Profile();

        profile.setFullName("Inactive User");
        profile.setEmail(email);
        profile.setPassword("$2a$10$example");
        profile.setIsActive(false);

        profileRepo.save(profile);

        String requestBody = """
                {
                    "email": "%s",
                    "password": "Password123"
                }
                """.formatted(email);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }


    @Test
    void forgotPasswordShouldSendEmail() throws Exception {

        String email = "forgot" + UUID.randomUUID() + "@gmail.com";

        Profile profile = new Profile();

        profile.setFullName("Forgot Password User");
        profile.setEmail(email);
        profile.setPassword("Password123");
        profile.setIsActive(true);

        profileRepo.save(profile);

        mockMvc.perform(post("/forgot-password")
                        .param("email", email))
                .andExpect(status().isOk());

        verify(emailServiceImpl).sendEmail(
                org.mockito.ArgumentMatchers.eq(email),
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.anyString()
        );
    }


    @Test
    void resetPasswordWithInvalidTokenShouldFail() throws Exception {

        mockMvc.perform(post("/reset-password")
                        .param("token", "invalid-token")
                        .param("newPassword", "NewPassword123"))
                .andExpect(status().isBadRequest());
    }
}

