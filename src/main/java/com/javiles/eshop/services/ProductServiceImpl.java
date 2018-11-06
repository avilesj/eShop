package com.javiles.eshop.services;

import com.javiles.eshop.models.Product;
import com.javiles.eshop.repositories.ProductRepository;
import com.javiles.eshop.storage.ProductImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageStorageService productImageStorageService;

    private Product receivedProduct;
    private List<Product> receivedProductList;

    @Override
    public void saveProduct(Product product)
    {
        this.receivedProduct = product;
        this.prepareProductPersistence();
        productRepository.save(product);

    }

    @Override
    public void saveProduct(Product product, MultipartFile picture)
    {
        this.receivedProduct = product;
        this.prepareProductPersistence();
        productRepository.save(this.receivedProduct);

        try
        {
            String imageFilename = this.productImageStorageService.storeFile(picture, String.valueOf(this.receivedProduct.getId()));
            this.receivedProduct.setImageFilename(imageFilename);
            productRepository.save(this.receivedProduct);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Product findProductByName(String name)
    {
        return productRepository.findByName(this.prepareProductName(name));
    }

    @Override
    public void updateProduct(Product product)
    {
        product.setName(this.prepareProductName(product.getName()));

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
        this.receivedProduct = productRepository.findById(id);
        this.prepareProductDelivery();
        return this.receivedProduct;
    }

    @Override
    public List<Product> showAllProducts()
    {
        this.receivedProductList = productRepository.findAll();
        for (Product p : this.receivedProductList)
        {
            this.receivedProduct = p;
            this.prepareProductDelivery();
            p = this.receivedProduct;
        }

        return this.receivedProductList;
    }

    @Override
    public List<Product> findProductsBySearchTerms(String searchQuery)
    {
        this.receivedProductList = productRepository.findByNameContaining(prepareProductName(searchQuery));
        for (Product p : this.receivedProductList)
        {
            this.receivedProduct = p;
            this.prepareProductDelivery();
            p = this.receivedProduct;
        }

        return this.receivedProductList;
    }

    private void prepareProductPersistence()
    {
        this.receivedProduct.setName(this.prepareProductName(this.receivedProduct.getName()));
    }

    private void prepareProductDelivery()
    {
        if (this.receivedProduct.getImageFilename() != null)
        {
            try
            {
                this.receivedProduct.setImageFileUrl(productImageStorageService.getFileUrl(this.receivedProduct.getImageFilename()));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private String prepareProductName(String name)
    {
        return name.toLowerCase();
    }

}
