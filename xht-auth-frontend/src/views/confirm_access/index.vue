<template>
  <div class="min-h-screen bg-gray-50">
    <div class="max-w-6xl mx-auto p-6">
      <!-- 页面头部 -->
      <div class="mb-6">
        <h1 class="text-2xl font-bold text-gray-800 mb-2">第三方应用授权管理</h1>
        <p class="text-gray-500">
          以下是你已经授权访问你账户的第三方应用，你可以随时查看详情或撤销它们的访问权限。
        </p>
      </div>

      <!-- 应用列表 -->
      <el-card class="shadow-sm">
        <el-table :data="apps" border stripe>
          <!-- 应用信息列 -->
          <el-table-column label="应用" min-width="250">
            <template #default="{ row }">
              <div class="flex items-center gap-3">
                <img
                    :src="row.logo"
                    :alt="row.name"
                    class="w-10 h-10 rounded-full object-cover border border-gray-100"
                />
                <div>
                  <div class="font-medium text-gray-800">{{ row.name }}</div>
                  <div class="text-xs text-gray-500">{{ row.description }}</div>
                </div>
              </div>
            </template>
          </el-table-column>

          <!-- 权限列 -->
          <el-table-column label="权限" min-width="200">
            <template #default="{ row }">
              <div class="flex flex-wrap gap-1">
                <el-tag
                    v-for="scope in row.scopes.slice(0, 3)"
                    :key="scope.key"
                    :type="scope.type === 'write' ? 'warning' : scope.type === 'admin' ? 'danger' : 'success'"
                    size="small"
                >
                  {{ scope.label }}
                </el-tag>
                <el-tag v-if="row.scopes.length > 3" size="small" type="info">
                  +{{ row.scopes.length - 3 }}
                </el-tag>
              </div>
            </template>
          </el-table-column>

          <!-- 授权时间列 -->
          <el-table-column prop="authorizedAt" label="授权时间" width="120" align="center" />

          <!-- 最后使用列 -->
          <el-table-column prop="lastUsedAt" label="最后使用" width="120" align="center" />

          <!-- 操作列 -->
          <el-table-column label="操作" width="180" align="center">
            <template #default="{ row }">
              <el-button
                  size="small"
                  text
                  type="primary"
                  @click="handleViewDetail(row)"
              >
                查看详情
              </el-button>
              <el-button
                  size="small"
                  text
                  type="danger"
                  @click="handleRevoke(row)"
              >
                撤销授权
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <!-- 详情弹窗 -->
      <el-dialog
          v-model="dialogVisible"
          :title="currentApp?.name"
          width="600px"
      >
        <div v-if="currentApp" class="space-y-4">
          <!-- 应用基本信息 -->
          <div class="flex items-center gap-4 pb-4 border-b border-gray-100">
            <img
                :src="currentApp.logo"
                :alt="currentApp.name"
                class="w-16 h-16 rounded-lg object-cover border border-gray-100"
            />
            <div>
              <div class="text-lg font-medium text-gray-800">{{ currentApp.name }}</div>
              <a
                  :href="currentApp.website"
                  target="_blank"
                  class="text-sm text-primary hover:underline"
              >
                {{ currentApp.website }}
              </a>
              <div class="text-xs text-gray-500 mt-1">客户端ID: {{ currentApp.clientId }}</div>
            </div>
          </div>

          <!-- 权限详情 -->
          <div>
            <h3 class="font-medium text-gray-700 mb-3">已授予权限</h3>
            <div class="space-y-2">
              <div
                  v-for="scope in currentApp.scopes"
                  :key="scope.key"
                  class="flex items-start gap-3 p-3 bg-gray-50 rounded-lg"
              >
                <el-icon
                    :color="scope.type === 'write' ? '#e6a23c' : scope.type === 'admin' ? '#f56c6c' : '#67c23a'"
                >
                  <component :is="scope.type === 'write' ? Edit : scope.type === 'admin' ? Setting : View" />
                </el-icon>
                <div>
                  <div class="font-medium text-gray-800">{{ scope.label }}</div>
                  <div class="text-sm text-gray-500">{{ scope.description }}</div>
                </div>
              </div>
            </div>
          </div>

          <!-- 时间信息 -->
          <div class="grid grid-cols-2 gap-4 pt-4 border-t border-gray-100">
            <div>
              <div class="text-xs text-gray-500">授权时间</div>
              <div class="text-gray-800">{{ currentApp.authorizedAt }}</div>
            </div>
            <div>
              <div class="text-xs text-gray-500">最后使用时间</div>
              <div class="text-gray-800">{{ currentApp.lastUsedAt }}</div>
            </div>
            <div>
              <div class="text-xs text-gray-500">授权过期时间</div>
              <div class="text-gray-800">{{ currentApp.expiresAt }}</div>
            </div>
          </div>
        </div>

        <template #footer>
          <div class="flex justify-end gap-2">
            <el-button
                type="danger"
                @click="dialogVisible = false; handleRevoke(currentApp!)"
            >
              撤销授权
            </el-button>
            <el-button @click="dialogVisible = false">关闭</el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { View, Edit, Setting } from '@element-plus/icons-vue'

