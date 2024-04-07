import service from 'request'

export function apiGetChatRoomById(chatRoomId: string) {
  return service({
    url: `/chatrooms/${chatRoomId}`,
    method: 'get'
  })
}
