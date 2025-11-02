# 个人博客系统
一个基于前后端分离架构的现代化博客系统，实现了完整的用户管理、文章发布和权限控制功能。
## 🌟 在线演示
> **注意**：项目部署在本地虚拟机，通过ngrok内网穿透提供访问
> 
> 访问链接：[https://lairdly-uromeric-aracelis.ngrok-free.dev](https://lairdly-uromeric-aracelis.ngrok-free.dev)
> 
> 📅 **访问时间**：仅当虚拟机运行时可访问（建议工作日 9:00-22:00）
## 🛠️ 技术栈
### 后端技术
- **框架**: Spring Boot 3.x
- **ORM**: MyBatis-Plus（简化数据库操作）
- **认证**: Sa-Token（轻量级权限认证框架）
- **数据库**: MySQL 8.0
- **构建工具**: Maven
### 前端技术
- **框架**: Vue.js 3.x
- **HTTP客户端**: Axios
- **UI组件**: Element Plus
- **构建工具**: Vite
### 部署运维
- **容器化**: Docker + Docker Compose
- **反向代理**: Nginx
- **内网穿透**: ngrok
## ✨ 核心功能
### 🔐 用户认证系统
- 用户注册、登录、登出
- 基于Sa-Token的权限控制
- 管理员与普通用户角色区分
### 📝 文章管理
- 文章的增删改查
- Markdown编辑器支持
- 文章状态管理（发布/草稿）
### 💬 交互功能
- 用户评论系统
- 文章浏览统计
- 响应式设计，支持移动端
## 📸 项目截图
### 🏠 首页展示
未登录用户的博客首页，展示文章列表和基本信息
<img width="2538" height="1398" alt="未登录时的首页" src="https://github.com/user-attachments/assets/88164c90-a33c-464c-be05-eb83c38833a3" />
### 👨‍💻 管理员后台
管理员登录后的完整管理界面
<img width="2534" height="1390" alt="管理员登录后的页面" src="https://github.com/user-attachments/assets/3079d535-5d21-4f66-921f-c1bc8638f883" />
### ✍️ 文章编辑
管理员创建和编辑文章的界面
<img width="2550" height="1393" alt="管理员可以创建文章" src="https://github.com/user-attachments/assets/e70bd629-42cc-4c8c-923c-4b4585a08b9e" />
### 👤 用户界面
普通用户登录后的个性化页面
<img width="2499" height="1367" alt="普通用户登录后的页面" src="https://github.com/user-attachments/assets/e5d2fc17-a667-40f1-b313-f2890062150d" />
## 🚀 快速开始
### 环境要求
- jdk 17
- Node.js 20
- MySQL 8.0
- Docker & Docker Compose
### 本地运行
1. **克隆项目**
```bash
git clone https://github.com/Eadn1417/myblog
cd myblog
```
2. **后端启动**
```bash
cd MyBlog
mvn clean install
mvn spring-boot:run
```
3. **前端启动**
```bash
cd satokentest
npm install
npm run dev
```
### Docker部署（推荐）
```bash
# 先build一下
先cd docker-deploy目录
然后docker compose build
# 使用Docker Compose一键启动
docker-compose up -d
# 查看运行状态
docker-compose ps
```
## 📁 项目结构
```
myblog/
├── MyBlog/                 # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/       # Java源码
│   │   │   └── resources/  # 配置文件
│   │   └── test/          # 测试代码
│   └── pom.xml            # Maven配置
├── satokentest/               # 前端项目
│   ├── src/
│   │   ├── components/    # Vue组件
│   │   ├── views/         # 页面视图
│   │   └── utils/         # 工具函数
│   └── package.json       # NPM配置
├── docker-deploy/          # Docker部署配置
│   ├── docker-compose.yml
│   ├── nginx/nginx.conf
│   └── db-scripts/         # 用于初始化数据库，避免每次手动插入数据
|         |__01-shcema.sql  # 先建好数据库和数据表
|         |__02-data.sql    # 载插入数据
└── README.md
```
## 🎯 项目亮点
- **🏗️ 现代化架构**：前后端分离，RESTful API设计
- **🔒 安全认证**：集成Sa-Token，实现细粒度权限控制
- **📦 容器化部署**：Docker一键部署，环境一致性
- **🎨 响应式设计**：适配多种设备，提升用户体验
- **⚡ 高性能**：MyBatis-Plus优化数据库操作
## 📈 学习收获
通过这个项目，我深入掌握了：
- Spring Boot企业级开发
- MyBatis-Plus数据访问层优化
- Sa-Token权限认证框架
- Vue.js前端开发
- Docker容器化部署
- 前后端分离架构设计
## 📞 联系方式
- **邮箱**: 1776411753@qq.com
- **GitHub**: [Eadn1417](https://github.com/Eadn1417)
- **技术博客**: [项目在线地址](https://lairdly-uromeric-aracelis.ngrok-free.dev)
---
⭐ 如果这个项目对你有帮助，请给个Star支持一下！
