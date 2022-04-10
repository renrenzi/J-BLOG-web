<template>
  <div>
    <el-container>
      <el-header>
        <BlogHead/>
      </el-header>
      <el-main style="margin-top: 300px; width: 1000px;margin-left: 150px">
        <el-container>
          <el-main>
            <el-row>
              <el-col :span="1">
                <div>
                  <el-avatar :src="imgUrl" size=""></el-avatar>
                </div>
              </el-col>
              <el-col :span="23">
                <el-input
                    type="textarea"
                    :rows="6"
                    placeholder="请输入内容"
                    v-model="textarea"
                >
                </el-input>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2" push="19">
                <el-button type="info" @click="cleanReply()">取消留言</el-button>
              </el-col>
              <el-col  :span="2" push="20">
                <el-button type="primary" @click="create()">留言</el-button>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-divider content-position="left"
                ><i class="el-icon-chat-line-square"></i>全部评论
                </el-divider
                >
              </el-col>
            </el-row>
            <div v-for="comment in commentList">
              <el-row>
                <el-col :span="2">
                  <div>
                    <el-avatar :src="imgUrl"></el-avatar>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <span>{{ comment.commentator }}</span>
                  </div>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" :push="2">
                  <span>{{ comment.commentBody }}</span>
                </el-col>
                <el-col :span="4" :push="16">
                  <span>{{ comment.commentCreateTime | moment }}</span>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" :push="2">
                  <el-popover placement="bottom" width="400" trigger="click">
                    <el-input
                        type="textarea"
                        :rows="4"
                        placeholder="请输入内容"
                        v-model="replyBody"
                    >
                    </el-input>
                    <el-row>
                      <el-col :push="20" :span="2">
                        <el-button
                            type="primary"
                            @click="reply(comment.commentId)"
                        >回复
                        </el-button
                        >
                      </el-col>
                    </el-row>
                    <el-link slot="reference">回复</el-link>
                  </el-popover>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" :push="2">
                  <span>{{ comment.replyBody }}</span>
                </el-col>
                <el-col :span="4" :push="16">
                  <span>{{  comment.replyCreateTime | moment  }}</span>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
                  <el-divider content-position="left"></el-divider>
                </el-col>
              </el-row>
            </div>
          </el-main>
        </el-container>

      </el-main>
      <el-footer class="footCss" style="background-color: rgb(39, 53, 71);height: 120px">
        <BlogFooter/>
      </el-footer>
    </el-container>
  </div>

</template>
<script>
import BlogFooter from "../../components/blog/layout/BlogFooter";
import BlogHead from "../../components/blog/layout/BlogHead";
import {createComment, pageComment, replyComment} from "../../api/blogmanager/blogComment";
import qs from "qs";

export default {
  name: "MessageBoard",
  components: {BlogFooter, BlogHead},
  data() {
    return {
      linkList: [],
      imgUrl: this.$store.state.userUrl,
      textarea: '',
      commentList: [],
      replyBody: '',
    };
  },
  created() {
    this.getBlogInfo();
  },
  methods: {
    cleanReply(){
      this.textarea = "";
    },
    create() {
      const _this = this;
      createComment(
          qs.stringify({
            blogId: 33,
            commentBody: this.textarea,
          })
      ).then((res) => {
        if (res.code == 2000) {
          _this.$message({
            showClose: true,
            message: "评论成功",
            type: "success",
          });
         _this.getBlogInfo()
        } else if (res.code == 5002) {
          _this.$message({
            showClose: true,
            message: res.message,
            type: "warning",
          });
        } else {
        }
      });
    },
    getBlogInfo() {
      pageComment(
          qs.stringify({
            pageNum: 1,
            pageSize: 5,
            blogId: 33,
          })
      ).then((res) => {
        this.commentList = res.data;
      });
    },
    reply(commentId) {
      const _this = this;
      replyComment(
          qs.stringify({
            commentId: commentId,
            replyBody: this.replyBody,
          })
      ).then((res) => {
        if (res.code == 2000) {
          _this.$message({
            showClose: true,
            message: "回复成功",
            type: "success",
          });
          _this.getBlogInfo();
        } else if (res.code == 5002) {
          _this.$message({
            showClose: true,
            message: res.message,
            type: "warning",
          });
        } else {
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
