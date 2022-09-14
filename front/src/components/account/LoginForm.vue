<template>
  <div class="contents">
    <div class="form-wrapper form-wrapper-sm">
      <form @submit.prevent="submitForm">
        <fieldset>
          <legend>Sign In | 로그인</legend>
          <div>
            <label for="username" class="screen_out">id : </label>
            <input
              id="username"
              type="text"
              v-model="username"
              placeholder="이메일 주소"
              class="form-input"
            />
          </div>
          <div>
            <label for="password" class="screen_out">pw : </label>
            <input
              id="password"
              type="text"
              v-model="password"
              placeholder="비밀번호"
              class="form-input"
            />
          </div>
          <button :disabled="!isUsernameValid || !password" type="submit">
            로그인
          </button>
          <p>{{ logMessage }}</p>
        </fieldset>
      </form>
    </div>
  </div>
</template>

<script>
import { loginUser } from '@/api/index'
// import { validateEmail } from '@/utils/validation';

export default {
  data() {
    return {
      // form values
      username: '',
      password: '',
      // log
      logMessage: '',
    }
  },
  computed: {
    /*
          isUsernameValid() {
              // return validateEmail(this.username);
          },*/
  },
  methods: {
    async submitForm() {
      try {
        const userData = {
          username: this.username,
          password: this.password,
        }
        const response = await loginUser(userData)
        this.initForm()
        console.log(response)
        // this.logMessage = `${data}`;
      } catch (error) {
        console.log(error)
      }
    },
    initForm() {
      this.username = ''
      this.password = ''
    },
  },
}
</script>

<style lang="scss">
@import './scss/style.scss';
</style>
