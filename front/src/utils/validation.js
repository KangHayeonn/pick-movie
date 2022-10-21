// ReDoS 에 취약한 문제 때문에 삭제
export const _isValidEmail = email => {
  const regex =
    /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i
  return email != '' && email != 'undefined' && regex.test(email)
}

export const _isValidPassword = password => {
  const regex = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
  return (
    password != '' &&
    password != 'undefined' &&
    regex.test(password) &&
    password.search(/\s/) == -1 // 공백
  )
}
