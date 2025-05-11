// Import Tailwind CSS first
//import '@/assets/public.css'
import 'floating-vue/dist/style.css'

import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
import FloatingVue from 'floating-vue'

const app = createApp(App)

app.use(createPinia())
app.use(FloatingVue)
app.use(router)

app.mount('#app')
