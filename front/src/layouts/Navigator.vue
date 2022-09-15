<template>
  <nav class="nav-wrapper">
    <ul class="horizontal">
      <li>
        <a href="javascript:void(0)"><span>최신 콘텐츠</span></a>
      </li>
      <li>
        <a href="javascript:void(0)"><span>시리즈</span></a>
      </li>
      <li>
        <a href="javascript:void(0)"><span>영화</span></a>
      </li>
      <li>
        <a href="javascript:void(0)"><span>내가 찜한 콘텐츠</span></a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'Navigator',
}
</script>

<style scoped lang="scss">
.nav-wrapper {
  padding-top: 43px;
  font-size: 21px;
}
.horizontal {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
}
.horizontal li {
  float: left;
  border-right: 1px solid #aeadad;
}
.horizontal li:last-child {
  border: none;
}
.horizontal li a {
  display: inline-block;
  color: #aeadad;
  font-weight: 300;
  text-align: center;
  padding: 0 19px;
  text-decoration: none;
  // border-right: 1px solid #aeadad;
}
.horizontal li a:hover,
.horizontal li a:focus {
  color: #fff;
}
.horizontal li a:focus {
  font-weight: 400;
}
</style>
