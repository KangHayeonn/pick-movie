import { getAccessToken } from '@/plugins/tokenControl'

export const setInterceptors = instance => {
  instance.interceptors.request.use(
    config => {
      // 토큰을 헤더에 추가
      const token = getAccessToken()

      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`
      }

      return config
    },
    error => {
      return Promise.reject(error)
    },
  )

  // TODO 에러처리
  // instance.interceptors.response.use()

  return instance
}
