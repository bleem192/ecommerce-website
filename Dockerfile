# 阶段1：构建 Jar 包（多阶段构建，减小镜像体积）
# 使用镜像加速源
FROM docker.m.daocloud.io/maven:3.8.5-openjdk-8 AS build
# 设置工作目录
WORKDIR /app
# 复制 pom.xml 和源码
COPY pom.xml .
COPY src ./src
# 配置Maven阿里云镜像源
RUN mkdir -p /root/.m2 && echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd"><mirrors><mirror><id>aliyun</id><name>aliyun maven</name><url>https://maven.aliyun.com/repository/public</url><mirrorOf>central</mirrorOf></mirror></mirrors></settings>' > /root/.m2/settings.xml
# 打包生成 Jar 包（跳过测试）
RUN mvn clean package -DskipTests

# 阶段2：运行 Jar 包
# 使用镜像加速源
FROM docker.m.daocloud.io/eclipse-temurin:8-jdk-alpine
# 设置工作目录
WORKDIR /app
# 从构建阶段复制 Jar 包到当前镜像
COPY --from=build /app/target/ecommerce-1.0.0.jar app.jar
# 暴露端口（与 application.properties 中的 server.port 一致）
EXPOSE 8080
# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
