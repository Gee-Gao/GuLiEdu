<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>

        <!--
         v-show：是否显示上传组件
         :key：类似于id，如果一个页面多个图片上传控件，可以做区分
         :url：后台上传的url地址
         @close：关闭上传组件
         @crop-upload-success：上传成功后的回调 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>

      </el-form-item>

      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" />
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



      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import teacher from "../../../api/edu/teacher";
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'

  export default {
    components:{ImageCropper,PanThumb},
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
        //上传组件是否显示
        imagecropperShow:false,
        //上传组件key值
        imagecropperKey:0,
        //获取dev.env.js的地址
        BASE_API:process.env.BASE_API,
        //保存按钮是否禁用
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
      //上传成功方法
      cropSuccess(data){
        this.teacher.avatar= data.url;
        this.close();
      },
      //关闭上传弹框
      close(){
        this.imagecropperShow=false;
        this.imagecropperKey=this.imagecropperKey+1;
      },
      //初始化方法
      init() {
        //如果路径有id值，进行修改操作
        if (this.$route.params && this.$route.params.id) {
          //从路径中获取id值
          const id = this.$route.params.id
          //根据id进行查询
          this.getTeacherInfoById(id);
        } else {//路径中没有id，进行添加操作
          //清空表单
          this.teacher = {};
        }
      },
      saveOrUpdate() {
        if (!this.teacher.id) {
          this.saveTeacher();
        } else {
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
