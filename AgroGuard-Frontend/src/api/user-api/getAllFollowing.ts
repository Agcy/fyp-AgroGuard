import service from 'request'

export function apiGetAllFollowing(userId: string) {
  return service({
    url: `/users/${userId}/following`,
    method: 'get'
  })
}
