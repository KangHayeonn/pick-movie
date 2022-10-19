<template>
  <div>
    <h1 class="screen_out">회원가입 페이지</h1>
    <SignupForm></SignupForm>
  </div>
</template>

<script>
import SignupForm from '@/components/account/SignupForm.vue'
export default {
  name: 'Signup',
  components: {
    SignupForm,
  },
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
