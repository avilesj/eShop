package com.javiles.eshop.controllers.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController
{
    @RequestMapping("")
    public String getAdminIndex()
    {
        return "admin/adminIndex";
    }
}
