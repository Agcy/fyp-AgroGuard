import service from 'request'

export function apiGetAllDisease() {
  return service({
    url: `/diseases`,
    method: 'get',
  })
}