// 权限项类型定义
interface Scope {
  key: string
  label: string
  description: string
  type: 'read' | 'write' | 'admin'
}

// 已授权应用类型定义
interface AuthorizedApp {
  id: string
  clientId: string
  name: string
  logo: string
  description: string
  website: string
  scopes: Scope[]
  authorizedAt: string
  lastUsedAt: string
  expiresAt: string
}

// 模拟数据：实际项目中替换为API请求数据
const mockApps: AuthorizedApp[] = [
  {
    id: '1',
    clientId: 'client_001',
    name: 'Demo Task App',
    logo: 'https://picsum.photos/40/40?random=1',
    description: '任务管理第三方应用',
    website: 'https://demo-task.example.com',
    scopes: [
      { key: 'openid', label: 'openid', description: '获取你的用户唯一标识', type: 'read' },
      { key: 'profile', label: 'profile', description: '查看你的基本个人信息', type: 'read' },
      { key: 'user_write', label: 'user_write', description: '修改你的任务数据', type: 'write' },
    ],
    authorizedAt: '2024-01-15',
    lastUsedAt: '2024-03-28',
    expiresAt: '2025-01-15',
  },
  {
    id: '2',
    clientId: 'client_002',
    name: 'Photo Sync Tool',
    logo: 'https://picsum.photos/40/40?random=2',
    description: '照片同步工具',
    website: 'https://photo-sync.example.com',
    scopes: [
      { key: 'openid', label: 'openid', description: '获取你的用户唯一标识', type: 'read' },
      { key: 'email', label: 'email', description: '获取你的邮箱地址', type: 'read' },
      { key: 'photos_read', label: 'photos_read', description: '读取你的照片数据', type: 'read' },
      { key: 'photos_write', label: 'photos_write', description: '上传和修改你的照片', type: 'write' },
    ],
    authorizedAt: '2024-02-20',
    lastUsedAt: '2024-03-10',
    expiresAt: '2025-02-20',
  },
  {
    id: '3',
    clientId: 'client_003',
    name: 'Analytics Platform',
    logo: 'https://picsum.photos/40/40?random=3',
    description: '数据分析平台',
    website: 'https://analytics.example.com',
    scopes: [
      { key: 'openid', label: 'openid', description: '获取你的用户唯一标识', type: 'read' },
      { key: 'user_read', label: 'user_read', description: '读取你的统计数据', type: 'read' },
    ],
    authorizedAt: '2023-11-05',
    lastUsedAt: '2023-12-01',
    expiresAt: '2024-11-05',
  },
]

// 响应式数据
const apps = ref<AuthorizedApp[]>(mockApps)
const dialogVisible = ref(false)
const currentApp = ref<AuthorizedApp | null>(null)

// 查看应用详情
const handleViewDetail = (app: AuthorizedApp) => {
  currentApp.value = app
  dialogVisible.value = true
}

// 撤销授权
const handleRevoke = async (app: AuthorizedApp) => {
  try {
    await ElMessageBox.confirm(
        `确定要撤销 ${app.name} 的访问权限吗？\n撤销后该应用将无法再访问你的账户，如需再次使用需要重新授权。`,
        '确认撤销',
        {
          confirmButtonText: '确定撤销',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )

    // 实际项目中此处调用后端API完成授权撤销操作
    apps.value = apps.value.filter(item => item.id !== app.id)
    ElMessage.success('授权已撤销')
  } catch {
    // 用户取消操作，无需处理
  }
}
</script>

<style scoped>
/* 仅保留少量必要样式，其余样式均通过Unocss原子类实现 */
:deep(.el-card__body) {
  padding: 0;
}
</style>