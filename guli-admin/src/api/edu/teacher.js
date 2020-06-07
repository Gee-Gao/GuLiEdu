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
  }
}
