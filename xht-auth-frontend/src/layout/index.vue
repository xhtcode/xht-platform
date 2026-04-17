<script setup lang="ts">
import logImage from '@/assets/logo.svg'
</script>

<template>
  <el-container class="xht-layout">
    <el-header class="xht-header">
      <div class="pl-5 flex items-center">
        <el-image :src="logImage" alt="" fit="fill" class="h-28px w-28px" :scale="1"/>
        <h3>
          小糊涂统一身份认证
        </h3>
      </div>
      <div class="flex-1 flex items-center justify-end pr-5">
        <el-space size="default" spacer="|" alignment="center">
          <el-button text link>注册</el-button>
          <el-button text link type="primary">登录</el-button>
          <el-button text link>退出</el-button>
        </el-space>
      </div>
    </el-header>
    <el-main class="xht-main">
      <el-main class="flex-1 min-h-full flex! flex-col">
        <router-view/>
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
  background: #FFFFFF;
  height: var(--xht-header-height) !important;
  padding: 0 !important;
  border-bottom: var(--el-border);
  box-shadow: 0 6px 16px 2px #0000000a, 0 4px 10px #00000014;
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