# 电商网站项目

一个基于Spring Boot的电商网站项目，实现了完整的购买流程和客户管理功能。

## 功能特点

### 购买流程
- 浏览/查询商品
- 添加商品至购物车
- 结账付款（简化为填写邮箱）
- 订单确认和查看

### 客户管理
- 用户注册和登录
- 用户信息管理
- 客户活动日志记录（浏览、购买、登录、订单查看）

### 管理员功能
- 商品管理（增删改查）
- 客户管理
- 订单管理
- 活动日志查看

## 技术栈

- **后端框架**：Spring Boot 2.7.18
- **前端模板**：Thymeleaf
- **安全认证**：Spring Security
- **数据库**：MySQL
- **ORM框架**：Spring Data JPA
- **构建工具**：Maven
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
```

### 3. 构建和运行

使用Maven构建并运行项目：

```bash
mvn spring-boot:run
```

### 4. 访问网站

打开浏览器访问：http://localhost:8080

## 项目结构

```
src/main/java/com/example/ecommerce/
├── config/             # 配置类
├── controller/         # 控制器
├── entity/             # 实体类
├── repository/         # 数据访问层
├── service/            # 业务逻辑层
└── EcommerceApplication.java  # 应用入口
```

### 主要控制器

- `ProductController`：商品列表、搜索和详情页
- `CartController`：购物车管理和结账
- `OrderController`：订单查看和管理
- `UserController`：用户信息管理
- `AdminController`：管理员功能
- `UserActivityLogController`：用户活动日志管理

### 实体类

- `Product`：商品信息
- `User`：用户信息
- `CartItem`：购物车商品
- `Order`：订单信息
- `OrderItem`：订单商品
- `UserActivityLog`：用户活动日志

## 主要功能模块

### 1. 商品管理
- 商品列表展示
- 商品搜索
- 商品详情页
- 浏览记录日志

### 2. 购物车
- 添加商品到购物车
- 购物车商品管理
- 结账流程

### 3. 订单管理
- 订单创建
- 订单查看
- 订单详情
- 订单记录日志

### 4. 用户管理
- 用户注册
- 用户登录
- 用户信息修改
- 用户活动日志

### 5. 管理员功能
- 客户管理
- 商品管理
- 订单管理
- 活动日志查看

## 配置说明

### 数据库配置

在`application.properties`中配置MySQL数据库连接：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=your-password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 安全配置

在`SecurityConfig.java`中配置用户认证和授权规则。

## 开发说明

### 开发工具

- IDE：IntelliJ IDEA 或 Eclipse
- 数据库工具：MySQL Workbench 或 Navicat

### 构建命令

```bash
# 编译项目
mvn compile

# 运行测试
mvn test

# 打包项目
mvn package

# 运行项目
mvn spring-boot:run
```

## 部署说明

### Docker部署

项目包含Dockerfile和docker-compose.yml文件，可以使用Docker部署：

```bash
docker-compose up -d
```

### 传统部署

1. 打包项目：`mvn package`
2. 将生成的jar文件复制到服务器
3. 运行：`java -jar ecommerce-1.0.0.jar`

## 许可证

MIT License
