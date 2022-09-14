<template>
  <div class="contents">
    <div class="form-wrapper form-wrapper-sm">
      <form @submit.prevent="submitForm">
        <fieldset>
          <legend>Sign Up | 회원가입</legend>
          <div>
            <label for="username" class="screen_out">id: </label>
            <input
              id="username"
              type="text"
              v-model="username"
              placeholder="이메일 주소"
              class="form-input"
            />
          </div>
          <p class="validation-text">
            <span class="warning">중복 아이디가 있습니다.</span>
          </p>
          <div>
            <label for="password" class="screen_out">비밀번호: </label>
            <input
              id="password"
              type="text"
              v-model="password"
              placeholder="비밀번호"
              class="form-input"
            />
          </div>
          <div>
            <label for="password" class="screen_out">비밀번호 확인: </label>
            <input
              id="password"
              type="text"
              v-model="passwordCheck"
              placeholder="비밀번호 확인"
              class="form-input"
            />
          </div>
          <p class="validation-text">
            <span class="warning">비밀번호가 다릅니다.</span>
          </p>
          <div class="input-form">
            <label for="interest" class="title">관심 분야</label>
            <input
              id="interest"
              type="text"
              v-model="interests"
              placeholder="관심 분야 추가"
              class="form-input"
            />
          </div>
          <button type="submit">회원가입</button>
          <p>{{ logMessage }}</p>
        </fieldset>
      </form>
    </div>
  </div>
</template>

<script>
import { registerUser } from '@/api/index'

export default {
  data() {
    return {
      // form values
      username: '',
      password: '',
      passwordCheck: '',
      interests: [],
      // log
      logMessage: '',
    }
  },
  methods: {
    async submitForm() {
      try {
        const userData = {
          username: this.username,
          password: this.password,
          tags: ['호러', '로맨스'],
        }
        const { data } = await registerUser(userData)
        this.logMessage = `${data}`
        this.initForm()
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
