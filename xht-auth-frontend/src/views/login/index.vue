<script setup lang="ts">
import {onMounted, reactive, ref, useTemplateRef} from 'vue'
import {useRouter} from 'vue-router'
import type {FormInstance, FormRules} from 'element-plus'
import {authApi} from "@/utils/request";
import {useMessage} from "@/hooks/use-message";
import {useLoginStore} from "@/stores/login.store";

defineOptions({
  name: 'Login'
})
const ruleFormRef = useTemplateRef<FormInstance>('ruleFormRef')
const number = ref<number>(0)
const ruleForm = reactive({
  username: 'admin',
  password: '123456',
  rememberMe: false,
})
const data = ref<any>()
const data2 = ref<any>()
const router = useRouter()
const rules = reactive<FormRules<typeof ruleForm>>({
  username: [{required: true, message: '请输入用户名', trigger: ['blur', 'change']}],
  password: [{required: true, message: '请输入密码', trigger: ['blur', 'change']}],
})
const loginStore = useLoginStore()
const submitForm = () => {
  ruleFormRef.value?.validate((valid) => {
    if (valid) {
      authApi.login(ruleForm.username, ruleForm.password, ruleForm.rememberMe).then((res) => {
        data.value = res
        loginStore.changeLoginStatus(true)
        if (res.data) {
          window.location.href = res.data
        } else {
          //  router.push('/home')
        }
      }).catch(_ => {
      })
      console.log('submit!')
    } else {
      useMessage().error('校验未通过！')
    }
  })
}
let timer: any = null;
const x = () => {
  number.value = 0
  if (timer) {
    window.clearTimeout(timer)
  }
  timer = setInterval(() => {
    number.value += 1
  }, 1000)
}
const getToken = () => {
  loginStore.changeLoginStatus(true)
  authApi.testApi().then(res => {
    data2.value = res
    useMessage().success('获取成功！')
  })
}

onMounted(() => {
})
</script>

<template>
  <div class="login-container flex-1 flex-center w-full" style="flex-direction: column">
    {{ loginStore.loginStatus }}
    <el-button @click="getToken">测试token</el-button>
    <el-form
        ref="ruleFormRef"
        style="max-width: 600px"
        :model="ruleForm"
        status-icon
        :rules="rules"
        label-width="auto"
        class="w-460px"
    >
      <el-form-item prop="username">
        <el-input v-model="ruleForm.username" class="login-input" placeholder="请输入用户名" size="large"/>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
            v-model="ruleForm.password"
            class="login-input"
            placeholder="请输入密码"
            show-password
            size="large"
            type="password"
        />
      </el-form-item>
      <el-form-item prop="rememberMe" label="记住我">
        <el-checkbox v-model="ruleForm.rememberMe"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm()" class="w-full">
          登录
        </el-button>
      </el-form-item>
    </el-form>
    {{ data }}
  </div>
</template>

<style scoped>
</style>