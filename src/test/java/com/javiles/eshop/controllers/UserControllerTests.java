package com.javiles.eshop.controllers;

import com.javiles.eshop.models.Country;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.CountryService;
import com.javiles.eshop.services.SecurityService;
import com.javiles.eshop.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class UserControllerTests
{

    private String DEFAULT_USERNAME = "javiles";
    private String DEFAULT_PASSWORD = "321321";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CountryService countryService;

    private User databaseUser;
    private User testingUser;


    @Before
    public void initialization()
    {
        Country country = countryService.getCountryByCode("NZ");

        databaseUser = new User();
        databaseUser.setUsername(DEFAULT_USERNAME);
        databaseUser.setPassword(DEFAULT_PASSWORD);
        databaseUser.setPasswordConfirm(DEFAULT_PASSWORD);
        databaseUser.setCountry(country);

        testingUser = new User();
        testingUser.setUsername(DEFAULT_USERNAME);
        testingUser.setPassword(DEFAULT_PASSWORD);
        testingUser.setPasswordConfirm(DEFAULT_PASSWORD);
        testingUser.setCountry(country);
    }

    @Test
    public void rendersLoginForm() throws Exception
    {
        this.mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    public void rendersNewUserForm() throws Exception
    {
        this.mockMvc.perform(get("/registration")).andExpect(status().isOk());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createsNewUser() throws Exception
    {
        this.mockMvc.perform(post("/registration").flashAttr("userForm", testingUser))
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void performsLogin() throws Exception
    {
        userService.save(this.databaseUser);
        this.mockMvc.perform(formLogin("/login").user(testingUser.getUsername()).password(testingUser.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void performsLogout() throws Exception
    {
        userService.save(this.databaseUser);
        securityService.autologin(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        this.mockMvc.perform(get("/logout").requestAttr("username", DEFAULT_USERNAME).requestAttr("password", DEFAULT_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
    }

}
