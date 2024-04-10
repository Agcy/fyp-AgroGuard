import service from 'request'

export function apiGetDiseaseById(id: string) {
  return service({
    url: `/diseases/${id}`,
    method: 'get',
  })
}
