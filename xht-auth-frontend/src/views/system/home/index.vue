<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/modules/user.store'

const router = useRouter()
const userStore = useUserStore()

/**
 * 查看详情（跳转账号设置）
 */
const goDetail = () => {
  router.push('/account')
}
</script>

<template>
  <div class="home-page">
    <!-- 用户信息卡片 -->
    <el-card class="user-info-card" shadow="never">
      <div class="user-info-body">
        <el-avatar :size="72" :src="userStore.userInfo?.userAvatar || ''">
          {{ userStore.userInfo?.nickName?.charAt(0) || 'U' }}
        </el-avatar>
        <div class="user-detail">
          <h2 class="user-name">{{ userStore.userInfo?.nickName || '系统管理员' }}</h2>
          <p class="user-meta">
            <span>
              账号:
              <em>{{ userStore.userInfo?.userName || '-' }}</em>
            </span>
            <span>
              部门:
              <em>{{ userStore.userInfo?.deptName || '-' }}</em>
            </span>
            <span>
              手机:
              <em>{{ userStore.userInfo?.userPhone || '-' }}</em>
            </span>
            <span>
              注册时间:
              <em>{{ userStore.userInfo?.registerDate || '-' }}</em>
            </span>
          </p>
        </div>
        <el-button plain @click="goDetail">详情</el-button>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.home-page {
  box-sizing: border-box;
  padding: 10px 10px 5px 10px;
  min-height: 100%;
  overflow: hidden;
  color: var(--el-text-color-primary);
}

/* 用户信息卡片 */
.user-info-card {
  border-radius: 12px;
  margin-bottom: 28px;
}

.user-info-body {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-detail {
  flex: 1;
}

.user-name {
  margin: 0 0 10px 0;
  font-size: 22px;
  font-weight: 700;
  color: #303133;
}

.user-meta {
  margin: 0;
  font-size: 13px;
  color: #909399;
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.user-meta em {
  color: #606266;
  font-style: normal;
}
</style>
