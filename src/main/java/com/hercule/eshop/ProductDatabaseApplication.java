package com.hercule.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.hercule.eshop.repositories")
public class ProductDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductDatabaseApplication.class, args);
	}

}
