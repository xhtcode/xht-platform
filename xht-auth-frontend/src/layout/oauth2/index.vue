<script setup lang="ts">
import {useRoute, useRouter} from 'vue-router'
import {computed} from 'vue'
import logImage from '@/assets/logo.svg'
import {useUserStore} from '@/stores/modules/user.store'
import {useMessage} from '@/hooks/use-message'
import {useCheckLoginHooks} from "@/hooks/use-check-login";

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const checkLoginHooks = useCheckLoginHooks()
const isLoginActive = computed(() => route.path === '/sso/login')
const isRegisterActive = computed(() => route.path === '/sso/register')


const pushUrl = (path: string) => {
  router.push(path)
}

const handleLogout = () => {
  // @ts-ignore
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
      .then(() => {
        userStore
            .clearUserInfo()
            .then(() => {
              router.push('/sso/login')
              useMessage().success('退出登录成功')
            })
            .catch(() => {
              useMessage().error('退出登录失败')
            })
      })
      .catch(() => {
      })
}
</script>

<template>
  <el-container class="xht-layout">
    <el-header class="xht-header">
      <div class="flex items-center pl-5 cursor-pointer" @click="pushUrl('/sso/login')">
        <el-image :src="logImage" alt="" fit="fill" class="h-28px w-28px" :scale="1"/>
        <h3>小糊涂统一身份认证</h3>
      </div>
      <div class="flex flex-1 items-center justify-end pr-5">
        <template v-if="!checkLoginHooks.isLogin">
          <el-space size="default" spacer="|" alignment="center">
            <el-button text link :type="isRegisterActive ? 'primary' : 'default'"
                       :class="{ '!font-bold': isRegisterActive }" @click="pushUrl('/sso/register')">
              注册
            </el-button>
            <el-button text link :type="isLoginActive ? 'primary' : 'default'" :class="{ '!font-bold': isLoginActive }"
                       @click="pushUrl('/sso/login')">
              登录
            </el-button>
          </el-space>
        </template>
        <template v-else>
          <el-space size="default" spacer="|" alignment="center">
            <span class="text-gray-600">{{ userStore.userInfo?.nickName }}</span>
            <el-button text link type="danger" @click="handleLogout">切换账号</el-button>
          </el-space>
        </template>
      </div>
    </el-header>
    <el-main class="xht-main">
        <router-view/>
    </el-main>
  </el-container>
</template>

<style scoped>

</style>
