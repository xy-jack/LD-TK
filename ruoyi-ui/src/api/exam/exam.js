import request from '@/utils/request'

// 查询题库信息列表
export function listRepo(query) {
  return request({
    url: '/exam/api/exam/exam/page',
    method: 'post',
    params: query
  })
}

// 查询题库信息详细
/*export function getRepo(id) {
  return request({
    url: '/exam/api/exam/exam/detail/' + id,
    method: 'get'
  })
}*/

// 新增题库信息
export function saveOrUpdateRepo(data) {
  return request({
    url: '/exam/api/exam/exam/save',
    method: 'post',
    data: data
  })
}

// 修改题库信息
export function updateRepo(data) {
  return request({
    url: '/exam/api/exam/exam/repo',
    method: 'put',
    data: data
  })
}

// 删除题库信息
export function delRepo(ids) {
  return request({
    url: '/exam/api/exam/exam/delete',
    method: 'delete',
    data: ids
  })
}

// 题库详情
export function fetchDetail(id) {
  return request({
    url: '/exam/api/exam/exam/detail/' + id,
    method: 'get'
  })
}
