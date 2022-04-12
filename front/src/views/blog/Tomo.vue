<template>
  <div>
    <el-container>
      <el-header>
        <BlogHead/>
      </el-header>
      <el-main style="margin-top: 300px;margin-left: 150px;width: 500px">
        <h1>友链</h1>
        <el-card>
          <el-row v-for="link in linkList" v-if="link.linkType === 0">
            <el-link :href="link.linkUrl" target="_blank">{{link.linkName}}</el-link> -{{link.linkDescription}}
          </el-row>
        </el-card>
        <el-row >
          <h1>推荐</h1>
          <el-card>
            <el-row v-for="link in linkList" v-if="link.linkType === 1">
              <el-link :href="link.linkUrl" target="_blank">{{link.linkName}}</el-link> -{{link.linkDescription}}
            </el-row>
          </el-card>
        </el-row>
        <el-row :gutter="8">
        </el-row>
        <el-row>
          <h1>个人网站</h1>
        </el-row>
        <el-card>
          <el-row v-for="link in linkList" v-if="link.linkType === 2">
            <el-link :href="link.linkUrl" target="_blank">{{link.linkName}}</el-link> -{{link.linkDescription}}
          </el-row>
        </el-card>

        <el-row>
          <h1>链接须知</h1>
        </el-row>
        <el-row>
          欢迎互换友链 ^_^ 不过请确定贵站可以正常运营.
          我的邮箱是 486084544@qq.com ,<br>
          格式要求如下:<br>
          网站名称：微极客<br>
          网站链接：<el-link href="http://www.renrenzi.top/">微极客</el-link><br>
          网站描述：任人子的个人博客<br>
          请保证自己的链接长期有效,不然可能会被清理.
        </el-row>
      </el-main>
      <el-footer class="footCss" style="background-color: rgb(39, 53, 71);height: 120px">
        <BlogFooter/>
      </el-footer>
    </el-container>
  </div>

</template>
<script>
import BlogHead from "../../components/blog/layout/BlogHead";
import BlogFooter from "../../components/blog/layout/BlogFooter";
import {pageLinkList} from "@/api/blogmanager/blogLink";
import qs from "qs";
export default {
  name: 'Nav',
  components: {BlogFooter, BlogHead},
  data() {
    return {
      linkList: []
    };
  },
  created() {
    this.getLinkList();
  },
  methods: {
    getLinkList(){
      pageLinkList(qs.stringify({
        pageNum:1,
        pageSize: 99
      })).then(res => {
        this.linkList = res.data;
      })
    }
  },

}
</script>
<style>
  .image{
    width: 200px;

  }
</style>
