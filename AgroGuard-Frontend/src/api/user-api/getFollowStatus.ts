import service from 'request'

export function apiGetFollowStatus(userId: string, targetUserId: string) {
  return service({
    url: `/users/${userId}/follows-status`,
    method: 'get',
    params: {
      targetUserId
    }
  })
}
