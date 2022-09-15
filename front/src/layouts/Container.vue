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
    <Footer />
  </div>
</template>

<script>
import Header from './Header.vue'
import Footer from './Footer.vue'
import Contents from './Contents.vue'

export default {
  name: 'Container',
  components: {
    Header,
    Footer,
    Contents,
  },
}
</script>

<style scoped lang="scss">
.container-doc {
  min-width: 650px;
}
</style>
