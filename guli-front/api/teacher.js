import request from '@/utils/request'
export default {
  //分页讲师查询
  getTeacherListPageFront(page,limit) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
      method: 'post',
    })
  },
}
