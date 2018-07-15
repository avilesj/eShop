package com.hercule.eshop.services;

import com.hercule.eshop.models.Product;

import java.util.List;

public interface ProductService
{

    void saveProduct(Product product);

    Product findProductByName(String name);

    void updateProduct(Product product);

    void deleteProduct(long id);

    Product findProductById(long id);

    List<Product> showAllProducts();
}
