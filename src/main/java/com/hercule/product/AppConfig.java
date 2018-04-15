package com.hercule.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hercule.product.services.ProductService;
import com.hercule.product.services.ProductServiceImpl;

@Configuration
public class AppConfig {

	
	@Bean
	public ProductService productService()
	{
		return new ProductServiceImpl();
	}
}
