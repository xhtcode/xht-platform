<script setup lang="ts">
import { computed } from 'vue'
import { Document, Location, Menu } from '@element-plus/icons-vue'
import logImage from '@/assets/logo.svg'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const pushUrl = (path: string) => {
  router.push(path)
}
const route = useRoute()
const activeMenuPath = computed<any>(() => {
  return route.path
})
</script>

<template>
  <el-container class="xht-layout">
    <el-header class="xht-header">
      <div class="flex items-center pl-5 cursor-pointer" @click="pushUrl('/sso/login')">
        <el-image :src="logImage" alt="" fit="fill" class="h-28px w-28px" :scale="1" />
        <h3>小糊涂统一身份认证</h3>
      </div>
      <!--   个人设置   -->
      <div class="flex flex-1 items-center justify-end pr-5">
        <xht-user-setting />
      </div>
    </el-header>
    <el-container class="h-full w-full overflow-hidden">
      <el-aside width="220px" class="select-none">
        <el-menu mode="vertical" :default-active="activeMenuPath" unique-opened :collapse-transition="false" class="h-full">
          <el-menu-item index="/home" @click="pushUrl('/home')">
            <el-icon>
              <location />
            </el-icon>
            <template #title>仪表盘</template>
          </el-menu-item>
          <el-menu-item index="/account" @click="pushUrl('/account')">
            <el-icon>
              <Menu />
            </el-icon>
            <template #title>账号设置</template>
          </el-menu-item>
          <el-menu-item index="/applications" @click="pushUrl('/applications')">
            <el-icon>
              <document />
            </el-icon>
            <template #title>第三方应用</template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="p-4! flex-[1]">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
