<script lang="ts" setup>
import {onMounted, reactive, ref} from 'vue'
import type {LoginRequestType} from '@/service/model/api.model'
import type {FormRules} from 'element-plus'
import {Lock, User} from '@element-plus/icons-vue'
import {generateCaptcha} from '@/service/api/auth.api'
import CaptchaImg from "@/views/login/components/captcha-img.vue";

defineOptions({name: 'PasswordForm', inheritAttrs: false})

const captchaImage = ref<string>()
const captchaLoading = ref<boolean>(false)

withDefaults(
    defineProps<{
      loading?: boolean
    }>(),
    {
      loading: false,
    }
)

const formData = reactive<LoginRequestType>({
  username: '',
  password: '',
  rememberMe: false,
  captchaKey: '',
  captchaCode: '',
})

const rules: FormRules<typeof formData> = {
  username: [
    {required: true, message: '请输入用户名或邮箱', trigger: ['blur', 'change']},
    {min: 2, max: 50, message: '用户名长度为2-50个字符', trigger: ['blur', 'change']},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: ['blur', 'change']},
    {min: 6, max: 30, message: '密码长度为6-30个字符', trigger: ['blur', 'change']},
  ],
  captchaCode: [{required: true, message: '请输入图形验证码', trigger: ['blur', 'change']}],
}

const getCaptcha = async () => {
  captchaLoading.value = true
  try {
    const key = Date.now().toString()
    const res = await generateCaptcha(key)
    if (res.data) {
      formData.captchaKey = res.data.key
      captchaImage.value = `data:image/png;base64,${res.data.code}`
    }
  } finally {
    captchaLoading.value = false
  }
}
onMounted(() => {
  getCaptcha()
})
</script>

<template>
  <el-form ref="formRef" :model="formData" :rules="rules" label-position="top" size="large" class="h-[260px]">
    <el-form-item prop="username">
      <el-input v-model="formData.username" placeholder="请输入用户账号" :prefix-icon="User" autocomplete="username"/>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
          v-model="formData.password"
          placeholder="请输入用户密码"
          :prefix-icon="Lock"
          type="password"
          show-password
          autocomplete="current-password"
      />
    </el-form-item>
    <el-form-item prop="captchaCode">
      <captcha-img v-model:captcha-key="formData.captchaKey" v-model:captcha-code="formData.captchaCode"/>
    </el-form-item>

    <div class="flex items-center justify-between">
      <el-checkbox v-model="formData.rememberMe" class="remember-check">
        <span class="remember-text">记住我</span>
      </el-checkbox>
      <button class="forgot-link">忘记密码？</button>
    </div>

    <el-form-item class="submit-item">
      <el-button type="primary" class="submit-btn" :loading="loading">登 录</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss">
:deep(.el-form-item--large) {
  margin-bottom: 18px !important;
}

/* ========== 密码登录面板样式 ========== */
.remember-text {
  font-size: 13px;
  color: #64748b;
}

.forgot-link {
  font-size: 13px;
  color: #3b82f6;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  transition: color 0.2s;
}

.forgot-link:hover {
  color: #2563eb;
  text-decoration: underline;
}

.submit-item {
  margin-top: 4px !important;
}

.submit-btn {
  width: 100% !important;
  height: 40px !important;
  border-radius: 10px !important;
  font-size: 14px !important;
  font-weight: 600 !important;
  letter-spacing: 2px !important;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%) !important;
  border: none !important;
  box-shadow: 0 4px 14px rgba(59, 130, 246, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(59, 130, 246, 0.4) !important;
  background: linear-gradient(135deg, #2563eb 0%, #1d4ed8 100%) !important;
}

.submit-btn:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3) !important;
}
</style>
