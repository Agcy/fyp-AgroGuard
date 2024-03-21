import service from 'request'

export function apiGetAllPost() {
  return service({
    url: '/posts/getAll',
    method: 'get'
  })
}
