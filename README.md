# 电商网站项目

一个基于Spring Boot的完整电商网站项目，实现了从商品浏览到订单管理的全流程功能。
姓名：林冠中         学号： 202330451101    
部署在线网站：
 http://8.138.245.15:8080 
测试账号口令： id：testuser 密码：123456
 id：admin   密码： 123456                  
## 功能特点

### 用户功能
- 商品列表浏览和关键词搜索
- 商品详情查看
- 购物车管理（添加、删除商品）
- 订单结算（简化为填写邮箱）
- 订单查看和管理
- 用户注册和登录
- 用户信息管理

### 管理员功能
- 商品管理：添加、编辑、删除商品
- 订单管理：查看所有订单、更新订单状态
- 客户管理：查看所有用户、修改用户角色（管理员/普通用户）
- 销售统计：订单数据统计、销售额统计
- 用户活动日志：记录和查看用户浏览、购买、登录等行为

## 技术栈

- **后端框架**：Spring Boot 2.7.18
- **前端模板**：Thymeleaf 3.x
- **安全认证**：Spring Security
- **数据库**：MySQL 8.0+
- **ORM框架**：Spring Data JPA
- **构建工具**：Maven
- **前端框架**：Bootstrap 5.1.3
- **开发语言**：Java 8

## 环境要求

- JDK 8
- Maven 3.6+
- MySQL 8.0+

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/bleem192/ecommerce-website.git
cd ecommerce-website/ecommerce
```

### 2. 配置数据库

创建MySQL数据库：

```sql
CREATE DATABASE ecommerce;
```

修改`src/main/resources/application.properties`文件，配置数据库连接：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=your-password

# JPA配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3. 构建和运行

使用Maven构建并运行项目：

```bash
# 使用Maven运行
mvn spring-boot:run

# 或使用Docker运行
docker compose up --build -d
```

### 4. 访问网站

打开浏览器访问：http://localhost:8080

## 项目结构

```
src/main/java/com/example/ecommerce/
├── config/                    # 配置类
│   └── SecurityConfig.java    # Spring Security配置
├── controller/                # 控制器
│   ├── AdminController.java           # 管理员功能
│   ├── CartController.java            # 购物车管理
│   ├── LoginController.java           # 登录控制器
│   ├── OrderController.java           # 订单管理
│   ├── ProductController.java         # 商品管理
│   ├── UserActivityLogController.java # 用户活动日志
│   └── UserController.java            # 用户管理
├── entity/                    # 实体类
│   ├── CartItem.java         # 购物车商品
│   ├── Order.java            # 订单
│   ├── OrderItem.java        # 订单商品
│   ├── Product.java          # 商品
│   ├── User.java             # 用户
│   └── UserActivityLog.java  # 用户活动日志
├── repository/                # 数据访问层
│   ├── CartItemRepository.java
│   ├── OrderItemRepository.java
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   ├── UserActivityLogRepository.java
│   └── UserRepository.java
├── service/                   # 业务逻辑层
│   ├── CartService.java
│   ├── OrderService.java
│   ├── ProductService.java
│   ├── UserActivityLogService.java
│   └── UserService.java
└── EcommerceApplication.java  # 应用入口
```

## 主要功能模块

### 1. 商品管理
- 商品列表展示和搜索
- 商品详情页
- 商品添加、编辑和删除（管理员）

### 2. 购物车
- 添加商品到购物车
- 查看购物车
- 删除购物车商品
- 结算购物车

### 3. 订单系统
- 创建订单
- 查看订单列表
- 查看订单详情
- 更新订单状态（管理员）
- 订单数据统计

### 4. 用户系统
- 用户注册
- 用户登录
- 用户信息管理
- 用户角色管理（管理员）

### 5. 管理员功能
- 商品管理页面：`/admin/products`
- 订单管理页面：`/admin/orders`
- 客户管理页面：`/admin/customers`
- 销售统计页面：`/admin/statistics`

## 管理功能使用说明

### 角色管理
在客户管理页面(`/admin/customers`)，管理员可以：
- 查看所有用户的详细信息
- 通过下拉菜单修改用户角色（普通用户/管理员）
- 系统会自动记录角色变更的时间

### 订单管理
在订单管理页面(`/admin/orders`)，管理员可以：
- 查看所有用户的订单列表
- 点击订单查看详细信息
- 更新订单状态（待付款/已付款/已发货/已完成/已取消）

### 商品管理
在商品管理页面(`/admin/products`)，管理员可以：
- 查看所有商品列表
- 添加新商品
- 编辑现有商品信息
- 删除不需要的商品

## 部署说明

### Docker部署
项目包含Dockerfile和docker-compose.yml文件，可以使用Docker快速部署：

```bash
docker-compose up --build -d
```

### 传统部署
1. 打包项目：
```bash
mvn package
```

2. 运行生成的jar文件：
```bash
java -jar target/ecommerce-1.0.0.jar
```

## 安全特性

- 使用Spring Security实现身份认证和授权
- 密码加密存储（BCrypt）
- 基于角色的访问控制
- 防止CSRF攻击

## 许可证

MIT License
