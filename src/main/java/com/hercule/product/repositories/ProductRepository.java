package com.hercule.product.repositories;

import org.springframework.data.repository.CrudRepository;

import com.hercule.product.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
	
	Product findByName(String name);
	Product findById(long id);

}
