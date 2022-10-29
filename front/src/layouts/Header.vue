<template>
  <header class="header-wrapper">
    <router-link to="/main">
      <img
        :src="require(`@/assets/images/${images}`)"
        alt="logo"
        class="logo-img"
      />
    </router-link>
    <Navigator v-if="isShow" />
    <button
      v-if="!isLogin"
      type="button"
      class="direct-link"
      @click="clickLogin"
    >
      로그인
    </button>
    <button v-else type="button" class="direct-link" @click="clickLogout">
      로그아웃
    </button>
  </header>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import { getAccessToken } from '@/plugins/tokenControl'
import Navigator from './Navigator.vue'

const { mapGetters, mapActions } = createNamespacedHelpers('auth')

export default {
  name: 'Header',
  components: { Navigator },
  data() {
    return {
      images: 'Logo.png',
      isShow: true,
      isLogin: false,
    }
  },
  created() {
    // this.isShow = !(this.$route.meta?.type === 'auth')
  },
  computed: {
    ...mapGetters(['getIsOpen', 'getIsToken']),
  },
  watch: {
    getIsOpen: {
      deep: true,
      handler(val) {
        this.isShow = val
      },
    },
    getIsToken: {
      deep: true,
      handler(val) {
        this.isLogin = val
      },
    },
  },
  methods: {
    ...mapActions(['logoutUser']),
    clickLogin() {
      this.$router.push('/login')
    },
    loginCheck() {
      const isLogin = true
      return isLogin
    },
    clickLogout() {
      alert('로그아웃 되었습니다.')
      this.logoutUser()
      this.isLogin = false
    },
  },
}
</script>

<style scoped lang="scss">
@import '@/styles/variables/index.scss';
.header-wrapper {
  display: flex;
  justify-content: space-between;
}
.logo-img {
  width: 181px;
  height: 45px;
  margin: 25px;
}
.direct-link {
  width: 91px;
  height: 36px;
  margin: 29px;
  background-color: $red-60;
  border-radius: 5px;
  color: $gray-100;
  font-size: 17px;
  font-weight: 400;
}
</style>
