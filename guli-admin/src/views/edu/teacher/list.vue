<template>
  <div class="app-container">
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
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
