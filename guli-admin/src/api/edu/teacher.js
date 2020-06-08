import request from '@/utils/request'
export default {
  //获取讲师列表，
  //current当前页，limit每页记录数，teacherQuery查询条件对象
  getTeacherListPage(current,limit,teacherQuery){
    return request({
      url: `/edu/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      //data表示使用json传递到接口
      data: teacherQuery
    })
  },

  //删除讲师
  deleteTeacherById(id){
    return request({
      url: `/edu/teacher/${id}`,
      method: 'delete',
    })
  },

  //添加讲师
  addTeacher(teacher){
    return request({
      url: `/edu/teacher/addTeacher`,
      method: 'post',
      data:teacher
    })
  },

  //根据id获得讲师信息
  getTeacherInfoById(id){
    return request({
      url: `/edu/teacher/getTeacher/${id}`,
      method: 'get'
    })
  },

  //修改讲师
  updateTeacher(teacher){
    return request({
      url: `/edu/teacher/updateTeacher`,
      method: 'post',
      data:teacher
    })
  }

}
