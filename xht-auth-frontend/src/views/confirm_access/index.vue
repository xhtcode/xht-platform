<script setup lang="ts">
import {computed, nextTick, onMounted, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {getConsentParameters, getOauth2Authorize} from '@/service/api/auth.api'
import {useMessage} from "@/hooks/use-message";
import {CircleClose} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const consentInfo = ref<any>({})
const agreed = ref<any[]>([])

const scopes = computed<any[]>(() => consentInfo.value?.scopes || [])

const fetchConsentInfo = async () => {
  try {
    loading.value = true
    const res = await getConsentParameters(route.query)
    if (res.data) {
      consentInfo.value = res.data
      await nextTick()
      agreed.value = (res.data.scopes || [])
          .filter((scope: any) => scope.value)
          .map((scope: any) => scope.scopeName)
      await nextTick()
    }
  } catch (error) {
    useMessage().error('获取授权信息失败')
    router.push('/home')
  } finally {
    loading.value = false
  }
}
const submitApprove = (cancel: boolean) => {
  const data = new FormData()
  if (cancel) {
    if (
      agreed.value !== null &&
      typeof agreed.value !== 'undefined' &&
      agreed.value.length > 0
    ) {
      agreed.value.forEach((e: any) => data.append('scope', e))
    }
  }
  data.append('state', consentInfo.value.state)
  data.append('client_id', consentInfo.value.clientId)
  data.append('user_code', consentInfo.value.userCode)
  getOauth2Authorize(data).then((res) => {
    console.log(res)
    window.location.href = res.data.redirect_uri
  }).catch(error => {
    console.log(error.msg)
    router.push('/home')
  })
}
onMounted(() => {
  fetchConsentInfo()
})
</script>

<template>
  <div class="flex-1 flex-center" v-loading="loading">
    <div class="w-full max-w-[460px] overflow-hidden">
      <!-- 应用图标和标题 -->
      <div class="flex items-center p-4">
        <!-- 应用信息卡片 -->
        <el-avatar :size="56" class="mr-4 bg-blue-50 flex-shrink-0">
          <img v-if="consentInfo.clientLogo" :src="consentInfo.clientLogo" alt="应用图标" />
          <span v-else class="text-blue-600 font-bold text-xl">{{ consentInfo.clientName?.charAt(0) || 'G' }}</span>
        </el-avatar>
        <div class="text-left flex-1 min-w-0">
          <h2 class="text-base font-semibold text-gray-900 mb-1">
            {{ consentInfo.clientName || 'gulimall 客户端' }}
          </h2>
          <p class="text-sm text-gray-500 truncate">此第三方应用请求获得以下权限</p>
        </div>
      </div>
      <!-- 权限列表 -->
      <div class="p-4 bg-gray-50 rounded-lg border border-dashed border-gray-300">
        <el-checkbox-group v-model="agreed">
          <el-space v-if="scopes.length > 0" direction="vertical" fill>
            <div v-for="scope in scopes" :key="scope.id">
              <el-checkbox :label="scope.scopeName" :value="scope.scopeName" :disabled="scope.disabled">
                <template #default>
                  <div class="flex items-center">
                    <el-text size="default" tag="b">{{ scope.scopeName+':' }}</el-text>
                    <div class="text-[12px] pl-1 text-gray-500 text-wrap overflow-hidden line-clamp-1">{{ scope.scopeDesc
                      }}</div>
                  </div>
                </template>
              </el-checkbox>
            </div>
          </el-space>
          <div v-else class="text-center py-12  ">
            <el-icon class="text-4xl mb-3 text-gray-300">
              <CircleClose />
            </el-icon>
            <p class="text-sm text-gray-500">该应用未请求任何权限</p>
          </div>
        </el-checkbox-group>
      </div>

      <!-- 按钮 -->
      <div class="flex space-x-3 p-4">
        <el-button @click="submitApprove(false)" size="default" class="flex-1">
          取消
        </el-button>
        <el-button type="primary" @click="submitApprove(true)" size="default" :disabled="agreed.length === 0"
          class="flex-1">
          授权
        </el-button>
      </div>

      <!-- 协议说明 -->
      <div class="text-xs text-gray-600 leading-relaxed mb-4">
        点击授权即表示您同意
        <a href="#" class="text-blue-600 hover:underline">服务条款</a> 和
        <a href="#" class="text-blue-600 hover:underline">隐私政策</a>，
        并将被重定向至 "{{ consentInfo.redirectUri || '该应用' }}" 你可以随时在
        <a href="#" class="text-blue-600 hover:underline">账号设置</a> >
        <a href="#" class="text-blue-600 hover:underline">第三方应用</a>
        中取消你的授权
      </div>

      <!-- 切换账号 -->
      <div class="text-center">
        <span class="text-sm text-gray-600">不是您？</span>
        <a href="#" class="text-sm text-blue-600 hover:underline font-medium">使用其他账号</a>
      </div>
    </div>
  </div>
</template>
