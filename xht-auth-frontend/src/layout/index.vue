<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { computed } from 'vue'
import logImage from '@/assets/logo.svg'
import { useUserStore } from '@/stores/modules/user.store'
import {useMessage} from "@/hooks/use-message";

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const isLoginActive = computed(() => route.path === '/sso/login')
const isRegisterActive = computed(() => route.path === '/sso/register')

const goRegister = () => {
  router.push('/sso/register')
}

const goLogin = () => {
  router.push('/sso/login')
}

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      userStore.clearUserInfo().then(() => {
        router.push('/sso/login')
        useMessage().success('退出登录成功')
      }).catch(() => {
        useMessage().error('退出登录失败')
      })
    })
    .catch(() => {})
}
</script>

<template>
  <el-container class="xht-layout">
    <el-header class="xht-header">
      <div class="flex items-center pl-5">
        <el-image :src="logImage" alt="" fit="fill" class="h-28px w-28px" :scale="1" />
        <h3>小糊涂统一身份认证</h3>
      </div>
      <div class="flex flex-1 items-center justify-end pr-5">
        <template v-if="!userStore.loginStatus">
          <el-space size="default" spacer="|" alignment="center">
            <el-button text link :type="isRegisterActive ? 'primary' : 'default'" :class="{ '!font-bold': isRegisterActive }" @click="goRegister">
              注册
            </el-button>
            <el-button text link :type="isLoginActive ? 'primary' : 'default'" :class="{ '!font-bold': isLoginActive }" @click="goLogin">
              登录
            </el-button>
          </el-space>
        </template>
        <template v-else>
          <el-space size="default" spacer="|" alignment="center">
            <span class="text-gray-600">{{ userStore.userInfo?.nickName || '用户' }}</span>
            <el-button text link type="danger" @click="handleLogout">退出</el-button>
          </el-space>
        </template>
      </div>
    </el-header>
    <el-main class="xht-main">
      <el-main class="min-h-full flex-1 flex-col flex!">
        <router-view />
      </el-main>
    </el-main>
  </el-container>
</template>

<style scoped>
.xht-layout {
  width: 100%;
  height: 100%;
  --xht-header-height: 50px;
}

.xht-header {
  user-select: none;
  background: #ffffff;
  height: var(--xht-header-height) !important;
  padding: 0 !important;
  border-bottom: var(--el-border);
  box-shadow:
    0 6px 16px 2px #0000000a,
    0 4px 10px #00000014;
  display: flex;
  align-items: center;
}

.xht-main {
  padding: 0;
  flex: 1;
}

.xht-footer {
  border-top: var(--el-border);
  padding: 0 !important;
  height: 0 !important;
  background: var(--el-color-info-light-9);
}
</style>
