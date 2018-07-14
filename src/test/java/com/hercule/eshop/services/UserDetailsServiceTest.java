package com.hercule.eshop.services;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hercule.eshop.models.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class UserDetailsServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	UserDetailsService userDetailsService;
	
	User user;
	
	@Before
	public void initialize()
	{
		user = new User();
		user.setUsername("javiles");
		user.setPassword("321321");
		userService.save(user);
	}
	
	@Test
	public void loadsUser()
	{
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		assertEquals(user.getUsername().toUpperCase(), userDetails.getUsername());
	}
	
	

}
