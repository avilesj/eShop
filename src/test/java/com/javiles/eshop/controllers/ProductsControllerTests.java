package com.javiles.eshop.controllers;

import com.javiles.eshop.models.Product;
import com.javiles.eshop.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("dev")
public class ProductsControllerTests
{

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
    @WithMockUser(roles = {"ADMIN"})
    public void rendersProductsPage() throws Exception
    {
        this.mockMvc.perform(get("/products/")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Products")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void rendersProductDetailPage() throws Exception
    {
        this.mockMvc.perform(get("/products/" + product.getId())).andDo(print()).andExpect(status().isOk()).
                andExpect(content().string(containsString(product.getName()))).
                andExpect(content().string(containsString(Double.toString(product.getPrice())))).
                andExpect(content().string(containsString(product.getDescription())));

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void rendersNewProductForm() throws Exception
    {
        this.mockMvc.perform(get("/products/new")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Product Form")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void rendersEditProductForm() throws Exception
    {
        this.mockMvc.perform(get("/products/edit/" + product.getId())).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(product.getName())));
    }

    @Test
    public void requireAdminRoleToViewNewProductForm() throws Exception
    {
        /*
            Localhost is hardcoded for now since the redirect made from the Spring Security config is done with full context path
         */
        this.mockMvc.perform(get("/products/new")).andExpect(redirectedUrl("http://localhost/login")).andExpect(status().is3xxRedirection());
    }

    @Test
    public void displaySearchedProducts() throws Exception
    {
        String searchString = this.product.getName();
        this.mockMvc.perform(get("/products/search?name=" + searchString))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(searchString)));
    }
}
