<script lang="ts" setup>
import {ref} from 'vue'

defineOptions({
  name: 'QrCodeForm',
  inheritAttrs: false,
})
type QrStatusType = 'waiting' | 'scanned' | 'confirmed' | 'expired'

const qrCodeImage = ref<string>('0000')
const qrStatus = ref<QrStatusType>('scanned')
</script>

<template>
  <div class="qr-wrapper">
    <div class="qr-border">
      <div v-if="qrCodeImage" class="qr-image-wrap cursor-pointer">
        <img src="https://ts1.tc.mm.bing.net/th?id=OJ.ilrwlLiKV5AQIA&w=80&h=80&c=8&rs=1&pid=academic" alt="扫码登录"
             class="qr-image "/>
        <div v-if="qrStatus !== 'waiting'" class="qr-overlay">
          <div v-if="qrStatus === 'scanned'" class="qr-status scanned-status">
            <div class="status-icon scanned-icon">&#10003;</div>
            <p>已扫描</p>
            <span>请在手机上确认登录</span>
          </div>
          <div v-else-if="qrStatus === 'expired'" class="qr-status expired-status">
            <div class="status-icon expired-icon">&#x2718;</div>
            <p>已过期</p>
            <span>点击刷新</span>
          </div>
        </div>
      </div>
      <div v-else class="qr-loading">
        <div class="qr-spinner"></div>
        <span>二维码生成中...</span>
      </div>
    </div>
    <p class="qr-hint">
      <template v-if="qrStatus === 'waiting'">打开对应App，扫描二维码登录</template>
      <template v-else-if="qrStatus === 'scanned'">扫描成功，请在手机确认</template>
      <template v-else-if="qrStatus === 'confirmed'">登录成功，正在跳转...</template>
      <template v-else-if="qrStatus === 'expired'">二维码已过期，请点击刷新</template>
    </p>
  </div>
</template>

<style lang="scss" scoped>

.qr-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 260px;
  box-sizing: border-box;
}

.qr-border {
  width: 220px;
  height: 220px;
  border-radius: 14px;
  border: 1px dashed #cbd5e1;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  position: relative;
  background: #fafbfc;
}

.qr-image-wrap {
  position: relative;
  padding: 5px;
  width: 220px;
  height: 220px;
  border-radius: 8px;
  overflow: hidden;
}

.qr-image {
  width: 100%;
  height: 100%;
  border-radius: 8px;
  object-fit: contain;
}

.qr-overlay {
  position: absolute;
  inset: 0;
  background: rgba(255, 255, 255, 0.92);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
}

.qr-status {
  text-align: center;
}

.status-icon {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  margin: 0 auto 10px;
  font-weight: 700;
}

.scanned-icon {
  background: #dbeafe;
  color: #3b82f6;
}

.expired-icon {
  background: #fef2f2;
  color: #ef4444;
}

.qr-status p {
  font-size: 13px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 4px;
}

.qr-status span {
  font-size: 12px;
  color: #94a3b8;
}

.qr-loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  color: #94a3b8;
  font-size: 13px;
}

.qr-spinner {
  width: 32px;
  height: 32px;
  border: 3px solid #e2e8f0;
  border-top-color: #3b82f6;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.qr-hint {
  margin-top: 24px;
  font-size: 12px;
  color: #64748b;
  text-align: center;
}
</style>
