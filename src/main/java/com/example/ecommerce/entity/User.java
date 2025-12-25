package com.example.ecommerce.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "users") // 数据库表名
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增ID
    private Long id;

    @Column(unique = true, nullable = false) // 用户名唯一、非空
    private String username;

    @Column(nullable = false)
    private String password; // 密码（Spring Security 会加密）

    private String fullName; // 昵称
    
    private String role; // 用户角色
    
    @Column(unique = true)
    private String email; // 邮箱
    
    private String phone; // 电话
    
    private String address; // 地址
    
    private String city; // 城市
    
    private String province; // 省份
    
    private String postalCode; // 邮政编码
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 注册时间

    private LocalDateTime updatedAt; // 更新时间

    // 关联购物车（一个用户对应多个购物车项）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;
    
    // 关联订单（一个用户对应多个订单）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders;
    
    // 关联用户活动日志（一个用户对应多个日志）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserActivityLog> activityLogs;
}