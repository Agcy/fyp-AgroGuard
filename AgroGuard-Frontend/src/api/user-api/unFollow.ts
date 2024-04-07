import service from 'request'

export function apiUnFollow(userId: string, followUserId: string) {
  return service({
    url: `/users/${userId}/unfollow/${followUserId}`,
    method: 'post'
  })
}
