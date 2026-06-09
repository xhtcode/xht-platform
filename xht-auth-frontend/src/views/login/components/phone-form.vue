<script lang="ts" setup>
import { computed, reactive, ref, useTemplateRef } from 'vue'
import type { FormInstance } from 'element-plus'
import { Iphone, Message } from '@element-plus/icons-vue'
import CaptchaImg from '@/views/login/components/captcha-img.vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/modules/user.store'
import { loginPhoneFromRules } from '@/views/login/login.data'
import { useMessage } from '@/hooks/use-message'
import { sendSmsCode, ssoPhoneLogin } from '@/service/api/auth.api'
import { LoginPhoneFromModel } from '@/service/model/system/login.model'

defineOptions({
  name: 'PhoneLogin',
  inheritAttrs: false,
})
const loading = defineModel<boolean>('loading', {
  required: true,
  default: false,
})

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const targetUrl = computed<any>(() => route.query.target)
const smsFormRef = useTemplateRef<FormInstance>('smsFormRef')
const countdown = ref<number>(0)
const formData = reactive<LoginPhoneFromModel>({
  phone: '13812345678',
  phoneCode: '',
  rememberMe: false,
  captchaCode: '0',
})

let timer: ReturnType<typeof setInterval> | null = null

/**
 * 发送手机短信验证码
 *
 * 功能说明：
 * 1. 验证手机号是否已输入且格式正确
 * 2. 检查是否在冷却时间内（防止重复发送）
 * 3. 调用短信发送接口并启动倒计时计时器
 *
 *
 * @returns {void} 该函数无返回值
 */
const sendPhoneCode = () => {
  loading.value = true
  if (!formData.phone) {
    useMessage().error('请先输入手机号')
    loading.value = false
    return
  }
  if (!/^1[3-9]\d{9}$/.test(formData.phone)) {
    useMessage().error('请输入正确的手机号')
    loading.value = false
    return
  }
  if (timer) {
    useMessage().error('请注意查收手机短信')
    return
  }
  sendSmsCode({ phone: formData.phone })
    .then(() => {
      useMessage().success('验证码已发送')
      countdown.value = 120
      timer = setInterval(() => {
        countdown.value--
        if (countdown.value <= 0 && timer) {
          clearInterval(timer)
          timer = null
        }
      }, 1000)
    })
    .finally(() => {
      loading.value = false
    })
}

/**
 * 提交短信验证码登录表单
 *
 * 功能说明：
 * 1. 验证表单数据（手机号、验证码等）的合法性
 * 2. 调用SSO手机号登录接口进行用户认证
 * 3. 登录成功后更新用户登录状态并跳转到目标页面
 *
 * @returns {void} 该函数无返回值
 */
const submitSmsForm = () => {
  smsFormRef.value?.validate((valid) => {
    if (valid) {
      loading.value = true
      ssoPhoneLogin(formData)
        .then(() => {
          userStore.changeLoginStatus(true)
          if (targetUrl.value) {
            window.location.href = targetUrl.value
          } else {
            router.push('/home')
          }
        })
        .finally(() => {
          loading.value = false
        })
    } else {
      useMessage().error('校验未通过！')
    }
  })
}
</script>

<template>
  <el-form ref="smsFormRef" :model="formData" :rules="loginPhoneFromRules" label-position="top" size="large" class="h-[250px]">
    <el-form-item prop="phone">
      <el-input v-model="formData.phone" placeholder="请输入手机号" :prefix-icon="Iphone" maxlength="11" />
    </el-form-item>
    <el-form-item prop="captchaCode" disabled>
      <captcha-img v-model:captcha-key="formData.captchaKey" v-model:captcha-code="formData.captchaCode" disabled />
    </el-form-item>
    <el-form-item prop="phoneCode">
      <div class="sms-row">
        <el-input v-model="formData.phoneCode" placeholder="短信验证码" :prefix-icon="Message" class="sms-input" maxlength="6" />
        <el-button class="sms-send-btn" :disabled="countdown > 0" @click="sendPhoneCode">
          {{ countdown > 0 ? `${countdown}s 后重发` : '获取验证码' }}
        </el-button>
      </div>
    </el-form-item>
    <div class="flex items-center justify-between">
      <el-checkbox v-model="formData.rememberMe" size="default">记住我</el-checkbox>
      <button class="forgot-link">忘记密码？</button>
    </div>
    <el-button type="primary" class="login-button" :loading="loading" @click="submitSmsForm">登 录</el-button>
  </el-form>
</template>

<style lang="scss" scoped>
:deep(.el-form-item--large) {
  margin-bottom: 18px !important;
}

.sms-row {
  display: flex;
  width: 100%;
  gap: 12px;
  align-items: flex-start;
}

.sms-input {
  flex: 1;
}

/* ========== 密码登录面板样式 ========== */

.sms-send-btn {
  width: 110px;
  flex-shrink: 0;
  height: 42px !important;
  padding: 0 14px !important;
  border-radius: 8px !important;
  font-size: 13px !important;
  font-weight: 500;
  white-space: nowrap;
  transition: all 0.25s;
}

.sms-send-btn:not(:disabled):hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.25);
}
</style>
