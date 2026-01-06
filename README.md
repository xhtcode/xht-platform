# xht-platform

![项目logo]()

## 项目介绍
xht-platform 是一个基于 Spring Cloud Alibaba 的微服务架构平台，专为企业级应用开发设计。平台提供完整的认证授权体系、网关路由、系统管理等核心功能，帮助开发团队快速构建安全、可靠、可扩展的微服务应用。

## 核心优势
- 基于 Spring Cloud Alibaba 生态，兼容主流微服务技术栈
- 完整的认证授权体系，基于 OAuth2 和 JWT
- 模块化设计，支持按需集成
- 丰富的基础管理功能，包括部门、角色、用户、菜单等
- 内置缓存、日志、异常处理等通用组件
- 完善的文档和示例

## 软件架构
![架构图]()

### 技术栈
- **核心框架**：Spring Boot 3.5.8、Spring Cloud 2025.0.0、Spring Cloud Alibaba 2025.0.0.0-preview
- **认证授权**：OAuth2、Spring Security
- **数据访问**：MyBatis Plus、MySQL
- **缓存存储**：Redis
- **服务发现与配置**：Nacos
- **API 网关**：Spring Cloud Gateway
- **负载均衡**：Ribbon
- **服务调用**：OpenFeign
- **消息队列**：RabbitMQ
- **日志管理**：SLF4J、Logback
- **API 文档**：Swagger/OpenAPI
- **前端技术**：Vue 3、Element Plus

## 项目结构

```txt
xht-platform
├── .gitignore
├── LICENSE
├── README.md
├── doc                  # 文档目录
│   ├── sql              # SQL脚本
│   │   ├── datatabase.sql
│   │   ├── xht-generate.sql
│   │   ├── xht-platform.sql
│   │   └── xht_nacos.sql
│   ├── 开发规范和编码指南.md
│   └── 开发需求.md
├── lombok.config
├── pom.xml
├── xht-api              # API 定义
│   ├── xht-api-system       # 系统API
│   └── pom.xml
├── xht-auth             # 认证中心
│   ├── src
│   └── pom.xml
├── xht-demo             # 示例模块
│   ├── src
│   └── pom.xml
├── xht-framework        # 框架核心
│   ├── xht-framework-bom               # BOM管理
│   ├── xht-framework-core              # 核心模块
│   ├── xht-framework-log               # 日志模块
│   ├── xht-framework-mybatis           # MyBatis扩展
│   ├── xht-framework-openfeign         # OpenFeign扩展
│   ├── xht-framework-redis             # Redis缓存模块
│   ├── xht-framework-resource-server   # 资源服务器
│   ├── xht-framework-security          # 安全模块
│   ├── xht-framework-sms               # 短信模块
│   ├── xht-framework-swagger           # Swagger扩展
│   ├── xht-framework-web               # Web扩展
│   ├── xht-spring-boot-starter         # Spring Boot启动器
│   ├── xht-spring-cloud-starter        # Spring Cloud启动器
│   └── pom.xml
├── xht-gateway          # 网关服务
│   ├── src
│   └── pom.xml
└── xht-modules          # 业务模块
    ├── xht-module-generate        # 代码生成模块
    ├── xht-module-system          # 系统管理模块
    └── pom.xml
```