import request from '@/utils/request'

export default {
  //生成统计数据
  createStaData(day) {
    return request({
      url: `/edusta/sta/countRegister/` + day,
      method: 'get'
    })
  },
}
