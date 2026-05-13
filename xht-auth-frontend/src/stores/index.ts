import type { App } from 'vue'
import { createPinia } from 'pinia'
import piniaPlugin from 'pinia-plugin-persistedstate'

const store = createPinia()

// 全局注册 store
export function setupStore(app: App<Element>) {
  store.use(piniaPlugin)
  store.use(({ store }) => {
    const initialState = JSON.parse(JSON.stringify(store.$state))
    store.$reset = () => {
      store.$state = JSON.parse(JSON.stringify(initialState))
    }
  })
  app.use(store)
}

export { store }
