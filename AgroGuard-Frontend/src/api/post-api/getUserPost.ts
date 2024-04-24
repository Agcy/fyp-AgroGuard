import service from 'request'

export function apiGetUserPosts(userId: string) {
  return service({
    url: `/posts/${userId}/getAll`,
    method: 'get',
  })
}
