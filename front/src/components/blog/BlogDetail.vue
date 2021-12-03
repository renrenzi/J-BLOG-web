<template>
  <el-container >
    <el-header style="z-index: 10">
      <el-row>
        <el-col :span="8">
          <el-page-header @back="goBack" :content="blogInfo.blogTitle">
          </el-page-header>
        </el-col>
        <el-col :span="4" :pull="3">
          <el-divider direction="vertical"></el-divider>
          <el-link @click="getBlogByTag(blogInfo.blogCategoryName)">{{blogInfo.blogCategoryName}}</el-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-container>
        <el-main style="width: 1000px;margin-left: 180px">
          <el-row>
            <mavon-editor v-model="blogInfo.blogContent"
                          :subfield="false"
                          :editable="false"
                          defaultOpen="preview"
                          :toolbarsFlag="false"
                          fontSize="16px"
            ></mavon-editor>
          </el-row>
          </el-main>

        <el-footer style="width: 1000px;margin-left: 180px">
          <el-row>
            <div class="blog ">
              <p>本站文章除注明转载/出处外，皆为作者原创，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利。</p>
            </div>
          </el-row>
          <el-row v-if="blogInfo.blogId !== 33">
            <el-col :span="1">
              <div>
                <el-avatar :src="imgUrl"></el-avatar>
              </div>
            </el-col>
            <el-col :span="23">
              <el-input
                  type="textarea"
                  :rows="4"
                  placeholder="请输入内容"
                  v-model="textarea">
              </el-input>
            </el-col>
          </el-row>
          <el-row v-if="blogInfo.blogId !== 33">
            <el-col :push="22" :span="2">
              <el-button type="primary">评论</el-button>
            </el-col>
          </el-row>
          <el-row v-if="blogInfo.blogId !== 33">
            <el-col>
              <el-divider content-position="left"><i class="el-icon-chat-line-square"></i>全部评论</el-divider>
            </el-col>
          </el-row>
          <div v-for="comment in commentList" v-if="blogInfo.blogId !== 33">
            <el-row>
              <el-col :span="2">
                <div>
                  <el-avatar :src="imgUrl"></el-avatar>
                </div>
              </el-col>
              <el-col :span="4">
                <div>
                  <span>{{comment.commentator}}</span>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" :push="2">
                <span>{{comment.commentBody}}</span>
              </el-col>
              <el-col :span="4" :push="18">
                <span>{{comment.commentCreateTime | moment}}</span>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" :push="2">
                回复
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" :push="2">
                <span>{{comment.replyBody}}</span>
              </el-col>
              <el-col :span="4" :push="18">
                <span>{{comment.replyCreateTime | moment}}</span>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-divider content-position="left"></el-divider>
              </el-col>
            </el-row>
          </div>

        </el-footer>
      </el-container>
    </el-main>

  </el-container>
</template>

<script>
import {getBlogInfo} from "@/api/blogmanager/blog";
import qs from "qs";
import BlogComment from "./BlogComment";
import comment from 'bright-comment'
import {pageComment} from "@/api/blogmanager/blogComment";
import {blogDetail} from "@/api/front/blogView";
export default {
  name: "BlogDetail",
  components: {
    BlogComment,
    comment
  },
  data(){
    return {
      blogInfo:{},
      textarea: '',
      commentList: [],
      imgUrl: this.$store.state.userUrl
    }
  },
  created() {
    this.getBlogInfo();
  },
  methods:{

    getBlogByTag(blogCategoryName){
      this.$router.push({
        path: 'index',
        query: {
          blogCategoryName : blogCategoryName
        }
      })
    },
    goBack(){
      this.$router.push(
          {
            path: '/index',
          })
    },
    getBlogInfo(){
      blogDetail(qs.stringify({
        blogId: this.$route.query.blogId
      })).then(res => {
        this.blogInfo =   res.data;
      })
      pageComment(qs.stringify({
          pageNum: 1,
          pageSize: 5,
          blogId: this.$route.query.blogId
      })).then(res => {
          this.commentList = res.data;
      })
    }
  }
}
</script>

<style scoped>
.el-container{
  display: flex;
}
.blog{
  padding-top: 5px;
  padding-right: 10px;
  padding-bottom: 10px;
  padding-left: 15px;
  font-family: 微软雅黑;
  font-size: 12px;
}
</style>
