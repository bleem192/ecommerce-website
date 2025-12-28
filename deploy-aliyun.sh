#!/bin/bash

# 阿里云服务器部署脚本
# 功能：安装Docker、Docker Compose，配置环境并启动服务

echo "=== 阿里云服务器部署脚本 ==="

# 1. 更新系统包
echo "1. 更新系统包..."
yum update -y

# 2. 安装Docker
echo "2. 安装Docker..."
yum install -y yum-utils device-mapper-persistent-data lvm2
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
yum install -y docker-ce docker-ce-cli containerd.io

# 3. 启动Docker服务
echo "3. 启动Docker服务..."
systemctl start docker
systemctl enable docker

# 4. 安装Docker Compose
echo "4. 安装Docker Compose..."
curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

# 5. 验证安装
echo "5. 验证安装..."
docker --version
docker-compose --version

echo "=== 环境配置完成 ==="
echo "使用以下命令启动服务："
echo "cd /path/to/ecommerce && docker-compose up -d"