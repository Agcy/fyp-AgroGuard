import service from 'request'

export function apiUnLikePost(postId: string) {
  return service({
    url: `/users/unlike/${postId}`,
    method: 'get'
  })
}
