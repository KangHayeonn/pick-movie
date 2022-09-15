import Vue from 'vue'
import App from './App.vue'
import router from '@/routes/index'
import Dropdown from 'vue-simple-search-dropdown'

Vue.use(Dropdown)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
