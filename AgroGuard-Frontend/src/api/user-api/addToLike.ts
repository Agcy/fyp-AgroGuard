import service from 'request'

export function apiAddToFavorite(postId: string) {
  return service({
    url: '/user/post/addToFavorite',
    method: 'post'
  })
}
