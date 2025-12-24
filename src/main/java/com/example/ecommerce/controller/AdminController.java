package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    public AdminController(ProductService productService, OrderService orderService, UserService userService) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    // 商品管理首页
    @GetMapping("/products")
    public String manageProducts(Model model, Authentication authentication) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("username", authentication.getName());
        return "admin/products";
    }

    // 显示添加商品表单
    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-form";
    }

    // 处理添加商品
    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    // 显示编辑商品表单
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "admin/product-form";
    }

    // 处理编辑商品
    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id); // 确保ID正确
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    // 删除商品
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
    
    // 销售统计页面
    @GetMapping("/statistics")
    public String salesStatistics(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        
        // 计算总销售额
        BigDecimal totalSales = orders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalSales", totalSales);
        
        // 计算已完成订单数（已完成和已付款）
        long completedOrders = orders.stream()
                .filter(order -> "已完成".equals(order.getStatus()) || "已付款".equals(order.getStatus()))
                .count();
        model.addAttribute("completedOrders", completedOrders);
        
        // 计算待处理订单数（待付款）
        long pendingOrders = orders.stream()
                .filter(order -> "待付款".equals(order.getStatus()))
                .count();
        model.addAttribute("pendingOrders", pendingOrders);
        
        return "admin/statistics";
    }
    
    // 客户管理页面
    @GetMapping("/customers")
    public String customerManagement(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/customers";
    }
    
    // 修改用户角色
    @PostMapping("/customers/change-role/{id}")
    public String changeUserRole(@PathVariable Long id, @RequestParam String role) {
        User user = userService.getUserById(id);
        user.setRole(role);
        userService.saveUser(user);
        return "redirect:/admin/customers";
    }
}
