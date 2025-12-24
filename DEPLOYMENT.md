# 阿里云部署与CI/CD配置指南

## 1. 阿里云服务器配置

### 1.1 购买ECS实例
1. 登录阿里云控制台，进入ECS服务
2. 购买一台ECS实例，建议配置：
   - 实例规格：2核4GB或更高
   - 镜像：CentOS 7.9或Ubuntu 20.04
   - 网络：配置公网IP
   - 存储：40GB以上高效云盘

### 1.2 安全组配置
1. 进入ECS实例的安全组配置
2. 开放以下端口：
   - 8080（应用访问端口）
   - 22（SSH访问端口）
   - 3306（MySQL访问端口，可选，仅开发时使用）

### 1.3 安装依赖
使用我们提供的部署脚本快速安装环境：

```bash
# 1. 连接到ECS服务器
ssh root@your-ecs-ip

# 2. 创建项目目录
mkdir -p /home/ecommerce
cd /home/ecommerce

# 3. 下载部署脚本
wget https://your-repo-url/deploy-aliyun.sh
chmod +x deploy-aliyun.sh

# 4. 运行脚本安装环境
./deploy-aliyun.sh
```

## 2. CI/CD流水线配置

### 2.1 选项1：使用阿里云DevOps流水线

1. 登录阿里云DevOps控制台
2. 创建项目并关联代码仓库
3. 创建流水线，配置以下阶段：
   - **代码源**：关联GitHub/GitLab代码仓库
   - **构建**：使用Maven构建项目
   - **镜像构建**：使用Dockerfile构建镜像
   - **部署**：部署到ECS服务器

4. 配置部署阶段参数：
   - 服务器地址：ECS实例公网IP
   - 登录方式：密码或密钥
   - 部署脚本：
     ```bash
     cd /home/ecommerce
     docker-compose up -d
     ```

### 2.2 选项2：使用Jenkins流水线

1. 在ECS服务器或独立服务器上安装Jenkins
2. 安装必要插件：Git Plugin、Pipeline Plugin、SSH Agent Plugin
3. 配置Jenkins凭证：
   - ECS服务器SSH凭证
   - Git仓库凭证

4. 创建流水线项目，选择"Pipeline script from SCM"
5. 配置Git仓库地址和Jenkinsfile路径

### 2.3 选项3：使用GitLab CI/CD

1. 在GitLab项目中启用CI/CD
2. 配置GitLab Runner（可在ECS服务器上安装）
3. 设置CI/CD变量：
   - `ALIYUN_ECS_IP`：ECS实例公网IP
   - `ALIYUN_ECS_SSH_KEY`：ECS服务器SSH私钥

## 3. 手动部署测试

### 3.1 本地构建
```bash
cd ecommerce
mvn clean package -DskipTests
docker build -t ecommerce-app .
```

### 3.2 上传到ECS服务器
```bash
# 上传Docker镜像
docker save ecommerce-app | ssh root@your-ecs-ip docker load

# 上传docker-compose.yml文件
scp docker-compose.yml root@your-ecs-ip:/home/ecommerce/
```

### 3.3 启动服务
```bash
ssh root@your-ecs-ip "cd /home/ecommerce && docker-compose up -d"
```

### 3.4 验证部署
1. 访问 `http://your-ecs-ip:8080` 查看应用
2. 使用管理员账号登录测试功能
3. 检查数据库连接和数据初始化

## 4. 常见问题排查

### 4.1 应用启动失败
```bash
# 查看应用日志
docker logs -f ecommerce-app

# 查看MySQL日志
docker logs -f ecommerce-mysql
```

### 4.2 端口无法访问
```bash
# 检查安全组配置是否正确
# 检查容器端口映射
ss -tuln | grep 8080
```

### 4.3 数据库连接错误
```bash
# 检查application.properties中的数据库配置
# 检查MySQL容器是否正常运行
```

## 5. CI/CD最佳实践

1. **分支管理**：使用Git Flow，仅主分支触发生产部署
2. **自动化测试**：在构建阶段运行单元测试和集成测试
3. **镜像版本控制**：为Docker镜像添加版本标签
4. **回滚机制**：配置自动回滚策略
5. **监控告警**：配置应用和服务器监控

## 6. 后续优化

1. **HTTPS配置**：使用阿里云SSL证书配置HTTPS
2. **负载均衡**：配置SLB负载均衡
3. **数据库优化**：使用RDS数据库替代容器化MySQL
4. **弹性伸缩**：配置Auto Scaling自动伸缩

---

**部署成功后，您的电商网站将在阿里云服务器上运行，并通过CI/CD流水线实现自动化部署！**
