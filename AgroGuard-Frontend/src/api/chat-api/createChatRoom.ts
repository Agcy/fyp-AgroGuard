import service from 'request'

export function apiCreateChatRoom(name: string, participants: string[]) {
  return service({
    url: `/chatrooms/create`,
    method: 'post',
    data: { "name": name, "participants": participants }
  })
}
