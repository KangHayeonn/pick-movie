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
              v-on:filter="getDropdownValues"
              name="new"
              :disabled="false"
              :maxItem="20"
              placeholder="관심 분야 추가"
            >
            </Dropdown>
          </div>
          <button type="submit" class="submit-btn signup-btn">회원가입</button>
        </fieldset>
      </form>
    </div>
  </div>
</template>

<script>
import { v1GetTags } from '@/api/v1Movie.js'
import { createNamespacedHelpers } from 'vuex'
import Dropdown from 'vue-simple-search-dropdown'
import { _isValidEmail, _isValidPassword } from '@/utils/validation'

const { mapActions } = createNamespacedHelpers('auth')

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
      selected: { id: null, name: null },
      filter: '',
      interestOptions: [
        { id: 1, name: '액션' },
        { id: 2, name: '범죄' },
      ],
      isEmailError: false,
      isPwdError: false,
      isPwdChkError: false,
      errorMessage: '',
    }
  },
  async created() {
    this.offOpen()
    const result = await v1GetTags()

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
    ...mapActions(['registerUser', 'offOpen']),
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
          document.getElementById('username').focus()
          return
        }

        if (!_isValidPassword(userData.password)) {
          this.isPwdError = true
          this.isPwdError =
            '영문과 특수문자 숫자를 포함하며 8자 이상이여야 합니다.'
          document.getElementById('pw').focus()
          return
        }

        await this.registerUser(userData)
        this.initForm()

        alert('회원가입에 성공하였습니다.')
        this.$router.push({ name: 'Login' })
      } catch (error) {
        const errorCode = error.response.status

        if (errorCode === 401) {
          this.logMessage = '이미 존재하는 회원입니다.'
        } else if (errorCode === 402) {
          this.logMessage =
            '비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.'
        } else if (errorCode === 403) {
          this.logMessage = '이메일 형식을 유지해주세요.'
        } else if (errorCode === 405) {
          this.logMessage = '올바른 요청을 해주세요.'
        } else if (errorCode === 500) {
          this.logMessage = '서버에 연결할 수 없습니다.'
        } else console.log(error)

        this.initForm()
      }
    },
    initForm() {
      this.username = ''
      this.password = ''
      this.passwordCheck = ''
      this.errorMessage = ''
      this.interests = []
      this.selected = { id: null, name: null }
      this.isEmailError = false
      this.isPwdError = false
      this.isPwdChkError = false
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
    getDropdownValues(keyword) {},
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
