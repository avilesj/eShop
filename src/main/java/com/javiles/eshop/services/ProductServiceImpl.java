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
        this.receivedProduct = product;

        Product dbProduct = productRepository.findById(this.receivedProduct.getId());
        if (dbProduct != null)
        {
            this.prepareProductPersistence();
            dbProduct.setName(this.receivedProduct.getName());
            dbProduct.setPrice(this.receivedProduct.getPrice());
            dbProduct.setDescription(this.receivedProduct.getDescription());

            productRepository.save(dbProduct);
        }
    }

    @Override
    public void updateProduct(Product product, MultipartFile picture)
    {
        this.receivedProduct = product;

        Product foundProduct = productRepository.findById(this.receivedProduct.getId());
        if (foundProduct != null)
        {
            try
            {
                //Prepare the information of the received product to match database stored data.
                this.prepareProductPersistence();
                //Proceed to delete stored file
                this.productImageStorageService.deleteFile(foundProduct.getImageFilename());
                //Upload new file
                String imageFilename = this.productImageStorageService.storeFile(picture, String.valueOf(this.receivedProduct.getId()));

                //Proceed to update the product
                foundProduct.setName(this.receivedProduct.getName());
                foundProduct.setPrice(this.receivedProduct.getPrice());
                foundProduct.setDescription(this.receivedProduct.getDescription());
                foundProduct.setImageFilename(imageFilename);

                productRepository.save(foundProduct);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteProduct(long id)
    {
        Product foundProduct = productRepository.findById(id);
        try
        {
            this.productImageStorageService.deleteFile(foundProduct.getImageFilename());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

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
        //Refactor this.
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
