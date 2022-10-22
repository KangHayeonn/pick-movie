<template>
  <div class="contents">
    <div class="form-wrapper form-wrapper-sm">
      <form @submit.prevent="submitForm">
        <fieldset>
          <legend>Sign Up | 회원가입</legend>
          <div>
            <label for="username" class="screen_out">id: </label>
            <input
              type="text"
              id="username"
              name="username"
              v-model="username"
              placeholder="이메일 주소"
              class="form-input"
              autocomplete="off"
            />
          </div>
          <!--
          <p v-if="isEmailError" class="validation-text">
            <span class="warning">중복 아이디가 있습니다.</span>
          </p>
          -->
          <p v-if="isEmailError" class="validation-text">
            <span class="warning">정확한 이메일 주소를 입력하세요.</span>
          </p>
          <div>
            <label for="pw" class="screen_out">비밀번호: </label>
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
          <p v-if="isPwdError" class="validation-text">
            <span class="warning">
              영문과 특수문자 숫자를 포함하며 8자 이상이여야 합니다.
            </span>
          </p>
          <div>
            <label for="pwChk" class="screen_out">비밀번호 확인: </label>
            <input
              type="password"
              id="pwChk"
              name="pwChk"
              v-model="passwordCheck"
              placeholder="비밀번호 확인"
              class="form-input"
              autocomplete="off"
            />
          </div>
          <p v-if="isPwdChkError" class="validation-text">
            <span class="warning">비밀번호가 다릅니다.</span>
          </p>
          <div class="input-form">
            <label for="interest" class="tit-interest">관심 분야</label>
            <div class="list-form">
              <div
                v-for="(interest, index) in interests"
                class="item-chip"
                :key="index"
              >
                <span class="tit-chip">#{{ interest }}</span>
                <i
                  type="button"
                  class="icon-cancel"
                  aria-label="삭제"
                  @click="clickDelete(index)"
                />
              </div>
            </div>
            <Dropdown
              :options="interestOptions"
              v-on:selected="validateSelection"
              :disabled="false"
              :maxItem="20"
              placeholder="관심 분야 추가"
            >
            </Dropdown>
          </div>
          <button type="submit" class="submit-btn signup-btn">회원가입</button>
          <p>{{ logMessage }}</p>
        </fieldset>
      </form>
    </div>
  </div>
</template>

<script>
import { registerUser, getTags } from '@/api/index'
import Dropdown from 'vue-simple-search-dropdown'
import { _isValidEmail, _isValidPassword } from '@/utils/validation'

export default {
  components: {
    Dropdown,
  },
  data() {
    return {
      // form values
      username: '',
      password: '',
      passwordCheck: '',
      interests: [],
      // log
      logMessage: '',
      selected: {},
      filter: '',
      interestOptions: [
        { id: 1, name: '액션' },
        { id: 2, name: '범죄' },
        { id: 3, name: 'SF' },
        { id: 4, name: '코미디' },
        { id: 5, name: '로맨스' },
        { id: 6, name: '스릴러' },
        { id: 7, name: '공포' },
        { id: 8, name: '전쟁' },
        { id: 9, name: '스포츠' },
        { id: 10, name: '판타지' },
        { id: 11, name: '음악' },
        { id: 12, name: '뮤지컬' },
        { id: 13, name: '멜로' },
        { id: 14, name: '드라마' },
      ],
      isEmailError: false,
      isPwdError: false,
      isPwdChkError: false,
      errorMessage: '',
    }
  },
  async created() {
    const result = await getTags()
    const tmpArr = []

    result.data.forEach(e => {
      tmpArr.push({
        id: e.id,
        name: e.tag,
      })
    })

    this.interestOptions = [...tmpArr]
  },
  watch: {
    username: {
      deep: true,
      handler() {
        if (!_isValidEmail(this.username)) {
          this.isEmailError = true
          this.errorMessage = '정확한 이메일 주소를 입력하세요.'
        } else {
          this.isEmailError = false
        }
      },
    },
    password: {
      deep: true,
      handler() {
        if (!_isValidPassword(this.password)) {
          this.isPwdError = true
          this.isPwdError =
            '영문과 특수문자 숫자를 포함하며 8자 이상이여야 합니다.'
        } else {
          this.isPwdError = false
        }
      },
    },
    passwordCheck: {
      deep: true,
      handler() {
        if (this.password != this.passwordCheck) {
          this.isPwdChkError = true
          this.errorMessage = '비밀번호가 다릅니다.'
        } else {
          this.isPwdChkError = false
        }
      },
    },
  },
  methods: {
    async submitForm() {
      try {
        const userData = {
          username: this.username,
          password: this.password,
          tags: this.interests,
        }

        if (!_isValidEmail(userData.username)) {
          this.isEmailError = true
          this.errorMessage = '정확한 이메일 주소를 입력하세요.'
          return
        }

        if (!_isValidPassword(userData.password)) {
          this.isPwdError = true
          this.isPwdError =
            '영문과 특수문자 숫자를 포함하며 8자 이상이여야 합니다.'
          return
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
      this.passwordCheck = ''
      this.isEmailError = false
      this.isPwdError = false
      this.isPwdChkError = false
      this.errorMessage = ''
    },
    clickDelete(idx) {
      this.interests.splice(idx, 1)
    },
    validateSelection(selection) {
      if (this.isEmpty(selection.name)) return

      this.selected = selection

      if (!this.interests.includes(selection.name)) {
        this.interests.push(selection.name)
      }
    },
    isEmpty(str) {
      if (typeof str == 'undefined' || str == null || str == '') return true
      else return false
    },
  },
}
</script>

<style lang="scss">
@import './scss/style.scss';
</style>
