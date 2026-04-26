import router from '@/router/index'
import type {RouteLocationNormalized} from 'vue-router'
import {useUserStore} from "@/stores/modules/user.store";

const whiteList = ['/sso/login', '/sso/register','/sso/confirm_access']

export function setupPermission() {
    router.beforeEach(async (to, _, next) => {
        const userStore = useUserStore()

        if (userStore.loginStatus) {
            if (to.path === '/sso/login' || to.path === '/sso/register') {
                next({path: '/home', replace: true})
                return
            }

            if (userStore.userInfo) {
                next()
            } else {
                const success = await userStore.fetchUserInfo()
                if (success) {
                    next()
                } else {
                    userStore.clearUserInfo()
                    next({
                        path: '/sso/login',
                        query: {redirect: to.fullPath},
                    })
                }
            }
        } else {
            if (whiteList.includes(to.path)) {
                next()
            } else {
                next({
                    path: '/sso/login',
                    query: {redirect: to.fullPath},
                })
            }
        }
    })

    router.afterEach((to: RouteLocationNormalized) => {
    })
}