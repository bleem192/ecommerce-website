package com.example.ecommerce.controller;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.UserActivityLogService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserActivityLogController {
    private final UserActivityLogService activityLogService;
    private final UserService userService;
    
    public UserActivityLogController(UserActivityLogService activityLogService, UserService userService) {
        this.activityLogService = activityLogService;
        this.userService = userService;
    }
    
    // 用户查看自己的活动日志
    @GetMapping("/user/activity-logs")
    public String showUserActivityLogs(Model model, Authentication authentication, 
                                      @RequestParam(required = false) String activityType) {
        String username = authentication.getName();
        User user = userService.getCurrentUser(username);
        
        if (activityType != null && !activityType.isEmpty()) {
            model.addAttribute("activityLogs", activityLogService.getUserActivityLogsByType(user, activityType));
        } else {
            model.addAttribute("activityLogs", activityLogService.getUserActivityLogs(user));
        }
        
        model.addAttribute("selectedType", activityType);
        return "user-activity-logs";
    }
    
    // 管理员查看所有用户的活动日志
    @GetMapping("/admin/activity-logs")
    public String showAllActivityLogs(Model model, @RequestParam(required = false) String activityType) {
        if (activityType != null && !activityType.isEmpty()) {
            model.addAttribute("activityLogs", activityLogService.getActivityLogsByType(activityType));
        } else {
            model.addAttribute("activityLogs", activityLogService.getAllActivityLogs());
        }
        
        model.addAttribute("selectedType", activityType);
        return "admin-activity-logs";
    }
}