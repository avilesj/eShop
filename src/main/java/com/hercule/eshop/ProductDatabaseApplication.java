package com.hercule.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(value = {"com.hercule.eshop.repositories", "com.hercule.eshop.stripe.repositories"})
public class ProductDatabaseApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ProductDatabaseApplication.class, args);
    }

}
