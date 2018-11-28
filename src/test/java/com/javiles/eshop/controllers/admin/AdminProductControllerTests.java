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
public class AdminProductControllerTests
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAllowUserWithAdminRoleToAccessProductsAdminPanel() throws Exception
    {
        this.mockMvc.perform(get("/admin/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("Product Dashboard")))
                .andExpect(content().string(containsString("New Product")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAllowUserWithAdminRoleToAccessNewProductForm() throws Exception
    {
        this.mockMvc.perform(get("/admin/product/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                /*And now, the form itself*/
                .andExpect(content().string(containsString("Name")))
                .andExpect(content().string(containsString("Price")))
                .andExpect(content().string(containsString("Description")))
                .andExpect(content().string(containsString("type=\"file\"")))
                .andExpect(content().string(containsString("Create Product")))
                .andExpect(content().string(containsString("Cancel")));
    }


    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldNotAllowUserWithUserRoleToAccessAdminPanel() throws Exception
    {
        this.mockMvc.perform(get("/admin/product"))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(get("/admin/product/new"))
                .andDo(print())
                .andExpect(status().isForbidden());

    }
}
