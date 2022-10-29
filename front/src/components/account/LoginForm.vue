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
          <p v-if="!isUsernameValid" class="validation-text">
            <span class="warning">정확한 이메일 주소를 입력하세요.</span>
          </p>
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
          <p v-if="!isPasswordValid" class="validation-text">
            <span class="warning">
              영문과 특수문자 숫자를 포함하며 8자 이상이여야 합니다.
            </span>
          </p>
          <button
            type="submit"
            class="submit-btn"
            :disabled="!isUsernameValid || !isPasswordValid"
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
          <div class="link-wrapper">
            <button type="button" class="btn-link" @click="goSignup">
              회원이 아니신가요?
            </button>
          </div>
        </fieldset>
      </form>
    </div>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import Checkbox from 'vue-material-checkbox'
import { _isValidEmail, _isValidPassword } from '@/utils/validation'

const { mapActions } = createNamespacedHelpers('auth')

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
      someVar: false,
      isEmailError: false,
      isPwdError: false,
      errorMessage: '',
    }
  },
  created() {
    this.offOpen()
  },
  computed: {
    isUsernameValid() {
      if (this.username === '') return true
      return _isValidEmail(this.username)
    },
    isPasswordValid() {
      if (this.password === '') return true
      return _isValidPassword(this.password)
    },
  },
  watch: {
    someVar: {
      deep: true,
      handler() {
        // console.log(this.someVar)
      },
    },
    username: {
      deep: true,
      handler() {
        this.isEmailError = false
        this.errorMessage = ''
      },
    },
    password: {
      deep: true,
      handler() {
        this.isPwdError = false
        this.errorMessage = ''
      },
    },
  },
  methods: {
    ...mapActions(['loginUser', 'offOpen']),
    async submitForm() {
      try {
        const userData = {
          username: this.username,
          password: this.password,
        }

        if (!_isValidEmail(userData.username)) {
          this.isEmailError = true
          this.errorMessage = '정확한 이메일 주소를 입력하세요.'
          document.getElementById('id').focus()
          return
        }

        if (!_isValidPassword(userData.password)) {
          this.isPwdError = true
          this.isPwdError =
            '영문과 특수문자 숫자를 포함하며 8자 이상이여야 합니다.'
          document.getElementById('pw').focus()
          return
        }

        await this.loginUser(userData)
        this.initForm()

        alert('로그인에 성공하였습니다.')
        this.$router.push({ name: 'Main' })
      } catch (error) {
        console.log(error)
        this.initForm()
      }
    },
    initForm() {
      this.username = ''
      this.password = ''
      this.errorMessage = ''
      this.isEmailError = false
      this.isPwdError = false
    },
    goSignup() {
      this.$router.push({ name: 'Signup' })
    },
  },
}
</script>

<style lang="scss">
@import './scss/style.scss';
</style>
