package com.hercule.eshop.services;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hercule.eshop.models.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class SecurityServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	SecurityService securityService;
	
	User user;
	User loginUser;
	
	@Before
	public void initialize()
	{
		user = new User();
		user.setUsername("javiles");
		user.setPassword("321321");
		userService.save(user);
	}
	
	@Test
	public void userLogsIn()
	{
		loginUser = new User();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		assertEquals(user.getUsername(), authentication.getName());
	}
	
	@Test
	public void findLoggedInUsername()
	{
		loginUser = new User();
		loginUser.setUsername("javiles");
		loginUser.setPassword("321321");
		securityService.autologin(loginUser.getUsername(), loginUser.getPassword());
		
		String loggedUsername = securityService.findLoggedInUsername();
		
		assertEquals(loginUser.getUsername(), loggedUsername);
	}
	
	

}