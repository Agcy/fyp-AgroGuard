import service from 'request'

// @ts-ignore
export function apiGeProjData({report_month, commodity, item} = {}) {
  return service({
    url: `/dashboard/supply-demand/agricultural-proj`,
    method: 'get',
    params: { report_month, commodity, item}
  })
}
