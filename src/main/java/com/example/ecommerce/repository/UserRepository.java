package com.example.ecommerce.repository;

import com.example.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

// JpaRepository<实体类, 主键类型>：内置增删改查方法
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查询用户（登录时用）
    Optional<User> findByUsername(String username);
    
    // 根据邮箱查询用户
    Optional<User> findByEmail(String email);
    
    // 根据角色查询用户
    List<User> findByRole(String role);
    
    // 根据用户名或邮箱模糊查询
    List<User> findByUsernameContainingOrEmailContaining(String username, String email);
}