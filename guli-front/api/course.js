import request from '@/utils/request'
export default {
  //条件分页课程查询
  getCourseListPageFront(page,limit,serach) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data:serach
    })
  },
  //查询所有一级分类
  getAllSubject(){
    return request({
      url: `/eduservice/subject/getAllSubject`,
      method: 'get',
    })
  },
  //查询课程详情
  getCourseInfo(courseId){
    return request({
      url: `eduservice/coursefront/getFrontCourseInfo/${courseId}`,
      method: 'get',
    })
  }
}
