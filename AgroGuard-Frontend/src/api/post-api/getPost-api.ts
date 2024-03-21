import service from 'request'

export function apiGetSinglePost(postId: string) {
  return service({
    url: `/posts/post/${postId}`,
    method: 'get'
  })
}
