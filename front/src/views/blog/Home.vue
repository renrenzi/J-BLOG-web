<template>

  <div class="home">
    <el-container>
      <el-header height="360px">
        <BlogHead/>
      </el-header>
      <el-main>
      <router-view/>
      </el-main>
      <el-footer class="footCss" style="background-color: rgb(39, 53, 71);height: 120px">
        <BlogFooter/>
      </el-footer>
    </el-container>

  </div>

</template>
<script>
// @ is an alias to /src
import BlogHead from "../../components/blog/layout/BlogHead";
import BlogFooter from "../../components/blog/layout/BlogFooter";
import Index from "../../components/blog/Index";

export default {
  name: 'Home',
  components: {
    BlogFooter,
    BlogHead,
    Index
  }
}
</script>
<style>
.el-container .el-main {
  padding: 0;
}

.el-container .el-footer {
  padding: 0;
}

html, body, #app, .el-container {
  padding: 0px;
  margin: 0px;
  height: 100%;
}

</style>
