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
          <p class="validation-text">
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
          <p class="validation-text">
            <span class="warning">
              영문과 특수문자 숫자를 포함하며 8자 이상이여야 합니다.
            </span>
          </p>
          <button type="submit" :disabled="!isUsernameValid || !password">
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
.item-choice {
  font-size: 14px;
  color: #e4e4e4;
}
.m-chckbox--group {
  border: 2px solid #828282;
}
.link-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 54px;
  font-size: 17px;
}
input:invalid {
  border-color: red;
}
</style>
