package com.javiles.eshop.controllers.user;

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

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController
{
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order")
    public String getOrderDashBoard(Model model, Principal principal)
    {
        User user = userService.findByUsername(principal.getName());
        List<Order> orders = orderService.getAllOrdersByUserId(user.getId());
        model.addAttribute("userOrders", orders);

        return "order/orders";
    }

    @RequestMapping(value = "/order/cancel/{id}", method = RequestMethod.POST)
    public String cancelOrder(Principal principal, @PathVariable("id") long orderId)
    {
        User user = userService.findByUsername(principal.getName());
        orderService.cancelOrderByUserIdAndOrderId(user.getId(), orderId);

        return "redirect:/order";
    }

    @RequestMapping(value = "/order/{id}")
    public String getOrderDetails(Principal principal, @PathVariable("id") long orderId, Model model)
    {
        User user = userService.findByUsername(principal.getName());
        Order order = orderService.getOrderByUserIdAndOrderId(user.getId(), orderId);
        model.addAttribute("order", order);

        return "order/orderDetail";
    }
}
