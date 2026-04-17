// src/types/env.d.ts
interface ImportMetaEnv {
    /**
     * 应用标题
     */
    VITE_APP_TITLE: string
    /**
     * 应用端口
     */
    VITE_APP_PORT: number
    /**
     * API基础路径(反向代理)
     */
    VITE_BASE_API: string
}

export interface ImportMeta {
    readonly env: ImportMetaEnv
}

/**
 * 平台的名称、版本、运行所需的node版本、依赖、构建时间的类型提示
 */
export declare const __APP_INFO__: {
    pkg: {
        name: string
        version: string
        engines: {
            node: string
        }
        dependencies: Record<string, string>
        devDependencies: Record<string, string>
    }
    buildTimestamp: number
}

declare module '*.vue' {
    import type {DefineComponent} from 'vue'
    const component: DefineComponent<object, object, any>
    export default component
}

export {}
