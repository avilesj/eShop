package com.hercule.eshop.services;

import com.hercule.eshop.models.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class ProductServiceTests
{

    @Autowired
    private ProductService productService;

    private Product product;
    private Product secondProduct;

    @Before
    public void initialize()
    {
        this.product = new Product();
        this.product.setName("Butter");
        this.product.setPrice(20.00);
        this.product.setDescription("Just butter");

        this.secondProduct = new Product();
        this.secondProduct.setName("Mayoniez");
        this.secondProduct.setPrice(31.99);
        this.secondProduct.setDescription("Slav blood");
    }

    @Test
    @Transactional
    public void searchProductByName()
    {
        productService.saveProduct(this.product);
        Product dbProduct = productService.findProductByName(this.product.getName());
        assertEquals(this.product.getName(), dbProduct.getName());

    }

    @Test
    @Transactional
    public void updatesProduct()
    {
        double newPrice = 25.91;

        productService.saveProduct(this.product);
        Product changedProduct = productService.findProductByName(this.product.getName());
        changedProduct.setPrice(newPrice);
        productService.updateProduct(changedProduct);
        changedProduct = productService.findProductByName(this.product.getName());
        assertEquals(newPrice, changedProduct.getPrice(), 0);
    }

    @Test
    @Transactional
    public void deletesProduct()
    {
        productService.saveProduct(product);
        Product retrievedProduct = productService.findProductByName(this.product.getName());
        productService.deleteProduct(retrievedProduct.getId());
        retrievedProduct = productService.findProductByName(this.product.getName());
        assertNull(retrievedProduct);
    }

    @Test
    @Transactional
    public void searchProductById()
    {
        productService.saveProduct(product);
        long id = product.getId();
        Product searchedProduct = productService.findProductById(id);
        assertEquals(id, searchedProduct.getId());
    }

    @Test
    @Transactional
    public void displaysAllProducts()
    {
        List<Product> storedProducts = new ArrayList<>();
        storedProducts.add(product);
        storedProducts.add(secondProduct);

        productService.saveProduct(product);
        productService.saveProduct(secondProduct);
        List<Product> productList = productService.showAllProducts();

        assertEquals(storedProducts.size(), productList.size());
    }

}
