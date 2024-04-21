import service from 'request'

// @ts-ignore
export function apiGePriceData({type, category} = {}) {
  return service({
    url: `/dashboard/price/agricultural-price`,
    method: 'get',
    params: { type, category }
  })
}
