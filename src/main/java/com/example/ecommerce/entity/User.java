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
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 注册时间

    // 关联购物车（一个用户对应多个购物车项）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;
}