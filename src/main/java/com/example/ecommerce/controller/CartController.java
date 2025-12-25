package com.example.ecommerce.controller;

import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserActivityLogService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.entity.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final OrderService orderService;

    public CartController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    // 加入购物车
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            Authentication authentication) {
        String username = authentication.getName();
        cartService.addToCart(username, productId, quantity);
        return "redirect:/products"; // 跳转回商品列表页
    }

    // 查看购物车
    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        String username = authentication.getName();
        // 获取购物车项并传递到前端
        model.addAttribute("cartItems", cartService.getCartItems(username));
        model.addAttribute("username", username);
        return "cart"; // 对应 templates/cart.html
    }

    // 删除购物车项
    @GetMapping("/remove/{id}")
    public String removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return "redirect:/cart"; // 跳转回购物车页
    }

    // 结算购物车 - 跳转到填写邮箱页面
    @GetMapping("/checkout")
    public String checkout(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "checkout-email"; // 跳转到填写邮箱页面
    }

    // 提交邮箱并完成订单
    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String email, Authentication authentication) {
        String username = authentication.getName();
        orderService.createOrder(username, email); // 创建订单并传递邮箱
        return "redirect:/orders?checkout=success&email=" + email;
    }
}