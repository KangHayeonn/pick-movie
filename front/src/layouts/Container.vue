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
  </div>
</template>

<script>
import Header from './Header.vue'
import Contents from './Contents.vue'

export default {
  name: 'Container',
  components: {
    Header,
    Contents,
  },
}
</script>
