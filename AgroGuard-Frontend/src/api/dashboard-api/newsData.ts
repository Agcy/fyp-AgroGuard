import service from 'request'

// @ts-ignore
export function apiGetNewsData() {
  return service({
    url: `/dashboard/news/agriculture-news-data`,
    method: 'get',
  })
}
