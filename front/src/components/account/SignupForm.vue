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
          <p class="validation-text">
            <span class="warning">정확한 이메일 주소를 입력하세요.</span>
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
          <p class="validation-text">
            <span class="warning">
              영문과 특수문자 숫자를 포함하며 8자 이상이여야 합니다.
            </span>
          </p>
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
            <label for="interest" class="tit-interest">관심 분야</label>
            <div class="list-form">
              <div class="item-chip">
                <span class="tit-chip">#공포</span>
                <i
                  type="button"
                  class="icon-cancel"
                  aria-label="삭제"
                  @click="clickDelete"
                />
              </div>
              <div class="item-chip">
                <span class="tit-chip">#로맨틱코미디</span>
                <i
                  type="button"
                  class="icon-cancel"
                  aria-label="삭제"
                  @click="clickDelete"
                />
              </div>
              <div class="item-chip">
                <span class="tit-chip">#공포</span>
                <i
                  type="button"
                  class="icon-cancel"
                  aria-label="삭제"
                  @click="clickDelete"
                />
              </div>
              <div class="item-chip">
                <span class="tit-chip">#공포</span>
                <i
                  type="button"
                  class="icon-cancel"
                  aria-label="삭제"
                  @click="clickDelete"
                />
              </div>
              <div class="item-chip">
                <span class="tit-chip">#공포</span>
                <i
                  type="button"
                  class="icon-cancel"
                  aria-label="삭제"
                  @click="clickDelete"
                />
              </div>
              <div class="item-chip">
                <span class="tit-chip">#공포</span>
                <i
                  type="button"
                  class="icon-cancel"
                  aria-label="삭제"
                  @click="clickDelete"
                />
              </div>
              <div class="item-chip">
                <span class="tit-chip">#공포</span>
                <i
                  type="button"
                  class="icon-cancel"
                  aria-label="삭제"
                  @click="clickDelete"
                />
              </div>
            </div>
            <Dropdown
              :options="[
                { id: 1, name: 'Option 1' },
                { id: 2, name: 'Option 2' },
                { id: 3, name: 'Option 3' },
                { id: 4, name: 'Option 4' },
                { id: 5, name: 'Option 5' },
                { id: 6, name: 'Option 6' },
                { id: 7, name: 'Option 7' },
                { id: 8, name: 'Option 8' },
                { id: 9, name: 'Option 9' },
                { id: 10, name: 'Option 10' },
                { id: 11, name: 'Option 11' },
                { id: 12, name: 'Option 12' },
              ]"
              v-on:selected="validateSelection"
              v-on:filter="getDropdownValues"
              :disabled="false"
              name="zipcode"
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
import { registerUser } from '@/api/index'
import Dropdown from 'vue-simple-search-dropdown'

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
    clickDelete() {
      console.log('삭제됨')
    },
  },
}
</script>

<style lang="scss">
@import './scss/style.scss';
</style>
