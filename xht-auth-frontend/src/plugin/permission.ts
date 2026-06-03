import router from '@/router/index'
import type {RouteLocationNormalized} from 'vue-router'
import {useCheckLoginHooks} from '@/hooks/use-check-login'

const whiteList = ['/sso/login', '/sso/register']

export function setupPermission() {
    router.beforeEach(async (to: RouteLocationNormalized): Promise<any> => {
        const checkLoginHooks = useCheckLoginHooks()
        let redirectUrl: string | null = null
        if (checkLoginHooks.isLogin) {
            if (whiteList.includes(to.path)) {
                redirectUrl = '/home'
            }
        } else {
            if ((to.path === '/sso/login' && to.query.target)) {
                return
            }
            if (to.query.error !== null) {
                await checkLoginHooks.checkLoginStatus().then(() => {
                    if (whiteList.includes(to.path)) {
                        redirectUrl = '/home'
                    }
                })
            } else {
                if (!whiteList.includes(to.path)) {
                    redirectUrl = '/sso/login?error'
                }
            }
        }
        if (redirectUrl) {
            return redirectUrl
        }
    })
    router.afterEach((to: RouteLocationNormalized) => {
        document.title = (to.meta.title as string) || '小糊涂统一身份认证'
    })
}

