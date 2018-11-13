package com.javiles.eshop.controllers.admin;

import com.javiles.eshop.models.Product;
import com.javiles.eshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProductController
{
    @Autowired
    private ProductService productService;

    //Product section methods
    @RequestMapping("/product")
    public String getProductDashboard()
    {
        return "admin/product/adminProductDashboard";
    }

    @RequestMapping("/product/search")
    public String searchProduct(@RequestParam("productSearch") String productName, Model model)
    {
        List<Product> foundProducts = productService.findProductsBySearchTerms(productName);
        model.addAttribute("foundProducts", foundProducts);
        return "admin/product/adminProductDashboard";
    }

    @RequestMapping("/product/new")
    public String newProductForm(Model model)
    {
        model.addAttribute("productForm", new Product());
        return "admin/product/adminProductNew";
    }

    @RequestMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") long id)
    {
        Product product = productService.findProductById(id);
        model.addAttribute("productForm", product);
        return "admin/product/adminProductEdit";
    }


    @RequestMapping(value = {"/product/save"}, method = RequestMethod.POST)
    public String saveProduct(final Product product, Model model, @RequestParam(value = "file", required = false) MultipartFile file)
    {
        if (file.isEmpty())
        {
            productService.saveProduct(product);
        } else
        {
            productService.saveProduct(product, file);
        }

        model.addAttribute("product", product);
        return "redirect:/admin/product/edit/" + product.getId();
    }

    @RequestMapping(value = {"/product/edit/{id}"}, method = RequestMethod.POST)
    public String saveEditedProduct(final Product product, Model model, @RequestParam(value = "file", required = false) MultipartFile file)
    {
        if (file == null)
        {
            productService.updateProduct(product);
        } else
        {
            productService.updateProduct(product, file);
        }

        return "redirect:/admin/product/";
    }

    @RequestMapping(value = "/product/delete/{id}", method = RequestMethod.POST)
    public String deleteProduct(Model model, @PathVariable("id") long id)
    {
        productService.deleteProduct(id);
        return "admin/product/adminProductDashboard";
    }
}
