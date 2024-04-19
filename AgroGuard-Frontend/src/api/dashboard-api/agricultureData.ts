import service from 'request'

export function apiGetAgricultureData(code: string) {
  return service({
    url: `/dashboard/agriculture/data`,
    method: 'get',
    params: { code }
  })
}
