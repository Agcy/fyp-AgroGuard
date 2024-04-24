import service from 'request'

export function apiGetAllFollowers(userId: string) {
  return service({
    url: `/users/${userId}/followers`,
    method: 'get'
  })
}
