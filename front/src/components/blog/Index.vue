<template>
  <el-container class="bodyCss" style="border: 1px solid rgb(255, 255, 255);">
    <el-aside width="75%" style="background-color: rgb(255, 255, 255);">
<!--      <el-button @click="drawer = true" icon="el-icon-more" circle type="primary">-->
<!--      </el-button>-->
      <el-drawer
          :with-header="false"
          :visible.sync="drawer"
          :direction="direction"
         >
        <el-container>
          <el-main>
            <h1>搜索文章</h1>
            <el-row>
              <el-col>
                <el-divider></el-divider>
              </el-col>
            </el-row>
            <el-row>
              <el-input placeholder="请输入内容" v-model="condition.keyWord">
                <i slot="suffix" class="el-input__icon el-icon-search" @click="searchByWord"></i>
              </el-input>
            </el-row>
            <el-row>
              <h1>热门标签</h1>
              <el-divider></el-divider>
            </el-row>
            <el-row :gutter="8">
              <el-button v-for="tag in tagList"  @click="searchByTag(tag.tagId)">{{tag.tagName}}()</el-button>
            </el-row>
            <el-row>
              <h1>最新发布</h1>
              <el-divider></el-divider>
            </el-row>
            <el-row>
              <el-link href="https://element.eleme.io" target="_blank">SSM整合Redis做缓存1</el-link>
            </el-row>
            <el-row>
              <h1>点击最多</h1>
              <el-divider></el-divider>
            </el-row>
            <el-row>
              <el-link href="https://element.eleme.io" target="_blank">SSM整合Redis做缓存41</el-link>
            </el-row>
          </el-main>

        </el-container>

      </el-drawer>
      <el-row v-for="blogInfo in blogInfoList">
        <el-row>
          <el-col :span="1" :offset="3">
            <el-avatar sha pe="square" :src="imageUrl" fit="cover"></el-avatar>
          </el-col>
          <el-col :span="5" :offset="1">
            <div class="grid-content bg-purple">
              任人子 in <el-link @click="searchByCategory(blogInfo.blogCategoryName)">
              {{blogInfo.blogCategoryName}} </el-link>
            </div>
          </el-col>
          <el-col :span="8" :offset="6">
            <div class="grid-content bg-purple">
              {{blogInfo.createTime | moment}}
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8" :offset="3">
            <div class="grid-content bg-purple">
              <el-link  @click="checkBlog(blogInfo.blogId)" style="font-size: 20px;">
                {{blogInfo.blogTitle}}
              </el-link>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8" :offset="3">
            <div class="grid-content bg-purple">
              {{blogInfo.blogPreface }}
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15" :offset="3">
            <el-divider></el-divider>
          </el-col>
        </el-row>
      </el-row>
      <!--         分页  -->
      <el-row>
        <el-row>
          <el-col :span="16" :offset="3">
            <div class="grid-content bg-purple">
              <el-pagination
                  background layout="total,sizes, prev, pager, next, jumper"
                  :total="totalSize"
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="condition.pageNum"
                  :page-sizes="[5, 15, 25, 35]"
                  :page-size="condition.pageSize"
              >
              </el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-row>
    </el-aside>
    <el-main style="background-color: rgb(255, 255, 255);">
      <h1>搜索文章</h1>
      <el-row>
        <el-col>
          <el-divider></el-divider>
        </el-col>
      </el-row>
      <el-row>
        <el-input placeholder="请输入内容" v-model="condition.keyWord">
          <i slot="suffix" class="el-input__icon el-icon-search" @click="searchByWord"></i>
        </el-input>
      </el-row>
      <el-row>
        <h1>热门标签</h1>
        <el-divider></el-divider>
      </el-row>
      <el-row :gutter="8">
        <el-button v-for="tag in tagList"  @click="searchByTag(tag.tagId)">{{tag.tagName}}()</el-button>
      </el-row>
      <el-row>
        <h1>最新发布</h1>
        <el-divider></el-divider>
      </el-row>
      <el-row>
        <div v-for="(blogInfo,index) in blogInfoList">
          <el-link v-if="index < 3" @click="checkBlog(blogInfo.blogId)">{{blogInfo.blogTitle}}</el-link>
          <br>
        </div>
      </el-row>
      <el-row>
        <h1>点击最多</h1>
        <el-divider></el-divider>
      </el-row>
      <el-row>
        <div v-for="(blogInfo,index) in blogInfoList">
          <el-link v-if="index < 1" @click="checkBlog(blogInfo.blogId)">{{blogInfo.blogTitle}}</el-link>
          <br>
        </div>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import {getBlogTagList} from "../../api/blogmanager/blogTag";
import {pageBlog} from "../../api/blogmanager/blog";
import qs from "qs";
import {pageBlogView} from "../../api/front/blogView";

export default {
  name: 'Index',
  props: {
    msg: String
  },
  data() {
    return {
      drawer: false,
      direction: 'ltr',
      loading: false,
      imgCarousel: {
        image1: this.$store.state.userUrl
      },
      imageUrl: this.$store.state.userUrl,
      tagList: [],
      condition: {
        pageNum: 0,
        pageSize: 5,
        tagId: '',
        keyWord: '',
        categoryName: this.$route.query.blogCategoryName
      },
      totalSize: 0,
      blogInfoList:[],
      index: 0
    };
  },
  created() {
    this.openFullScreen2();
    this.getTagList();
    this.handleCurrentChange(1);
    this.getMaxClick();
  },
  methods: {
    getMaxClick(){
      let blogInfoList = this.blogInfoList;
      let blogViews = 0;
      if (blogInfoList != null){
        for (let i = 0; i < blogInfoList; i++) {
          let blogInfo = blogInfoList[i];
          if (blogInfo.blogViews > blogViews){
            blogViews = blogInfo.blogViews;
            this.index = i;
          }
        }
      }
    },
    checkBlog(blogId){
      this.$router.push(
          {
            path: '/blogDetail',
            query: {
              blogId: blogId
            }
          })

    },
    searchByCategory(categoryName){
      this.condition.categoryName = categoryName;
      this.handleCurrentChange(1);
    },
    searchByWord(){
     this.handleCurrentChange(1);
     this.condition.keyWord = "";
    },
    searchByTag(id){
      this.condition.tagId = id;
      this.handleCurrentChange(1);
      this.condition.tagId = "";
    },
    handleSizeChange(val) {
      const _this = this;
      this.condition.pageSize = val;
      pageBlogView(qs.stringify(this.condition)).then(res => {
        _this.loading = false;
        this.totalSize = res.totalSize;
        this.blogInfoList = res.data;
      })
    },
    handleCurrentChange(val) {
      const _this = this;
      this.condition.pageNum = val;
      this.openFullScreen2();
      pageBlogView(qs.stringify(this.condition)).then(res => {
        _this.loading = false;
        this.totalSize = res.totalSize;
        this.blogInfoList = res.data;
      })
    },
    getTagList() {
      const _this = this;
      getBlogTagList().then(res => {
        _this.tagList = res;
      })
    },
    openFullScreen2() {
      const loading = this.$loading({
        lock: true,
        text: '正在拼命加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 500);
    }
  }
}

</script>
<style>
.headCss {
  height: 100px;
}

.footCss {

  background-color: #273547;
  color: #fff !important
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-container .el-header {
  padding: 0;
}

.el-container:nth-child(5) .el-main {
  padding: 0;
}

.el-container:nth-child(6) .el-footer {
  padding: 0;
}

.blog-text-center {
  text-align: center;
  font-size: 14px;
}
</style>
