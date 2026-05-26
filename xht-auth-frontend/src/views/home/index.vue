<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/modules/user.store'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const appListLoading = ref(false)

const getAppList = async () => {
  appListLoading.value = true
  try {
    await userStore.fetchAppList()
  } catch {
    ElMessage.error('获取应用列表失败')
  } finally {
    appListLoading.value = false
  }
}

const goToApp = (url: string) => {
  window.open(url, '_blank')
}
const handleClick = () => {
  window.location.href =
    'http://www.xht.sso.com:9000/oauth2/authorize?client_id=oidc-client&response_type=code&scope=openid write&redirect_uri=https://www.baidu.com'
    'http://www.xht.sso.com:9000/oauth2/authorize?response_type=code&client_id=oidc-client&scope=openid%20write&redirect_uri=http://www.xht.com:8080/login/oauth2/code/spring'

}
onMounted(() => {
  //   getAppList()
})
</script>

<template>
  <div class="box-border h-full bg-gray-50 p-6">
    <div class="mx-auto max-w-6xl">
      <el-card class="mb-6" shadow="never">
        <div class="flex items-center gap-6">
          <el-avatar :size="80" :src="userStore.userInfo?.userAvatar || ''">
            {{ userStore.userInfo?.nickName?.charAt(0) || 'U' }}
          </el-avatar>
          <div class="flex-1">
            <h2 class="mb-2 text-2xl text-gray-800 font-bold">
              {{ userStore.userInfo?.nickName || '未知用户' }}
            </h2>
            <div class="flex flex-wrap gap-4 text-sm text-gray-500">
              <span class="flex items-center gap-1">
                <span class="text-gray-700 font-medium">账号:</span>
                {{ userStore.userInfo?.userName || '-' }}
              </span>
              <span class="flex items-center gap-1">
                <span class="text-gray-700 font-medium">部门:</span>
                {{ userStore.userInfo?.deptName || '-' }}
              </span>
              <span class="flex items-center gap-1">
                <span class="text-gray-700 font-medium">手机:</span>
                {{ userStore.userInfo?.userPhone || '-' }}
              </span>
              <span class="flex items-center gap-1">
                <span class="text-gray-700 font-medium">注册时间:</span>
                {{ userStore.userInfo?.registerDate || '-' }}
              </span>
            </div>
          </div>
          <el-tag :type="userStore.userInfo?.userStatus === 1 ? 'success' : 'danger'" size="large">
            {{ userStore.userInfo?.userStatus === 1 ? '正常' : '异常' }}
          </el-tag>
        </div>
      </el-card>
      <el-button @click="handleClick">跳转到</el-button>
      <div class="mb-4">
        <h3 class="text-xl text-gray-800 font-bold">可访问的应用</h3>
        <p class="mt-1 text-sm text-gray-500">点击下方应用卡片即可跳转到对应系统</p>
      </div>

      <el-row :gutter="20" v-loading="appListLoading">
        <el-col v-for="app in userStore.appList" :key="app.appId" :xs="24" :sm="12" :md="8" :lg="6" class="mb-4">
          <el-card
            class="app-card cursor-pointer transition-all duration-300 hover:shadow-lg hover:-translate-y-1"
            shadow="never"
            @click="goToApp(app.appUrl)"
          >
            <div class="flex flex-col items-center text-center">
              <el-avatar :size="64" :src="app.appLogo" class="mb-4">
                {{ app.appName.charAt(0) }}
              </el-avatar>
              <h4 class="mb-2 text-lg text-gray-800 font-bold">{{ app.appName }}</h4>
              <p class="line-clamp-2 mb-3 text-sm text-gray-500">{{ app.appDescription }}</p>
              <el-tag :type="app.appStatus === 1 ? 'success' : 'info'" size="small">
                {{ app.appStatus === 1 ? '可用' : '维护中' }}
              </el-tag>
            </div>
          </el-card>
        </el-col>

        <el-col v-if="!appListLoading && userStore.appList.length === 0" :span="24">
          <el-empty description="暂无可访问的应用" />
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped>
.app-card {
  border: 1px solid #e5e7eb;
  border-radius: 12px;
}

.app-card:hover {
  border-color: #409eff;
}
</style>
