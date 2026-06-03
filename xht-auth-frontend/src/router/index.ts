import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.VITE_APP_BASE),
    scrollBehavior: () => ({left: 0, top: 0}),
    routes: [
        {
            path: '/',
            name: 'oauth2Index',
            redirect: '/sso/login',
            component: () => import('@/layout/oauth2/index.vue'),
            children: [
                {
                    path: '/sso/login',
                    name: 'login',
                    component: () => import('@/views/login/index.vue'),
                },
                {
                    path: '/sso/register',
                    name: 'register',
                    component: () => import('@/views/register/index.vue'),
                },
                {
                    path: '/sso/confirm_access',
                    name: 'confirm_access',
                    component: () => import('@/views/oauth2/consent-page/index.vue'),
                },
            ],
        },
        {
            path: '/',
            name: 'dashboardIndex',
            component: () => import('@/layout/dashboard/index.vue'),
            children: [
                {
                    path: '/home',
                    name: 'home',
                    component: () => import('@/views/home/index.vue'),
                },
            ]
        }
    ],
})

export default router
