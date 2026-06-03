import {fileURLToPath, URL} from 'node:url'

import UnoCSS from 'unocss/vite'
import {ConfigEnv, loadEnv, UserConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'

// @ts-nocheck
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
                    imports: ['vue', '@vueuse/core'],
                    resolvers: [ElementPlusResolver()],
                }),
                Components({
                    resolvers: [ElementPlusResolver()],
                    globs: ['src/components/**/index.vue'], // 指定自定义组件位置(默认:src/components)
                }),
            ],
            // 本地反向代理解决浏览器跨域限制
            server: {
                host: '0.0.0.0',
                port: Number(env.VITE_APP_PORT) || 3009,
                allowedHosts: ['xhtgateway.com', 'xhtiam.com', 'www.xht.gatway.com', 'www.xht.sso.com'],
                open: false, // 启动是否自动打开浏览器
                proxy: {
                    [env.VITE_BASE_API]: {
                        target: env.VITE_GATEWAY_API, // easymock
                        changeOrigin: true,
                        rewrite: (path: string) => path.replace(new RegExp('^' + env.VITE_BASE_API), ''),
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
