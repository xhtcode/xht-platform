<script lang="ts" setup>
import {reactive, computed, useTemplateRef} from 'vue'
import type {FormInstance} from 'element-plus'
import {Lock, User} from '@element-plus/icons-vue'
import CaptchaImg from '@/views/login/components/captcha-img.vue'
import type {LoginPassWordFromModel} from "@/service/model/system/login.model";
import {loginPassWordFromRules} from "@/views/login/login.data";
import {useRoute, useRouter} from "vue-router";
import {ssoPassWordLogin} from "@/service/api/auth.api";
import {useUserStore} from "@/stores/modules/user.store";
import {useMessage} from "@/hooks/use-message";

defineOptions({name: 'PasswordForm', inheritAttrs: false})

const loading = defineModel<boolean>('loading', {
  required: true,
  default: false
})
const formData = reactive<LoginPassWordFromModel>({
  username: 'admin',
  password: '123456',
  rememberMe: false,
  captchaKey: '',
  captchaCode: '',
})
const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const targetUrl = computed<any>(() => route.query.target)
const ruleFormRef = useTemplateRef<FormInstance>('ruleFormRef')
const captchaRef = useTemplateRef('captchaRef')

/**
 * 提交密码登录表单
 *
 * 功能说明：
 * 1. 验证表单数据的合法性
 * 2. 调用SSO密码登录接口进行用户认证
 * 3. 登录成功后更新用户登录状态并跳转到目标页面
 * 4. 登录失败时刷新验证码以便用户重新输入
 * @returns {void} 该函数无返回值
 */
const submitPasswordForm = () => {
  ruleFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      loading.value = true
      ssoPassWordLogin(formData)
          .then(() => {
            userStore.changeLoginStatus(true)
            if (targetUrl.value) {
              window.location.href = targetUrl.value
            } else {
              router.push('/home')
            }
          })
          .catch((_) => {
            captchaRef.value.refreshCaptcha()
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
  <el-form ref="ruleFormRef" :model="formData" :rules="loginPassWordFromRules" label-position="top" size="large"
           class="h-[260px]">
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
      <captcha-img v-model:captcha-key="formData.captchaKey" v-model:captcha-code="formData.captchaCode"
                   ref="captchaRef"/>
    </el-form-item>
    <div class="flex items-center justify-between">
      <el-checkbox v-model="formData.rememberMe" size="default">
        记住我
      </el-checkbox>
      <button class="forgot-link">忘记密码？</button>
    </div>
    <el-button type="primary" class="login-button" :loading="loading" @click="submitPasswordForm">登 录</el-button>
  </el-form>
</template>

<style scoped lang="scss">
:deep(.el-form-item--large) {
  margin-bottom: 18px !important;
}
</style>
