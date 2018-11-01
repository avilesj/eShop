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

    Order getOrderByUserIdAndOrderId(long userId, long orderId);

    void completeOrderByUserIdAndOrderId(long userId, long orderId);

    void cancelOrderByUserIdAndOrderId(long userId, long orderId);

    List<Order> getAllPendingOrders();

    List<Order> getPendingOrdersByUserId(long userId);

    List<Order> getCancelledOrdersByUserId(long userId);

    List<Order> getCompletedOrdersByUserId(long userId);

    List<Order> getAllOrdersByUserId(long userId);
}
