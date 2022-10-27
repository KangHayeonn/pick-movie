import Api from '@/api/v1Auth'
import {
  setAccessToken,
  setRefreshToken,
  clearToken,
} from '@/plugins/tokenControl'

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
  async registerUser({ commit }, params) {
    return Api.v1RegisterUser(params)
      .then(response => {
        return response
      })
      .catch(error => {
        return Promise.reject(error)
      })
  },
  async loginUser({ commit }, params) {
    return Api.v1LoginUser(params)
      .then(response => {
        return response
      })
      .catch(error => {
        return Promise.reject(error)
      })
  },
  logoutUser({ commit }) {
    clearToken()
  },
  getUserInfo({ commit }) {},
}

export default {
  state,
  getters,
  mutations,
  actions,
}
