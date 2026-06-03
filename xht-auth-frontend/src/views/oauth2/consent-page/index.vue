<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import { findConsentByParameters, submitOauth2Authorize } from '@/service/api/auth.api'

/**
 * OAuth2授权同意页面的状态接口定义
 */
interface Oauth2ConsentState {
  loading: boolean
  consentInfo: any
  errorMessage: string | null
}

/**
 * 页面响应式状态对象
 * 管理加载状态、同意授权信息和错误消息
 */
const state = reactive<Oauth2ConsentState>({
  loading: true,
  consentInfo: {},
  errorMessage: null,
})

/**
 * 用户选择的授权范围列表
 * 存储用户勾选的scope名称数组
 */
const modelValue = ref<string[]>([])

/**
 * 获取当前路由实例
 * 用于访问URL查询参数
 */
const route = useRoute()

/**
 * 计算属性：从同意信息中提取可用的授权范围列表
 * @returns {Array} 授权范围数组，如果不存在则返回空数组
 */
const scopes = computed<any[]>(() => state.consentInfo?.scopes || [])

/**
 * 提交授权确认或取消操作
 * 将用户的授权选择发送到OAuth2服务器并处理重定向
 *
 * @param {boolean} cancel - 是否为取消授权操作，true表示取消，false表示确认授权
 * @returns {void}
 */
const submitApprove = (cancel: boolean): void => {
  const data = new FormData()

  if (cancel) {
    data.append('cancelApprove', 'true')
  }

  if (modelValue.value !== null && typeof modelValue.value !== 'undefined' && modelValue.value.length > 0) {
    modelValue.value.forEach((e: any) => data.append('scope', e))
  }

  data.append('state', state.consentInfo.state)
  data.append('client_id', state.consentInfo.clientId)
  data.append('user_code', state.consentInfo.userCode)

  submitOauth2Authorize(data)
    .then((res) => {
      if (res.data?.redirect_uri) {
        window.location.href = res.data.redirect_uri
      } else {
        state.errorMessage = '服务器遇到了错误：' + res.msg
      }
    })
    .catch((err: any) => {
      state.errorMessage = err.msg
    })
}

/**
 * 获取授权信息
 */
const getConsentInfo = async () => {
  try {
    state.loading = true
    const res = await findConsentByParameters(route.query)
    if (res.data) {
      state.consentInfo = res.data
      modelValue.value = (res.data.scopes || []).filter((scope: any) => scope.value).map((scope: any) => scope.scopeName)
    }
  } catch (error: any) {
    state.errorMessage = error.msg
  } finally {
    state.loading = false
  }
}

onMounted(async () => {
  await getConsentInfo()
})
</script>

<template>
  <div class="flex-center flex-1 select-none">
    <div class="max-w-[440px] w-full overflow-hidden" v-loading="state.loading" v-if="!state.errorMessage">
      <!-- 应用图标和标题 -->
      <div class="flex items-center p-4">
        <!-- 应用信息卡片 -->
        <el-avatar :size="72" class="mr-4 flex-shrink-0 cursor-pointer bg-blue-50">
          <img src="https://picsum.photos/48/48" alt="应用图标" />
        </el-avatar>
        <div class="flex-1 text-left">
          <h3 class="mb-2 text-base">
            {{ state.consentInfo?.clientName || '第三方应用' }}
          </h3>
          <p class="text-sm text-gray-500">此第三方应用请求获得以下权限:</p>
        </div>
      </div>
      <!-- 权限列表 -->
      <div class="p-3">
        <el-checkbox-group v-model="modelValue" size="large">
          <div v-for="scope in scopes" :key="scope.id" class="flex">
            <el-checkbox :value="scope.scopeName" :disabled="scope.disabled" size="large" />
            <div class="w-full flex justify-between">
              <el-text size="large" tag="b">{{ scope.scopeName }}</el-text>
              <el-text size="large" type="info">
                {{ scope.scopeDesc }}
              </el-text>
            </div>
          </div>
        </el-checkbox-group>
      </div>

      <!-- 按钮 -->
      <div class="flex p-4">
        <el-button @click="submitApprove(true)" size="default" class="flex-1">取消</el-button>
        <el-button type="primary" @click="submitApprove(false)" size="default" class="flex-1" :disabled="modelValue.length === 0">授权</el-button>
      </div>

      <div class="text-center">
        <el-text size="large">
          你可以随时在
          <a href="#" class="text-blue-600 hover:underline">账号设置</a>
          >
          <a href="#" class="text-blue-600 hover:underline">第三方应用</a>
          中取消你的授权
        </el-text>
      </div>
    </div>
    <div v-else class="text-center">
      <h1>存在错误</h1>
      <div class="pt-2 text-gray-500">{{ state.errorMessage }}</div>
    </div>
  </div>
</template>
