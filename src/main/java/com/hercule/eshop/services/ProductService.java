package com.hercule.eshop.services;

import java.util.List;

import com.hercule.eshop.models.Product;

public interface ProductService {

		public void saveProduct(Product product);
		public Product findProductByName(String name);
		public void updateProduct(Product product);
		public void deleteProduct(long id);
		public Product findProductById(long id);
		public List<Product> showAllProducts();
}
