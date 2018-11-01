package com.javiles.eshop.controllers.admin;

import com.javiles.eshop.models.Order;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.OrderService;
import com.javiles.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminOrderController
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping("/order")
    public String getOrderDashBoard(Model model)
    {
        List<Order> orders = orderService.getAllPendingOrders();
        model.addAttribute("pendingOrders", orders);

        return "admin/order/adminOrderDashboard";
    }

    @RequestMapping(value = "/order/cancel/{username}/{id}", method = RequestMethod.POST)
    public String cancelOrder(@PathVariable("username") String username, @PathVariable("id") long orderId)
    {
        User user = userService.findByUsername(username);
        orderService.cancelOrderByUserIdAndOrderId(user.getId(), orderId);

        return "redirect:/admin/order";
    }

    @RequestMapping(value = "/order/complete/{username}/{id}", method = RequestMethod.POST)
    public String completeOrder(@PathVariable("username") String username, @PathVariable("id") long orderId)
    {
        User user = userService.findByUsername(username);
        orderService.completeOrderByUserIdAndOrderId(user.getId(), orderId);

        return "redirect:/admin/order";
    }

    @RequestMapping(value = "/order/search", method = RequestMethod.POST)
    public String searchOrder(@RequestParam("userSearch") String username, Model model)
    {
        User user = userService.findByUsername(username);
        List<Order> orders = orderService.getPendingOrdersByUserId(user.getId());
        model.addAttribute("pendingOrders", orders);

        return "admin/order/adminOrderDashboard";
    }
}
