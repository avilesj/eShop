package com.hercule.eshop.controllers;

import com.hercule.eshop.models.User;
import com.hercule.eshop.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private User user;


    @Before
    public void initialization() {
        user = new User();
        user.setUsername("javiles");
        user.setPassword("321321");
        user.setPasswordConfirm(user.getPassword());
    }

    @Test
    public void rendersLoginForm() throws Exception {
        this.mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    public void rendersNewUserForm() throws Exception

    {
        this.mockMvc.perform(get("/registration")).andExpect(status().isOk());
    }

    @Test
    public void createsNewUser() throws Exception {
        this.mockMvc.perform(post("/registration").flashAttr("userForm", this.user))
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void performsLogin() throws Exception {
        userService.save(this.user);
        User loginUser = new User();
        loginUser.setPassword("321321");
        loginUser.setUsername("javiles");

        this.mockMvc.perform(formLogin("/login").user(loginUser.getUsername()).password(loginUser.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
