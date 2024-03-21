import service from 'request'

export function apiAddNewComment(postId: string, content: string) {
  return service({
    url: '/comments/comments',
    method: 'post',
    data: { "postId": postId, "content": content }
  })
}
