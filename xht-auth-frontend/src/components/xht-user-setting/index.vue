<script setup lang="ts">
import { useMessage } from '@/hooks/use-message'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/modules/user.store'

defineOptions({
  name: 'XhtUserSetting',
})
const router = useRouter()
const userStore = useUserStore()

/**
 * 跳转
 * @param path 路径
 */
const pushUrl = (path: string) => {
  router.push(path)
}
/**
 * 退出登录
 */
const handleLogout = () => {
  // @ts-ignore
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      userStore
        .clearUserInfo()
        .then(() => {
          router.push('/sso/login')
          useMessage().success('退出登录成功')
        })
        .catch(() => {
          useMessage().error('退出登录失败')
        })
    })
    .catch(() => {})
}
</script>

<template>
  <el-dropdown trigger="click" placement="bottom-end" size="large">
    <el-avatar src="https://picsum.photos/48/48"></el-avatar>
    <template #dropdown>
      <el-dropdown-menu class="w-[180px]">
        <el-dropdown-item @click="pushUrl('/home')">
          <el-avatar src="https://picsum.photos/48/48" size="small"></el-avatar>
          <div class="flex-1 text-center">系统管理员</div>
        </el-dropdown-item>
        <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<style scoped></style>
