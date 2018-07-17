package com.hercule.eshop.spring;

import com.hercule.eshop.services.*;
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
    CartService cartService()
    {
        return new CartServiceImpl();
    }

    @Bean
    CartItemService cartItemService()
    {
        return new CartItemServiceImpl();
    }

    @Bean
    public MessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        return messageSource;
    }
}
