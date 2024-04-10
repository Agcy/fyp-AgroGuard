import service from 'request'

export function apiGetUserDetectionRecord(userId: string) {
  return service({
    url: `/detection/${userId}`,
    method: 'get',
  })
}
