import router from '@/router/index'
import type {RouteLocationNormalized} from 'vue-router'
import {useLoginStore} from "@/stores/login.store";

/**
 * 白名单路由
 */
const whiteList = ['/sso/login']

export function setupPermission() {
    router.beforeEach(async (to, _, next) => {
        const loginStore = useLoginStore()
        console.log(loginStore.loginStatus)
       if (loginStore.loginStatus){
           next()
       }else {
           if (whiteList.includes(to.path)) {
               next()
           } else {
               // 重定向登录页
               next({
                   path: '/sso/login',
                   query: {
                       redirect: to.fullPath,
                   },
               })
           }
       }

    })

    router.afterEach((to: RouteLocationNormalized) => {

    })
}
