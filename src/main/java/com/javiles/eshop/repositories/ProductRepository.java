package com.javiles.eshop.repositories;

import com.javiles.eshop.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>
{

    Product findByName(String name);

    Product findById(long id);

    List<Product> findAll();

    List<Product> findByNameContaining(String searchQuery);

}
