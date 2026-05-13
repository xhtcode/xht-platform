// 导入必要的ESLint插件和配置
import { globalIgnores } from 'eslint/config'
import eslintPluginVue from 'eslint-plugin-vue' // Vue.js的ESLint插件
import { defineConfigWithVueTs, vueTsConfigs } from '@vue/eslint-config-typescript' // Vue+TypeScript的ESLint配置
import unoCss from '@unocss/eslint-config/flat'
import skipFormatting from '@vue/eslint-config-prettier/skip-formatting' // 跳过Prettier格式化的配置
// 导出ESLint配置
export default defineConfigWithVueTs(
  // 指定需要检查的文件
  {
    name: 'app/files-to-lint', // 配置名称
    files: ['**/*.{ts,mts,tsx,vue}'], // 匹配所有TypeScript和Vue文件
  },

  // 指定需要忽略的文件
  globalIgnores(['**/dist/**', '**/dist-ssr/**', '**/coverage/**', '**/node_modules/**', '**/auto-imports.d.ts', '**/components.d.ts']),
  // 应用Vue.js的基本规则集
  eslintPluginVue.configs['flat/essential'],

  // 应用Vue+TypeScript的推荐规则集
  vueTsConfigs.recommended,
  unoCss,
  {
    languageOptions: {
      parserOptions: {
        ecmaVersion: 'latest',
        ecmaFeatures: {
          jsx: true,
        },
      },
    },
    rules: {
      '@typescript-eslint/no-explicit-any': 'off', // 允许使用any类型，方便开发
      '@typescript-eslint/no-empty-function': 'off',
      '@typescript-eslint/no-empty-object-type': 'off',
      '@typescript-eslint/ban-ts-comment': 'off',
      '@typescript-eslint/no-non-null-assertion': 'off',
      '@typescript-eslint/no-unused-vars': 'off', // 关闭未使用变量警告
      '@typescript-eslint/no-unused-expressions': 'off', // 降级为警告
      '@typescript-eslint/no-import-type-side-effects': 'error',
      '@typescript-eslint/no-unsafe-function-type': 'off',
      'no-unused-vars': 'off', // 关闭未使用变量警告
      'no-console': 'off',
      'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'warn',
      'vue/multi-word-component-names': 'off', // Vue 组件的名称应该是多词的，以提高可读性和维护性
      'vue/no-reserved-component-names': 'error', // 禁止在组件定义中使用保留名称。
      'vue/require-name-property': 'error', // 确保组件有name属性
      'vue/require-prop-types': 'error', // props设置类型
      'vue/require-default-prop': 'error', // props设置默认值
      'vue/operator-linebreak': 'off', //关闭操作符换行规则的检查。
      'vue/html-self-closing': 'error', //强制自闭样式
      'vue/max-attributes-per-line': [
        'error',
        {
          singleline: 3, // 单行最多 3 个属性
          multiline: {
            max: 1, // 每行最多一个属性
            allowFirstLine: false, // 不允许属性出现在第一行
          },
        },
      ],
      'vue/no-deprecated-inline-template': 'error', // 禁止使用弃用属性
      'vue/no-multi-spaces': 'error', // 禁止使用多个空格
      'vue/no-export-in-script-setup': 'error', // 禁止在script setup中导出变量
      'vue/attributes-order': 'off', // 属性顺序
      'vue/block-order': [
        'error',
        {
          order: ['script', 'template', 'style'],
        },
      ], // 块顺序
      'vue/padding-line-between-blocks': 'error', // 块之间添加空行
      'vue/define-macros-order': [
        'error',
        {
          order: ['defineOptions', 'defineProps', 'defineEmits', 'defineSlots'],
        },
      ], // 宏定义顺序
    },
  },
  skipFormatting // 跳过Prettier格式化相关规则
)
