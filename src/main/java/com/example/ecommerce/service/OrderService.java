package com.example.ecommerce.service;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    // 创建订单（从购物车生成）
    @Transactional
    public Order createOrder(String username, String email) {
        User user = userService.getCurrentUser(username);
        List<CartItem> cartItems = cartService.getCartItems(username);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车为空，无法创建订单");
        }

        // 创建订单
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUser(user);
        order.setStatus("已付款"); // 修改状态为已付款，因为我们简化了付款流程
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setEmail(email); // 设置用户邮箱

        // 计算总价并创建订单项
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            orderItem.setTotalPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            order.getOrderItems().add(orderItem);
            totalPrice = totalPrice.add(orderItem.getTotalPrice());
        }
        order.setTotalPrice(totalPrice);

        // 保存订单并清空购物车
        orderRepository.save(order);
        cartService.clearCart(username);

        // 移除实际发送邮件的代码，仅保留邮箱输入功能
        System.out.println("订单已创建，邮箱：" + email + "，订单号：" + order.getOrderNumber());

        return order;
    }

    // 获取用户的所有订单
    public List<Order> getUserOrders(String username) {
        User user = userService.getCurrentUser(username);
        return orderRepository.findByUser(user);
    }

    // 获取订单详情
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在：" + orderId));
    }
    
    // 获取所有订单（用于管理员统计）
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 更新订单状态
    public void updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    // 生成订单号
    private String generateOrderNumber() {
        return "ORD" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12).toUpperCase();
    }
}
