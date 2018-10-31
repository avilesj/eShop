package com.javiles.eshop.repositories;

import com.javiles.eshop.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>
{
    Order findByUserId(long userId);

    List<Order> findByStatus(String status);
}
