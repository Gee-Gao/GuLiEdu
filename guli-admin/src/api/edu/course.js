import request from '@/utils/request'

export default {
  //添加课程信息，
  addCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/course/addCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  //查询所有讲师
  getListTeacher() {
    return request({
      url: `/eduservice/teacher/findAll`,
      method: 'get'
    })
  },
  //根据课程id查询课程基本信息
  getCourseInfoById(id) {
    return request({
      url: `/eduservice/course/getCourseInfo/` + id,
      method: 'get'
    })
  },
  //修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/course/updateCourseInfo/`,
      method: 'post',
      data: courseInfo
    })
  },
  //课程确认信息显示
  getPublishCourseInfo(id) {
    return request({
      url: `/eduservice/course/getPublishCourseInfo/`+id,
      method: 'get',
    })
  },
  //课程最终信息
  publishCourse(id) {
    return request({
      url: `/eduservice/course/publishCourse/`+id,
      method: 'post',
    })
  },

  //课程列表
  getListCoursePage(page,limit,courseQuery) {
    return request({
      url: `/eduservice/course/pageCourseCondition/`+page+'/'+limit,
      method: 'post',
      data:courseQuery
    })
  },

  //删除课程
  deleteCourse(id) {
    return request({
      url: `/eduservice/course/`+id,
      method: 'delete',
    })
  },
}
