package com.javiles.eshop.controllers.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class AdminControllerTests
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAllowUserWithAdminRoleToAccessAdminPanel() throws Exception
    {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("MAIN MENU")))
                .andExpect(content().string(containsString("Dashboard")))
                .andExpect(content().string(containsString("Users")))
                .andExpect(content().string(containsString("Products")))
                .andExpect(content().string(containsString("Orders")))
                .andExpect(content().string(containsString("OPTIONS")))
                .andExpect(content().string(containsString("Settings")))
                .andExpect(content().string(containsString("Back to eShop")));
    }


    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldNotAllowUserWithUserRoleToAccessAdminPanel() throws Exception
    {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());

    }
}
