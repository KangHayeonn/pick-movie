import { instance } from '@/api/index'
const prefix = '/api/v1'

const Auth = {
  async v1RegisterUser(userData) {
    try {
      const url = `${prefix}/signup`
      const result = await instance.post(url, userData)
      return result
    } catch (error) {
      return Promise.reject(error)
    }
  },
  async v1LoginUser(userData) {
    try {
      const url = `${prefix}/login`
      const result = await instance.post(url, userData)
      return result
    } catch (error) {
      return Promise.reject(error)
    }
  },
}

export default Auth
