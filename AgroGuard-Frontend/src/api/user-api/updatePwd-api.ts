import service from 'request'

export function apiReqReset(email: String) {
  return service({
    url: '/auth/forgot-password',
    method: 'post',
    data: { "email": email }
  })
}

export function apiReset(token: String, newPwd: String) {
  return service({
    url: '/auth/reset-password',
    method: 'post',
    params: { token }, // 通过URL参数发送token
    data: { newPassword: newPwd } // 在请求体中发送新密码
  })
}
