import service from 'request'

export function apiGetUserById(id: string) {
  return service({
    url: `/users/${id}`,
    method: 'get'
  })
}
