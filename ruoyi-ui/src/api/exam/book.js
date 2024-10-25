import request from '@/utils/request'

/**
 * 题库详情
 */
export function nextQu(examId, quId) {
  return request({
    url: '/exam/api/user/wrong-book/next',
    method: 'post',
    data: {quId: quId, examId: examId}
  })
}

// 查询错题列表
export function listWrong(page, query) {
  return request({
    url: '/exam/api/user/wrong-book/paging',
    method: 'post',
    params: page,
    data: query
  })
}

