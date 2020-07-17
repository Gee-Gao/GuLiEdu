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

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video/uploadAliVideo'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
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
                <el-button style="" type="text" @click="openVideo(chapter.id)">添加小节</el-button>
                <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
            </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                <el-button style="" type="text" @click="openEditVideo(video.id)">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
              </span>
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
  import video from "../../../api/edu/video";

  export default {
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        courseId: "",//课程id
        chapterVideoList: [],
        dialogChapterFormVisible: false,
        dialogVideoFormVisible: false,
        chapter: {//章节对象
          title: '',
          sort: 0
        },
        video: {//小节对象
          title: '',
          sort: 0,
          free: 0,
          videoSourceId: '',
          videoOriginalName: ''
        },
        fileList: [],//上传文件列表
        BASE_API: process.env.BASE_API // 接口API地址
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
      //点击x时
      beforeVodRemove(file) {
        return this.$confirm(`确实删除 ${file.name}?`);
      },
      //点击确定删除
      handleVodRemove() {

      },

      //上传之前
      handleUploadExceed() {
        this.$message.warning('每个小节只能上传一个视频,想要重新上传视频，请先删除已上传的视频')
      },
      //上传成功
      handleVodUploadSuccess(response, file) {
        //上传之后的视频id
        this.video.videoSourceId = response.data.videoId;
        //上传之后的文件名
        this.video.videoOriginalName = file.name;
      },

      /********小节操作*******/
      //删除小节
      removeVideo(videoId) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          video.deleteVideo(videoId).then(() => {
            //提示信息
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            //刷新页面
            this.getChapterVideo();
          })
          ;
        }).catch(response => {
          if (response === 'cancel') {
          }
        })
      },

      //修改小节
      updateVideo() {
        video.updateVideo(this.video).then(() => {
          //关闭弹框，
          this.dialogVideoFormVisible = false;
          //提示信息
          this.$message({
            type: 'success',
            message: `修改小节成功`
          });
          //刷新页面
          this.getChapterVideo();
        })
      },

      //修改小节数据回显
      openEditVideo(videoId) {
        this.dialogVideoFormVisible = true;
        //调用接口
        video.getVideo(videoId).then(response => {
          this.video = response.data.video;
        });
      },

      //添加小节弹框
      openVideo(chapterId) {
        this.dialogVideoFormVisible = true;
        //清空表单
        this.video = {};
        this.fileList = [];
        //设置章节id
        this.video.chapterId = chapterId;
      },
      //添加小节
      addVideo() {
        //设置课程id
        this.video.courseId = this.courseId;
        video.addVideo(this.video).then(() => {
          //关闭弹框，
          this.dialogVideoFormVisible = false;
          //提示信息
          this.$message({
            type: 'success',
            message: `添加小节成功`
          });
          //刷新页面
          this.getChapterVideo();
        })
      },

      saveOrUpdateVideo() {
        if (!this.video.id) {
          this.addVideo()
        } else {
          this.updateVideo()
        }

      },
      /********章节操作*******/
      //删除章节
      removeChapter(chapterId) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          chapter.deleteChapter(chapterId).then(() => {
            //提示信息
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            //刷新页面
            this.getChapterVideo();
          })
          ;
        }).catch(response => {
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
      updateChapter() {
        chapter.updateChapter(this.chapter).then(() => {
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
        if (!this.chapter.id) {
          this.addChapter();
        } else {
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
