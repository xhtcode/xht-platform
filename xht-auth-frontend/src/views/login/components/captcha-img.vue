<script setup lang="ts">
import {onMounted, ref, useAttrs} from 'vue'
import {generateCaptcha} from '@/service/api/auth.api'

defineOptions({
  name: 'CaptchaImg',
  inheritAttrs: false,
})
const attrs = useAttrs();
const captchaCode = defineModel<string>('captchaCode')
const captchaKey = defineModel<string>('captchaKey')
const captchaImage = ref<string>()
const captchaLoading = ref<boolean>(false)
/**
 * 获取验证码
 */
const refreshCaptcha = async () => {
  captchaLoading.value = true
  try {
    const res = await generateCaptcha(captchaKey.value)
    if (res.data) {
      captchaKey.value = res.data.key
      captchaImage.value = `data:image/png;base64,${res.data.code}`
    }
  } finally {
    captchaLoading.value = false
  }
}
onMounted(() => {
  refreshCaptcha()
})
defineExpose({
  refreshCaptcha: refreshCaptcha()
})
</script>

<template>
  <div class="captcha-row-container">
    <el-input v-model="captchaCode" placeholder="图形验证码" class="captcha-input" v-bind="{...attrs}">
      <template #prefix>
        <div class="i-login-code h-1rem w-1rem color-[var(--color)]"/>
      </template>
    </el-input>
    <div class="captcha-img-wrap" v-loading="captchaLoading" @click="refreshCaptcha">
      <el-image :src="captchaImage" alt="验证码" fit="fill" style="width: 100%; height: 100%; object-fit: cover"/>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.captcha-row-container {
  display: flex;
  width: 100%;
  gap: 12px;
  align-items: center;
  justify-items: center;

  .captcha-input {
    flex: 1;
  }

  .captcha-img-wrap {
    width: 120px;
    height: 42px;
    padding: 1px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 8px;
    overflow: hidden;
    cursor: pointer;
    background: #f1f5f9;
    border: 1px solid #e2e8f0;
    transition: all 0.25s;
    flex-shrink: 0;
  }

  .captcha-img-wrap:hover {
    border-color: var(--el-text-color-disabled);
  }
}
</style>
