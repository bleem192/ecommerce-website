package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 构造器注入（Spring 自动装配）
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Spring Security 登录时调用：根据用户名查询用户
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在：" + username));

        // 根据用户角色创建权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        // 封装成 Spring Security 的 UserDetails（包含用户名、密码、权限）
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    // 获取当前登录用户的实体
    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    // 保存用户（初始化测试用户用）
    public User saveUser(User user) {
        // 密码加密（Spring Security 要求密码必须加密）
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置注册时间
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        if (user.getRole() == null) {
            user.setRole("USER"); // 默认角色为普通用户
        }
        return userRepository.save(user);
    }
    
    // 更新用户信息（不包括密码）
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + id));
        
        // 更新用户信息
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setProvince(updatedUser.getProvince());
        existingUser.setPostalCode(updatedUser.getPostalCode());
        existingUser.setUpdatedAt(LocalDateTime.now());
        
        return userRepository.save(existingUser);
    }
    
    // 更新用户密码
    public User updateUserPassword(Long id, String newPassword) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + id));
        
        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setUpdatedAt(LocalDateTime.now());
        
        return userRepository.save(existingUser);
    }
    
    // 删除用户
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + id));
        
        userRepository.delete(user);
    }
    
    // 获取所有用户（用于管理员客户管理）
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // 根据角色获取用户
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
    
    // 根据ID获取用户
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + id));
    }
    
    // 根据用户名或邮箱搜索用户
    public List<User> searchUsers(String keyword) {
        return userRepository.findByUsernameContainingOrEmailContaining(keyword, keyword);
    }
}