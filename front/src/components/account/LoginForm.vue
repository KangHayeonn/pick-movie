<template>
  <div class="contents">
    <div class="form-wrapper form-wrapper-sm">
      <form @submit.prevent="submitForm">
        <fieldset>
          <legend>Sign In | 로그인</legend>
          <div>
            <label for="id" class="screen_out">id : </label>
            <input
              type="text"
              id="id"
              name="id"
              v-model="username"
              placeholder="이메일 주소"
              class="form-input"
              autocomplete="off"
            />
          </div>
          <div>
            <label for="pw" class="screen_out">pw : </label>
            <input
              type="password"
              id="pw"
              name="pw"
              v-model="password"
              placeholder="비밀번호"
              class="form-input"
              autocomplete="off"
            />
          </div>
          <button
            type="submit"
            class="submit-btn"
            :disabled="!isUsernameValid || !password"
          >
            로그인
          </button>
          <div class="item-choice">
            <Checkbox
              id="check"
              v-model="someVar"
              color="#828282"
              :fontSize="14"
              :value="34"
            >
              로그인 정보 저장
            </Checkbox>
          </div>
          <p>{{ logMessage }}</p>
          <div class="link-wrapper">회원이 아니신가요?</div>
        </fieldset>
      </form>
    </div>
  </div>
</template>

<script>
import { loginUser } from '@/api/index'
import Checkbox from 'vue-material-checkbox'
// import { validateEmail } from '@/utils/validation';

export default {
  name: 'LoginForm',
  components: {
    Checkbox,
  },
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
