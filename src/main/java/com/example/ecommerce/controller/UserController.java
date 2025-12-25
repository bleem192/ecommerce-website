package com.example.ecommerce.controller;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // 用户注册页面
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 处理用户注册
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam String confirmPassword) {
        // 验证密码是否一致
        if (!user.getPassword().equals(confirmPassword)) {
            return "redirect:/register?passwordMismatch";
        }
        
        try {
            userService.saveUser(user);
            return "redirect:/login?registerSuccess";
        } catch (RuntimeException e) {
            if (e.getMessage().equals("用户名已存在")) {
                return "redirect:/register?usernameExists";
            } else if (e.getMessage().equals("邮箱已存在")) {
                return "redirect:/register?emailExists";
            } else {
                throw e; // 其他异常继续抛出
            }
        }
    }
    
    // 用户个人信息页面
    @GetMapping("/user/profile")
    public String showUserProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getCurrentUser(username);
        model.addAttribute("user", user);
        return "user-profile";
    }
    
    // 处理更新用户信息
    @PostMapping("/user/profile")
    public String updateUserProfile(@ModelAttribute User updatedUser, Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.getCurrentUser(username);
        userService.updateUser(currentUser.getId(), updatedUser);
        return "redirect:/user/profile?updateSuccess";
    }
    
    // 管理员：查看所有用户
    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-users";
    }
    
    // 管理员：查看用户详情
    @GetMapping("/admin/users/{id}")
    public String showUserDetails(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin-user-details";
    }
    
    // 管理员：编辑用户页面
    @GetMapping("/admin/users/{id}/edit")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin-user-edit";
    }
    
    // 管理员：处理更新用户
    @PostMapping("/admin/users/{id}/edit")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
        userService.updateUser(id, updatedUser);
        return "redirect:/admin/users?updateSuccess";
    }
    
    // 管理员：删除用户
    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users?deleteSuccess";
    }
    
    // 管理员：搜索用户
    @GetMapping("/admin/users/search")
    public String searchUsers(@RequestParam String keyword, Model model) {
        List<User> users = userService.searchUsers(keyword);
        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "admin-users";
    }
}
