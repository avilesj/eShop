package com.hercule.eshop.spring;

import com.hercule.eshop.stripe.StripeProperties;
import com.hercule.eshop.stripe.repositories.StripeRepository;
import com.hercule.eshop.stripe.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:stripe.properties")
public class StripeConfig
{
    @Autowired
    private Environment environment;

    @Bean
    public StripeProperties stripeProperties()
    {
        StripeProperties sp = new StripeProperties();
        sp.setSecretKey(environment.getProperty("stripe.secret"));
        return sp;
    }

    @Bean
    public StripeCustomerPaymentService stripeCustomerPaymentService()
    {
        return new StripeCustomerPaymentServiceImpl();
    }
    @Bean
    public StripeCustomerService stripeCustomerService()
    {
        return new StripeCustomerServiceImpl();
    }

    @Bean
    public StripeRepository stripeRepository()
    {
        return new StripeRepository();
    }

    @Bean
    public StripeService stripeService()
    {
        return new StripeServiceImpl();
    }
}
