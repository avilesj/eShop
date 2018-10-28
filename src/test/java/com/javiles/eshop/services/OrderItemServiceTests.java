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
public class OrderItemServiceTests
{
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Test
    public void shouldRetrieveOrderItemsByOrderId()
    {

    }

}
