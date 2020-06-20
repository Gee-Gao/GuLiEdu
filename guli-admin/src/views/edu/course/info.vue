<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类 -->
      <el-form-item label="课程分类">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类" @change="subjectOneListChanged">
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>

        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.value"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>
      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介 TODO -->
      <el-form-item label="课程简介">
        <el-input v-model="courseInfo.description" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 课程封面-->
      <el-form-item label="课程封面">

        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/fileoss'"
          class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>

      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/>
        元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import course from "../../../api/edu/course";
  import subject from "../../../api/edu/subject";

  export default {
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        courseInfo: {
          title: '',
          subjectParentId: '',
          subjectId: '',
          teacherId: '',
          lessonNum: 0,
          description: '',
          cover: '/static/1.png',
          price: 0
        },
        teacherList: [],
        subjectOneList: [],
        subjectTwoList: [],
        BASE_API: process.env.BASE_API // 接口API地址
      }
    },

    created() {
      //初始化所有讲师
      this.getListTeacher();
      //初始化一级分类
      this.getOneSubject();
    },

    methods: {
      handleAvatarSuccess(res) {
        this.courseInfo.cover = res.data.url
      },

      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isPNG = file.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;
        let isPicture = false;
        if (!isJPG && !isPNG) {
          this.$message.error('上传头像图片只能是JPG格式或PNG格式!')
        }else {
          isPicture = true;
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }

        return isPicture && isLt2M;
      },


      //切换一级分类，显示对应二级分类
      subjectOneListChanged(subjectOneId) {
        //遍历所有的分类，包含一级和二级
        for (let i = 0; i < this.subjectOneList.length; i++) {
          //每个一级分类
          let oneSubject = this.subjectOneList[i];
          if (subjectOneId == oneSubject.id) {
            //从一级分类获取所有的二级分类
            this.subjectTwoList = oneSubject.children;
            //把二级分类id值清空
            this.courseInfo.subjectId = "";
          }

        }
      },
      //查询所有一级分类
      getOneSubject() {
        subject.getSubjectList().then(response => {
          this.subjectOneList = response.data.list
        })
      },
      //查询所有讲师
      getListTeacher() {
        course.getListTeacher().then(response => {
          this.teacherList = response.data.items;
        })
      },
      saveOrUpdate() {
        course.addCourseInfo(this.courseInfo).then(response => {
          //提示信息
          this.loading = false
          this.$message({
            type: 'success',
            message: `添加课程信息成功`
          })
          //跳转到第二步
          this.$router.push({path: '/course/chapter/' + response.data.courseId})
        })

      },
    }
  }

</script>
