/**
 * pinia持久化参数配置
 */
import type { PersistenceOptions } from 'pinia-plugin-persistedstate'

/**
 * @param key  缓存key的名称
 * @param pick 指定 state 中哪些数据需要被持久化。[] 表示不持久化任何状态，undefined 或 null 表示持久化整个 state
 */
const pInIaPersistConfig = (key: string, pick?: string[]) => {
  const persist: PersistenceOptions = {
    key: `xht_${key}`,
    storage: window.localStorage,
    //storage: window.sessionStorage,
    debug: false,
    pick: pick,
  }
  return persist
}

export default pInIaPersistConfig
