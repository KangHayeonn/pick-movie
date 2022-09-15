import Vue from 'vue'
import App from './App.vue'
import router from '@/routes/index'
import Dropdown from 'vue-simple-search-dropdown'
import Viconly from 'viconly'

Vue.use(Dropdown)
Vue.component('ic', Viconly)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
