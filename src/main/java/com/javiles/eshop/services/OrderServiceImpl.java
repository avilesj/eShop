package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import com.javiles.eshop.models.Order;
import com.javiles.eshop.models.OrderItem;
import com.javiles.eshop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService
{
    private final String COMPLETE = "COMPLETED";
    private final String PENDING = "PENDING";
    private final String CANCELLED = "CANCELLED";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Override
    public void createOrder(Cart cart)
    {
        double orderTotal = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = new Order();
        order.setStatus(PENDING);
        order.setUser(cart.getUser());

        //Extract cart products and add them to the order items
        for (CartItem cartItem : cart.getCartItem())
        {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice(orderItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotal(cartItem.getTotal());
            orderTotal += orderItem.getTotal();
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotal(orderTotal);
        order.setSize(orderItems.size());

        orderRepository.save(order);

        cartService.emptyCart(cart);
    }

    @Override
    public Order getOrderByUserIdAndOrderId(long userId, long orderId)
    {
        return orderRepository.findOneByUserIdAndId(userId, orderId);
    }

    @Override
    public void completeOrderByUserIdAndOrderId(long userId, long orderId)
    {
        Order order = orderRepository.findOneByUserIdAndId(userId, orderId);
        if (order.getStatus().equals(PENDING))
        {
            order.setStatus(COMPLETE);
            orderRepository.save(order);
        }

    }

    @Override
    public void cancelOrderByUserIdAndOrderId(long userId, long orderId)
    {
        Order order = orderRepository.findOneByUserIdAndId(userId, orderId);
        if (order.getStatus().equals(PENDING))
        {
            order.setStatus(CANCELLED);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> getAllPendingOrders()
    {
        return orderRepository.findByStatus(PENDING);
    }

    @Override
    public List<Order> getPendingOrdersByUserId(long userId)
    {
        return orderRepository.findByUserIdAndStatus(userId, PENDING);
    }

    @Override
    public List<Order> getCancelledOrdersByUserId(long userId)
    {
        return orderRepository.findByUserIdAndStatus(userId, CANCELLED);
    }

    @Override
    public List<Order> getCompletedOrdersByUserId(long userId)
    {
        return orderRepository.findByUserIdAndStatus(userId, COMPLETE);
    }

    @Override
    public List<Order> getAllOrdersByUserId(long userId)
    {
        return orderRepository.findByUserId(userId);
    }
}
