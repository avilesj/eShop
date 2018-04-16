package com.hercule.eshop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hercule.eshop.services.ProductService;
import com.hercule.eshop.services.ProductServiceImpl;

@Configuration
public class AppConfig {

	
	@Bean
	public ProductService productService()
	{
		return new ProductServiceImpl();
	}
}
