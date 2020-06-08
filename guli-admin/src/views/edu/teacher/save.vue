<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import teacher from "../../../api/edu/teacher";

  export default {
    data() {
      return {
        teacher: {
          name: '',
          sort: 0,
          level: 1,
          career: '',
          intro: '',
          avatar: ''
        },
        saveBtnDisabled: false
      }
    },
    created() {
      this.init();
    },
    watch: {
      $route() {
        this.init();
      }
    },
    methods: {
      init(){
        //如果路径有id值，进行修改操作
        if (this.$route.params && this.$route.params.id) {
          //从路径中获取id值
          const id = this.$route.params.id
          //根据id进行查询
          this.getTeacherInfoById(id);
        }else{//路径中没有id，进行添加操作
          //清空表单
          this.teacher={};
        }
      },
      saveOrUpdate() {
        if(!this.teacher.id){
          this.saveTeacher();
        }else {
          this.updateTeacher();
        }

      },
      //添加讲师
      saveTeacher() {
        teacher.addTeacher(this.teacher).then(() => {
          //提示信息
          this.$message({
            type: 'success',
            message: '添加成功'
          });
          //回到列表页面
          this.$router.push({path: '/teacher/table'});
        })
      },

      //根据id获得讲师信息
      getTeacherInfoById(id) {
        teacher.getTeacherInfoById(id).then(response => {
          this.teacher = response.data.teacher;
        });
      },

      //修改讲师
      updateTeacher() {
        teacher.updateTeacher(this.teacher).then(() => {
          //提示信息
          this.$message({
            type: 'success',
            message: '修改成功'
          });
          //回到列表页面
          this.$router.push({path: '/teacher/table'});
        })
      }
    }
  }
</script>
