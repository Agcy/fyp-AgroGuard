import service from 'request'

export function apiLikePost() {
  return service({
    url: `/users/liked-posts`,
    method: 'get'
  })
}
