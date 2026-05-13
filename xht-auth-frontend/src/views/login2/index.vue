<script setup lang="ts">
import { computed, nextTick, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { fetch, generateQrCode } from '@/service/api/QrCodeLogin'
import { useMessage } from '@/hooks/use-message'
import { useUserStore } from '@/stores/modules/user.store'
// 是否显示三方登录
const showThirdLogin = ref(false)
// 定义二维码信息的对象
const qrCodeInfo = ref({
  qrCodeStatus: 0,
  expired: false,
  avatarUrl: '',
  name: '',
  scopes: [],
})
// 生成二维码响应数据
const getQrCodeInfo = ref({
  qrCodeId: '',
  imageData: '',
})

/**
 * 生成二维码
 */
const refreshQrCode = () => {
  generateQrCode()
    .then((r) => {
      getQrCodeInfo.value.qrCodeId = r.data.qrCodeId
      getQrCodeInfo.value.imageData = r.data.imageData
      // 开始轮询获取二维码信息
      fetchQrCodeInfo(r.data.qrCodeId)
    })
    .catch((e: any) => {
      useMessage().warning(`生成二维码失败：${e.message}`)
    })
}
const userStore = useUserStore()
const router = useRouter()
/**
 * 根据二维码id轮询二维码信息
 * @param qrCodeId 二维码id
 */
const fetchQrCodeInfo = (qrCodeId: string) => {
  fetch(qrCodeId)
    .then((r: any) => {
      if (r.ok) {
        qrCodeInfo.value = r.data
        if (qrCodeInfo.value.qrCodeStatus !== 0 && qrCodeInfo.value.avatarUrl) {
          // 只要不是待扫描并且头像不为空
          getQrCodeInfo.value.imageData = qrCodeInfo.value.avatarUrl
        }

        if (r.data.qrCodeStatus !== 2 && !qrCodeInfo.value.expired) {
          if (!showThirdLogin.value) {
            // 显示三方登录代表不是二维码登录，不轮询；否则继续轮询
            // 1秒后重复调用
            setTimeout(() => {
              fetchQrCodeInfo(qrCodeId)
            }, 1000)
          }
          return
        }
        if (qrCodeInfo.value.expired) {
          // 二维码过期
          return
        }
        if (qrCodeInfo.value.qrCodeStatus === 2) {
          userStore.changeLoginStatus(true)
          window.setTimeout(() => {
            useMessage().success('登录成功')
            router.push('/home')
          }, 1000)
        }
      } else {
        useMessage().warning(r.message)
      }
    })
    .catch((e: any) => {
      useMessage().warning(`获取二维码信息失败：${e.message || e.statusText}`)
    })
}
onMounted(() => {
  refreshQrCode()
})
</script>

<template>
  <div class="flex-center">
    <el-input v-model="getQrCodeInfo.qrCodeId" type="textarea" :rows="10" />
    <el-image width="300" :src="getQrCodeInfo.imageData" preview-disabled />
  </div>
</template>

<style scoped></style>
