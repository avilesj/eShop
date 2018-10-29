package com.javiles.eshop.repositories;

import com.javiles.eshop.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>
{

}
