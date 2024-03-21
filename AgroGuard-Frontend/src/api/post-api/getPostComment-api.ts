import service from 'request'

export function apiGetPostComment(postId: string) {
  return service({
    url: `/comments/post/${postId}`,
    method: 'get'
  })
}
