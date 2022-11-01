<template>
  <el-container >
    <el-header  style="text-align: right;
                font-size: 12px;
                background-color: rgb(61,0,0);
                margin: 0;"
                >
      <el-row type="flex" justify="end" style="margin-top: 0">
        <Head/>
      </el-row>
    </el-header>

      <Main/>



    <el-footer  style="background-color: rgb(238, 241, 246);" height="20px">
      <div class="el-login-footer">
       <span>Copyright © 2021-2022 RenRenZi All Rights Reserved.</span>
      <el-link href="https://beian.miit.gov.cn/#/Integrated/recordQuery">闽ICP备2022003846号</el-link>
    Powered by <el-link href="#" target="_blank">任人子</el-link>
      </div>
    </el-footer>

  </el-container>

</template>

<script>
import Head from "../../../components/layout/Head";
import Main from "../../../components/layout/Main";

export default {
  name: "admin",
  components: {
    Head,
    Main
  }
}
</script>

<style scoped>
#main{
  padding: 0;
}
.el-login-footer {
  height: 20px;
  line-height: 20px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #0c0c0c;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
