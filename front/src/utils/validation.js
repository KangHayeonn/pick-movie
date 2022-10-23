// ReDoS 에 취약한 문제 때문에 삭제
export const _isValidEmail = email => {
  const regex =
    /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/
  return email != 'undefined' && regex.test(email)
}

export const _isValidPassword = password => {
  const regex = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
  return (
    password != 'undefined' &&
    regex.test(password) &&
    password.search(/\s/) == -1 // 공백
  )
}
