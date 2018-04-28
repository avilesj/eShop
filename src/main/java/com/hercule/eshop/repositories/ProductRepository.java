package com.hercule.eshop.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hercule.eshop.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
	
	Product findByName(String name);
	Product findById(long id);
	List<Product>findAll(); 

}
