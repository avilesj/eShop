package com.hercule.eshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hercule.eshop.models.Product;
import com.hercule.eshop.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public void saveProduct(Product product)
	{
		productRepository.save(product);

	}

	@Override
	public Product findProductByName(String name)
	{
		
		return productRepository.findByName(name);
	}

	@Override
	public void updateProduct(Product product)
	{	
		productRepository.save(product);
	}

	@Override
	public void deleteProduct(Product product)
	{ 
		productRepository.delete(product);
	}

	@Override
	public Product findProductById(long id) {
	
		return productRepository.findById(id);
	}

}
