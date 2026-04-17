import {fileURLToPath, URL} from 'node:url'

import UnoCSS from 'unocss/vite'
import {ConfigEnv, loadEnv, UserConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

export default ({command, mode}: ConfigEnv): UserConfig => {
    // 获取 .env 环境配置文件
    const env = loadEnv(mode, process.cwd())

    return (
        {
            base: env.VITE_APP_BASE || '/',
            plugins: [
                vue(),
                UnoCSS(),
                vueDevTools(),
                AutoImport({
                    resolvers: [ElementPlusResolver()],
                }),
                Components({
                    resolvers: [ElementPlusResolver()],
                }),
            ],
            // 本地反向代理解决浏览器跨域限制
            server: {
                host: '0.0.0.0',
                port: Number(env.VITE_APP_PORT) || 3009,
                open: false, // 启动是否自动打开浏览器
                proxy: {
                    ['/api']: {
                        target: env.VITE_GATEWAY_API, // 有来商城线上接口地址
                        changeOrigin: true,
                        rewrite: path => path.replace(new RegExp('^/api' ), '')
                    },
                    ['/test']: {
                        target: 'http://127.0.0.1:8180', // 有来商城线上接口地址
                        changeOrigin: true,
                        rewrite: path => path.replace(new RegExp('^/test' ), '')
                    }
                }
            },
            resolve: {
                alias: {
                    '@': fileURLToPath(new URL('./src', import.meta.url))
                }
            }
        }
    )
}
