<template>
  <footer class="footer-wrapper">
    <div class="item-group">
      <div class="left-group">
        <div class="link-group">
          <div class="tit-group">Quick Link</div>
          <div>Series</div>
          <div>Movie</div>
        </div>
        <div class="follow-group">
          <div class="tit-group">Follow Us On</div>
          <div>Hayeon</div>
          <div>Seungmin</div>
        </div>
      </div>
      <div class="right-group">
        <SearchForm />
      </div>
    </div>
  </footer>
</template>

<script>
import SearchForm from '../components/common/SearchForm.vue'
export default {
  name: 'Footer',
  components: { SearchForm },
}
</script>

<style scoped lang="scss">
.footer-wrapper {
  margin-bottom: 1em;
  border-top: 1px solid #c4c4c4;
  margin-top: 63px;
  padding-bottom: 1em;
  padding: 26px 148px;
  color: #858080;
  font-size: 21px;
}
.item-group {
  display: flex;
  justify-content: space-between;
}
.left-group {
  display: flex;
  margin-right: 3em;
}
.link-group {
  margin-right: 3em;
}
.tit-group {
  margin-bottom: 1.5em;
}
</style>
