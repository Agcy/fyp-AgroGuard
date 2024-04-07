import service from 'request'

export function apiAddToFollow(userId: string, followUserId: string) {
  return service({
    url: `/users/${userId}/follow/${followUserId}`,
    method: 'post'
  })
}
