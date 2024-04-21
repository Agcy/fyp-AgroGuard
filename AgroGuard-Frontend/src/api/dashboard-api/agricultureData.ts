import service from 'request'

// @ts-ignore
export function apiGetAgricultureData({region, commodity, year, item, period} = {}) {
  return service({
    url: `/dashboard/supply-demand/agriculture-data`,
    method: 'get',
    params: { region, commodity, year, item, period }
  })
}
