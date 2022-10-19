<template>
  <div>
    <h1 class="screen_out">로그인 페이지</h1>
    <LoginForm></LoginForm>
  </div>
</template>

<script>
import LoginForm from '@/components/account/LoginForm.vue'

export default {
  name: 'Login',
  components: { LoginForm },
  props: {},
  data() {
    return {}
  },
  created() {},
  computed: {},
  watch: {},
  methods: {},
  mounted() {},
}
</script>
