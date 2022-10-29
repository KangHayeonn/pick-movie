import Api from '@/api/v1Auth'
import {
  setAccessToken,
  setRefreshToken,
  clearToken,
} from '@/plugins/tokenControl'

const state = {
  isOpen: true,
  userInfo: {
    name: '',
  },
  isToken: false,
}

const getters = {
  getIsOpen: state => state.isOpen,
  getUserInfo: state => state.userInfo,
  getIsToken: state => state.isToken,
}

const mutations = {
  SET_ON_OPEN: (state, payload) => {
    state.isOpen = payload
  },
  SET_OFF_OPEN: (state, payload) => {
    state.isOpen = payload
  },
  SET_USER_INFO(state, payload) {
    state.userInfo = payload
  },
  SET_IS_TOKEN(state, payload) {
    state.isToken = payload
  },
}

const actions = {
  onOpen: ({ commit }) => {
    commit('SET_ON_OPEN', true)
  },
  offOpen: ({ commit }) => {
    commit('SET_OFF_OPEN', false)
  },
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
        setAccessToken(response.data.jwtAccessToken)
        setRefreshToken(response.data.jwtRefreshToken)
        commit('SET_IS_TOKEN', true)
        return response
      })
      .catch(error => {
        return Promise.reject(error)
      })
  },
  logoutUser({ commit }) {
    clearToken()
    commit('SET_IS_TOKEN', false)
  },
  getUserInfo({ commit }) {},
}

export default {
  state,
  getters,
  mutations,
  actions,
}
