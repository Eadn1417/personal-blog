import request from '@/utils/request'

// 获取文章评论列表
export function getCommentList(articleId, params) {
  return request({
    url: `/api/articles/${articleId}/comments`,
    method: 'get',
    params
  })
}

// 发表评论
export function createComment(articleId, data) {
  return request({
    url: `/api/articles/${articleId}/comments`,
    method: 'post',
    data
  })
}

// 更新评论
export function updateComment(id, data) {
  return request({
    url: `/api/comments/${id}`,
    method: 'put',
    data
  })
}

// 删除评论
export function deleteComment(id) {
  return request({
    url: `/api/comments/${id}`,
    method: 'delete'
  })
}
