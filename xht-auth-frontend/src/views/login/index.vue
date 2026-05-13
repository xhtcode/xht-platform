<script setup lang="ts">
import { onMounted, reactive, ref, useTemplateRef } from 'vue'
import { useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'
import { useMessage } from '@/hooks/use-message'
import { useUserStore } from '@/stores/modules/user.store'
import { generateCaptcha, sendSmsCode, smsLogin, ssoLogin } from '@/service/api/auth.api'
import type { LoginRequestType, SmsLoginRequestType } from '@/service/model/api.model'

defineOptions({
  name: 'Login',
})

const loginMode = ref<'password' | 'sms'>('password')
const ruleFormRef = useTemplateRef<FormInstance>('ruleFormRef')
const smsFormRef = useTemplateRef<FormInstance>('smsFormRef')

const ruleForm = reactive<LoginRequestType>({
  username: 'admin',
  password: '123456',
  rememberMe: false,
  captchaKey: '1',
  captchaCode: '2',
})
const smsForm = reactive<SmsLoginRequestType>({
  phone: '',
  smsCode: '',
})
const loading = ref(false)
const router = useRouter()
const captchaImage = ref('')
const captchaLoading = ref(false)
const countdown = ref(0)
let timer: ReturnType<typeof setInterval> | null = null

const rules = reactive<FormRules<typeof ruleForm>>({
  username: [{ required: true, message: '请输入用户名', trigger: ['blur', 'change'] }],
  password: [{ required: true, message: '请输入密码', trigger: ['blur', 'change'] }],
  captchaCode: [{ required: true, message: '请输入验证码', trigger: ['blur', 'change'] }],
})

const smsRules = reactive<FormRules<typeof smsForm>>({
  phone: [
    { required: true, message: '请输入手机号', trigger: ['blur', 'change'] },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: ['blur', 'change'] },
  ],
  smsCode: [{ required: true, message: '请输入短信验证码', trigger: ['blur', 'change'] }],
})

const userStore = useUserStore()

const getCaptcha = async () => {
  captchaLoading.value = true
  try {
    const key = Date.now().toString()
    const res = await generateCaptcha(key)
    if (res.data) {
      ruleForm.captchaKey = res.data.key
      captchaImage.value = `data:image/png;base64,${res.data.code}`
    }
  } finally {
    captchaLoading.value = false
  }
}

const refreshCaptcha = () => {
  ruleForm.captchaCode = ''
  getCaptcha()
}

const submitPasswordForm = () => {
  ruleFormRef.value?.validate((valid) => {
    if (valid) {
      loading.value = true
      ssoLogin(ruleForm)
        .then((res) => {
          userStore.changeLoginStatus(true)
          window.setTimeout(() => {
            if (res.data.targetUrl) {
              window.location.href = res.data.targetUrl
            } else {
              router.push('/home')
            }
          }, 500)
        })
        .catch((_) => {
          refreshCaptcha()
        })
        .finally(() => {
          loading.value = false
        })
    } else {
      useMessage().error('校验未通过！')
    }
  })
}

const sendSms = async () => {
  if (!smsForm.phone) {
    useMessage().warning('请先输入手机号')
    return
  }
  if (!/^1[3-9]\d{9}$/.test(smsForm.phone)) {
    useMessage().warning('请输入正确的手机号')
    return
  }
  try {
    await sendSmsCode({ phone: smsForm.phone })
    useMessage().success('验证码已发送')
    countdown.value = 60
    timer = setInterval(() => {
      countdown.value--
      if (countdown.value <= 0 && timer) {
        clearInterval(timer)
        timer = null
      }
    }, 1000)
  } catch (_) {}
}

const submitSmsForm = () => {
  smsFormRef.value?.validate((valid) => {
    if (valid) {
      loading.value = true
      smsLogin(smsForm)
        .then((res) => {
          userStore.changeLoginStatus(true)
          if (res.data.targetUrl) {
            window.location.href = res.data.targetUrl
          } else {
            router.push('/home')
          }
        })
        .catch((_) => {})
        .finally(() => {
          loading.value = false
        })
    } else {
      useMessage().error('校验未通过！')
    }
  })
}

const goRegister = () => {
  router.push('/sso/register')
}

const thirdPartyList = [
  { name: '微信', color: '#07c160' },
  { name: 'QQ', color: '#12b7f5' },
  { name: 'Gitee', color: '#c71d23' },
  { name: 'GitHub', color: '#24292f' },
  { name: '钉钉', color: '#0089ff' },
]

