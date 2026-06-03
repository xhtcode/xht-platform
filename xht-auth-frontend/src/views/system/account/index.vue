<script setup lang="ts">
defineOptions({
  name: 'AccountIndex',
})
</script>

<template>
  <div class="xht-layout-body">
    <div v-for="item in 20" :key="item">{{ item }}你好啊</div>
  </div>
</template>

<style scoped></style>
