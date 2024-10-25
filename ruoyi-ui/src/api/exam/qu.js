import request from '@/utils/request'

// 查询试题列表
export function listRepo(page, query) {
  return request({
    url: '/exam/api/qu/page',
    method: 'post',
    params: page,
    data: query
  })
}

// 查询试题信息详细
export function getRepo(id) {
  return request({
    url: '/exam/api/qu/detail/' + id,
    method: 'get'
  })
}

// 新增试题信息
export function saveOrUpdateRepo(data) {
  return request({
    url: '/exam/api/qu/save',
    method: 'post',
    data: data
  })
}

// 修改试题信息
export function updateRepo(data) {
  return request({
    url: '/exam/api/qu',
    method: 'put',
    data: data
  })
}

// 删除试题信息
export function delRepo(ids) {
  return request({
    url: '/exam/api/qu/delete',
    method: 'delete',
    data: ids
  })
}
