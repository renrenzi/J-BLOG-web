<template>
  <el-container >
    <el-header  style="text-align: right;
                font-size: 12px;
                background-color: rgb(61,0,0);
"
    >

      <el-row type="flex" justify="end" style="margin-top: 0">
        <Head/>
      </el-row>
    </el-header>
    <el-main>
      <Main/>
    </el-main>
    <el-footer height="100px" style="background-color: rgb(238, 241, 246);">
      <div class="el-login-footer">
        <span >Copyright © 2021 RenRenZi All Rights Reserved.</span>
      </div>
    </el-footer>
  </el-container>
</template>

<script>
import Head from "../../components/admin/layout/Head";
import Main from "../../components/admin/layout/Main";

export default {
  name: "admin",
  components: {
    Head,
    Main

  }
}
</script>

<style scoped>
.el-container .el-header{
  padding: 0;
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
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
