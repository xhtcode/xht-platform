<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import PasswordLogin from '@/views/login/components/password-form.vue'
import SmsLogin from '@/views/login/components/phone-form.vue'
import QrCodeLogin from '@/views/login/components/qrcode-form.vue'
import LeftSide from '@/views/login/components/left-side.vue'
import { useMessage } from '@/hooks/use-message'

defineOptions({
  name: 'LoginNew',
})

// ========== 状态定义 ==========
// 当前激活的登录方式
type LoginType = 'password' | 'qrcode' | 'phone' | 'wechat' | 'qq' | 'dinging'

// 第三方登录
const activeLoginType = ref<LoginType>('password')

const router = useRouter()

// UI状态
const loading = ref(false)

/**
 * 切换登录方式
 * @param type 登录方式
 */
const handleChangeLoginType = (type: LoginType) => {
  if (type === 'password' || type === 'phone' || type === 'qrcode') {
    activeLoginType.value = type
  } else {
    useMessage().error(`暂不支持\`${type}\`第三方登录`)
  }
}
// ========== 导航与第三方 ==========
const goRegister = () => {
  router.push('/sso/register')
}
</script>

<template>
  <div class="login-container">
    <!-- 左侧品牌展示区 -->
    <left-side />

    <!-- 右侧登录功能区 -->
    <div class="login-section select-none">
      <div class="login-wrapper">
        <h2 class="login-title">小糊涂・统一身份认证</h2>
        <p class="login-subtitle">请选择方式登录您的账号</p>
        <!-- 登录面板区域 -->
        <Transition name="panel-fade" mode="out-in">
          <!-- 密码登录面板 -->
          <PasswordLogin v-if="activeLoginType === 'password'" :loading="loading" />
          <!-- 手机号登录面板 -->
          <SmsLogin v-else-if="activeLoginType === 'phone'" :loading="loading" />
          <!-- 扫码登录面板 -->
          <QrCodeLogin v-else-if="activeLoginType === 'qrcode'" :loading="loading" />
        </Transition>

        <!-- 分割线 -->
        <el-divider border-style="solid">
          <span class="divider-text">其他登录方式</span>
        </el-divider>

        <!-- 第三方登录 -->
        <div class="other-party-login">
          <div class="other-party-icons">
            <div
              :class="{ active: activeLoginType === 'password' }"
              class="other-party-icon-item"
              title="密码登录"
              @click="handleChangeLoginType('password')"
            >
              <div class="icon i-login-password" />
            </div>
            <div
              :class="{ active: activeLoginType === 'phone' }"
              class="other-party-icon-item"
              title="手机号登录"
              @click="handleChangeLoginType('phone')"
            >
              <div class="icon i-login-phone" />
            </div>
            <div
              :class="{ active: activeLoginType === 'qrcode' }"
              class="other-party-icon-item"
              title="扫码登录"
              @click="handleChangeLoginType('qrcode')"
            >
              <div class="icon i-login-sm" />
            </div>
            <div
              :class="{ active: activeLoginType === 'wechat' }"
              class="other-party-icon-item"
              title="微信登录"
              @click="handleChangeLoginType('wechat')"
            >
              <div class="icon i-login-wechat" />
            </div>
            <div :class="{ active: activeLoginType === 'qq' }" class="other-party-icon-item" title="QQ登录" @click="handleChangeLoginType('qq')">
              <div class="icon i-login-qq" />
            </div>
            <div
              :class="{ active: activeLoginType === 'dinging' }"
              class="other-party-icon-item"
              title="钉钉登录"
              @click="handleChangeLoginType('dinging')"
            >
              <div class="icon i-login-ding-ding" />
            </div>
          </div>
        </div>

        <!-- 注册入口 -->
        <div class="register-row">
          <el-link class="register-link" @click="goRegister">还没有账号？立即注册</el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
/* ========== 容器布局 ========== */
.login-container {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

/* ========== 右侧功能区 ========== */
.login-section {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: var(--el-bg-color);
  padding: 32px;
  animation: slideInRight 0.8s 0.15s both cubic-bezier(0.16, 1, 0.3, 1);
}

@keyframes slideInRight {
  from {
    opacity: 0;
    transform: translateX(60px);
  }

  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.login-wrapper {
  width: 100%;
  max-width: 400px;
}

.login-title {
  font-family:
    'DM Sans',
    -apple-system,
    BlinkMacSystemFont,
    sans-serif;
  font-size: 22px;
  font-weight: 700;
  color: #0f172a;
  margin: 0 0 8px;
  letter-spacing: -0.3px;
}

.login-subtitle {
  font-size: 14px;
  color: #94a3b8;
  margin: 0 0 24px;
}

.divider-text {
  font-size: 10px;
  color: #94a3b8;
  white-space: nowrap;
}

/* ========== 注册入口 ========== */
.register-row {
  text-align: center;
  padding-top: 18px;
  display: flex;
  justify-content: center;
  font-size: 14px;
  color: #64748b;
}

/* ========== 响应式设计 ========== */

@media (max-width: 767px) {
  .login-container {
    flex-direction: column;
  }

  .login-section {
    animation: fadeInMobile 0.6s ease-out;
    padding: 24px 20px;
  }

  @keyframes fadeInMobile {
    from {
      opacity: 0;
      transform: translateY(20px);
    }

    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  .login-wrapper {
    max-width: 100%;
  }

  .login-title {
    font-size: 20px;
    text-align: center;
  }

  .login-subtitle {
    text-align: center;
  }
}

// 第三方登录样式
.other-party-login {
  width: 100%;

  .other-party-icons {
    display: flex;
    justify-content: center;
    gap: 18px;
    flex-wrap: wrap;
    padding: 0 10px;

    .other-party-icon-item {
      width: 28px;
      height: 28px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
      border-radius: 50%;
      border: 2px solid transparent;
      position: relative;

      &:hover {
        background: #e8e8e8;
        transform: scale(1.05);
        box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
      }

      &.active {
        background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
        box-shadow: 0 8px 25px rgba(64, 158, 255, 0.3);
        transform: scale(1.15);
        border-color: #3b82f6;

        .icon {
          width: 12px;
          height: 12px;
          color: #0f2044;
          fill: currentColor;
          filter: drop-shadow(0 0 6px rgba(59, 130, 246, 0.6));
          transform: scale(1.1);
        }
      }

      .icon {
        width: 24px;
        height: 24px;
        font-size: 18px;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
      }
    }
  }
}
</style>
<style lang="scss">
.forgot-link {
  font-size: 13px;
  color: #3b82f6;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  transition: color 0.2s;

  &:hover {
    color: #2563eb;
    text-decoration: underline;
  }
}

.login-button {
  width: 100%;
  height: 40px;
  font-size: 0.8rem;
  font-weight: 600;
  border-radius: 5px;
  background: linear-gradient(135deg, #3b82f6, #1d4ed8);
  border: none;
  margin-bottom: 0.6rem;

  &:hover {
    background: linear-gradient(135deg, #2563eb, #1e40af);
    transform: translateY(-1px);
    box-shadow: 0 10px 25px rgba(59, 130, 246, 0.3);
  }

  &:active {
    transform: translateY(0);
  }
}
</style>
