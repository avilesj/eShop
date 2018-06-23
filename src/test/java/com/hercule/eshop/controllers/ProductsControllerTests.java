package com.hercule.eshop.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.hercule.eshop.models.Product;
import com.hercule.eshop.services.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ProductsControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProductService productService;
	
	private Product product;
	
	@Before
	public void initialize()
	{
		this.product = new Product();
		this.product.setName("Butter");
		this.product.setPrice(20.00);
		this.product.setDescription("Just butter");
		productService.saveProduct(this.product);
	}
	
	@Test
	public void rendersProductsPage() throws Exception
	{
		this.mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Products")));
	}
	
	@Test
	public void rendersProductDetailPage() throws Exception 
	{
		this.mockMvc.perform(get("/products/1")).andDo(print()).andExpect(status().isOk()).
		andExpect(content().string(containsString("Butter"))).
		andExpect(content().string(containsString("20.00"))).
		andExpect(content().string(containsString("Just butter")));
		
	}
	
	@Test
	public void rendersNewProductForm() throws Exception
	{
		this.mockMvc.perform(get("/products/new")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Product Form")));
	}
	
	@Test
	public void rendersEditProductForm() throws Exception
	{
		this.mockMvc.perform(get("/products/edit/1")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Butter")));
	}
}
