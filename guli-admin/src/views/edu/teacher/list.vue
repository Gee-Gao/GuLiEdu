<template>
  <div class="app-container">
    讲师表单
  </div>
</template>

<script>
  import teacher from "@/api/edu/teacher";
  export default {
    data(){
      return{
        list:null,//查询接口返回的集合
        page:1,//当前页
        limit:10,//每页记录数
        total:0,//总记录数
        teacherQuery:{}//条件封装对象
      }

    },
    created() {
        this.getList();
    },
    methods: {
      //获取讲师列表
      getList() {
        teacher.getTeacherListPage(this.page, this.limit, this.teacherQuery).then(response =>{
         this.list=response.data.rows;
         this.total=response.data.total;
        }).catch(error=>console.log(error))
      }


    },
  }
</script>
