package com.hercule.eshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hercule.eshop.models.Product;
import com.hercule.eshop.services.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class ProductServiceTests {

	@Autowired
	ProductService productService;
	
	Product product;
	Product product2;
	
	@Before
	public void initialize()
	{
		this.product = new Product();
		this.product.setName("Butter");
		this.product.setPrice(20.00);
		this.product.setDescription("Just butter");
		
		this.product2 = new Product();
		this.product2.setName("Mayoniez");
		this.product2.setPrice(31.99);
		this.product2.setDescription("Slav blood");
	}
	
	@Test
	public void addsProduct()
	{
		productService.saveProduct(this.product);	
	}
	
	@Test
	public void searchProductByName()
	{
		productService.saveProduct(this.product);
		Product dbProduct = productService.findProductByName("Butter");
		assertEquals("Butter", dbProduct.getName());
		
	}
	
	@Test
	public void updatesProduct()
	{
		productService.saveProduct(this.product);
		Product changedProduct = productService.findProductByName("Butter");
		changedProduct.setPrice(25.91);
		productService.updateProduct(changedProduct);
		changedProduct = productService.findProductByName("Butter");
		assertEquals(25.91, changedProduct.getPrice(), 0);
	}
	
	@Test
	public void deletesProduct()
	{
		productService.saveProduct(product);
		Product retrievedProduct = productService.findProductByName("Butter");
		productService.deleteProduct(retrievedProduct);
		retrievedProduct = productService.findProductByName("Butter");
		assertNull(retrievedProduct);
	}
	
	@Test
	public void searchProductById()
	{
		productService.saveProduct(product);
		long id = product.getId();
		Product searchedProduct = productService.findProductById(id);
		assertEquals(id, searchedProduct.getId());
	}
	
	@Test
	public void displaysAllProducts()
	{
		productService.saveProduct(product);
		productService.saveProduct(product2);
		
		List<Product> productList = productService.showAllProducts();
		assertEquals(2, productList.size());
	}

}
