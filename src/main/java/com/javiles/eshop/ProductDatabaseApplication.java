package com.javiles.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(value = {"com.javiles.eshop.repositories", "com.javiles.eshop.stripe.repositories"})
public class ProductDatabaseApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ProductDatabaseApplication.class, args);
    }

}
