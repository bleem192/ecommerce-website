package com.example.ecommerce.repository;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 根据用户查询购物车项
    List<CartItem> findByUser(User user);
    // 根据用户和商品查询购物车项（判断是否已加入购物车）
    CartItem findByUserAndProduct(User user, com.example.ecommerce.entity.Product product);
}