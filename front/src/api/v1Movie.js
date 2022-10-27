import { instance } from '@/api/index'
const prefix = '/api/v1'

/* 
function v1GetTags() {
  return instance.get(`${prefix}/showtags`)
}

export { v1GetTags }
*/

export const v1GetTags = async () => {
  try {
    const url = `${prefix}/showtags`
    const result = await instance.get(url)
    return result
  } catch (error) {
    return Promise.reject(error)
  }
}

// 아래 구조 is not undefined 에러 발생
/*
export default {
  async v1GetTags() {
    try {
      const url = `${prefix}/showtags`
      const result = await instance.get(url)
      return result
    } catch (error) {
      return Promise.reject(error)
    }
  },
}*/
