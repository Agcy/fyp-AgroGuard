import service from 'request'

export function apiGetMutualFollowing(userId: string) {
  return service({
    url: `/users/${userId}/mutual-follows`,
    method: 'get'
  })
}
