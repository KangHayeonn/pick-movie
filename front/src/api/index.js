import axios from 'axios'
import { setInterceptors } from './common/interceptors'

// without token
const createInstance = () => {
  return axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
    /*baseURL: 'http://192.168.0.67',*/
    headers: {
      'Content-Type': 'application/json; charset=utf-8',
    },
  })
}

// with token
const createInstanceWithAuth = () => {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
    headers: {
      'Content-Type': 'application/json; charset=utf-8',
    },
  })
  return setInterceptors(instance)
}

export const instance = createInstance()
export const instanceWithToken = createInstanceWithAuth()
