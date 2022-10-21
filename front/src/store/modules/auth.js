/*
import {
  setAccessToken,
  setRefreshToken,
  clearToken,
} from '@/plugins/tokenControl'*/

const state = {
  userInfo: {
    name: '',
  },
  token: {
    accessToken: '',
  },
}

const getters = {
  getUserInfo: state => state.userInfo,
  getToken: state => state.token,
}

const mutations = {
  SET_USER_INFO(state, payload) {
    state.userInfo = payload
  },
  SET_TOKEN(state, payload) {
    state.token = payload
  },
}

const actions = {
  authLogin({ commit }) {},
  authLogout({ commit }) {},
  authSignUp({ commit }) {},
  getUserInfo({ commit }) {},
}

export default {
  state,
  getters,
  mutations,
  actions,
}
