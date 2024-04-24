import service from 'request'

export function apiGetLikeStatus(userId: string, targetPostId: string) {
  return service({
    url: `/users/${userId}/follows-status`,
    method: 'get',
    params: {
      targetPostId
    }
  })
}
