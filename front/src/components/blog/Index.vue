<template>
  <el-container class="bodyCss" style="border: 1px solid rgb(255, 255, 255);">
    <el-aside width="75%" style="background-color: rgb(255, 255, 255);">
      <!--      <el-button @click="drawer = true" icon="el-icon-more" circle type="primary">-->
      <!--      </el-button>-->
<!--      <el-drawer
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
              <h2>热门标签</h2>
              <el-divider></el-divider>
            </el-row>
            <el-row>
              <el-button  v-for="tag in tagList" @click="searchByTag(tag.tagId)">{{ tag.tagName }}</el-button>
            </el-row>
            <el-row>
              <h2>最新发布</h2>
              <el-divider></el-divider>
            </el-row>
            <el-row>
              <el-link href="https://element.eleme.io" target="_blank">SSM整合Redis做缓存1</el-link>
            </el-row>
            <el-row>
              <h2>点击最多</h2>
              <el-divider></el-divider>
            </el-row>
            <el-row>
              <el-link href="https://element.eleme.io" target="_blank">SSM整合Redis做缓存41</el-link>
            </el-row>
          </el-main>

        </el-container>

      </el-drawer>-->
      <el-row v-for="blogInfo in blogInfoList">
        <el-row>
          <el-col :span="1" :offset="3">
            <el-avatar sha pe="square" :src="imageUrl" fit="cover"></el-avatar>
          </el-col>
          <el-col :span="5" :offset="1">
            <div class="grid-content bg-purple">
              任人子 in
              <el-link @click="searchByCategory(blogInfo.blogCategoryName)">
                {{ blogInfo.blogCategoryName }}
              </el-link>
            </div>
          </el-col>
          <el-col :span="8" :offset="6">
            <div class="grid-content bg-purple">
              {{ blogInfo.createTime | moment }}
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" :offset="3">
            <div class="grid-content bg-purple">
              <el-link @click="checkBlog(blogInfo.blogId)" style="font-size: 20px;">
                {{ blogInfo.blogTitle }}
              </el-link>
            </div>
          </el-col>
          <el-col :span="3" :offset="1">
            <i class="el-icon-view">{{ blogInfo.blogViews }}</i> 浏览量
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" :offset="3">
            <div class="grid-content bg-purple">
              {{ blogInfo.blogPreface }}
            </div>
          </el-col>
          <el-col :span="3" :offset="1">
            <svg t="1649343941014" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2695" width="16" height="16"><path d="M939.358251 423.424662c-23.01825-37.252439-62.924121-60.779272-107.019409-63.209624-2.755764-0.38681-5.510504-0.579191-8.347109-0.579191l-152.202471-0.121773c6.649444-28.975938 10.015098-58.653865 10.015098-88.657202 0-28.223808-3.213181-57.139372-9.556657-85.952604-0.447185-2.043542-1.098008-4.006244-1.932002-5.866614-15.820314-57.302077-67.37755-96.841605-127.282918-96.841605-72.827679 0-132.081201 59.254545-132.081201 132.081201 0 3.334955 0.132006 6.66991 0.406253 10.015098-2.196015 57.211003-32.108279 109.947088-80.269162 141.363611-14.447037 9.42465-18.524912 28.773324-9.099239 43.220361 9.414417 14.437827 28.752858 18.535145 43.220361 9.099239 65.811892-42.925648 106.429984-115.325585 108.656699-193.684234 0.030699-1.332345-0.010233-2.663666-0.14224-3.996011-0.203638-2.012843-0.304945-4.016477-0.304945-6.019087 0-38.381146 31.233352-69.614497 69.614497-69.614497 32.57593 0 60.474326 22.204721 67.824735 53.997821 0.356111 1.534959 0.823761 3.019777 1.402953 4.453429 4.696975 22.814612 7.076162 45.579081 7.076162 67.743894 0 37.485753-6.222725 74.352405-18.494213 109.592001-3.324722 9.546424-1.819438 20.111037 4.02671 28.345582 5.856381 8.245801 15.332197 13.146415 25.448602 13.156648l193.226816 0.101307c1.423419 0.264013 2.857071 0.426719 4.300956 0.477884 24.116257 0.9967 45.935192 13.614066 58.603723 34.090423 7.838525 12.31242 11.438517 26.800389 10.431583 41.939181-0.080841 0.945535-0.121773 1.911536-0.11154 2.877537 0 0.854461 0.040932 1.697665 0.11154 2.53166 0.010233 0.335644-0.030699 0.661056-0.11154 0.976234-0.101307 0.376577-0.193405 0.772596-0.284479 1.159406l-74.972529 330.391802c-0.914836 1.281179-1.738597 2.6432-2.449795 4.046153-5.937223 11.762905-14.660908 21.48329-25.346271 28.172643-10.746762 6.812149-23.059182 10.614755-35.757388 11.06194-0.854461-0.061398-513.766226-0.224104-513.766226-0.224104-0.467651-0.020466-0.935302-0.030699-1.402953-0.030699 0 0-111.01542 0.172939-112.718201 0.457418-1.932002 0-3.446495-1.50426-3.446495-3.415796l0.299829-416.334173c0-1.901303 1.545192-3.446495 3.01466-3.456728l1.245364 0.121773c1.174756 0.132006 2.653433 0.284479 3.52836 0.193405l83.82822-0.222057 0 339.367221c0 17.253966 13.979386 31.233352 31.233352 31.233352s31.233352-13.979386 31.233352-31.233352L281.009092 435.350273c0-1.778506 0-8.631588 0-10.411117 0-17.253966-13.979386-30.928407-31.233352-30.928407-1.50426 0-117.547183 0.304945-119.402437 0.304945-36.34272 0-65.913199 29.566386-65.913199 65.893756l-0.299829 416.334173c0 36.337603 29.571503 65.902966 65.913199 65.902966 2.541893 0 111.406323-0.457418 111.406323-0.457418 0.457418 0.020466 0.925069 0.030699 1.382487 0.030699 0 0 511.458671 0.274246 512.505513 0.274246 25.469068 0 50.296523-7.197936 71.647807-20.73116 19.612687-12.281721 35.777855-29.881564 46.839795-50.967812 3.660366-5.622044 6.435573-11.875468 8.256034-18.615986 0.11154-0.416486 0.213871-0.823761 0.304945-1.240247l74.881454-330.340637c1.596358-6.212492 2.257413-12.586666 2.00261-18.992563C960.892707 473.366098 953.948551 446.331371 939.358251 423.424662z" p-id="2696"></path><path d="M450.027553 518.650467c-17.253966 0-31.233352 13.979386-31.233352 31.233352l0 30.470989c0 17.253966 13.979386 31.233352 31.233352 31.233352 17.253966 0 31.233352-13.979386 31.233352-31.233352l0-30.470989C481.260905 532.629853 467.281519 518.650467 450.027553 518.650467z" p-id="2697"></path><path d="M693.805696 518.650467c-17.253966 0-31.233352 13.979386-31.233352 31.233352l0 30.470989c0 17.253966 13.979386 31.233352 31.233352 31.233352 17.253966 0 31.233352-13.979386 31.233352-31.233352l0-30.470989C725.039048 532.629853 711.058638 518.650467 693.805696 518.650467z" p-id="2698"></path><path d="M648.940882 660.09492c-14.304797-9.393951-33.592073-5.398964-43.159986 8.763594-0.132006 0.193405-13.614066 19.754926-34.171264 19.754926-19.98824 0-32.423457-18.09717-33.266661-19.368116-9.17087-14.427594-28.254507-18.809391-42.834574-9.770528-14.650675 9.109472-19.155269 28.366048-10.055007 43.016723 11.214413 18.047028 41.96988 48.588625 86.156242 48.588625 43.962258 0 75.104535-30.318516 86.572728-48.222281C667.404396 688.461991 663.216004 669.500127 648.940882 660.09492z" p-id="2699"></path></svg>
            {{ blogInfo.blogLikes }}
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
                  :page-sizes="[6, 12, 18, 24]"
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
      <el-divider></el-divider>
      <el-row>
        <el-input placeholder="请输入内容" v-model="condition.keyWord">
          <i slot="suffix" class="el-input__icon el-icon-search" @click="searchByWord"></i>
        </el-input>
      </el-row>
      <el-row>
        <h1>热门标签</h1>
        <el-divider></el-divider>
      </el-row>
      <el-row>
        <el-button v-for="tag in tagList" @click="searchByTag(tag.tagId)" round size="medium" :type="getRandType()">
          {{ tag.tagName }}({{tag.tagId}})
        </el-button>
      </el-row>
      <el-row>
        <h1>最新发布</h1>
        <el-divider></el-divider>
      </el-row>
      <el-row>
        <div v-for="(blogInfo,index) in blogInfoList" v-if="index < 3">
          <el-link v-if="index < 3" @click="checkBlog(blogInfo.blogId)">{{ blogInfo.blogTitle }}</el-link>
          <br>
        </div>
      </el-row>
      <el-row>
        <h1>点击最多</h1>
        <el-divider></el-divider>
      </el-row>
      <el-row>
        <div v-for="(blogInfo,index) in blogInfoList" v-if="index < 3">
          <el-link  @click="checkBlog(blogInfo.blogId)">{{ blogInfo.blogTitle }}</el-link>
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
        pageSize: 6,
        tagId: '',
        keyWord: '',
        categoryName: this.$route.query.blogCategoryName
      },
      totalSize: 0,
      blogInfoList: [],
      index: 0,
      typeList: ['primary', 'success', 'warning', 'danger', 'info', 'text']
    };
  },
  created() {
    this.openFullScreen2();
    this.getTagList();
    this.handleCurrentChange(1);
    this.getMaxClick();
  },
  methods: {
    getRandType() {
      return this.typeList[Math.floor((Math.random() * 6))] ;
    },
    getMaxClick() {
      let blogInfoList = this.blogInfoList;
      let blogViews = 0;
      if (blogInfoList != null) {
        for (let i = 0; i < blogInfoList; i++) {
          let blogInfo = blogInfoList[i];
          if (blogInfo.blogViews > blogViews) {
            blogViews = blogInfo.blogViews;
            this.index = i;
          }
        }
      }
    },
    checkBlog(blogId) {
      this.$router.push(
          {
            path: '/blogDetail',
            query: {
              blogId: blogId
            }
          })

    },
    searchByCategory(categoryName) {
      this.condition.categoryName = categoryName;
      this.handleCurrentChange(1);
    },
    searchByWord() {
      this.handleCurrentChange(1);
      this.condition.keyWord = "";
    },
    searchByTag(id) {
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
.el-container .el-main .el-row .el-button{
  margin-left: 5px;
  margin-top: 5px;
}

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
