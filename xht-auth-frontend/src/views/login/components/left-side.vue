<script lang="ts" setup>
import imageSrc from '@/assets/svg/logo.svg'
import imageBgSrc from '@/assets/svg/login-bg.svg'

defineOptions({
  inheritAttrs: false,
})
</script>

<template>
  <div class="left-container select-none">
    <!-- 顶部品牌Logo -->
    <div class="brand-header">
      <div class="brand-logo">
        <el-image :fit="'cover'" :src="imageSrc" alt="系统Logo" class="logo-icon" />
        <span class="brand-name">小糊涂统一身份认证</span>
      </div>
    </div>
    <!-- 品牌内容区 -->
    <div class="brand-content">
      <!-- 品牌图 -->
      <el-image :fit="'contain'" :src="imageBgSrc" alt="品牌宣传图" class="brand-banner" />
      <!-- 补充品牌文案，提升左侧内容丰富度 -->
      <div class="brand-desc">
        <h3 class="desc-title">高效 · 安全 · 便捷</h3>
        <p class="desc-text">为企业构建安全、便捷、统一的数字身份入口，一次登录，畅通所有业务系统</p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
// 左侧品牌介绍区域
.left-container {
  flex: 0 0 550px;
  background: linear-gradient(180deg, #0f2044 0%, #19376d 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  color: #fff;

  // 顶部Logo
  .brand-header {
    position: absolute;
    top: 2rem;
    left: 2rem;

    .brand-logo {
      display: flex;
      align-items: center;
      gap: 0.75rem;

      .logo-icon {
        width: 35px;
        height: 35px;
        border-radius: 4px;
      }

      .brand-name {
        font-size: 1.25rem;
        font-weight: 500;
        line-height: 1.5;
      }
    }
  }

  // 品牌内容区
  .brand-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    text-align: center;

    .brand-banner {
      max-width: 60%;
      height: auto;
      max-height: 300px; // 限制图片高度，避免拉伸
    }

    .brand-desc {
      .desc-title {
        font-size: 1.5rem;
        font-weight: 600;
        margin-bottom: 0.5rem;
      }

      .desc-text {
        font-size: 0.9rem;
        color: rgba(255, 255, 255, 0.8);
        line-height: 1.6;
        max-width: 400px;
      }
    }
  }
}

@media (max-width: 992px) {
  .left-container {
    flex: 0 0 400px;
  }
}

@media (max-width: 768px) {
  .left-container {
    display: none;
  }
}
</style>
