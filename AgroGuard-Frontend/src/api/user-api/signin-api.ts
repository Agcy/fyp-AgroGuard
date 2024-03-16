import service from 'request'

export function apiSignIn(userName: String, userPw: String) {
  return service({
    url: '/auth/signin',
    method: 'post',
    data: { "name": userName, "password": userPw }
  })
}
