<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-button type="text" style="margin-left: 12.5%" @click="openChapterDialog()">添加章节</el-button>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 章节 -->
    <ul class="chanpterList">
      <li
        v-for="chapter in chapterVideoList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}

          <span class="acts">
                <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                <el-button type="text" @click="removeChapter(chapter.id)" >删除</el-button>
            </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>{{ video.title }}

            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button style="margin-left: 12%" @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>
  </div>
</template>

<script>
  import chapter from "../../../api/edu/chapter";

  export default {
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        courseId: "",//课程id
        chapterVideoList: [],
        dialogChapterFormVisible: false,
        chapter: {//章节对象
          title: '',
          sort: 0
        }
      }
    },

    created() {
      //获取地址栏的id值
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id;
      }
      this.getChapterVideo();
    },
    methods: {
      //删除章节
      removeChapter(chapterId){
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          chapter.deleteChapter(chapterId).then(()=>{
            //提示信息
            this.$message({
              type:'success',
              message:'删除成功'
            })
            //刷新页面
            this.getChapterVideo();
          })
          ;
        }).catch(response=>{
          if (response === 'cancel') {
          }
        })
      },

      //修改章节数据回显
      openEditChapter(chapterId) {
        //显示弹框
        this.dialogChapterFormVisible = true;
        //调用接口
        chapter.getChapter(chapterId).then(response => {
          this.chapter = response.data.chapter;
        });
      },

      //弹出添加章节页面
      openChapterDialog() {
        //显示弹框
        this.dialogChapterFormVisible = true;
        //表单数据清空
        this.chapter.title = "";
        this.chapter.sort = 0;

      },

      //添加章节
      addChapter() {
        //设置课程id到Chapter对象中
        this.chapter.courseId = this.courseId;
        chapter.addChapter(this.chapter).then(() => {
          //关闭弹框，
          this.dialogChapterFormVisible = false;
          //提示信息
          this.$message({
            type: 'success',
            message: `添加章节成功`
          });
          //刷新页面
          this.getChapterVideo();
        })
      },

      //修改章节
      updateChapter(){
        chapter.updateChapter(this.chapter).then(()=>{
          //关闭弹框，
          this.dialogChapterFormVisible = false;
          //提示信息
          this.$message({
            type: 'success',
            message: `修改章节成功`
          });
          //刷新页面
          this.getChapterVideo();
        })
      },

      saveOrUpdate() {
        if(!this.chapter.id){
          this.addChapter();
        }else {
          this.updateChapter();
        }

      },

      //根据课程id获取章节和小节列表，
      getChapterVideo() {
        chapter.getAllChapterVideo(this.courseId).then(response => {
          this.chapterVideoList = response.data.allChapterVideo
        });

      },

      previous() {
        this.$router.push({path: '/course/info/' + this.courseId})
      },

      next() {
        this.$router.push({path: '/course/publish/' + this.courseId})
      }
    }
  }
</script>

<style scoped>
  .chanpterList {
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }

  .chanpterList li {
    position: relative;
  }

  .chanpterList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }

  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList {
    padding-left: 50px;
  }

  .videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }

</style>
