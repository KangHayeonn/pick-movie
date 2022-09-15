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
    <div class="search-result">
      <div class="search-item">검색1</div>
      <div class="search-item">검색2</div>
      <div class="search-item">검색3</div>
    </div>
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
.search-group {
  position: relative;
}
.search-form {
  position: relative;
}
.search-txt {
  width: 195px;
  height: 29px;
  border: none;
  border-radius: 4px;
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
  top: 6px;
  width: 18px;
  height: 18px;
}
.search-result {
  width: 235px;
  height: 80px;
  border: 1px solid rgba(227, 225, 225, 0.437);
  position: absolute;
  top: 35px;
  font-size: 14px;
  border-radius: 4px;
  overflow: auto;
  &::-webkit-scrollbar {
    width: 10px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: #8f8c8c;
    border-radius: 12px;
    background-clip: padding-box;
    border: 2px solid transparent;
  }
  &::-webkit-scrollbar-track {
    background: #222222;
    border-radius: 10px;
    box-shadow: inset 0px 0px 5px #222222;
  }
}
.search-item {
  padding: 7px 10px;
}
.search-item:hover {
  color: #c7c6c6;
  background: rgba(227, 225, 225, 0.069);
}
</style>
