import request from '@/utils/request'

/**
 * 试卷列表
 * @param data
 */
export function listPaper(userId, examId) {
  return request({
    url: '/exam/api/paper/paper/paging',
    method: 'post',
    data: {pageNum: 1, pageSize: 5, userId: userId, examId: examId}
  })
}
