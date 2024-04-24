import service from 'request'

export function apiLikePost(postId: string) {
  return service({
    url: `/users/like/${postId}`,
    method: 'get'
  })
}
