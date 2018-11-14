package com.javiles.eshop.spring;

import com.javiles.eshop.services.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AppConfig
{


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

    @Bean
    public RoleService roleService()
    {
        return new RoleServiceImpl();
    }

    @Bean
    public CartService cartService()
    {
        return new CartServiceImpl();
    }

    @Bean
    public CartItemService cartItemService()
    {
        return new CartItemServiceImpl();
    }

    @Bean
    public OrderService orderService()
    {
        return new OrderServiceImpl();
    }

    @Bean
    public CountryService countryService()
    {
        return new CountryServiceImpl();
    }

    @Bean
    public MessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        return messageSource;
    }
}
