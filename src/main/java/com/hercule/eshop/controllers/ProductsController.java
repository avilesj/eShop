package com.hercule.eshop.controllers;

import com.hercule.eshop.models.CartItem;
import com.hercule.eshop.models.Product;
import com.hercule.eshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController
{

    @Autowired
    ProductService productService;

    @RequestMapping("")
    public String getProducts(Model model)
    {
        List<Product> allProducts = productService.showAllProducts();
        model.addAttribute("allProducts", allProducts);
        return "products/products";
    }

    @RequestMapping("/{id}")
    public String getProductDetail(Model model, @PathVariable("id") long id)
    {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("cartItem", new CartItem());

        return "products/productDetail";
    }

    @RequestMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") long id)
    {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "products/editProduct";
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(Model model, @PathVariable("id") long id)
    {
        productService.deleteProduct(id);
        return "products/products";
    }

    @RequestMapping("/new")
    public String newProduct(Model model)
    {
        model.addAttribute("product", new Product());
        return "products/newProductForm";
    }

    @RequestMapping(value = {"/save", "/edit/{id}"}, method = RequestMethod.POST)
    public String saveProduct(final Product product, Model model)
    {
        productService.saveProduct(product);
        model.addAttribute("product", product);
        return "redirect:/products/" + product.getId();
    }

    @RequestMapping(value = "/search")
    public String searchProducts(@RequestParam("name") String searchQuery, Model model)
    {
        List<Product> foundProducts = productService.findProductsBySearchTerms(searchQuery);
        model.addAttribute("foundProducts", foundProducts);
        return "products/productSearch";
    }

}

