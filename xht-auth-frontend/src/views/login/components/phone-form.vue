<script lang="ts" setup>
import {onMounted, reactive, ref, useTemplateRef} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'
import {Iphone, Message, Promotion} from '@element-plus/icons-vue'
import {generateCaptcha} from '@/service/api/auth.api'
import CaptchaImg from "@/views/login/components/captcha-img.vue";

defineOptions({
  name: 'SmsLogin',
  inheritAttrs: false,
})

withDefaults(
    defineProps<{
      loading?: boolean
    }>(),
    {
      loading: false,
    }
)
const formRef = useTemplateRef<FormInstance>('formRef')
const captchaImage = ref('')
const captchaLoading = ref(false)
const countdown = ref<number>(0)
const formData = reactive<{
  phone: string;
  smsCode: string;
  captchaKey?: string
  captchaCode: string
}>({
  phone: '',
  smsCode: '',
  captchaCode: '',
})

const rules = reactive<FormRules<typeof formData>>({
  phone: [
    {required: true, message: '请输入手机号', trigger: ['blur', 'change']},
    {pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: ['blur', 'change']},
  ],
  smsCode: [{required: true, message: '请输入短信验证码', trigger: ['blur', 'change']}],
  captchaCode: [{required: true, message: '请输入图形验证码', trigger: ['blur', 'change']}],
})

const getCaptcha = (key?: string) => {
  formData.captchaKey = key
}

</script>

<template>
  <el-form ref="formRef" :model="formData" :rules="rules" label-position="top" size="large" class="h-[260px]">
    <el-form-item prop="phone">
      <el-input v-model="formData.phone" placeholder="请输入手机号" :prefix-icon="Iphone" maxlength="11"/>
    </el-form-item>
    <el-form-item prop="captchaCode">
      <captcha-img v-model:captcha-key="formData.captchaKey" v-model:captcha-code="formData.captchaCode"/>
    </el-form-item>
    <el-form-item prop="smsCode">
      <div class="sms-row">
        <el-input
            v-model="formData.smsCode"
            placeholder="短信验证码"
            :prefix-icon="Message"
            class="sms-input"
            maxlength="6"
            @keyup.enter="$emit('submit')"
        />
        <el-button class="sms-send-btn" :disabled="countdown > 0" @click="$emit('sendCode')">
          {{ countdown > 0 ? `${countdown}s 后重发` : '获取验证码' }}
        </el-button>
      </div>
    </el-form-item>

    <el-form-item class="submit-item">
      <el-button type="primary" class="submit-btn" :loading="loading" @click="$emit('submit')">登 录</el-button>
    </el-form-item>
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
</style>
