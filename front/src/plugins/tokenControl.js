export const setAccessToken = token => {
  localStorage.setIem('accessToken', token)
}

export const getAccessToken = () => {
  return localStorage.getItem('accessToken')
}

export const setRefreshToken = token => {
  localStorage.setItem('refreshToken', token)
}

export const getRefreshToken = () => {
  return localStorage.getItem('refreshToken')
}

export const clearToken = () => {
  localStorage.removeItem('accessToken')
  localStorage.removeItem('refreshToken')
}
