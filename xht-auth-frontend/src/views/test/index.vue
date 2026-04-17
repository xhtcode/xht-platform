<script setup lang="ts">
import {authApi} from "@/utils/request";
import {onMounted, ref} from 'vue'
import {useMessage} from "@/hooks/use-message";

const data2 = ref<any>()
const getToken = () => {
  authApi.testApi().then(res => {
    data2.value = res
    useMessage().success('登录成功！')
  })
}
</script>

<template>
  <div>
    <h1>登录成功页面</h1>
    <el-button @click="getToken">测试token</el-button>
    <div>
      {{ data2 }}
    </div>
  </div>
</template>

<style scoped>

</style>