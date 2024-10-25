import request from '@/utils/request'

/**
 * 创建试卷
 * @param data
 */
export function createPaper(data) {
  return request({
    url: '/exam/api/paper/paper/create-paper',
    method: 'post',
    data: data
  })
}

/**
 * 试卷详情
 * @param data
 */
export function paperDetail(data) {
  return request({
    url: '/exam/api/paper/paper/paper-detail',
    method: 'post',
    data: data
  })
}

/**
 * 题目详情
 * @param data
 */
export function quDetail(data) {
  return request({
    url: '/exam/api/paper/paper/qu-detail',
    method: 'post',
    data: data
  })
}

/**
 * 填充答案
 * @param data
 */
export function fillAnswer(data) {
  return request({
    url: '/exam/api/paper/paper/fill-answer',
    method: 'post',
    data: data
  })
}

/**
 * 交卷
 * @param data
 */
export function handExam(data) {
  return request({
    url: '/exam/api/paper/paper/hand-exam',
    method: 'post',
    data: data
  })
}

/**
 * 试卷详情
 * @param data
 */
export function paperResult(data) {
  return request({
    url: '/exam/api/paper/paper/paper-result',
    method: 'post',
    data: data
  })
}

/**
 * 错题训练
 * @param data
 */
export function training(data) {
  return request({
    url: '/exam/api/paper/paper/training',
    method: 'post',
    data: data
  })
}


/**
 * 检查是否有进行中的考试
 * @returns {*}
 */
export function checkProcess() {
  return request({
    url: '/exam/api/paper/paper/check-process',
    method: 'post',
    data: {}
  })
}
