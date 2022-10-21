const modules = {}
const requireModule = require.context('.', false, /\.js$/)

// 모듈 자동 생성
requireModule.keys().forEach(fileName => {
  if (fileName === './index.js') return
  const moduleName = fileName.replace(/(\.\/|\.js)/gi, '')
  modules[moduleName] = {
    namespaced: true,
    ...requireModule(fileName).default,
  }
})

export default modules
