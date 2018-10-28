package com.javiles.eshop.stripe;

import com.javiles.eshop.models.User;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.models.StripeCustomerPayment;
import com.javiles.eshop.stripe.services.StripeCustomerPaymentService;
import com.javiles.eshop.stripe.services.StripeCustomerService;
import com.stripe.model.Charge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class StripeCustomerPaymentServiceTests
{
    @Autowired
    private StripeCustomerPaymentService stripeCustomerPaymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private StripeCustomerService stripeCustomerService;

    @Test
    public void shouldSaveStripeCustomerPaymentsAndRetrieveThemByUserId()
    {
        User user = new User();
        user.setUsername("bobvance");
        user.setPassword("password");
        userService.save(user);

        stripeCustomerService.saveStripeCustomer(user, "key");

        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(user.getId());

        Charge charge = new Charge();
        charge.setAmount(Long.valueOf(100));
        charge.setId("payment1");
        charge.setCurrency("usd");

        Charge charge2 = new Charge();
        charge2.setAmount(Long.valueOf(120));
        charge2.setId("payment2");
        charge2.setCurrency("usd");

        stripeCustomerPaymentService.saveCustomerPayment(stripeCustomer, charge);
        stripeCustomerPaymentService.saveCustomerPayment(stripeCustomer, charge2);


        List<StripeCustomerPayment> paymentList = stripeCustomerPaymentService.getCustomerPaymentByCustomerId(stripeCustomer.getId());

        assertNotNull(paymentList);
        assertEquals(2, paymentList.size());
    }
}
