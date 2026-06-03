<script setup lang="ts">
import logImage from "@/assets/logo.svg";
import {useRouter} from "vue-router";

const router = useRouter()

/**
 * 路由跳转
 * @param path 路由地址
 */
const pushUrl = (path: string) => {
  router.push(path)
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
        个人信息
      </div>
    </el-header>
    <el-main class="xht-main">
      <router-view/>
    </el-main>
  </el-container>
</template>

<style scoped>

</style>
