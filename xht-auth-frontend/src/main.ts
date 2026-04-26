import '@/assets/main.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import 'virtual:uno.css'
import '@unocss/reset/normalize.css'
import {createApp} from 'vue'
import {setupStore} from '@/stores'

import App from '@/App.vue'
import router from '@/router'
import {setupPermission} from "@/plugin/permission";

setupPermission()
const app = createApp(App)
setupStore(app)
app.use(router)

app.mount('#app')
