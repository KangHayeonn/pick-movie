import axios from 'axios'

const instance = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  /*baseURL: 'http://192.168.0.67',*/
})

function callHome() {
  return instance.get('/api/v1/home')
}

function registerUser(userData) {
  console.log(userData)
  return instance.post('/api/v1/signup', userData)
}

function loginUser(userData) {
  return instance.post('/api/v1/login', userData)
}

function getTags() {
  return instance.get('/api/v1/showtags')
}

export { registerUser, loginUser, callHome, getTags }
