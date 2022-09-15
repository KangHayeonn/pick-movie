<template>
  <div class="search-group">
    <div class="search-form">
      <form>
        <label for="id" class="screen_out">검색창 : </label>
        <input
          type="text"
          id="keyword"
          name="keyword"
          class="search-txt"
          placeholder="Search"
          autocomplete="off"
        />
        <img
          :src="require(`@/assets/icons/${images}`)"
          alt="search"
          class="search-icon"
        />
      </form>
    </div>
    <div class="search-result">검색 내역 출력</div>
  </div>
</template>

<script>
import Vue from 'vue'
import Viconly from 'viconly'
Vue.component('ic', Viconly)

export default {
  data() {
    return {
      images: 'magnify.png',
    }
  },
}
</script>

<style scoped lang="scss">
.search-form {
  position: relative;
}
.search-txt {
  width: 195px;
  height: 29px;
  border: none;
  border-radius: 5px;
  background: rgba(227, 225, 225, 0.15);
  padding: 0 12px 0 30px;
  color: #c7c6c6;
}
.search-txt:focus {
  border-bottom: 1px solid #e5e5e5;
}
.search-icon {
  position: absolute;
  left: 10px;
  top: 7px;
  width: 18px;
  height: 18px;
}
</style>
