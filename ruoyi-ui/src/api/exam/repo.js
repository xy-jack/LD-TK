import request from '@/utils/request'

// 查询题库信息列表
export function listRepo(page, query) {
  return request({
    url: '/exam/api/repo/page',
    method: 'post',
    params: page,
    data: query
  })
}

// 查询题库信息详细
export function getRepo(id) {
  return request({
    url: '/exam/api/repo/detail/' + id,
    method: 'get'
  })
}

// 新增题库信息
export function saveOrUpdateRepo(data) {
  return request({
    url: '/exam/api/repo/save',
    method: 'post',
    data: data
  })
}

// 修改题库信息
export function updateRepo(data) {
  return request({
    url: '/exam/api/repo',
    method: 'put',
    data: data
  })
}

// 删除题库信息
export function delRepo(ids) {
  return request({
    url: '/exam/api/repo/delete',
    method: 'delete',
    data: ids
  })
}
