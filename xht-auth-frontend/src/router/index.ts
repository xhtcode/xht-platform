import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.VITE_APP_BASE),
    scrollBehavior: () => ({left: 0, top: 0}),
    routes: [
        {
            path: '/',
            name: 'index',
            redirect: '/sso/login',
            component: () => import('@/layout/index.vue'),
            children: [
                {
                    path: '/sso/login',
                    name: 'login',
                    component: () => import('@/views/login/index.vue'),
                },
                {
                    path: '/home',
                    name: 'home',
                    component: () => import('@/views/test/index.vue'),
                },
            ]
        },
        {
            path: '/sso/confirm_access',
            name: 'confirm_access',
            component: () => import('@/views/confirm_access/index.vue'),
        }
    ],
})


export default router
