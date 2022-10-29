<template>
  <div class="container-doc">
    <Header />
    <main class="doc-main">
      <section class="inner-main">
        <div class="main-content">
          <Contents />
        </div>
      </section>
    </main>
    <Footer v-show="isShow" />
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import Header from './Header.vue'
import Footer from './Footer.vue'
import Contents from './Contents.vue'

const { mapGetters } = createNamespacedHelpers('auth')

export default {
  name: 'Container',
  data() {
    return {
      isShow: true,
    }
  },
  components: {
    Header,
    Footer,
    Contents,
  },
  async created() {},
  computed: {
    ...mapGetters(['getIsOpen']),
  },
  watch: {
    getIsOpen: {
      deep: true,
      handler(val) {
        this.isShow = val
      },
    },
  },
}
</script>

<style scoped lang="scss">
.container-doc {
  min-width: 690px;
}
</style>
