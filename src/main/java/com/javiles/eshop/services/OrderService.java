package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public interface OrderService
{
    void createOrder(Cart cart);

    Order getOrderByUserId(long userId);

    void completeOrderByUserId(long userId);

    void cancelOrderByUserId(long userId);

    List<Order> getAllPendingOrders();
}
