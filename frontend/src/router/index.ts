import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/public/HomePage.vue'),
    }, {
      path: '/login',
      name: 'login',
      component: () => import('../views/public/LoginPage.vue'),
    }, {
      path: '/register',
      name: 'register',
      component: () => import('../views/public/RegisterPage.vue'),
    },
    {
      path: '/game',
      name: 'game-dashboard',
      component: () => import('../views/protected/GameDashboard.vue'),
      // We would typically add authentication guard here
      // meta: { requiresAuth: true }
    },
    {
      path: '/game/buildings',
      name: 'buildings',
      component: () => import('../views/protected/GameDashboard.vue'),
      // Placeholder for future building page
    },
    {
      path: '/game/research',
      name: 'research',
      component: () => import('../views/protected/GameDashboard.vue'),
      // Placeholder for future research page
    },
    {
      path: '/game/shipyard',
      name: 'shipyard',
      component: () => import('../views/protected/GameDashboard.vue'),
      // Placeholder for future shipyard page
    },
  ],
  scrollBehavior(to) {
    if (to.hash) {
      return {
        el: to.hash,
        behavior: 'smooth'
      }
    }
    return {top: 0}
  }
})

export default router
