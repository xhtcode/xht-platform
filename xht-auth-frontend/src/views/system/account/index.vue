<script setup lang="ts">
import { gateWayToken } from '@/service/api/auth.api'

defineOptions({
  name: 'AccountIndex',
})
const testToken = () => {
  window.location.href = 'http://www.xht.com:8080/get/token'
  // gateWayToken().then(res => {
  //   console.log(res)
  // })
}
</script>

<template>
  <div class="xht-layout-body">
    <el-button @click="testToken">请点击我</el-button>
    <div v-for="item in 20" :key="item">{{ item }}你好啊</div>
  </div>
</template>

<style scoped></style>
