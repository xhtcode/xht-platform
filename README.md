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
├── lombok.config
├── pom.xml
├── doc                          # 文档目录
│   ├── sql                      # SQL脚本
│   ├── 安装部署
│   ├── 开发规范和编码指南.md
│   └── 开发需求.md
├── xht-framework-parent         # 框架顶层父工程
│   ├── xht-framework-core                   # 核心模块（非 starter）
│   ├── xht-framework-dependencies           # 依赖版本 BOM
│   ├── xht-cache-spring-boot-starter        # Redis 缓存
│   ├── xht-log-spring-boot-starter          # 日志
│   ├── xht-mybatis-plus-spring-boot-starter # MyBatis-Plus 扩展
│   ├── xht-openfeign-spring-boot-starter    # OpenFeign 扩展
│   ├── xht-resource-server-spring-boot-starter # 资源服务器
│   ├── xht-security-spring-boot-starter     # 安全
│   ├── xht-sms-spring-boot-starter          # 短信
│   ├── xht-swagger-spring-boot-starter      # Swagger/OpenAPI 文档
│   ├── xht-web-spring-boot-starter          # Web 通信扩展
│   ├── xht-spring-cloud-starter             # Spring Cloud 启动器（Nacos 注册/配置、负载均衡）
│   └── pom.xml
├── xht-auth                     # 认证中心
├── xht-gateway                  # 网关服务
├── xht-admin                    # 后台管理服务（adapter/domain/infrastructure/start）
├── xht-generate                 # 代码生成服务（adapter/domain/infrastructure/start）
├── xht-platform-client          # 服务间调用客户端（blog/dict/message）
└── xht-platform-common          # 公共模块（admin-common/generate-common）
```