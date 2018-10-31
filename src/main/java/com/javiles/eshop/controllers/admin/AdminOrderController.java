package com.javiles.eshop.controllers.admin;

import com.javiles.eshop.models.Order;
import com.javiles.eshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminOrderController
{
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order")
    public String getOrderDashBoard(Model model)
    {
        List<Order> orders = orderService.getAllPendingOrders();
        model.addAttribute("pendingOrders", orders);

        return "admin/order/adminOrderDashboard";
    }

}
