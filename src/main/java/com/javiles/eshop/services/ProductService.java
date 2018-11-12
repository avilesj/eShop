package com.javiles.eshop.services;

import com.javiles.eshop.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
@Service
public interface ProductService
{

    void saveProduct(Product product);

    void saveProduct(Product product, MultipartFile picture);

    Product findProductByName(String name);

    void updateProduct(Product product);

    void updateProduct(Product product, MultipartFile picture);

    void deleteProduct(long id);

    Product findProductById(long id);

    List<Product> showAllProducts();

    List<Product> findProductsBySearchTerms(String searchQuery);
}
