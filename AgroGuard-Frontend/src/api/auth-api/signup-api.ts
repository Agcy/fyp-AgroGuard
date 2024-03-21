import service from 'request'

export function apiSignUp(userName: String, gender: String, occupation: String, userEmail: String, userPw: String) {
  return service({
    url: '/auth/signup',
    method: 'post',
    data: { "name": userName, "gender": gender, "occupation": occupation, "email": userEmail, "password": userPw }
  })
}
