import Vue from 'vue'
import App from './App.vue'

import router from '@/routes/index'
import store from './store/index'
import Dropdown from 'vue-simple-search-dropdown'
import Viconly from 'viconly'

Vue.use(Dropdown)
Vue.component('ic', Viconly)

Vue.config.productionTip = false

new Vue({
  router,
  store, // v3.6.2 버전 사용해야함 (vue2)
  render: h => h(App),
}).$mount('#app')
