package com.springboot.olympics2024;

import com.springboot.olympics2024.config.SecurityConfig;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class LoginMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginGet() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @WithMockUser(username = "User", roles = {"USER"})
    @Test
    public void testAccessWithUserRole() throws Exception {
        mockMvc.perform(get("/olympics2024"))
                .andExpect(status().isOk())
                .andExpect(view().name("olympics2024"));
    }

    @WithMockUser(username = "Admin", roles = {"ADMIN"})
    @Test
    public void testAccessWithAdminRole() throws Exception {
        mockMvc.perform(get("/olympics2024"))
                .andExpect(status().isOk())
                .andExpect(view().name("olympics2024"));
    }

    @WithMockUser(username = "User", roles = { "NOT_USER" })
    @Test
    public void testNoAccessWithWrongUserRole() throws Exception {
        mockMvc.perform(get("/olympics2024"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testWrongPassword() throws Exception {
        mockMvc.perform(formLogin("/login")
                        .user("username", "User")
                        .password("password", "wrongpassword"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    void testCorrectPassword() throws Exception {
        mockMvc.perform(formLogin("/login")
                        .user("username", "User")
                        .password("password", "12345678"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/olympics2024"));
    }

}
