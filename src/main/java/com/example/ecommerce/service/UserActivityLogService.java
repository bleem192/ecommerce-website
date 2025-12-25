package com.example.ecommerce.service;

import com.example.ecommerce.entity.UserActivityLog;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.repository.UserActivityLogRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserActivityLogService {
    private final UserActivityLogRepository activityLogRepository;
    
    public UserActivityLogService(UserActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }
    
    // 记录用户浏览商品日志
    public UserActivityLog logProductBrowse(User user, Product product, String ipAddress, String userAgent) {
        UserActivityLog log = new UserActivityLog();
        log.setUser(user);
        log.setActivityType("BROWSE");
        log.setProduct(product);
        log.setActivityTime(LocalDateTime.now());
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setDetails("浏览商品: " + product.getName());
        
        return activityLogRepository.save(log);
    }
    
    // 记录用户购买日志
    public UserActivityLog logProductPurchase(User user, Order order, String ipAddress, String userAgent) {
        UserActivityLog log = new UserActivityLog();
        log.setUser(user);
        log.setActivityType("PURCHASE");
        log.setOrder(order);
        log.setActivityTime(LocalDateTime.now());
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setDetails("购买商品，订单号: " + order.getOrderNumber());
        
        return activityLogRepository.save(log);
    }
    
    // 记录用户查看订单日志
    public UserActivityLog logOrderView(User user, Order order, String ipAddress, String userAgent) {
        UserActivityLog log = new UserActivityLog();
        log.setUser(user);
        log.setActivityType("VIEW_ORDER");
        log.setOrder(order);
        log.setActivityTime(LocalDateTime.now());
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setDetails("查看订单: " + order.getOrderNumber());
        
        return activityLogRepository.save(log);
    }
    
    // 记录用户登录日志
    public UserActivityLog logUserLogin(User user, String ipAddress, String userAgent) {
        UserActivityLog log = new UserActivityLog();
        log.setUser(user);
        log.setActivityType("LOGIN");
        log.setActivityTime(LocalDateTime.now());
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setDetails("用户登录");
        
        return activityLogRepository.save(log);
    }
    
    // 获取用户的所有活动日志
    public List<UserActivityLog> getUserActivityLogs(User user) {
        return activityLogRepository.findByUser(user);
    }
    
    // 获取所有活动日志
    public List<UserActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAll();
    }
    
    // 获取所有特定类型的活动日志
    public List<UserActivityLog> getActivityLogsByType(String activityType) {
        return activityLogRepository.findByActivityType(activityType);
    }
    
    // 获取用户的特定类型活动日志
    public List<UserActivityLog> getUserActivityLogsByType(User user, String activityType) {
        return activityLogRepository.findByUserAndActivityType(user, activityType);
    }
}