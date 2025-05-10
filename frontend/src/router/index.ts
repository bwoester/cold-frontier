import {createRouter, createWebHistory} from 'vue-router'
import HomePage from '../views/HomePage.vue'
import LoginPage from "@/views/LoginPage.vue";
import RegisterPage from "@/views/RegisterPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage
    }, {
      path: '/login',
      name: 'login',
      component: LoginPage
    }, {
      path: '/register',
      name: 'register',
      component: RegisterPage
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
