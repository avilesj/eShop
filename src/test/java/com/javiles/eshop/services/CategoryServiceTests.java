package com.javiles.eshop.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class CategoryServiceTests
{
    @Autowired
    private CategoryService categoryService;


    @Before
    public void init()
    {

        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setTier(1);
        electronics.setParent(null);

        Category laptops = new Category();
        laptops.setName("Laptops");
        laptops.setTier(2);
        laptops.setParent(electronics);

        Category smartphones = new Category();
        smartphones.setName("Smartphones");
        smartphones.setTier(2);
        smartphones.setParent(electronics);

    }

    @After
    public void terminate()
    {
        categoryService.deleteAllCategories();
    }

    @Test
    public void shouldRetrieveAllCategories()
    {
        List<Category> categories = categoryService.getAllCategories();
        assertEquals(3, categories.size());

    }

    @Test
    public void shouldNotAllowChildCategoriesToHaveNullParent()
    {

    }


}
