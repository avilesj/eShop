package com.hercule.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hercule.product.models.Product;
import com.hercule.product.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	ProductService productService;
	
	@RequestMapping("")
	public String getProducts()
	{
		return "products/products";
	}
	
	@RequestMapping("/{id}")
	public String getProductDetail(Model model, @PathVariable("id") long id)
	{
		Product product = productService.findProductById(id);
		model.addAttribute("product", product);
		System.out.println("USING ID: " + id);
		System.out.println("====PRODUCT FOUND====");
		System.out.println("ID: " + product.getId());
		System.out.println("NAME: " + product.getName());
		System.out.println("DESCRIPTION: " + product.getDescription());
		System.out.println("PRICE: " + product.getPrice());
		return "products/productDetail";
	}
	
	@RequestMapping("/new")
	public String newProduct(Model model)
	{
		model.addAttribute("product", new Product());
		return "products/newProductForm";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String createProduct(final Product product, Model model)
	{
		productService.saveProduct(product);
		model.addAttribute("product", product);
		return "redirect:" + product.getId();
	}
	
}

