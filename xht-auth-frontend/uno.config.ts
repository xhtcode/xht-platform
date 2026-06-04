// uno.config.js
import {
    defineConfig,
    presetAttributify,
    presetIcons,
    presetTypography,
    presetWind3,
    presetWebFonts,
    transformerDirectives,
    transformerVariantGroup,
} from 'unocss'
import { FileSystemIconLoader } from '@iconify/utils/lib/loader/node-loaders'

/**
 * SVG图标转换函数
 * @param svg SVG图标内容
 */
const transform = (svg: string) => {
    return svg.includes('fill="') ? svg : svg.replace(/^<svg /, '<svg fill="currentColor" ')
}

// noinspection JSUnusedGlobalSymbols
export default defineConfig({
    presets: [
        presetWind3(), // 添加 UnoCSS 的默认样式预设
        presetAttributify({}),
        presetTypography(),
        presetWebFonts({
            fonts: {},
        }),
        presetIcons({
            scale: 1.2, // 图标缩放比例
            prefix: 'i-', // 图标前缀
            warn: true, //当缺少的图标匹配时发出警告
            extraProperties: {
                // 额外属性
                display: 'inline-block',
                width: '16px',
                height: '16px',
            },
            // 图表集合
            collections: {
                // icon 是图标集合名称，使用 `i-icon:图标名` 调用
                login: FileSystemIconLoader(`./src/assets/login`, transform),
            },
        }),
    ],
    safelist: [],
    transformers: [transformerDirectives(), transformerVariantGroup()],
    shortcuts: {
        'm-0-auto': 'm-0 ma', // margin: 0 auto
        'wh-full': 'w-full h-full', // width: 100%, height: 100%
        'flex-center': 'flex justify-center items-center', // flex布局居中
        'flex-x-center': 'flex justify-center', // flex布局：主轴居中
        'flex-y-center': 'flex items-center', // flex布局：交叉轴居中
        'text-overflow': 'overflow-hidden whitespace-nowrap text-ellipsis', // 文本溢出显示省略号
        'flex-x-start': 'flex items-center justify-start',
        'flex-x-between': 'flex items-center justify-between',
        'flex-x-end': 'flex items-center justify-end',
        'text-break': 'whitespace-normal break-all break-words', // 文本溢出换行
    },
    theme: {
        /**
         * 响应式设计
         */
        breakpoints: {
            xxs: '0px',
            xs: '320px',
            sm: '480px',
            md: '768px',
            lg: '1024px',
            xl: '1280px',
            xxl: '1600px',
        },
    },
})
