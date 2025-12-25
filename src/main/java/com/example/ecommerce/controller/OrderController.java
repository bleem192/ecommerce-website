package com.example.ecommerce.controller;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserActivityLogService;
import com.example.ecommerce.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final UserActivityLogService activityLogService;

    public OrderController(OrderService orderService, UserService userService, 
                         UserActivityLogService activityLogService) {
        this.orderService = orderService;
        this.userService = userService;
        this.activityLogService = activityLogService;
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
        User user = userService.getCurrentUser(username);
        
        // 获取订单
        com.example.ecommerce.entity.Order order = orderService.getOrderById(orderId);
        
        // 记录查看订单日志
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        activityLogService.logOrderView(user, order, ipAddress, userAgent);
        
        model.addAttribute("order", order);
        model.addAttribute("username", username);
        return "order-detail"; // 对应 templates/order-detail.html
    }
    
    // 确认收货
    @GetMapping("/confirm/{orderId}")
    public String confirmOrder(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, "已完成");
        return "redirect:/orders/detail/" + orderId + "?confirmed=true";
    }
    
    // 获取客户端真实IP地址
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}