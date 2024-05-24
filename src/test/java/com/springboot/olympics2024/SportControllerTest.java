package com.springboot.olympics2024;

import com.springboot.olympics2024.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class SportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "User", roles = { "USER" })
    public void testGetRequestAsUser() throws Exception {
        mockMvc.perform(get("/olympics2024/sports/canoe"))
                .andExpect(status().isOk())
                .andExpect(view().name("sportdetail"))
                .andExpect(model().attributeExists("sport"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = { "ADMIN" })
    public void testGetRequestAsAdmin() throws Exception {
        mockMvc.perform(get("/olympics2024/sports/canoe"))
                .andExpect(status().isOk())
                .andExpect(view().name("sportdetail"))
                .andExpect(model().attributeExists("sport"));
    }

    @Test
    public void testGetRequestWithoutUser() throws Exception {
        mockMvc.perform(get("/olympics2024/sports/canoe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
