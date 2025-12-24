pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: '${GIT_REPO_URL}'
            }
        }
        
        stage('Build') {
            steps {
                // 使用Maven构建项目
                sh 'mvn clean package -DskipTests'
                
                // 构建Docker镜像
                sh 'docker build -t ecommerce-app .'
            }
        }
        
        stage('Test') {
            steps {
                // 运行单元测试
                sh 'mvn test'
            }
        }
        
        stage('Deploy to Aliyun ECS') {
            steps {
                // 连接到阿里云ECS服务器
                sshagent(['aliyun-ecs-credentials']) {
                    // 上传Docker Compose文件
                    sh 'scp docker-compose.yml root@${ALIYUN_ECS_IP}:/home/ecommerce/'
                    
                    // 上传构建的Docker镜像
                    sh 'docker save ecommerce-app | ssh root@${ALIYUN_ECS_IP} docker load'
                    
                    // 在服务器上启动服务
                    sh 'ssh root@${ALIYUN_ECS_IP} "cd /home/ecommerce && docker-compose up -d"'
                }
            }
        }
    }
    
    post {
        always {
            // 清理工作空间
            cleanWs()
        }
        success {
            echo '部署成功！'
        }
        failure {
            echo '部署失败！'
        }
    }
}