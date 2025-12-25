package com.example.ecommerce.controller;

import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.UserActivityLogService;
import com.example.ecommerce.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final UserActivityLogService activityLogService;

    public ProductController(ProductService productService, UserService userService, UserActivityLogService activityLogService) {
        this.productService = productService;
        this.userService = userService;
        this.activityLogService = activityLogService;
    }

    // 商品列表页（支持搜索）
    @GetMapping("/products")
    public String productList(@RequestParam(value = "keyword", required = false) String keyword, Model model, Authentication authentication) {
        // 获取当前登录用户名（用于后续购物车操作）
        String username = authentication.getName();
        // 根据关键词搜索商品并传递到前端页面
        model.addAttribute("products", productService.searchProducts(keyword));
        model.addAttribute("username", username);
        model.addAttribute("keyword", keyword); // 将搜索关键词传递回前端
        return "products"; // 对应 templates/products.html
    }
    
    // 商品详情页
    @GetMapping("/product/{productId}")
    public String productDetail(@PathVariable Long productId, Model model, Authentication authentication) {
        String username = authentication.getName();
        com.example.ecommerce.entity.Product product = productService.getProductById(productId);
        
        // 记录商品浏览日志
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        User user = userService.getCurrentUser(username);
        activityLogService.logProductBrowse(user, product, ipAddress, userAgent);
        
        model.addAttribute("product", product);
        model.addAttribute("username", username);
        return "product-detail"; // 对应 templates/product-detail.html
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