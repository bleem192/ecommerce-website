package com.example.ecommerce.controller;

import com.example.ecommerce.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 查看订单历史
    @GetMapping
    public String viewOrders(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("orders", orderService.getUserOrders(username));
        model.addAttribute("username", username);
        return "orders";
    }

    // 查看订单详情
    @GetMapping("/detail/{orderId}")
    public String viewOrderDetail(@PathVariable Long orderId, Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("order", orderService.getOrderById(orderId));
        model.addAttribute("username", username);
        return "order-detail";
    }
}
