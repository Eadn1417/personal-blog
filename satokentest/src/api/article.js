import request from '@/utils/request'

// 获取文章列表
export function getArticleList(params) {
  return request({
    url: '/api/articles',
    method: 'get',
    params
  })
}

// 获取文章详情
export function getArticleDetail(id) {
  return request({
    url: `/api/articles/${id}`,
    method: 'get'
  })
}

// 创建文章
export function createArticle(data) {
  return request({
    url: '/api/articles',
    method: 'post',
    data
  })
}

// 更新文章
export function updateArticle(id, data) {
  return request({
    url: `/api/articles/${id}`,
    method: 'put',
    data
  })
}

// 删除文章
export function deleteArticle(id) {
  return request({
    url: `/api/articles/${id}`,
    method: 'delete'
  })
}

// 点赞文章
export function likeArticle(id) {
  return request({
    url: `/api/articles/${id}/like`,
    method: 'post'
  })
}

// 取消点赞
export function unlikeArticle(id) {
  return request({
    url: `/api/articles/${id}/like`,
    method: 'delete'
  })
}