const handleThirdPartyLogin = (name: string) => {
  useMessage().info(`${name} 登录功能开发中...`)
}

onMounted(() => {
  getCaptcha()
})
</script>

<template>
  <div class="min-h-full flex items-center justify-center px-4 py-12 lg:px-8 sm:px-6">
    <div class="max-w-md w-full space-y-8">
      <div class="text-center">
        <h1 class="mt-6 text-3xl text-gray-900 font-bold tracking-tight">小糊涂统一身份认证</h1>
        <p class="mt-2 text-sm text-gray-600">请选择登录方式</p>
      </div>

      <el-tabs v-model="loginMode" class="login-tabs" stretch>
        <el-tab-pane label="账号密码登录" name="password">
          <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-position="top" class="mt-5">
            <el-form-item prop="username">
              <el-input v-model="ruleForm.username" placeholder="请输入用户名" size="large" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="ruleForm.password" placeholder="请输入密码" show-password size="large" type="password" />
            </el-form-item>
            <el-form-item prop="captchaCode">
              <div class="w-full flex gap-2">
                <el-input v-model="ruleForm.captchaCode" placeholder="请输入验证码" size="large" class="flex-1" />
                <div
                  class="h-10 w-[160px] flex cursor-pointer items-center justify-center overflow-hidden rounded"
                  @click="refreshCaptcha"
                  v-loading="captchaLoading"
                >
                  <img v-if="captchaImage" :src="captchaImage" alt="验证码" class="h-full w-full object-cover" />
                  <span v-else class="text-xs text-gray-400">点击刷新</span>
                </div>
              </div>
            </el-form-item>
            <el-form-item prop="rememberMe" class="mb-5">
              <el-checkbox v-model="ruleForm.rememberMe">记住我</el-checkbox>
            </el-form-item>
            <el-form-item class="mb-0">
              <el-button
                type="primary"
                @click="submitPasswordForm()"
                class="w-full transition-colors !rounded-lg hover:!bg-blue-600"
                size="large"
                :loading="loading"
              >
                登 录
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="短信验证登录" name="sms">
          <el-form ref="smsFormRef" :model="smsForm" status-icon :rules="smsRules" label-position="top" class="mt-4">
            <el-form-item prop="phone" class="mb-5">
              <el-input v-model="smsForm.phone" placeholder="请输入手机号" size="large" />
            </el-form-item>
            <el-form-item prop="smsCode" class="mb-5">
              <div class="w-full flex gap-3">
                <el-input v-model="smsForm.smsCode" placeholder="请输入短信验证码" size="large" class="flex-1" />
                <el-button size="large" :disabled="countdown > 0" @click="sendSms" class="w-28 flex-shrink-0 !rounded-lg">
                  {{ countdown > 0 ? `${countdown}s` : '获取验证码' }}
                </el-button>
              </div>
            </el-form-item>
            <el-form-item class="mb-0">
              <el-button
                type="primary"
                @click="submitSmsForm()"
                class="w-full transition-colors !rounded-lg hover:!bg-blue-600"
                size="large"
                :loading="loading"
              >
                登 录
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <div class="flex items-center justify-center gap-4 pt-2">
        <span class="h-px flex-1 bg-gray-200"></span>
        <span class="text-xs text-gray-400">其他登录方式</span>
        <span class="h-px flex-1 bg-gray-200"></span>
      </div>

      <div class="flex justify-center gap-5 py-2">
        <div
          v-for="item in thirdPartyList"
          :key="item.name"
          class="h-11 w-11 flex cursor-pointer items-center justify-center rounded-full transition-all duration-200 hover:shadow-md hover:-translate-y-1"
          :style="{ backgroundColor: item.color + '15' }"
          @click="handleThirdPartyLogin(item.name)"
          :title="item.name + '登录'"
        >
          <span class="text-sm font-medium" :style="{ color: item.color }">{{ item.name.charAt(0) }}</span>
        </div>
      </div>

      <div class="text-center">
        <span class="text-sm text-gray-500">还没有账号？</span>
        <el-button text type="primary" @click="goRegister" class="ml-1 hover:!text-blue-600">立即注册</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-tabs :deep(.el-tabs__header) {
  margin-bottom: 0;
}

.login-tabs :deep(.el-tabs__nav-wrap::after) {
  display: none;
}

.login-tabs :deep(.el-tabs__active-bar) {
  height: 3px;
  border-radius: 2px;
}

.login-tabs :deep(.el-tabs__item) {
  font-size: 16px;
  font-weight: 500;
}
</style>
