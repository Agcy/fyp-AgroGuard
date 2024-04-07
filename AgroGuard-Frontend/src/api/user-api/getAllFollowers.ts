import service from 'request'

export function apiGetAllFollowing(userId: string) {
  return service({
    url: `/users/${userId}/followers`,
    method: 'get'
  })
}
