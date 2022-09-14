import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  scrollBehavior: () => {
    return { x: 0, y: 0 }
  },
  routes: [
    {
      path: '*',
      redirect: '/',
    },
    {
      path: '/',
      name: 'Container',
      component: () => import('@/layouts/Container.vue'),
      redirect: { name: 'Main' },
      children: [
        {
          path: '/login',
          name: 'Login',
          component: () => import('@/views/account/LoginPage.vue'),
        },
        {
          path: '/signup',
          name: 'Signup',
          component: () => import('@/views/account/SignupPage.vue'),
        },
        {
          path: '/main',
          name: 'Main',
          component: () => import('@/views/main/MainPage.vue'),
          meta: { group: 'Main', auth: true },
        },
      ],
    },
    {
      path: '/error',
      name: 'Error',
      component: () => import('@/views/error/NotFoundPage.vue'),
    },
  ],
})

// router navigation guard
/*
router.beforeEach((to, from, next) => {
  if (to.meta.auth) {
    next('/login')
    console.log('인증이 필요합니다.')
    // next를 하고 다음 next를 하는 불필요한 호출이 생길 수 있기 때문
    return
  }
  next()
})*/

export default router
