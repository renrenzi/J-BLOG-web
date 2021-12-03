<template>
  <el-menu
      :default-active="$route.path"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#3D0000"
      text-color="#fff"
      :router="true"
      active-text-color="#ffd04b">
    <el-menu-item index="/admin">Blog</el-menu-item>
    <el-submenu index="2" class="myName">
      <template slot="title">{{ this.$store.state.adminUser.nickName }}</template>
      <el-menu-item @click="userInfo = true">个人信息</el-menu-item>
      <el-menu-item @click="exchange">切换账号</el-menu-item>
      <el-menu-item @click="exitUser">退出</el-menu-item>
    </el-submenu>
    <el-menu-item index="/" class="myIndex">>前台首页</el-menu-item>
    <el-dialog title="个人信息"
               :visible.sync="userInfo"
               width="35%"
               center>
      <el-row :gutter="10">
        <el-col :span="4">
          头像:
        </el-col>
        <el-col :span="4">
            <el-avatar shape="square"
                       v-if="squareUrl"
                       :size="80"
                       :src="squareUrl"
                       fit="fill">

            </el-avatar>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-col>

        <el-col :span="4" :offset="3">
          <el-upload
              action="#"
              class="avatar-uploader"
              :show-file-list="false"
              :auto-upload="false"
              :on-change="uploadImg"
              :before-upload="beforeAvatarUpload"

          >
            <el-button type="primary">预览</el-button>
          </el-upload>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" :loading="loading" @click="uploadAuthorImg">

            <span v-if="!loading">开始上传</span>
            <span v-else>上 传 中...</span>

          </el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          登录名:
        </el-col>
        <el-col :span="20">
          <el-input v-model="loginForm.loginUserName"></el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          昵称:
        </el-col>
        <el-col :span="20">
          <el-input v-model="loginForm.nickName"></el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          旧密码:
        </el-col>
        <el-col :span="20">
          <el-input v-model="loginForm.oldPassword" type="password"></el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          新密码:
        </el-col>
        <el-col :span="20">
          <el-input v-model="loginForm.loginPassword" type="password"></el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          确认密码:
        </el-col>
        <el-col :span="20">
          <el-input v-model="loginForm.confirmPassword" type="password"></el-input>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
            <el-button @click="userInfo = false">取 消</el-button>
            <el-button type="primary" @click="editAdminInfo">确 定</el-button>
          </span>
    </el-dialog>
  </el-menu>

</template>

<script>
import {editUserInfo, uploadAuthorImg} from "../../../api/blogmanager/admin";
import qs from "qs";

export default {
  name: "Head",
  data() {
    return {
      adminUserId: this.$store.state.adminUser.adminUserId,
      userInfo: false,
      loginForm: {
        loginUserName: this.$store.state.adminUser.loginUserName,
        nickName: this.$store.state.adminUser.nickName,
        oldPassword: '',
        loginPassword: '',
        confirmPassword: ''
      },
      file: {},
      squareUrl: this.$store.state.imgUrl,
      loading: false
    }
  },
  methods: {

    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    editAdminInfo(){
      const _this = this;
      editUserInfo(qs.stringify({
        adminUserId: this.adminUserId,
        loginUserName: this.loginForm.loginUserName,
        nickName: this.loginForm.nickName,
        loginPassword: this.loginForm.loginPassword,
      })).then(res => {
          if (res.code === 2000){
            _this.userInfo = false;
           _this.loginForm.oldPassword= '';
           _this.loginForm.loginPassword= '';
           _this.loginForm.confirmPasswor= '';
           this.$message.success("修改个人信息成功 ==> 请重新登录");

          }
      })
    },
    exchange() {
      this.$store.state.adminUser = null;
      this.$store.state.token = '';
      this.$router.push({path: '/login'})
    },
    exitUser() {
      this.$store.state.adminUser = null;
      this.$store.state.token = '';
      this.$router.push({path: '/login'})
    },
    uploadImg(file) {
      this.file = file;
      this.squareUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      this.squareUrl = URL.createObjectURL(file.raw);
      return isJPG && isLt2M;
    },
    // 上传用户头像
    uploadAuthorImg(){
      const _this = this;
      this.loading = true;
      const formdata = new FormData();
      formdata.append('userImage',this.file.raw);
      formdata.append('adminUserId',this.adminUserId);
      uploadAuthorImg(formdata).then(res => {
        if (res.code === 2000){
          _this.squareUrl = res.data;
          this.$message.success('上传头像成功');
        }else {
          this.$message.error('上传头像失败');
        }
        _this.loading = false;
      })
    }
  }
}
</script>

<style>

</style>
