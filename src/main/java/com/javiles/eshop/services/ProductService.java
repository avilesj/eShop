package com.javiles.eshop.services;

import com.javiles.eshop.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public interface ProductService
{

    void saveProduct(Product product);

    Product findProductByName(String name);

    void updateProduct(Product product);

    void deleteProduct(long id);

    Product findProductById(long id);

    List<Product> showAllProducts();

    List<Product> findProductsBySearchTerms(String searchQuery);
}
