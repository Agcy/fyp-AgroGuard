import service from 'request'

export function apiGetChatRoomByParticipant(participantId: string) {
  return service({
    url: `/chatrooms/participant/${participantId}`,
    method: 'get'
  })
}
