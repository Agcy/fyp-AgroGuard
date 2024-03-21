import service from 'request'

export function apiAddNewPost(title: string, content: string, base64Imgs: string[]) {
  return service({
    url: `/posts/addPost`,
    method: 'post',
    data: { "title": title, "content": content, "base64Imgs": base64Imgs }
  })
}
