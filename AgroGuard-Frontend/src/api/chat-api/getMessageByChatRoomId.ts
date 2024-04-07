import service from 'request'

export function apiGetMessageByChatRoomId(chatRoomId: string) {
  return service({
    url: `/chatrooms/${chatRoomId}/messages`,
    method: 'get'
  })
}
