package com.hercule.eshop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.hercule.eshop.services.ProductService;
import com.hercule.eshop.services.ProductServiceImpl;
import com.hercule.eshop.services.SecurityService;
import com.hercule.eshop.services.SecurityServiceImpl;
import com.hercule.eshop.services.UserDetailsServiceImpl;
import com.hercule.eshop.services.UserService;
import com.hercule.eshop.services.UserServiceImpl;

@Configuration
public class AppConfig {

	
	@Bean
	public ProductService productService()
	{
		return new ProductServiceImpl();
	}
	
	@Bean
	public UserService userService()
	{
		return new UserServiceImpl();
	}
	
	@Bean
	public SecurityService securityService()
	{
		return new SecurityServiceImpl();
	}

	@Bean
	public UserDetailsService userDetailsService()
	{
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public UserValidator userValidator()
	{
		return new UserValidator();
	}
}
