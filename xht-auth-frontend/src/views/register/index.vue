<script setup lang="ts">
import {reactive, ref, useTemplateRef} from 'vue'
import {useRouter} from 'vue-router'
import type {FormInstance, FormRules} from 'element-plus'
import {useMessage} from "@/hooks/use-message";
import {ssoRegister} from "@/service/api/auth.api";

defineOptions({
  name: 'Register'
})
const ruleFormRef = useTemplateRef<FormInstance>('ruleFormRef')
const ruleForm = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  nickName: '',
})
const loading = ref(false)
const router = useRouter()

const validateConfirmPassword = (rule: any, value: string, callback: any) => {
  if (value !== ruleForm.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = reactive<FormRules<typeof ruleForm>>({
  username: [
    {required: true, message: '请输入用户名', trigger: ['blur', 'change']},
    {min: 3, max: 20, message: '用户名长度为3-20个字符', trigger: ['blur', 'change']},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: ['blur', 'change']},
    {min: 6, max: 30, message: '密码长度为6-30个字符', trigger: ['blur', 'change']},
  ],
  confirmPassword: [
    {required: true, message: '请确认密码', trigger: ['blur', 'change']},
    {validator: validateConfirmPassword, trigger: ['blur', 'change']},
  ],
  nickName: [{required: true, message: '请输入昵称', trigger: ['blur', 'change']}],
})

const submitForm = () => {
  ruleFormRef.value?.validate((valid) => {
    if (valid) {
      loading.value = true
      ssoRegister({
        username: ruleForm.username,
        password: ruleForm.password,
        nickName: ruleForm.nickName,
      }).then(() => {
        useMessage().success('注册成功，请登录')
        router.push('/sso/login')
      }).catch((_) => {
      }).finally(() => {
        loading.value = false
      })
    } else {
      useMessage().error('校验未通过！')
    }
  })
}

const goLogin = () => {
  router.push('/sso/login')
}
</script>

<template>
  <div class="min-h-full flex items-center justify-center px-4 py-12 sm:px-6 lg:px-8">
    <div class="w-full max-w-md space-y-8">
      <div class="text-center">
        <h1 class="mt-6 text-3xl font-bold tracking-tight text-gray-900">创建新账号</h1>
        <p class="mt-2 text-sm text-gray-600">填写以下信息完成注册</p>
      </div>

      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          status-icon
          :rules="rules"
          label-position="top"
      >
        <el-form-item prop="nickName" class="mb-4">
          <el-input v-model="ruleForm.nickName" placeholder="请输入昵称" size="large"/>
        </el-form-item>
        <el-form-item prop="username" class="mb-4">
          <el-input v-model="ruleForm.username" placeholder="请输入用户名" size="large"/>
        </el-form-item>
        <el-form-item prop="password" class="mb-4">
          <el-input
              v-model="ruleForm.password"
              placeholder="请输入密码（至少6位）"
              show-password
              size="large"
              type="password"
          />
        </el-form-item>
        <el-form-item prop="confirmPassword" class="mb-5">
          <el-input
              v-model="ruleForm.confirmPassword"
              placeholder="请再次输入密码"
              show-password
              size="large"
              type="password"
          />
        </el-form-item>
        <el-form-item class="mb-0">
          <el-button type="primary" @click="submitForm()" class="w-full !rounded-lg hover:!bg-blue-600 transition-colors" size="large" :loading="loading">
            注 册
          </el-button>
        </el-form-item>
      </el-form>

      <div class="text-center">
        <span class="text-sm text-gray-500">已有账号？</span>
        <el-button text type="primary" @click="goLogin" class="ml-1 hover:!text-blue-600">立即登录</el-button>
      </div>
    </div>
  </div>
</template>