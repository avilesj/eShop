package com.javiles.eshop.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class OrderServiceTests
{
    @Autowired
    private OrderService orderService;

    @Test
    public void shouldMakeOrderWithCartItemsAndEmptyCart()
    {

    }

    @Test
    public void shouldGetOrderDetails()
    {

    }

    @Test
    public void shouldChangeOrderStatusToComplete()
    {

    }

    @Test
    public void shouldChangeOrderStatusToCancelled()
    {

    }

}
