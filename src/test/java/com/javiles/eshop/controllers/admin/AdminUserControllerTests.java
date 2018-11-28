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
public class AdminUserControllerTests
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAllowUserWithAdminRoleToAccessUserAdminPanel() throws Exception
    {
        this.mockMvc.perform(get("/admin/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("Users Dashboard")))
                .andExpect(content().string(containsString("New User")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAllowUserWithAdminRoleToAccessNewUserForm() throws Exception
    {
        this.mockMvc.perform(get("/admin/user/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("New User Form")))
                /*And now, the form itself*/
                .andExpect(content().string(containsString("Username")))
                .andExpect(content().string(containsString("E-mail")))
                .andExpect(content().string(containsString("Address")))
                .andExpect(content().string(containsString("Phone Number")))
                .andExpect(content().string(containsString("Country")))
                .andExpect(content().string(containsString("City")))
                .andExpect(content().string(containsString("Zip Code")))
                .andExpect(content().string(containsString("Cancel")));
    }


    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldNotAllowUserWithUserRoleToAccessAdminPanel() throws Exception
    {
        this.mockMvc.perform(get("/admin/user"))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(get("/admin/user/new"))
                .andDo(print())
                .andExpect(status().isForbidden());

    }
}
