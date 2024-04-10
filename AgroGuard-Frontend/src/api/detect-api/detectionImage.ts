import service from 'request'

export function apiDetection(formData: FormData) {
  return service({
    url: `/detection/detect`,
    method: 'post',
    data: formData,
  })
}
