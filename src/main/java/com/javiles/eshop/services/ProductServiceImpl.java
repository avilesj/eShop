package com.javiles.eshop.services;

import com.javiles.eshop.models.Product;
import com.javiles.eshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void saveProduct(Product product)
    {
        product.setName(product.getName().toLowerCase());
        productRepository.save(product);

    }

    @Override
    public void saveProduct(Product product, MultipartFile picture)
    {

    }

    @Override
    public Product findProductByName(String name)
    {
        return productRepository.findByName(name.toLowerCase());
    }

    @Override
    public void updateProduct(Product product)
    {
        product.setName(product.getName().toLowerCase());

        Product dbProduct = productRepository.findById(product.getId());
        dbProduct.setName(product.getName());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setDescription(product.getDescription());

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id)
    {
        productRepository.deleteById(id);
    }

    @Override
    public Product findProductById(long id)
    {

        return productRepository.findById(id);
    }

    @Override
    public List<Product> showAllProducts()
    {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsBySearchTerms(String searchQuery)
    {
        return productRepository.findByNameContaining(searchQuery.toLowerCase());
    }

}
