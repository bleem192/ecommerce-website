package com.example.ecommerce.repository;

import com.example.ecommerce.entity.UserActivityLog;
import com.example.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
    // 根据用户查询活动日志
    List<UserActivityLog> findByUser(User user);
    
    // 根据活动类型查询日志
    List<UserActivityLog> findByActivityType(String activityType);
    
    // 根据用户和活动类型查询日志
    List<UserActivityLog> findByUserAndActivityType(User user, String activityType);
}