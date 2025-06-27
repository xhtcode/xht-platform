

# xht-platform
这是一个开源平台，提供认证中心、网关、系统模块等基础功能，适用于微服务架构。

## 项目介绍
xht-platform 是一个基于 Spring Cloud 的微服务基础平台，包含认证中心、网关和系统模块，系统模块又包括部门管理、角色管理、用户管理、菜单管理、字典管理等功能。该项目适用于快速搭建微服务架构的后端管理系统。

## 软件架构
- 使用 Spring Cloud Alibaba Nacos 作为注册中心
- Spring Cloud Gateway 作为 API 网关
- Spring Authorization Server 实现认证中心
- Spring Security + OAuth2 实现权限控制
- 使用 MyBatis Plus 与 MySQL 数据库交互
- 使用 Redis 存储授权信息
- 使用 MapStruct 进行实体转换

## 安装教程
1. 安装 JDK 17+
2. 安装 Maven 3.8+
3. 安装 Nacos 2.2+
4. 安装 MySQL 8.0+
5. 克隆项目：`git clone [项目地址]`
6. 使用 Maven 构建：`mvn clean install`

## 使用说明
### 认证中心 (xht-auth)
认证中心基于 Spring Authorization Server 实现，提供以下功能：
- 多种认证方式 (密码、验证码等)
- JWT 令牌生成与验证
- 提供登录接口和验证码接口

### 网关 (xht-gateway)
网关基于 Spring Cloud Gateway 实现，提供：
- 路由管理
- 跨域处理
- 全局异常处理
- 请求链路追踪

### 系统模块 (xht-module-system)
系统模块包含以下子功能：

#### 部门管理
- 创建、删除、更新部门
- 查询部门树
- �
- 管理部门状态
- 部门岗位管理

#### 角色管理
- 创建、删除、更新角色
- 查询角色列表
- �

#### 用户管理
- 用户注册、删除、更新
- 用户状态管理
- 用户部门绑定
- 用户角色绑定
- 密码重置与修改

#### 菜单管理
- 创建、删除、更新菜单
- 查询菜单树
- �

#### 字典管理
- 字典类型管理
- 字典项管理
- 根据字典编码查询

## 参与贡献
1. Fork 项目
2. 创建新分支
3. 提交代码
4. 创建 Pull Request

## 技术栈
- Spring Cloud Alibaba
- Spring Authorization Server
- Spring Security
- OAuth2
- Redis
- MyBatis Plus
- MapStruct
- Lombok
- Swagger

## 特技
- 提供统一的响应结构 R<T>
- 自动填充创建人、更新人信息
- 支持统一的异常处理
- 支持链路追踪
- 提供代码生成器
- 支持自动注册服务
- 提供统一的跨域处理
- 支持请求参数校验
- 提供代码生成工具
- 提供树形结构处理工具