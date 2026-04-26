# xht-auth-frontend

认证服务前端项目，基于 Vue 3 + Vite 构建。

## 技术栈

- **框架**: Vue 3.5 + TypeScript
- **构建工具**: Vite 7.3
- **UI 组件库**: Element Plus 2.13
- **状态管理**: Pinia 3.0
- **路由**: vue-router 5.0
- **样式**: UnoCSS (原子化 CSS)
- **HTTP**: axios

## 环境要求

- Node.js: `^20.19.0 || >=22.12.0`

## 安装依赖

1. npm install

## 开发环境

npm run dev

启动后访问 `http://localhost:3009`

## 构建

\# 开发环境构建

npm run build:dev

<br />

\# 测试环境构建

npm run build:test

<br />

\# 生产环境构建

npm run build:prod

## 项目结构

src/

├── assets/          # 静态资源

├── components/      # 公共组件

├── router/          # 路由配置

├── stores/          # Pinia 状态管理

├── utils/           # 工具函数

├── views/           # 页面组件

├── App.vue          # 根组件

└── main.ts          # 入口文件

## 代理配置

开发环境配置了反向代理解决跨域问题：

- `/api` - 代理到 `VITE_GATEWAY_API` 环境变量配置的后端接口
- `/test` - 代理到 `http://127.0.0.1:8180`

可在 `.env.development`、`.env.test`、`.env.production` 文件中配置 `VITE_GATEWAY_API` 和 `VITE_APP_PORT`。
